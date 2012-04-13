package com.epic.framework.vendor.org.json.simple.parser;

import java.util.List;
import java.util.Map;

import com.epic.framework.vendor.org.json.simple.JSONArray;
import com.epic.framework.vendor.org.json.simple.JSONObject;

/**
 * Container factory for creating containers for JSON object and JSON array.
 * 
 * @see com.epic.framework.vendor.org.json.simple.parser.JSONParser#parse(java.io.Reader, ContainerFactory)
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public interface ContainerFactory {
	public static final ContainerFactory defaultContainerFactory = new ContainerFactory() {
		@Override
		public Map<String, Object> createObjectContainer() {
			return new JSONObject();
		}
		
		@Override
		public List<Object> creatArrayContainer() {
			return new JSONArray();
		}
	};
	/**
	 * @return A Map instance to store JSON object, or null if you want to use org.json.simple.JSONObject.
	 */
	Map<String, Object> createObjectContainer();
	
	/**
	 * @return A List instance to store JSON array, or null if you want to use org.json.simple.JSONArray. 
	 */
	List<Object> creatArrayContainer();
}
