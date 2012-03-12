package com.epic.framework.implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.epic.framework.common.util.EpicBufferedReader;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicLog;

import android.os.Environment;

public class EpicHttpImplementation {
	private static HttpClient httpClient = new DefaultHttpClient();

	public static long downloadFileTo(String url, String to) {
		FileOutputStream fileOutput = null;
		HttpClient client = null;
		InputStream in = null;
		long totalWritten = 0;
		
		try {
			client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			
			
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File (sdCard.getAbsolutePath() + "/WordFarmUpdates");
			dir.mkdirs();
			File file = new File(dir, to);
			fileOutput = new FileOutputStream(file);
	        in = response.getEntity().getContent();
	        byte[] buffer = new byte[1024];
	        int bufferLength = 0;
	        while ( (bufferLength = in.read(buffer)) != -1 ) {
	                fileOutput.write(buffer, 0, bufferLength);
	                totalWritten += bufferLength;
	        }
	       
	        fileOutput.close();
			in.close();
			
		} catch (FileNotFoundException e) {
			EpicLog.e(e.toString());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			EpicLog.e(e.toString());
		} catch (ClientProtocolException e) {
			EpicLog.e(e.toString());
		} catch (IOException e) {
			EpicLog.e(e.toString());
		} finally {
			try {
				if(fileOutput != null) fileOutput.close();
				if(in != null) in.close();
			} catch (IOException ex) {
				EpicLog.e(ex.toString());
			}
		}
		
		return totalWritten;
	}
		
	public static EpicHttpResponse get(EpicHttpRequest epicHttpRequest) throws IOException {
		EpicBufferedReader epicBufferedReader = null;
		try {
			// URI
			HttpRequestBase request;
			if(epicHttpRequest.body != null) {
				request = new HttpPost();
				((HttpPost) request).setEntity(new StringEntity(epicHttpRequest.body));
			} else {
				request = new HttpGet();
			}
			
			request.setURI(new URI(epicHttpRequest.url));
			
			// Request Headers
			for(Entry<String, String> entry : epicHttpRequest.headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			
			// Execute
			
			if(!epicHttpRequest.allowRedirect) {
				final HttpParams params = new BasicHttpParams();
				HttpClientParams.setRedirecting(params, false);
				request.setParams(params);
			}
			
			HttpResponse response = httpClient.execute(request);
			// Body Text
			epicBufferedReader = new EpicBufferedReader(response.getEntity().getContent());
			String body = epicBufferedReader.readEntireStreamAsString();
            
			// Response Headers
            HashMap<String, String> headers = new HashMap<String, String>();
            for(Header h : response.getAllHeaders()) {
            	headers.put(h.getName(), h.getValue());
            }
            
            return new EpicHttpResponse(response.getStatusLine().getStatusCode(), body, headers);
		} catch (URISyntaxException e) {
			throw EpicFail.unexpected();
		} finally {
            if (epicBufferedReader != null) {
                try {
                	epicBufferedReader.close();
                } catch (IOException e) {
                    EpicLog.e("IOException closing reader during finally block: ", e);
                }
            }
		}		
	}
}
