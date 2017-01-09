package com.nexon.jsonparser.bean;

public class WrappedToken {
	
	/*
	 * Declaration of tokens...
	 */
	public static final int INIT = -2; 
	public static final int EOF = -1;
	public static final int PRIMITIVE_VALUE = 1;
	public static final int LEFT_BRACE = 2;
	public static final int RIGHT_BRACE = 3;
	public static final int LEFT_SQUARE = 4;
	public static final int RIGHT_SQUARE = 5;
	public static final int COMMA = 6;
	public static final int COLON = 7;
	public static final int QUOTE = 8;
	
	private int type = INIT;
	private Object val;
	
	
	
	public WrappedToken() {
		super();
	}

	public WrappedToken(int type, Object value) {
		this.type = type;
		this.val = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}
	
}
