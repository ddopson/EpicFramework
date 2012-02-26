package java.lang;

public class StringBuilder {
	StringBuffer buffer = null;
	public StringBuilder() {
		buffer = new StringBuffer();}
	public StringBuilder(int length) {
		buffer = new StringBuffer(length);
	}
	public StringBuilder(String string) {
		buffer = new StringBuffer(string);
	}
	public StringBuilder append(boolean b) {
		buffer.append(b);
		return this;
	}
	public StringBuilder append(char c) {
		buffer.append(c);
		return this;
	}
	public StringBuilder append(char[] str) {
		buffer.append(str);
		return this;
	}
	public StringBuilder append(char[] str, int offset, int len) {
		buffer.append(str, offset, len);
		return this;
	}
	// public StringBuilder append(CharSequence s) {
	//  buffer.append(s);
	//  return this;
	// }
	// public StringBuilder append(CharSequence s, int start, int end) {
	//  buffer.append(s, start, end);
	//  return this;
	// }
	public StringBuilder append(double d) {
		buffer.append(d);
		return this;
	}
	public StringBuilder append(float f) {
		buffer.append(f);
		return this;
	}
	public StringBuilder append(int i) {
		buffer.append(i);
		return this;
	}
	public StringBuilder append(long lng) {
		buffer.append(lng);
		return this;
	}
	public StringBuilder append(Object obj) {
		buffer.append(obj);
		return this;
	}
	public StringBuilder append(String str) {
		buffer.append(str);
		return this;
	}
	public StringBuilder append(StringBuffer sb) {
		buffer.append(sb.toString());
		return this;
	}
	// public StringBuilder  appendCodePoint(int codePoint) {
	//  buffer.appendCodePoint(codePoint);
	//  return this;
	// }
	public int  capacity() {
		return buffer.capacity();
	}
	public char  charAt(int index) {
		return buffer.charAt(index);
	}
	// public int codePointAt(int index) {
	//  buffer.codePointAt(index);
	//  return this;
	// }
	// public int  codePointBefore(int index) {
	//  buffer.codePointBefore(index);
	//  return this;
	// }
	// public int  codePointCount(int beginIndex, int endIndex) {
	//  buffer.codePointCount(beginIndex, endIndex);
	// }
	public StringBuilder  delete(int start, int end) {
		buffer.delete(start, end);
		return this;
	}
	public StringBuilder  deleteCharAt(int index) {
		buffer.deleteCharAt(index);
		return this;
	}
	public void  ensureCapacity(int minimumCapacity) {
		buffer.ensureCapacity(minimumCapacity);
	}
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {

	}
	public int  indexOf(String str) {
		return buffer.toString().indexOf(str);
	}
	public int  indexOf(String str, int fromIndex) {
		return buffer.toString().indexOf(str, fromIndex);
	}
	public StringBuilder insert(int offset, boolean b) {
		buffer.insert(offset, b);
		return this;
	}
	public StringBuilder insert(int offset, char c) {
		buffer.insert(offset, c);
		return this;
	}
	public StringBuilder insert(int offset, char[] str) {
		buffer.insert(offset, str);
		return this;
	}
	// public StringBuilder insert(int index, char[] str, int offset, int len) {
	//  buffer.insert(index, str, offset, len);
	//  return this;
	// }
	// public StringBuilder  insert(int dstOffset, CharSequence s) {
	//  buffer.insert(dstOffset, s);
	//  return this;
	// }
	// public StringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
	//  buffer.insert(dstOffset, s, start, end);
	//  return this;
	// }
	public StringBuilder insert(int offset, double d) {
		buffer.insert(offset, d);
		return this;
	}
	public StringBuilder insert(int offset, float f) {
		buffer.insert(offset, f);
		return this;
	}
	public StringBuilder insert(int offset, int i) {
		buffer.insert(offset, i);
		return this;
	}
	public StringBuilder insert(int offset, long l) {
		buffer.insert(offset, l);
		return this;
	}
	public StringBuilder insert(int offset, Object obj) {
		buffer.insert(offset, obj);
		return this;
	}
	public StringBuilder insert(int offset, String str) {
		buffer.insert(offset, str);
		return this;
	}
	// int  lastIndexOf(String str) {
	//  return buffer.toString().lastIndexOf(str);
	// }
	// int  lastIndexOf(String str, int fromIndex) {
	//  return buffer.lastIndexOf(str, fromIndex);
	// }
	public int length() {
		return buffer.length();
	}
	// int  offsetByCodePoints(int index, int codePointOffset) {
	//  return buffer.offsetByCodePoints(index, codePointOffset);
	// }
	public StringBuilder replace(int start, int end, String str) {
		buffer.delete(start, end);
		buffer.insert(start, str);
		return this;
	}
	public StringBuilder reverse() {
		buffer.reverse();
		return this;
	}
	public void  setCharAt(int index, char ch) {
		buffer.setCharAt(index, ch);
	}
	public void  setLength(int newLength) {
		buffer.setLength(newLength);
	}
	// CharSequence  subSequence(int start, int end) {
	//  buffer.subSequence(start, end);
	// }
	public String  substring(int start) {
		return buffer.toString().substring(start);
	}
	public String  substring(int start, int end) {
		return buffer.toString().substring(start, end);
	}
	public String  toString() {
		return buffer.toString();
	}
	public void trimToSize() {
		; // no-op
	}
}
