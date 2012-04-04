package com.epic.framework.vendor.org.json.simple;

import com.epic.framework.vendor.org.json.simple.parser.JSONParser;
import com.epic.framework.vendor.org.json.simple.parser.Yytoken;

/**
 * ParseException explains why and where the error occurs in source JSON text.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 *
 */
public class JSONException extends Exception {
	JSONException(String msg) {
		super(msg);
	}
	
	JSONException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
