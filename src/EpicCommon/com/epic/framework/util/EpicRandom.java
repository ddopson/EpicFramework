package com.epic.framework.util;

import com.epic.cfg.EpicProjectConfig;

public class EpicRandom {
	public static boolean SHOULD_LOG = !EpicProjectConfig.isReleaseMode;
	private static int[] fakeData = null;
	private static int fakeDataPos = -1;

	public static int nextInt(int n) {
		if(fakeData != null) {
			int expect_n = fakeData[fakeDataPos++];
			int result = fakeData[fakeDataPos++];
			if(fakeDataPos == fakeData.length) {
				fakeData = null;
			}
			if(expect_n != n) {
				EpicLog.ef("EpicRandom.nextInt called with %d.  expected %d", n, expect_n);
				throw new RuntimeException(StringHelper.format("EpicRandom.nextInt called with %d.  expected %d", n, expect_n));
			}
			EpicLog.iff("EpicRandom(%d) = %d", n, result);
			return result;
		}
		else {
			int result = EpicRandomImplementation.nextInt(n);
			if(SHOULD_LOG) EpicLog.vf("EPIC_RANDOM %d, %d,", n, result);
			return result;
		}
	}

	public static void controlRandomWithFakeData(int[] data) {
		fakeData = data;
		if(data != null) {
			if(data.length % 2 != 0) {
				throw new RuntimeException("tried to seed fake data with an odd length");
			}
			fakeDataPos = 0;
		}
	}

	public static String getHashFromString(String fullUrl) {
		String h = EpicRandomImplementation.getHashFromString("POWPOW" + fullUrl);
		char[] chars = new char[h.length()];
		int p = 0;
		for(int i = 0; i < h.length(); i++) {
			char c = h.charAt(i);
			if(c != '0') {
				chars[p++] = c;
			}
		}
		return new String(chars, 0, p);
	}
}
