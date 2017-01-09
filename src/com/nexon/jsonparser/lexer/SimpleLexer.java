package com.nexon.jsonparser.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nexon.jsonparser.bean.CToken;

public class SimpleLexer {

	public SimpleLexer() {
	}

	public int whichToken(Object obj) {
		Pattern primitiveValue = Pattern.compile("[a-zA-Z0-9]");
		Matcher m = primitiveValue.matcher(String.valueOf(obj));

		if (m.find())
			return CToken.PRIMITIVE_VALUE;
		
		switch (String.valueOf(obj)) {
			case "{":
				return CToken.LEFT_BRACE;
			case "}":
				return CToken.RIGHT_BRACE;
			case "[":
				return CToken.LEFT_SQUARE;
			case "]":
				return CToken.RIGHT_SQUARE;
			case ",":
				return CToken.COMMA;
			case ":":
				return CToken.COLON;
			case "\"":
				return CToken.QUOTE;
			default:
				return -1;
		}
	}

}
