package com.epic.framework.util;
public interface EpicHttpResponseHandler {
	void handleResponse(EpicHttpResponse response);
	void handleFailure(Exception e);
}
