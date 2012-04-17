package com.epic.framework.vendor.org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class JSONWriter {
	private final Writer out;
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
	
	public JSONWriter(Writer out) {
		this.out = out;
	}
	
	public void write(Object value) throws IOException {
		JSONWriter.write(value, this.out);
	}
	
	public static void write(Object value, Writer out) throws IOException {
		if(value == null){
			out.write("null");
		} else if((value instanceof JSONAware)) {
			out.write(((JSONAware)value).toJSONString());
		} else if((value instanceof JSONStreamAware)) {
			((JSONStreamAware)value).writeJSONString(out);
		} else if(value instanceof String) {	
            out.write('"');
            String s = (String)value;
    		for(int i=0;i<s.length();i++){
    			char ch=s.charAt(i);
    			switch(ch){
    			case '"':
    				out.write("\\\"");
    				break;
    			case '\\':
    				out.write("\\\\");
    				break;
    			case '\b':
    				out.write("\\b");
    				break;
    			case '\f':
    				out.write("\\f");
    				break;
    			case '\n':
    				out.write("\\n");
    				break;
    			case '\r':
    				out.write("\\r");
    				break;
    			case '\t':
    				out.write("\\t");
    				break;
    			case '/':
    				out.write("\\/");
    				break;
    			default:
                    //Reference: http://www.unicode.org/versions/Unicode5.1.0/
    				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
    					String ss=Integer.toHexString(ch);
    					out.write("\\u");
    					for(int k=0;k<4-ss.length();k++){
    						out.write('0');
    					}
    					out.write(ss.toUpperCase());
    				}
    				else{
    					out.write(ch);
    				}
    			}
    		}
            out.write('"');
		} else if(value instanceof Double) {
			if(((Double)value).isInfinite() || ((Double)value).isNaN()) {
				out.write("null");
			} else {
				out.write(value.toString());
			}
		} else if(value instanceof Float) {
			if(((Float)value).isInfinite() || ((Float)value).isNaN()) {
				out.write("null");
			} else {
				out.write(value.toString());
			}
		} else if(value instanceof Number) {
			out.write(value.toString());
		} else if(value instanceof Boolean) {
			out.write(value.toString());
		} else if(value instanceof Map) {
			Map<?,?> map = (Map<?,?>) value;
			out.write('{');
			boolean first = true;
			for(Object key : map.keySet()) {
	            if(first) {
	            	first = false;
	            } else {
	            	out.write(',');
	            }
	            write(String.valueOf(key), out);
	            out.write(':');
				write(map.get(key), out);
			}
			out.write('}');
		} else if(value instanceof List) {
			List<?> list = (List<?>)value;
	        out.write('[');
	        boolean first = true;
	        for(Object el : list) {
	            if(first) {
	            	first = false;
	            } else {
	            	out.write(',');
	            }
	            write(el, out);
			}
			out.write(']');
		} else {
			out.write(value.toString());
		}
	}
}
