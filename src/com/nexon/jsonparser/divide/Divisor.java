package com.nexon.jsonparser.divide;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nexon.jsonparser.bean.WrappedToken;

public class Divisor {

	public Divisor() {
	}

	public int whichToken(Object obj) {
		Pattern primitiveValue = Pattern.compile("[a-zA-Z0-9]");
		Matcher m = primitiveValue.matcher(String.valueOf(obj));

		if (m.find())
			return WrappedToken.PRIMITIVE_VALUE;
		
		switch (String.valueOf(obj)) {
			case "{":
				return WrappedToken.LEFT_BRACE;
			case "}":
				return WrappedToken.RIGHT_BRACE;
			case "[":
				return WrappedToken.LEFT_SQUARE;
			case "]":
				return WrappedToken.RIGHT_SQUARE;
			case ",":
				return WrappedToken.COMMA;
			case ":":
				return WrappedToken.COLON;
			case "\"":
				return WrappedToken.QUOTE;
			default:
				return -1;
		}
	}

}
