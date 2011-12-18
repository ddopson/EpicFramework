package com.epic.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.microedition.io.HttpConnection;

import com.epic.framework.Ui.EpicScreen;

import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;

public class EpicHttpImplementation {

	public static EpicHttpResponse get(EpicHttpRequest epicHttpRequest) {
		ConnectionFactory connFact = new ConnectionFactory();
		connFact.setTimeoutSupported(true);
		connFact.setConnectionTimeout(5000);
		connFact.setAttemptsLimit(3);
		connFact.setTimeLimit(3000);
        ConnectionDescriptor connDesc;
        connDesc = connFact.getConnection(epicHttpRequest.url);
        
        if (connDesc != null)
        {
            HttpConnection httpConn;
            httpConn = (HttpConnection)connDesc.getConnection();
            
            try
            {
            	for(String s : epicHttpRequest.headers.keySet()) {
    				httpConn.setRequestProperty(s, epicHttpRequest.headers.get(s));
                }
            	
                final int iResponseCode = httpConn.getResponseCode();
                
                StringBuilder bodyString = new StringBuilder();
    			
    		    InputStream buffer = httpConn.openInputStream();
    		    
    		    EpicLog.v("Input stream opened from connection.");
    		    
    		    int byteRead;
    		    while ((byteRead = buffer.read()) != -1){
    		    	bodyString.append((char) byteRead);
    		    }
    		    
    		    buffer.close();
    		    String body = bodyString.toString();
    		    EpicLog.v("Response body: " + body);
    		    
    		    HashMap<String, String> responseHeaders = new HashMap<String, String>();
    		    
    		    int c = 0;
    		    while(httpConn.getHeaderField(c) != null) {
    		    	responseHeaders.put(httpConn.getHeaderFieldKey(c), httpConn.getHeaderField(c));
    		    	c++;
    		    }
    		    
    			return new EpicHttpResponse(0, body, responseHeaders);
             } 
             catch (IOException e) 
             {
               EpicLog.e("Caught IOException: " + e.getMessage());
             }
        }
		
        return null;
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}
}