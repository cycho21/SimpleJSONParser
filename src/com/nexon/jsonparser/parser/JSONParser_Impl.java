package com.nexon.jsonparser.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nexon.jsonparser.bean.JSONArray_Impl;
import com.nexon.jsonparser.bean.JSONObject_Impl;
import com.nexon.jsonparser.bean.WrappedToken;
import com.nexon.jsonparser.conf.Configuration;
import com.nexon.jsonparser.divide.Divisor;
import com.nexon.jsonparser.interfaces.JSONParser;
import com.nexon.jsonparser.jj.LexerJJ;
import com.nexon.jsonparser.jj.Token;

public class JSONParser_Impl implements JSONParser {

	private WrappedToken token;
	private int status;
	private LexerJJ lexer;
	private Divisor analysis;

	public JSONParser_Impl() {
	}
	
	public int peekStatus(LinkedList<Integer> statusStack) {
		if (statusStack.size() == 0) return -1;
		else return statusStack.getFirst();
	}
	
	@Override
	public void parse() {
		LinkedList<Integer> statusStack = new LinkedList<Integer>();
		LinkedList<Object> valStack = new LinkedList<Object>();
		
		token = new WrappedToken(-3, null);
		
		do {
			nextToken();
			
			System.out.println(token.getVal() + " : " + token.getType());
			System.out.println(status);
			System.out.println("-------------------");
			
			switch (status) {
			/*
			 * INIT case start
			 */
			case Configuration.INIT:
				switch (token.getType()) {
				
				case WrappedToken.PRIMITIVE_VALUE:
					status = Configuration.FINISHED;
					statusStack.addFirst(status);
					valStack.addFirst(token.getVal());
					break;

				case WrappedToken.LEFT_BRACE:
					status = Configuration.IN_OBJECT;
					statusStack.addFirst(status);
					valStack.addFirst(createMapObject());
					break;
				
				case WrappedToken.LEFT_SQUARE:
					status = Configuration.IN_ARRAY;
					statusStack.addFirst(status);
					valStack.addFirst(createArray());
					break;
					
				default:
					status = Configuration.ERROR;
					break;
				}
				break;
			/*
			 * INIT case end
			 */
				
				
			/*
			 * IN_OBJECT case start 
			 */
			case Configuration.IN_OBJECT:
				switch (token.getType()) {
				
				case WrappedToken.COMMA: // Do nothing
					break;
					
				case WrappedToken.PRIMITIVE_VALUE:
					status = Configuration.PASSING_THROUGH; 
					String key = (String) token.getVal();
					valStack.addFirst(key);
					statusStack.addFirst(status);
					break;
					
				case WrappedToken.RIGHT_BRACE:
					if (valStack.size() > 1) {
						statusStack.removeFirst();
						valStack.removeFirst();
						status = peekStatus(statusStack);
					} else {
						status = Configuration.FINISHED;
					}
					break;
					
				default:
					break;
				}
			/*
			 * IN_OBJECT case end 
			 */
				
			/*
			 * PASSING_THROUGH case start
			 */
			case Configuration.PASSING_THROUGH:
				switch (token.getType()) {
				
				case WrappedToken.COLON:
					break;
					
				case WrappedToken.PRIMITIVE_VALUE:
					statusStack.removeFirst();
					String key = (String) valStack.removeFirst();
					Map<String, Object> parent = (Map) valStack.getFirst();
					parent.put(key, token.getVal());
					status = statusStack.peekFirst();
					break;
					
				case WrappedToken.LEFT_SQUARE:
					statusStack.removeFirst();
					Object temp = valStack.removeFirst();
					key = (String) valStack.removeFirst();
					parent = (Map<String, Object>) valStack.getFirst();
					List nArr = createArray();
					parent.put(key, nArr);
					status = Configuration.IN_ARRAY;
					statusStack.addFirst(status);
					valStack.addFirst(nArr);
					break;
					
				case WrappedToken.LEFT_BRACE:
					statusStack.removeFirst();
					key = (String) valStack.removeFirst();
					parent = (Map<String, Object>) valStack.getFirst();
					Map nObj = createMapObject();
					parent.put(key, nObj);
					status = Configuration.IN_OBJECT;
					statusStack.addFirst(status);
					valStack.addFirst(nObj);
					break;
					
				default:
					break;
				}
			/*
			 * PASSING_THROUGH case end
			 */
				
			/*
			 * IN_ARRAY case start
			 */
			case Configuration.IN_ARRAY:
				switch (token.getType()) {

				case WrappedToken.COMMA:
					break;

				case WrappedToken.PRIMITIVE_VALUE:
					List val = (List) valStack.getFirst();
					val.add(token.getVal());
					break;
					
				case WrappedToken.RIGHT_SQUARE:
					status = Configuration.FINISHED;
					break;
					
				case WrappedToken.LEFT_BRACE:
					val = (List) valStack.getFirst();
					Map<String, Object> nObj = createMapObject();
					val.add(nObj);
					status = Configuration.IN_OBJECT;
					statusStack.addFirst(status);
					valStack.addFirst(nObj);
					break;
					
				case WrappedToken.LEFT_SQUARE:
					val = (List) valStack.getFirst();
					List nArr = createArray();
					val.add(nArr);
					status = Configuration.IN_ARRAY;
					statusStack.addFirst(status);
					valStack.addFirst(nArr);
					break;
					
				default:
					break;
				}
				
			default:
				break;
			}
		} while (token.getType() != WrappedToken.EOF && token != null);
		
	}

	private List<?> createArray() {
		return new JSONArray_Impl();
	}

	@Override
	public void setSource(String jsonFilePath) {
		try {
			lexer = new LexerJJ(new FileReader(jsonFilePath));
		} catch (FileNotFoundException e) {
			System.out.println("JSON File Path Error");
		}
	}

	@Override
	public void init() {
		System.out.println("JSONParser initialized...");
		analysis = new Divisor();
	}

	@Override
	public void nextToken() {
		Token obj = lexer.getNextToken();
		if (obj.image != "") {
			token.setVal(obj.image);
			token.setType(analysis.whichToken(obj.image));
		} else {
			token.setVal("EOF");
			token.setType(WrappedToken.EOF);
		}
	}
	
	private Map<String, Object> createMapObject() {
		return new JSONObject_Impl();
	}

}
