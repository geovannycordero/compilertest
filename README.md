Universidad de Costa Rica
Geovanny Cordero Valverde


compilertest

Archivos:
	Coco.jar
	Scanner.frame
	Parser.frame
	Sample.atg
	Main.java
	SymbolTable.java
	CodeGenerator.java
	out/run
	out/file.s

Instruciones:
	Desde consola, ejecutar el archivo bash llamado compile, que tiene los comandos necesarios para compilar el archivo .atg, y los archivos .java generados. Si quiere compilar sólo el archivo .atg, basta con correr en consola el comando java -jar Coco.jar Sample.atg. 
	Luego, dirijase a la carpeta out/ e corra el archivo bash llamado run, que contiene los comandos necesarios para correr el main, que va a generar un archivo llamado file.s que es el que contiene el código MIPS y el comando para ejecutar este archivo file.s . Es necesario rescatar que el archivo file.s se corre desde consola con el programa spim.
	
