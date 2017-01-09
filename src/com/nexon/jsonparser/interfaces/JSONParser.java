package com.nexon.jsonparser.interfaces;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface JSONParser {
	
	void setSource(String jsonFilePath);
	void parse();
	void init();
	void nextToken();
	List<Object> createArray();
	Map<String, Object> createMapObject();
	int peekStatus(LinkedList<Integer> statusStack);
}
