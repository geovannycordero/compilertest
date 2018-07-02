
public class CodeGenerator{
	int pc;
	int progStart;

	public CodeGenerator(){
		System.out.println("CodeGenerator");
	}
	
	public void Emit(int op){
		System.out.println("CodeGenerator Emit op");
	}
	public void Emit(int op, int val){
		System.out.println("CodeGenerator Emit op, val");
	}
	public void Patch(int adr, int val){
		System.out.println("CodeGenerator Patch adr, val");
	}
	
}
