package com.epic.framework.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.epic.framework.common.Ui2.EpicRestRequest;
import com.epic.framework.common.util.EpicBufferedReader;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
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

	public static void get(EpicRestRequest request, EpicRestRequest.RequestInstance response) {
		try {
			URL url = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			EpicLog.v("Connection open to " + url.toString());
			conn.setRequestMethod(request.method);
			for(Entry<String, String> entry : request.headers.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
				// EpicLog.v("Header added: " + entry.getKey() + ", " + entry.getValue());
			}
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter w = new OutputStreamWriter(out);
			w.write(request.getBody());
			w.close();
			out.close();

			EpicLog.v("Finished sending request.  waiting for response");
			// Response Code
			response.responseCode = conn.getResponseCode();
			response.responseMessage = conn.getResponseMessage();
			response.responseHeaders = conn.getHeaderFields();

			// Read Body
			InputStream in = conn.getInputStream();
			EpicBufferedReader epicBufferedReader = new EpicBufferedReader(in);
			EpicLog.v("reading response");
			response.responseBody = epicBufferedReader.readEntireStreamAsString();
			EpicLog.v("finish");
			response.finish();
		} catch (IOException e) {
			EpicLog.v("IOError:" + e);
			response.finish(e);
		}
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}

	public static void beginGet(EpicHttpRequest epicHttpRequest, EpicHttpResponseHandler handler) {
		EpicLog.w("NOT_IMPLEMENTED");
	}
}
