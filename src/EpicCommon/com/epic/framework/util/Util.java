package com.epic.framework.util;

public class Util {
	public static int div_and_round(int a, int b) {
		return (a + (b>>1)) / b;
	}
}
