package com.nexon.jsonparser.interfaces;

import com.nexon.jsonparser.bean.CToken;

public interface JSONParser {
	
	JSONObject getJSONObject(String key);
	void setSource(String jsonFilePath);
	void parse();
	void init();
	void nextToken();
}
