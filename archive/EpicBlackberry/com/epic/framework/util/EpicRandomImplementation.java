package com.epic.framework.util;

import java.util.Random;

public class EpicRandomImplementation {
	private static Random random = new Random();

	public static int nextInt(int n) {
		return random.nextInt(n);
	}

	public static String getHashFromString(String string) {
		// TODO: Needs to return same hash result as Android based on salted MD5
		return "";
	}
}
