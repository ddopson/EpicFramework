/*
 * $Id: JSONValue.java,v 1.1 2006/04/15 14:37:04 platform Exp $
 * Created on 2006-4-15
 */
package com.epic.framework.vendor.org.json.simple;

/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONValue {
	public final Object value;
	public final int type;
	
	public static final int TYPE_OBJECT    = 1;
	public static final int TYPE_ARRAY     = 2;
	public static final int TYPE_STRING    = 3;
	
	public static final int TYPE_NUMBER    = 4;
	public static final int TYPE_INTEGER   = TYPE_NUMBER | (1024 * 1);
	public static final int TYPE_LONG      = TYPE_NUMBER | (1024 * 2);
	public static final int TYPE_FLOAT     = TYPE_NUMBER | (1024 * 3);
	public static final int TYPE_DOUBLE    = TYPE_NUMBER | (1024 * 4);
	public static final int TYPE_BYTE      = TYPE_NUMBER | (1024 * 4);
	
	public static final int TYPE_NULL      = -1;
	public static final int TYPE_UNDEFINED = -2;
	
	public JSONValue(JSONObject value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_OBJECT;
	}

	public JSONValue(JSONArray value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_ARRAY;
	}
	
	public JSONValue(String value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_STRING;
	}
	
	public JSONValue(Integer value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_INTEGER;
	}
	
	public JSONValue(Long value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_LONG;
	}
	
	public JSONValue(Double value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_DOUBLE;
	}
	
	public JSONValue(Float value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_FLOAT;
	}
	
	public JSONValue(Object value) {
		this.value = value;
		this.type = (value == null) ? TYPE_NULL : TYPE_UNDEFINED;
	}

	public int getType() {
		return this.type & 1023;
	}
}
