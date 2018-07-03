import java.util.*;

public class SymbolTable{
    
    Hashtable varNames;
    
    public SymbolTable(){
        varNames = new Hashtable();    
    }
    
    public String insert(String name){
        varNames.put(name, name);
        return name;
    }
    
    public String find(String name){
        String ret = null;
        if(varNames.get(name) != null){
            ret = (String)varNames.get(name);
        }
        return ret;
    }
    
}