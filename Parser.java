
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
public CodeGenerator gen = new CodeGenerator();



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
		tab = new SymbolTable(); /* CodeGenerator gen = new CodeGenerator(); */ gen.initFile(); 
		Statement();
		while (la.kind == 9) {
			Prints();
		}
		gen.closeFile(); 
	}

	void Statement() {
		int nVars, nIn; 
		nVars = Variables();
		Expect(3);
		nIn = Method();
		Expect(4);
		if(nVars != nIn) SemErr("El par'ametro no coincide con el n'umero de variables indicado.");
	}

	void Prints() {
		Expect(9);
		Expect(1);
		if(tab.find(t.val) == null){ SemErr("Variable " + t.val + " no existe."); }
		else{ gen.printVal(t.val); } 
		Expect(4);
	}

	int  Variables() {
		int  numVars;
		numVars = 0; String vName, vName0; 
		Expect(1);
		numVars++; tab.insert(t.val); gen.insert(t.val); 
		while (la.kind == 5) {
			Get();
			Expect(1);
			if(tab.find(t.val) != null){ SemErr("Nombre de variable " + t.val + " ya existe.");}
			else{ tab.insert(t.val); numVars++; gen.insert(t.val); }
			
		}
		return numVars;
	}

	int  Method() {
		int  n;
		n = 0; 
		Expect(6);
		Expect(7);
		Expect(2);
		try{ n = Integer.parseInt(t.val); } catch(Exception e){}
		if(n > 6 || n < 1) SemErr("El par'ametro debe ser mayor a cero y menor a seis.");
		
		Expect(8);
		return n;
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
			case 3: s = "\"=\" expected"; break;
			case 4: s = "\";\" expected"; break;
			case 5: s = "\",\" expected"; break;
			case 6: s = "\"metodo\" expected"; break;
			case 7: s = "\"(\" expected"; break;
			case 8: s = "\")\" expected"; break;
			case 9: s = "\"print\" expected"; break;
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
