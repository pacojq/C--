package errors;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class ErrorHandler {

	
	private List<ErrorType> errors;
	
	
	private static ErrorHandler instance;
	public static ErrorHandler getInstance() {
		if (instance == null)
			instance = new ErrorHandler();
		return instance;
	}
	
	private ErrorHandler() {
		errors = new LinkedList<ErrorType>();
	}
	
	
	
	public void clear() {
		errors.clear();
	}
	
	public boolean anyError() {
		return !errors.isEmpty();
	}
	
	public ErrorType raiseError(int line, int column, String message) {
		ErrorType error = new ErrorType(line, column, message);
		errors.add(error);
		return error;
	}
	
	public void showErrors(PrintStream stream) {
		for (ErrorType e : errors)
			stream.println(e.toString());
	}
}
