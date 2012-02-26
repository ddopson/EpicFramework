package com.epic.framework.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EpicHasher {
	public static String createMd5Hash(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update((data + "epicsalt").getBytes());
			byte[] digestBytes = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for(int i=0; i< digestBytes.length; i++) {
				hexString.append(Integer.toHexString(0xFF & digestBytes[i]));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}
}
