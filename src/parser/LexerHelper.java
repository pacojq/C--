package parser;

public class LexerHelper {
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	
	public static char lexemeToChar(String str) {
		
		// When we have 'a'
		if (str.length() == 3)
			return str.toCharArray()[1];
		
		// When we have '\t'
		switch (str) {
			case "'\\t'": return '\t';
			case "'\\n'": return '\n';
			case "'\\r'": return '\r';
			case "'\''": return '\'';
			case "'\\'": return '\\';
		}
		
		// When we have '\126'
		str = str.replaceAll("'", "");
		str = str.substring(1);		
		return (char) Integer.parseInt(str);
	}
	
	
	public static double lexemeToReal(String str) {
		try {			
			String[] split = str.toLowerCase().split("e");
			
			double numb = Double.parseDouble(split[0]);
			if (split.length == 1)
				return numb;
			
			return numb * Math.pow(10, Double.parseDouble(split[1]));
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}
	
}
