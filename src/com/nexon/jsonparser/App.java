package com.nexon.jsonparser;

import com.nexon.jsonparser.parser.JSONParser_Impl;

public class App {

	public static void main(String[] args) {
		App mainApp = new App();
		mainApp.init();
	}

	private void init() {
		JSONParser_Impl jsonPasrer = new JSONParser_Impl();
		jsonPasrer.init();
		jsonPasrer.setSource("C:\\a\\test.json");
		jsonPasrer.parse();
	}
	
}
