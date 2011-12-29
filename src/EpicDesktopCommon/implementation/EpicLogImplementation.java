package com.epic.framework.implementation

public class EpicLogImplementation {
	private static final int LOGGING_STACK_DEPTH = 4;
	
	public static void log(String tag, String msg, int level, Throwable e) {
		String levelString;
		switch(level) {
		case EpicLog.LEVEL_DEBUG:
			levelString = "DEBUG";
			break;
		case EpicLog.LEVEL_VERBOSE:
			levelString = "VERBOSE";
			break;
		case EpicLog.LEVEL_INFO:
			levelString = "INFO";
			break;
		case EpicLog.LEVEL_WARN:
			levelString = "WARN";
			break;
		case EpicLog.LEVEL_ERROR:
			levelString = "ERROR";
			break;
		case EpicLog.LEVEL_DBG:
			levelString = "DEBUG";
			break;
		default:
			throw EpicFail.unhandled_case();
		}
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stack[LOGGING_STACK_DEPTH];

		write_log_line(caller, levelString, msg);
	}

	private static String shortClassName(StackTraceElement ste) {
		String className = ste.getClassName();
		String[] tokens = className.split("[.]");
		tokens = tokens[tokens.length - 1].split("[$]");
		return tokens[0];
	}
	
	private static final long firstLogLineMillis = System.currentTimeMillis();
	private static final int fieldLengthFileAndLine = 32;
	private static final int fieldLengthMethodAndLevel = 30;
	private static void write_log_line(StackTraceElement caller, String levelString, String msg) {
		String fileAndLineString = String.format( "(%s:%d)",caller.getFileName(), caller.getLineNumber() );
		long millis = System.currentTimeMillis() - firstLogLineMillis;
		int fileOverflow = (fileAndLineString.length() > fieldLengthFileAndLine ? fileAndLineString.length() - fieldLengthFileAndLine : 0);
		System.out.println(
				String.format(
						"%03.3f %s %s %s>   %s",
						millis / 1000f,
						StringHelper.leftPad(fileAndLineString, fieldLengthFileAndLine),
						StringHelper.rightPad(caller.getMethodName() + "()", fieldLengthMethodAndLevel - levelString.length() - fileOverflow),
						levelString,
						msg
				)
		);
	}
	
	public static void logStack() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stack[LOGGING_STACK_DEPTH];
		
		for(int i = LOGGING_STACK_DEPTH; i < stack.length; i++) {
			write_log_line(caller, "STACK", stack[i].toString() );
		}
	}
}
