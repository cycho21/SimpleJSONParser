package com.nexon.jsonparser.bean;

import java.util.HashMap;

import com.nexon.jsonparser.interfaces.JSONArray;
import com.nexon.jsonparser.interfaces.JSONObject;

public class JSONObject_Impl extends HashMap<String, Object> implements JSONObject {

	private static final long serialVersionUID = 1L;

	public JSONObject_Impl() {
	}

	@Override
	public JSONObject getJSONObject() {
		return null;
	}

	@Override
	public JSONArray getJSONArray() {
		return null;
	}

}
