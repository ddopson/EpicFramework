package com.epic.framework.common.util;

import com.epic.framework.implementation.util.EpicLogImplementation;

public class EpicLog {
	public static final int LEVEL_DBG = -1;
	public static final int LEVEL_ERROR = 0;
	public static final int LEVEL_WARN = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_DEBUG = 3;
	public static final int LEVEL_VERBOSE = 4;

	private static final String tag = "Epic";

	private static void log(String msg, int level) {
		EpicLogImplementation.log(tag, msg, level, null);
	}

	private static void log(String format, Object[] args, int level) {
		String msg = StringHelper.format(format, args);
		EpicLogImplementation.log(tag, msg, level, null);
	}

	private static void log(String msg, Throwable e, int level) {
		EpicLogImplementation.log(tag, msg, level, e);
	}

	private static void loglogStack() {
		// this pointless method serves to keep the stack depth consistent
		EpicLogImplementation.logStack();		
	}
	public static void logStack() {
		loglogStack();
	}
	
	public static void v(String msg) {
		log(msg, LEVEL_VERBOSE);
	}
	public static void d(String msg) {
		log(msg, LEVEL_DEBUG);
	}
	public static void i(String msg) {
		log(msg, LEVEL_INFO);
	}
	public static void w(String msg) {
		log(msg, LEVEL_WARN);
	}
	public static void e(String msg) {
		log(msg, LEVEL_ERROR);
	}
	public static void dbg(String msg) {
		log(msg, LEVEL_DBG);
	}
	
	
	public static void v(String msg, Throwable e) {
		log(msg, e, LEVEL_VERBOSE);
	}
	public static void d(String msg, Throwable e) {
		log(msg, e, LEVEL_DEBUG);
	}
	public static void i(String msg, Throwable e) {
		log(msg, e, LEVEL_INFO);
	}
	public static void w(String msg, Throwable e) {
		log(msg, e, LEVEL_WARN);
	}
	public static void e(String msg, Throwable e) {
		log(msg, e, LEVEL_ERROR);
	}
	public static void dbg(String msg, Throwable e) {
		log(msg, e, LEVEL_DBG);
	}

	public static void vf(String msg, Object...args) {
		log(msg, args, LEVEL_VERBOSE);
	}
	public static void df(String msg, Object...args) {
		log(msg, args, LEVEL_DEBUG);
	}
	public static void iff(String msg, Object...args) {
		log(msg, args, LEVEL_INFO);
	}
	public static void wf(String msg, Object...args) {
		log(msg, args, LEVEL_WARN);
	}
	public static void ef(String msg, Object...args) {
		log(msg, args, LEVEL_ERROR);
	}

}
