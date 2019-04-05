package errors;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
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
		errors = new ArrayList<ErrorType>();
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
		String header = String.format("[ %s Error(s) found ]\n", errors.size()); 
		stream.println(header);
		
		errors.sort(new ErrorComparator());
		for (ErrorType e : errors)
			stream.println(e.toString());
	}
	
	
	
	
	
	private class ErrorComparator implements Comparator<ErrorType> {
		@Override
		public int compare(ErrorType arg0, ErrorType arg1) {
			return arg0.getLine() - arg1.getLine();
		}
	}
}
