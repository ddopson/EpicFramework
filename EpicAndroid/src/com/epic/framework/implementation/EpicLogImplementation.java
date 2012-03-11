package com.epic.framework.implementation;

import static com.epic.framework.common.util.EpicLog.LEVEL_DEBUG;
import static com.epic.framework.common.util.EpicLog.LEVEL_ERROR;
import static com.epic.framework.common.util.EpicLog.LEVEL_INFO;
import static com.epic.framework.common.util.EpicLog.LEVEL_VERBOSE;
import static com.epic.framework.common.util.EpicLog.LEVEL_WARN;

import com.epic.framework.common.util.EpicFail;

import android.util.Log;

public class EpicLogImplementation {
	public static final void log(String tag, String msg, int level, Throwable e) {
		switch(level) {
		case LEVEL_ERROR:
			Log.e(tag, msg, e);
			break;
		case LEVEL_WARN:
			Log.w(tag, msg, e);
			break;
		case LEVEL_INFO:
			Log.i(tag, msg, e);
			break;
		case LEVEL_DEBUG:
			Log.d(tag, msg, e);
			break;
		case LEVEL_VERBOSE:
			Log.v(tag, msg, e);
			break;
		default:
			EpicFail.unhandled_case();
		}
	}

	public static void logStack() {
		// Optional Debugging Method.  Not implemented
	}
}
