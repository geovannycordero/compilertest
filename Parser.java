
public class Parser {
	public static final int _EOF = 0;
	public static final int _var = 1;
	public static final int _number = 2;
	public static final int maxT = 10;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	public SymbolTable tab;
public CodeGenerator gen;

/* ************ global fields and methods ************ */

void print(String message){
    System.out.println(message);
}



	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void Sample() {
		Statement();
		while (la.kind == 3) {
			Prints();
		}
	}

	void Statement() {
		int nVars, nIn; 
		nVars = Variables();
		Expect(5);
		nIn = Method();
		Expect(4);
		if(nVars != nIn) SemErr("El par'ametro no coincide con el n'umero de variables indicado.");
	}

	void Prints() {
		Expect(3);
		Expect(1);
		Expect(4);
	}

	int  Variables() {
		int  numVars;
		numVars = 0; 
		vName = VarName();
		numVars++; 
		while (la.kind == 6) {
			Get();
			vName = VarName();
			numVars++; 
		}
		return numVars;
	}

	int  Method() {
		int  n;
		n = 0; 
		Expect(7);
		Expect(8);
		Expect(2);
		n = Integer.parseInt(t.val);
		if(n > 6) SemErr("El par'ametro debe ser menor o igual a cinco."); 
		Expect(9);
		return n;
	}

	String  VarName() {
		String  varName;
		Expect(1);
		varName = t.val; 
		return varName;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Sample();
		Expect(0);

	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "var expected"; break;
			case 2: s = "number expected"; break;
			case 3: s = "\"print\" expected"; break;
			case 4: s = "\";\" expected"; break;
			case 5: s = "\"=\" expected"; break;
			case 6: s = "\",\" expected"; break;
			case 7: s = "\"metodo\" expected"; break;
			case 8: s = "\"(\" expected"; break;
			case 9: s = "\")\" expected"; break;
			case 10: s = "??? expected"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
