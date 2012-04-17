/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package com.epic.framework.vendor.org.json.simple;

import java.util.HashMap;
import java.util.Map;

import com.epic.framework.common.JSON;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONObject extends HashMap<String, Object> {

	private static final long serialVersionUID = -503443796854799292L;

	public JSONObject() {
		super();
	}
	
	/**
	 * Allows creation of a JSONObject from a Map. After that, both the
	 * generated JSONObject and the Map can be modified independently.
	 *
	 * @param map
	 */
	public JSONObject(Map<String, Object> map) {
		super(map);
	}


	public String toString(){
		return JSON.stringify(this);
	}


	public String getString(String key) throws JSONException {
		Object o = this.get(key);
		if(o == null || o instanceof String) {
			return (String) o;
		} else {
			throw new JSONException("Expected '" + key + "' to be of type String");
		}
	}
	
	public Integer getInt(String key) {
		Object val = this.get(key);
		if(val instanceof Number) {
			return ((Number)val).intValue();
		} else if (val == null){
			return null;
		} else {
			// TODO: total freaking hack.  need to clean this up
			throw new RuntimeException("not an integer:" + val.getClass());
		}
	}
	
	public long getLong(String key) {
		Object val = this.get(key);
		if(val instanceof Number) {
			return ((Number)val).longValue();
		} else {
			// TODO: total freaking hack.  need to clean this up
			throw new RuntimeException("not a long:" + val.getClass());
		}
	}

	public JSONObject getJSONObject(String key) throws JSONException {
		Object o = this.get(key);
		if(o == null || o instanceof JSONObject) {
			return (JSONObject) o;
		} else {
			throw new JSONException("Expected '" + key + "' to be of type JSONObject");
		}
	}
	
	public JSONArray getJSONArray(String key) throws JSONException {
		Object o = this.get(key);
		if(o == null || o instanceof JSONArray) {
			return (JSONArray) o;
		} else {
			throw new JSONException("Expected '" + key + "' to be of type JSONArray");
		}
	}
}
