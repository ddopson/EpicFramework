package com.epic.framework.build;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class StreamUtil {
	public static String consumeInputStream(InputStream in) throws IOException {
		Reader reader = new InputStreamReader(in);
		char[] buff = new char[4096];
		StringBuilder sb = new StringBuilder();
		int ret = 0;
		while(ret != -1) {
			ret = reader.read(buff);
			if(ret > 0) {
				sb.append(buff, 0, ret);
			}
		}
		return sb.toString();
	}
}
