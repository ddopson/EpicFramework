package com.epic.framework.util;

import java.io.UnsupportedEncodingException;

import net.rim.device.api.crypto.MD5Digest;

public class EpicHasher {

	public static String createMd5Hash(String data) {
	    byte[] bytes;
		try {
			data += "epicsalt";
			bytes = data.getBytes("UTF-8");
			 MD5Digest digest = new MD5Digest();
		    digest.update(bytes, 0, bytes.length);
		    int length = digest.getDigestLength();
		    byte[] md5 = new byte[length];
		    digest.getDigest(md5, 0, true);
		    String result = "";
		    for(int i = 0; i < length; ++i)
		    {
		    	result += Integer.toHexString(0xFF & md5[i]);
		    }

		    return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
	}
}
