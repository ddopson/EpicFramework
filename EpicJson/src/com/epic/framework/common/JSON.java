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
	public static void main(String[] args) throws JSONException {
		parser.DEBUG = true;
		// TODO: can we lax up the syntax to support unquoted keys???
//		Object o = decode("{\"a\": \"foo bar\", \"b\": 2   ,,,,,  ,\" c\":8}");
		JSONObject o = new JSONObject();
		o.put("foo", "bar");
		o.put(1, 2);
//		Object o = decode("true\n\n\nfalse");
		System.out.println(JSON.stringify(o));
	}
	
	public static Object parse(String data) throws JSONException {
		Object ret = parser.parse(data);
		return ret;
	}
	
	public static Object parse(Reader data) throws JSONException, IOException {
		return parser.parse(data);
	}

	public static Object parse(InputStream data) throws JSONException, IOException {
		return parser.parse(new InputStreamReader(data));
	}
	
	public static String stringify(Object obj) {
		StringWriter sw = new StringWriter();
		try {
			JSONValue.writeJSONString(obj, sw);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sw.toString();
	}
	
	public static void stringify(Object obj, Writer out) throws IOException {
		JSONValue.writeJSONString(obj, out);
	}
}
