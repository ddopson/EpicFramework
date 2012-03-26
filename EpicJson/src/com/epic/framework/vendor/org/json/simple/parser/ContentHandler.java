package com.epic.framework.vendor.org.json.simple.parser;

import java.io.IOException;

import com.epic.framework.vendor.org.json.simple.JSONException;

/**
 * A simplified and stoppable SAX-like content handler for stream processing of JSON text. 
 * 
 * @see org.xml.sax.ContentHandler
 * @see com.epic.framework.vendor.org.json.simple.parser.JSONParser#parse(java.io.Reader, ContentHandler, boolean)
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public interface ContentHandler {
	/**
	 * Receive notification of the beginning of JSON processing.
	 * The parser will invoke this method only once.
     * 
	 * @throws JSONException 
	 * 			- JSONParser will stop and throw the same exception to the caller when receiving this exception.
	 */
	void startJSON() throws JSONException, IOException;
	
	/**
	 * Receive notification of the end of JSON processing.
	 * 
	 * @throws JSONException
	 */
	void endJSON() throws JSONException, IOException;
	
	/**
	 * Receive notification of the beginning of a JSON object.
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     *          - JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * @see #endJSON
	 */
	boolean startObject() throws JSONException, IOException;
	
	/**
	 * Receive notification of the end of a JSON object.
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     * 
     * @see #startObject
	 */
	boolean endObject() throws JSONException, IOException;
	
	/**
	 * Receive notification of the beginning of a JSON object entry.
	 * 
	 * @param key - Key of a JSON object entry. 
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     * 
     * @see #endObjectEntry
	 */
	boolean startObjectEntry(String key) throws JSONException, IOException;
	
	/**
	 * Receive notification of the end of the value of previous object entry.
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     * 
     * @see #startObjectEntry
	 */
	boolean endObjectEntry() throws JSONException, IOException;
	
	/**
	 * Receive notification of the beginning of a JSON array.
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     * 
     * @see #endArray
	 */
	boolean startArray() throws JSONException, IOException;
	
	/**
	 * Receive notification of the end of a JSON array.
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
     * 
     * @see #startArray
	 */
	boolean endArray() throws JSONException, IOException;
	
	/**
	 * Receive notification of the JSON primitive values:
	 * 	java.lang.String,
	 * 	java.lang.Number,
	 * 	java.lang.Boolean
	 * 	null
	 * 
	 * @param value - Instance of the following:
	 * 			java.lang.String,
	 * 			java.lang.Number,
	 * 			java.lang.Boolean
	 * 			null
	 * 
	 * @return false if the handler wants to stop parsing after return.
	 * @throws JSONException
	 */
	boolean primitive(Object value) throws JSONException, IOException;
		
}
