package com.epic.framework.implementation;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.epic.framework.common.util.EpicBufferedReader;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicLog;


public class EpicHttpImplementation {
	public static EpicHttpResponse get(EpicHttpRequest request) throws IOException {
		URL url = new URL(request.url);
		URLConnection conn = url.openConnection();
		EpicLog.v("Connection open to " + url.toString());

		for(Entry<String, String> entry : request.headers.entrySet()) {
			conn.setRequestProperty(entry.getKey(), entry.getValue());
			EpicLog.v("Header added: " + entry.getKey() + ", " + entry.getValue());
		}

		// Body Text
		EpicBufferedReader epicBufferedReader = new EpicBufferedReader(conn.getInputStream());
		String body = epicBufferedReader.readEntireStreamAsString();

		// Response Headers
		HashMap<String, String> responseHeaders = new HashMap<String, String>();
		for(Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
			responseHeaders.put(entry.getKey(), entry.getValue().get(0));
		}

		return new EpicHttpResponse(0, body, responseHeaders);
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}
}
