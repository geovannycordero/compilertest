import java.io.*;

public class CodeGenerator{
	int rc;
	Hashtable registers;
	PrintWriter writer;
	
	public CodeGenerator(){
		try{
			rc = 0;
			writer = new PrintWriter("file.s", "UTF-8");
			registers = new Hashtable();
		}
		catch(Exception e){
		}
	}
	
	public void initFile(){
		writer.println("# Geovanny Cordero Valverde\n# B42057\n\n");
			writer.println("	.data\nvalue .word 0\n\n	.text\n		start:\n");
	}
	
	public void closeFile(){
		writer.println("	main:\n		j start");
		writer.close();
	}
	
	public void insert(String nVariable){
		registers.put(nVariable, rc);
		writer.println("		li t" + rc + ", " + val + "\n");
		writer.println("		li $v0, 5\n		syscall");
		writer.println("		sw $v0, value\n		lw $t" + rc + ", value");
		rc++;
	}

	public void printVal(String varName){
		if(varNames.get(name) != null){
            writer.println("		li $a0, t" + (String)varNames.get(varName));
			writer.println("		li $v0, 4\n		syscall");
        }
		else{
			System.out.println("Variable " + varName + " no usada. Imposible imprimir.");
		}
	}
}
