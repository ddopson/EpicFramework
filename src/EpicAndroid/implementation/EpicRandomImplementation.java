package com.epic.framework.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EpicRandomImplementation {
	private static Random random = new Random();

	public static int nextInt(int n) {
		return random.nextInt(n);
	}
	
	public static void setSeed(int seed) {
		random.setSeed(seed);
	}
	
	public static void resetSeed() {
		random = new Random();
	}

	public static String getHashFromString(String url) {
		try {
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(url.getBytes());
	        byte messageDigest[] = digest.digest();
	        
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString().toLowerCase();
	        
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";

	}
}
