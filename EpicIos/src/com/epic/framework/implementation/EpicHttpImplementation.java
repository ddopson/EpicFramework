package com.epic.framework.implementation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui2.EpicRestRequest;
import com.epic.framework.common.Ui2.EpicRestRequest.RequestInstance;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;
import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;

public class EpicHttpImplementation {

	private static String[] knownHeaders = new String[] { "Online", "Tokencount", "Coincount", "Points", "B", "Daily", "Open_challenges", "Rank", "Player_record", "C", "Usermessage", "Usermessageurl" };
	private static final int BUF_SIZE = 64000;

	public static EpicHttpResponse get(EpicHttpRequest epicHttpRequest) {
		HttpURLConnection c = null;
		URL url = null;
		try {
			// TODO: useful to debug Word Farm #38
			// Thread.sleep(2000);
			url = new URL(epicHttpRequest.url);
			c = (HttpURLConnection) url.openConnection();		
			if(epicHttpRequest.body != null) {
				c.setDoOutput(true);
				c.getOutputStream().write(epicHttpRequest.body.getBytes());
			}
		} catch(Exception e) {
			EpicLog.e("Problem opening connection: " + e.toString());
		}
		
		String body = "";

		try {
			InputStream in = c.getInputStream();
			EpicLog.i("Bytes available in stream: " + in.available());
			
			if(in.available() > 0) {
				byte[] buf = new byte[BUF_SIZE];
				int read = in.read(buf);
				EpicLog.i("Read " + read + " bytes from connection body.");
				
				body = new String(buf).trim();
			}
		} catch(Exception e) {
			EpicLog.e("Problem reading response body: " + e.toString());
		}
			
		try {
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
			EpicLog.e("Problem with reading response: " + e.toString());
		}
		
		throw new EpicFrameworkException("Failed to complete connection.");
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}

	public static void beginGet(final EpicHttpRequest request, final EpicHttpResponseHandler handler) {
//		int res = EpicPlatformImplementationNative.isNetworkAvailable(); 
//		EpicLog.i("Network status: " + res);
//		if(res == 0) {
//			handler.handleFailure(new EpicInvalidArgumentException("No network available."));
//			return;
//		}
//		
//		EpicPlatform.runInBackground(new Runnable() {
//			public void run() {
//				try {
//					EpicHttpResponse rs = request.get();
//					WordsHttp.processGenericResponseFields(rs);
//					if(handler != null) {
//						handler.handleResponse(rs);
//					}
//					
//					if(request.allowUiFromRequest) WordsHttp.shouldDisplayChallengeToasts = true;
//				} catch (Exception e) {
//					EpicLog.e("Problem with connection: " + e.toString());
//					if(handler != null) {
//						handler.handleFailure(e);
//					}
//				}
//			}
//		});
	}

	public static void get(EpicRestRequest epicRestRequest, RequestInstance requestInstance) {
		// TODO Auto-generated method stub
	}
}
