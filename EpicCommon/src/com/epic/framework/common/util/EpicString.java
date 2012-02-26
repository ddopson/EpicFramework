//package com.epic.framework.common.util;
//
//public class EpicString {
//	private final char[] chars;
//	private String asString = null;
//	public EpicString(String string) {
//		chars = new char[string.length()];
//		string.getChars(0, string.length(), chars, 0);
//	}
//
//	public String toString() {
//		if(asString == null) {
//			asString = new String(chars);
//		}
//		return asString;
//	}
//
//	public char charAt(int index) {
//		EpicFail.assertBounds(0, index, chars.length);
//		return chars[index];
//	}
//
//	public int compareTo(Object o)  {
//	}
//
//	public int compareTo(String anotherString)  {
//	}
//
//	public int compareToIgnoreCase(String str)  {
//	}
//
//	public String concat(String str)  {
//	}
//
//	public static String copyValueOf(char[] data)  {
//	}
//
//	public static String  copyValueOf(char[] data, int offset, int count)  {
//	}
//
//	public boolean endsWith(String  suffix)  {
//	}
//
//	public boolean equals(Object anObject)  {
//	}
//
//	public boolean equalsIgnoreCase(String  anotherString )  {
//	}
//
//	public byte[]getBytes()  {
//	}
//
//	public voidgetBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin)  {
//	}
//
//	public byte[]getBytes(String  enc)  {
//	}
//
//	public voidgetChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)  {
//	}
//
//	public inthashCode()  {
//	}
//
//	public intindexOf(int ch)  {
//	}
//
//	public intindexOf(int ch, int fromIndex)  {
//	}
//
//	public intindexOf(String  str)  {
//	}
//
//	public intindexOf(String  str, int fromIndex)  {
//	}
//
//	public String intern()  {
//	}
//
//	public intlastIndexOf(int ch)  {
//	}
//
//	public intlastIndexOf(int ch, int fromIndex)  {
//	}
//
//	public intlastIndexOf(String  str)  {
//	}
//
//	public intlastIndexOf(String  str, int fromIndex)  {
//	}
//
//	public intlength()  {
//	}
//
//	public boolean regionMatches(boolean  ignoreCase, int toffset, String  other, int ooffset, int len)  {
//	}
//
//	public boolean regionMatches(int toffset, String  other, int ooffset, int len)  {
//	}
//
//	public String replace(char oldChar, char newChar)  {
//	}
//
//	public boolean startsWith(String  prefix)  {
//	}
//
//	public boolean startsWith(String  prefix, int toffset)  {
//	}
//
//	public String subString (int beginIndex)  {
//	}
//
//	public String subString (int beginIndex, int endIndex)  {
//	}
//
//	public char[] toCharArray()  {
//		return chars;
//	}
//
//	public String toLowerCase()  {
//	}
//
//	public String toUpperCase()  {
//	}
//
//	public String trim()  {
//	}
//
//	public static String valueOf(boolean  b)  {
//	}
//
//	public static String valueOf(char c)  {
//	}
//
//	public static String valueOf(char[] data)  {
//	}
//
//	public static String valueOf(char[] data, int offset, int count)  {
//	}
//
//	public static String valueOf(double d)  {
//	}
//
//	public static String valueOf(float f)  {
//	}
//
//	public static String valueOf(int i)  {
//	}
//
//	public static String valueOf(long l)  {
//	}
//
//	public static String valueOf(Object obj)  {
//	}
//
//}
