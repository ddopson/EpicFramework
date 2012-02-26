package com.epic.framework.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class EpicBufferedReader extends BufferedReader {

	public EpicBufferedReader(Reader in, int sz) {
		super(in, sz);
	}

	public EpicBufferedReader(Reader in) {
		super(in);
	}

	public EpicBufferedReader(InputStream in) {
		super(new InputStreamReader(in));
	}

	public String readEntireStreamAsString() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		int c;
		while(-1 != (c = this.read())) {
			stringBuilder.append(c);
		}
		return stringBuilder.toString();
	}
	
}

//
//public class EpicBufferedReader {
//	private InputStream inputStream;
//	private byte[] buffer;
//	private int nChars;
//	private int p;
//	private boolean skipLF;
//	private static int defaultCharBufferSize = 8192;
//	private static int defaultExpectedLineLength = 80;
//
//	public EpicBufferedReader(InputStream inputStream, int bufferSize)
//	{
//		this.skipLF = false;
//		if (bufferSize <= 0) {
//			throw new IllegalArgumentException("Buffer size <= 0");
//		}
//		this.inputStream = inputStream;
//		this.buffer = new byte[bufferSize];
//		this.p = 0;
//		this.nChars = 0;
//	}
//
//	public EpicBufferedReader(InputStream inputStream)
//	{
//		this(inputStream, defaultCharBufferSize);
//	}
//
//	private void ensureOpen() throws IOException
//	{
//		if (this.inputStream == null) {
//			throw new IOException("Stream closed");
//		}
//	}
//
//	private int fill() throws IOException
//	{
//		int j;
//		do {
//			j = this.inputStream.read(this.buffer, 0, this.buffer.length);
//		} while (j == 0);
//		if (j > 0) {
//			this.nChars = j;
//			this.p = 0;
//			return j;
//		}
//		else {
//			return -1;
//		}
//	}
//
//	public int read() throws IOException {
//		if (this.p >= this.nChars) {
//			if(-1 == fill()) {
//				return -1;
//			}
//		}
//		return buffer[p++];
//	}
//
//	//	public int read() throws IOException
//	//	{
//	//		ensureOpen();
//	//		while (true) {
//	//			if (this.nextChar >= this.nChars) {
//	//				fill();
//	//				if (this.nextChar >= this.nChars)
//	//					return -1;
//	//			}
//	//			if (!(this.skipLF)) break;
//	//			this.skipLF = false;
//	//			if (this.cb[this.nextChar] != '\n') break;
//	//			this.nextChar += 1;
//	//		}
//	//
//	//		return this.cb[(this.nextChar++)];
//	//	}
//	//
//	//	private int read1(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException
//	//	{
//	//		if (this.nextChar >= this.nChars)
//	//		{
//	//			if ((paramInt2 >= this.cb.length) && (this.markedChar <= -1) && (!(this.skipLF))) {
//	//				return this.in.read(paramArrayOfChar, paramInt1, paramInt2);
//	//			}
//	//			fill();
//	//		}
//	//		if (this.nextChar >= this.nChars) return -1;
//	//		if (this.skipLF) {
//	//			this.skipLF = false;
//	//			if (this.cb[this.nextChar] == '\n') {
//	//				this.nextChar += 1;
//	//				if (this.nextChar >= this.nChars)
//	//					fill();
//	//				if (this.nextChar >= this.nChars)
//	//					return -1;
//	//			}
//	//		}
//	//		int i = Math.min(paramInt2, this.nChars - this.nextChar);
//	//		System.arraycopy(this.cb, this.nextChar, paramArrayOfChar, paramInt1, i);
//	//		this.nextChar += i;
//	//		return i;
//	//	}
//	//
//	//	public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException
//	//	{
//	//		ensureOpen();
//	//		if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0))
//	//		{
//	//			throw new IndexOutOfBoundsException(); }
//	//		if (paramInt2 == 0) {
//	//			return 0;
//	//		}
//	//
//	//		int i = read1(paramArrayOfChar, paramInt1, paramInt2);
//	//		if (i <= 0) return i;
//	//		while ((i < paramInt2) && (this.in.ready())) {
//	//			int j = read1(paramArrayOfChar, paramInt1 + i, paramInt2 - i);
//	//			if (j <= 0) break;
//	//			i += j;
//	//		}
//	//		return i;
//	//	}
//
//	public String readLine() throws IOException
//	{
//		StringBuilder stringBuilder = null;
//		ensureOpen();
//
//		while(true) {
//			if (this.p >= this.nChars) {
//				fill();
//			}
//
//			// the end-of-stream case
//			if (this.p >= this.nChars) {
//				if ((stringBuilder != null) && (stringBuilder.length() > 0)) {
//					return stringBuilder.toString();
//				}
//				return null;
//			}
//
//			// skip LF following CR
//			if (this.skipLF && (this.buffer[this.p] == '\n')) {
//				this.p++;
//			}
//			this.skipLF = false;
//
//			int startChar = this.p;
//			while (this.p < this.nChars) {
//				int c = this.buffer[this.p++];
//				if ((c == 10) || (c == 13)) {
//					if(c == 13) {
//						this.skipLF = true;
//					}
//					if (stringBuilder == null) {
//						return new String(this.buffer, startChar, this.p - startChar - 1);
//					} else {
//						stringBuilder.append(new String(this.buffer, startChar, this.p - startChar - 1));
//						return stringBuilder.toString();
//					}
//				}
//			}
//			if (stringBuilder == null) {
//				stringBuilder = new StringBuilder(defaultExpectedLineLength);
//			}
//			stringBuilder.append(new String(this.buffer, startChar, this.p - startChar));
//		}
//	}
//
//	//	public long skip(long paramLong) throws IOException {
//	//		if (paramLong < 0L) {
//	//			throw new IllegalArgumentException("skip value is negative");
//	//		}
//	//		ensureOpen();
//	//		long l1 = paramLong;
//	//		while (l1 > 0L) {
//	//			if (this.nextChar >= this.nChars)
//	//				fill();
//	//			if (this.nextChar >= this.nChars)
//	//				break;
//	//			if (this.skipLF) {
//	//				this.skipLF = false;
//	//				if (this.cb[this.nextChar] == '\n') {
//	//					this.nextChar += 1;
//	//				}
//	//			}
//	//			long l2 = this.nChars - this.nextChar;
//	//			if (l1 <= l2)
//	//			{
//	//				EpicBufferedReader tmp123_122 = this; tmp123_122.nextChar = (int)(tmp123_122.nextChar + l1);
//	//				l1 = 0L;
//	//				break;
//	//			}
//	//
//	//			l1 -= l2;
//	//			this.nextChar = this.nChars;
//	//		}
//	//
//	//		return (paramLong - l1);
//	//	}
//	//
//	//	public boolean ready() throws IOException {
//	//		ensureOpen();
//	//
//	//		if (this.skipLF) {
//	//			if ((this.nextChar >= this.nChars) && (this.in.ready())) {
//	//				fill();
//	//			}
//	//			if (this.nextChar < this.nChars) {
//	//				if (this.cb[this.nextChar] == '\n')
//	//					this.nextChar += 1;
//	//				this.skipLF = false;
//	//			}
//	//		}
//	//		return ((this.nextChar < this.nChars) || (this.in.ready()));
//	//	}
//	//
//	//	public boolean markSupported() {
//	//		return true;
//	//	}
//	//
//	//	public void mark(int paramInt) throws IOException {
//	//		if (paramInt < 0) {
//	//			throw new IllegalArgumentException("Read-ahead limit < 0");
//	//		}
//	//		ensureOpen();
//	//		this.readAheadLimit = paramInt;
//	//		this.markedChar = this.nextChar;
//	//		this.markedSkipLF = this.skipLF;
//	//	}
//	//
//	//	public void reset() throws IOException {
//	//		ensureOpen();
//	//		if (this.markedChar < 0) {
//	//			throw new IOException((this.markedChar == -2) ? "Mark invalid" : "Stream not marked");
//	//		}
//	//
//	//		this.nextChar = this.markedChar;
//	//		this.skipLF = this.markedSkipLF;
//	//	}
//
//	public void close() throws IOException {
//		if (this.inputStream == null) {
//			return;
//		}
//		this.inputStream.close();
//		this.inputStream = null;
//		this.buffer = null;
//	}
//
//	public String readEntireStreamAsString() throws IOException {
//		StringBuilder stringBuilder = new StringBuilder();
//		while(true) {
//			if (this.p >= this.nChars) {
//				fill();
//			}
//			// the end-of-stream case
//			if (this.p >= this.nChars) {
//				return stringBuilder.toString();
//			}
//			stringBuilder.append(new String(this.buffer, this.p, this.nChars - this.p));
//			p = nChars;
//		}
//	}
//}
