import java.io.*;
import java.util.*;

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
		catch(Exception e){}
	}
	
	public void initFile(){
		writer.println("# Geovanny Cordero Valverde\n# B42057\n\n");
		writer.println("	.data\n\n	.text\n		start:\n");
	}
	
	public void closeFile(){
		writer.println("			li  $v0, 10     # 10 is the exit syscall.\n			syscall\n");
		writer.println("main:\n		j start");
		writer.close();
	}
	
	public void insert(String nVariable){
		registers.put(nVariable, rc);
		writer.println("			li $v0, 5\n			syscall			# insert " + nVariable);
		writer.println("			move $t" + rc + ", $v0\n");
		rc++;
	}

	public void printVal(String varName){
		if(registers.get(varName) != null){
            writer.println("			move $a0, $t" + registers.get(varName));
			writer.println("			li $v0, 1\n			syscall			# print "  + varName + "\n");
        }
		else{
			System.out.println("Variable " + varName + " no usada. Imposible imprimir.");
		}
	}
}
