package com.epic.framework.common.util;

import java.io.IOException;
import java.util.HashMap;

import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;
import com.epic.framework.implementation.EpicHttpImplementation;

public class EpicHttpRequest {
	public String url;
	public HashMap<String, String> headers;
	public boolean allowRedirect = true;
	public String body;
	public boolean allowUiFromRequest = true;

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