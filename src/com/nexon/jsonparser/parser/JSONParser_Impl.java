package com.nexon.jsonparser.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.nexon.jsonparser.bean.JSONObject_Impl;
import com.nexon.jsonparser.bean.CToken;
import com.nexon.jsonparser.conf.Configuration;
import com.nexon.jsonparser.interfaces.JSONObject;
import com.nexon.jsonparser.interfaces.JSONParser;
import com.nexon.jsonparser.jj.LexerJJ;
import com.nexon.jsonparser.jj.Token;
import com.nexon.jsonparser.lexer.SimpleLexer;

public class JSONParser_Impl implements JSONParser {

	private CToken token;
	private int status;
	private LexerJJ lexer;
	private SimpleLexer analysis;

	public JSONParser_Impl() {
	}
	
	@Override
	public void parse() {
		ArrayDeque<Integer> statusStack = new ArrayDeque<Integer>();
		ArrayDeque<Object> valStack = new ArrayDeque<Object>();
		Queue<String> tempQueue = new LinkedList<String>();
		
		token = new CToken(-3, null);
		
		do {
			nextToken();
			System.out.println(token.getVal() + " : " + token.getType());
			switch (status) {
			/*
			 * INIT case start
			 */
			case Configuration.INIT:
				switch (token.getType()) {
				
				case CToken.PRIMITIVE_VALUE:
					status = Configuration.IN_VALUE;
					statusStack.addFirst(status);
					
					
					
					valStack.addFirst(token);
					break;

				case CToken.LEFT_BRACE:
					status = Configuration.IN_OBJECT;
					statusStack.addFirst(status);
					valStack.addFirst(createMapObject());
					break;
				
				case CToken.LEFT_SQUARE:
					status = Configuration.IN_ARRAY;
					statusStack.addFirst(status);
					valStack.addFirst(createMapObject());
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
				
				case CToken.COMMA: // Do nothing
					break;
					
				case CToken.PRIMITIVE_VALUE:
					 
				default:
					break;
				}
			default:
				break;
			}
		} while (token.getType() != CToken.EOF && token != null);
		
	}

	@Override
	public JSONObject getJSONObject(String key) {
		// TODO Auto-generated method stub
		return null;
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
		analysis = new SimpleLexer();
	}

	@Override
	public void nextToken() {
		Token obj = lexer.getNextToken();
		if (obj.image != "") {
			token.setVal(obj.image);
			token.setType(analysis.whichToken(obj.image));
		} else {
			token.setVal("\n");
			token.setType(CToken.EOF);
		}
	}
	
	private Map createMapObject() {
		return new JSONObject_Impl();
	}

}
