public class Main {
    public static void main(String[] arg) {
        Scanner scanner = new Scanner(arg[0]);
        Parser parser = new Parser(scanner);
        parser.Parse();
        
        if(parser.errors.count > 0){
            System.out.println("\n" + parser.errors.count + " errors found!");
        }
        
        /*
        CodeGenerator c = new CodeGenerator();
        c.initFile();
        c.insert("34");
        c.printVal(0);
        c.closeFile();
        */
    }   
} 