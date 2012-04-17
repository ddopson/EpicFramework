package com.epic.framework.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.epic.framework.vendor.org.json.simple.JSONAware;
import com.epic.framework.vendor.org.json.simple.JSONObject;
import com.epic.framework.vendor.org.json.simple.JSONStreamAware;
import com.epic.framework.vendor.org.json.simple.JSONValue;
import com.epic.framework.vendor.org.json.simple.JSONException;
import com.epic.framework.vendor.org.json.simple.JSONWriter;
import com.epic.framework.vendor.org.json.simple.parser.JSONParser;

public class JSON {
	private static JSONParser parser = new JSONParser();
	
	/**
	 * Parse JSON text into java object from the input source. 
	 * Please use parseWithException() if you don't want to ignore the exception.
	 * 
	 * @see com.epic.framework.vendor.org.json.simple.parser.JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @param in
	 * @return Instance of the following:
	 *	org.json.simple.JSONObject,
	 * 	org.json.simple.JSONArray,
	 * 	java.lang.String,
	 * 	java.lang.Number,
	 * 	java.lang.Boolean,
	 * 	null
	 * 
	 */

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
	
	public static String stringify(Object obj) {
		StringWriter sw = new StringWriter();
		try {
			JSONWriter.write(obj, sw);
		} catch (IOException e) {
			// this should NEVER happen ...
			throw new RuntimeException(e);
		}
		return sw.toString();
	}
	
    /**
     * Encode an object into JSON text and write it to out.
     * <p>
     * If this object is a Map or a List, and it's also a JSONStreamAware or a JSONAware, JSONStreamAware or JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from writeJSONString(Writer) of a class that implements both JSONStreamAware and (Map or List) with 
     * "this" as the first parameter, use JSONObject.writeJSONString(Map, Writer) or JSONArray.writeJSONString(List, Writer) instead. 
     * 
     * @see com.epic.framework.vendor.org.json.simple.JSONObject#writeJSONString(Map, Writer)
     * @see com.epic.framework.vendor.org.json.simple.JSONArray#writeJSONString(List, Writer)
     * 
     * @param value
     * @param writer
     */
	public static void write(Object value, Writer out) throws IOException {
		JSONWriter.write(value, out);
	}
}
