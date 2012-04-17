package com.epic.framework.common.Ui2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.implementation.EpicHttpImplementation;
import com.epic.framework.vendor.org.json.simple.JSONObject;

@EpicInflatableClass(inflationArguments=EpicRestRequest.InflationArguments.class)
public class EpicRestRequest extends EpicAction {
	public static final String SCHEME_HTTP = "http";
	public static final String SCHEME_HTTPS = "https";
	
	public static final String METHOD_GET = "GET";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_DELETE = "DELETE";
	
	public static abstract class RestResponseAction extends EpicAction {
		@Override
		public void run() {
		}
		
		public abstract void run(RequestInstance request);
	}
	
	@EpicInflatableClass
	public static class InflationArguments extends EpicObject {
		String method;
		String scheme;
		String host;
		Integer port;
		String path;
		JSONObject headers;
		JSONObject params;
		JSONObject body;
		EpicAction success;
		EpicAction fail;		
	}
	public String method;
	public String scheme;
	public String host;
	public Integer port;
	public String path;
	public Map<String, String> headers;
	public Map<String, String> params;
	public EpicAction success;
	public EpicAction fail;
	
	public static class RequestInstance {
		public EpicRestRequest request;
		public int responseCode = -1;
		public String responseMessage;
		public String responseBody;
		public Map<String, List<String>> responseHeaders;
		
		public RequestInstance(EpicRestRequest request) {
			this.request = request;
		}
		
		private void _runAction(EpicAction action) {
			if(action == null) {
				return;
			} else if(action instanceof RestResponseAction) {
				RestResponseAction restAction = (RestResponseAction)action;
				restAction.run(this);
			} else {
				action.run();
			}
		}
		
		public void finish() {
			if(responseCode == 200) {
				_runAction(request.success);
			} else {
				_runAction(request.fail);
			}
		}
		
		public void finish(Exception e) {
			responseMessage = e.getMessage();
			_runAction(request.fail);
		}
		
		public String getHeader(String key) {
			List<String> values = responseHeaders.get(key);
			if(values != null) {
				return values.get(0);
			} else {
				return null;
			}
		}
	}
	
	public static EpicRestRequest initialize(InflationArguments args) {
		EpicRestRequest me = new EpicRestRequest();
		me.method = args.method != null ? args.method : METHOD_GET;
		me.scheme = args.scheme != null ? args.scheme : SCHEME_HTTP;
		me.host = args.host;
		me.path = args.path;
		me.port = args.port;
		me.success = args.success;
		me.fail = args.fail;
		me.headers = new HashMap<String, String>();
		
		return me;
	}
	
	public String getUrl() {
		return scheme + "://" + host + (port != null ? ":" + port : "") + "/" + path + "?";
	}
	
	public String getBody() {
		return "";
	}
	
	@Override
	public void run() {
		EpicHttpImplementation.get(this, new RequestInstance(this));
	}
}
