package com.epic.framework.common.util;
public interface EpicHttpResponseHandler {
	void handleResponse(EpicHttpResponse response);
	void handleFailure(Exception e);
}
