import java.io.*;
import java.util.*;

public class CodeGenerator{
	int rc;
	Hashtable registers;
	PrintWriter writer;
    Enumeration names;
    String str;
	
	public CodeGenerator(){
		try{
			rc = 0;
			writer = new PrintWriter("file.s", "UTF-8");
			registers = new Hashtable();
		}
		catch(Exception e){
		}
	}
	
	
	public void ver(){
		names = registers.keys();
	      
	      while(names.hasMoreElements()) {
	         str = (String) names.nextElement();
	         System.out.println(str + ": " + registers.get(str));
	      }        
	      System.out.println();
	}
	
	public void initFile(){
		try{
			writer.println("# Geovanny Cordero Valverde\n# B42057\n\n");
			writer.println("	.data\n\n	.text\n		start:\n");
		}
		catch(Exception e){}
	}
	
	public void closeFile(){
		try{
			
			writer.println("			li  $v0, 10     # 10 is the exit syscall.\n			syscall\n");
			writer.println("main:\n		j start");
			writer.close();
		}
		catch(Exception e){}
	}
	
	public void insert(String nVariable){
		try{
			registers.put(nVariable, rc);
			writer.println("			li $v0, 5\n			syscall			# insert " + nVariable);
			writer.println("			move $t" + rc + ", $v0\n");
			rc++;
		}
		catch(Exception e){}
	}

	public void printVal(String varName){
		if(registers.get(varName) != null){
			//try{
	            writer.println("			move $a0, $t" + registers.get(varName));
				writer.println("			li $v0, 1\n			syscall			# print "  + varName + "\n");
			//}
			//catch(Exception e){}
        }
		else{
			System.out.println("Variable " + varName + " no usada. Imposible imprimir.");
		}
	}
}
