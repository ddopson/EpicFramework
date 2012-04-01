package com.epic.framework.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.epic.framework.vendor.org.json.simple.JSONObject;
import com.epic.framework.vendor.org.json.simple.JSONValue;
import com.epic.framework.vendor.org.json.simple.JSONException;
import com.epic.framework.vendor.org.json.simple.parser.JSONParser;

public class JSON {
	private static JSONParser parser = new JSONParser();
	
	public static Object parse(String data) throws JSONException {
		System.out.println("Parsing JSON: " + data);
		Object ret = parser.parse(data);
		return ret;
	}
	
	public static Object parse(Reader data) throws JSONException, IOException {
		return parser.parse(data);
	}

	public static Object parse(InputStream data) throws JSONException, IOException {
		return parser.parse(new InputStreamReader(data));
	}
	
//	public static String stringify(Object obj) {
//		StringWriter sw = new StringWriter();
//		try {
//			JSONValue.writeJSONString(obj, sw);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		return sw.toString();
//	}
	
	public static void stringify(Object obj, Writer out) throws IOException {
		JSONValue.writeJSONString(obj, out);
	}
}
