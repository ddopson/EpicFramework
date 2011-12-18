package com.epic.framework.util;

import java.util.HashMap;

public class EpicHttpResponse {
	public int responseCode;
	public String body;
	public HashMap<String, String> headers;
	
	public EpicHttpResponse(int responseCode, String body, HashMap<String, String> headers) {
		this.responseCode = responseCode;
		this.body = body;
		this.headers = headers;
	}
}