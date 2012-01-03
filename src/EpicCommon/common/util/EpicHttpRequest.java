package com.epic.framework.common.util;

import java.io.IOException;
import java.util.HashMap;

import com.epic.framework.implementation.EpicHttpImplementation;
import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;
import com.epic.framework.common.util.exceptions.EpicNotImplementedException;

public class EpicHttpRequest {
	public String url;
	public HashMap<String, String> headers;
	public boolean allowRedirect = true;
	public String body;

	public EpicHttpRequest(String url, HashMap<String, String> headers) {
		if(headers == null) throw new EpicInvalidArgumentException("Headers cannot be null");
		this.url = url;
		this.headers = headers;
	}

	public EpicHttpRequest(String url) {
		this(url, new HashMap<String, String>());
	}

	public void beginGet(final EpicHttpResponseHandler handler) {
		EpicHttpImplementation.beginGet(this, handler);
		// handler.handleFailure(new EpicNotImplementedException("Network calls not yet implemented."));
//		new Thread(new Runnable() {
//			Exception exception;
//			EpicHttpResponse response;		
//			public void run() {
//				try {
//					response = get();
//				} catch (Exception e) {
//					exception = e;
//				}
//				if(handler != null) {
//					EpicPlatform.runOnUiThread(new Runnable() {
//						public void run() {
//							if(exception == null) {
//								handler.handleResponse(response);
//							}
//							else {
//								handler.handleFailure(exception);
//							}
//						}
//					});
//				}
//			}
//		}).start();
	}

	public EpicHttpResponse get() throws IOException {
		return EpicHttpImplementation.get(this);
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public static long downloadFile(String url, String path) {
		return EpicHttpImplementation.downloadFileTo(url, path);
	}

	public void setBody(String string) {
		this.body = string;
	}
}