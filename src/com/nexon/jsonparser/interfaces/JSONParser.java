package com.nexon.jsonparser.interfaces;

public interface JSONParser {
	
	void setSource(String jsonFilePath);
	void parse();
	void init();
	void nextToken();
}
