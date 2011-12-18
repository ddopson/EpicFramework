package com.epic.framework.util;

import static com.epic.framework.util.EpicLog.LEVEL_DEBUG;
import static com.epic.framework.util.EpicLog.LEVEL_ERROR;
import static com.epic.framework.util.EpicLog.LEVEL_INFO;
import static com.epic.framework.util.EpicLog.LEVEL_VERBOSE;
import static com.epic.framework.util.EpicLog.LEVEL_WARN;
import net.rim.device.api.system.EventLogger;

public class EpicLogImplementation {
	static final long LOG_KEY = 0x1233448775592232L;
	private static final String APP_NAME = "WordFarm";

	static
	{
		EventLogger.register(LOG_KEY, APP_NAME, EventLogger.VIEWER_STRING);
		EventLogger.setMinimumLevel(EventLogger.DEBUG_INFO);
	}

	public static final void log(String tag, String msg, int level, Throwable e) {
		switch(level) {
		case LEVEL_ERROR:
			EventLogger.logEvent(LOG_KEY, msg.getBytes(), EventLogger.ERROR);
			break;
		case LEVEL_WARN:
			EventLogger.logEvent(LOG_KEY, msg.getBytes(), EventLogger.WARNING);
			break;
		case LEVEL_INFO:
			EventLogger.logEvent(LOG_KEY, msg.getBytes(), EventLogger.INFORMATION);
			break;
		case LEVEL_DEBUG:
			EventLogger.logEvent(LOG_KEY, msg.getBytes(), EventLogger.DEBUG_INFO);
			break;
		case LEVEL_VERBOSE:
			EventLogger.logEvent(LOG_KEY, msg.getBytes(), EventLogger.DEBUG_INFO);
			break;
		default:
			EpicFail.unhandled_case();
		}
	}

	public static void logStack() {
		try {
			throw new Throwable("Stack Trace Motherfucker.  gimme one.");
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		// Optional Debugging Method.  Not implemented
	}
}
