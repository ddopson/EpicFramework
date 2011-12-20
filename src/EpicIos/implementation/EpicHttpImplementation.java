package com.epic.framework.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicHttpRequest;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.exceptions.EpicNotImlementedException;

public class EpicHttpImplementation {

	public static EpicHttpResponse get(EpicHttpRequest epicHttpRequest) {
		throw EpicFail.not_implemented();
	}

	public static long downloadFileTo(String url, String path) {
		return 0;
	}
}