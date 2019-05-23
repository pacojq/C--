package code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import syntax.Program;

public class CodeGeneration {

	private Program program;
	
	public CodeGeneration(Program program) {
		this.program = program;
	}
	
	
	public void run(String input) {
		
		String output = "uo257181-output.txt";
		
		this.program.accept(new OffsetVisitor(), null);
		this.program.accept(new ExecuteVisitor(), null);
		
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
			
			writer.newLine();
			writer.write("#source \"" + input + "\"\n");
			writer.newLine();
			writer.write(this.program.cgCode);	
			
		}
		catch (IOException e) {
			System.err.println(e);
		}
	    
	}
	
}
