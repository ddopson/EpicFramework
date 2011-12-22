package com.epic.framework.implementation;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicLog;

public class EpicHttpImplementation {

	private static String[] knownHeaders = new String[] { "Online", "Tokencount", "Points", "B", "Open_challenges", "Rank", "Player_record", "C", "Usermessage", "Usermessageurl" };
	private static final int BUF_SIZE = 64000;

	public static EpicHttpResponse get(EpicHttpRequest epicHttpRequest) {
		try {
			URL url = new URL(epicHttpRequest.url);
			HttpURLConnection c = (HttpURLConnection) url.openConnection();		
			if(epicHttpRequest.body != null) {
				c.setDoOutput(true);
				c.getOutputStream().write(epicHttpRequest.body.getBytes());
			}
			
			InputStream in = c.getInputStream();
			EpicLog.i("Bytes available in stream: " + in.available());
			byte[] buf = new byte[BUF_SIZE];
			int read = in.read(buf);
			EpicLog.i("Read " + read + " bytes from connection body.");
			
			String body = new String(buf).trim();
			
			HashMap<String, String> headers = new HashMap<String, String>(); 
			
			for(String s : knownHeaders) {
				if(c.getHeaderField(s) != null) {
					String header = c.getHeaderField(s);
					if(header != null && header.length() > 0) {
						EpicLog.v("Found header: " + s + ", with: " + header);
						headers.put(s, header);
					}
				}
			}
			
			EpicHttpResponse r = new EpicHttpResponse(200, body, headers);
			return r;
		} catch (Exception e) {
			EpicLog.e("Problem with connection: " + e.toString());
		}
		
		throw new EpicRuntimeException("Failed to complete connection.");
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}
}