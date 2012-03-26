package com.epic.framework.common.util;

import java.io.FileNotFoundException;

import com.epic.framework.common.util.exceptions.EpicAssertionFailedException;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;
import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;
import com.epic.framework.common.util.exceptions.EpicMissingFileException;
import com.epic.framework.common.util.exceptions.EpicMissingImageException;
import com.epic.framework.common.util.exceptions.EpicNoSuchElementException;
import com.epic.framework.common.util.exceptions.EpicNotImplementedException;
import com.epic.framework.common.util.exceptions.EpicNotSupportedException;
import com.epic.framework.common.util.exceptions.EpicNullPointerException;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.framework.common.util.exceptions.EpicStringFormatException;
import com.epic.framework.common.util.exceptions.EpicUnexpectedDataException;
import com.epic.framework.common.util.exceptions.EpicUnhandledCaseException;

public class EpicFail {
	private static EpicRuntimeException log_exception(EpicRuntimeException e) {
		EpicLog.e("EpicFail: Throwing " + e.className + ": " + e.message);
//		EpicLog.logStack();
		return e;
	}

	public static EpicRuntimeException generic(String msg) {
		return log_exception( new EpicFrameworkException(msg));
	}

	public static EpicRuntimeException unhandled_case() {
		return log_exception( new EpicUnhandledCaseException());
	}
	public static EpicRuntimeException unhandled_case(String msg) {
		return log_exception( new EpicUnhandledCaseException(msg));
	}

	public static EpicRuntimeException missing_image(String imageName) {
		return log_exception( new EpicMissingImageException(imageName));
	}
	
	public static EpicRuntimeException missing_image(String imageName, Exception cause) {
		return log_exception( new EpicMissingImageException(imageName, cause));
	}

	public static EpicRuntimeException framework(String msg) {
		return log_exception( new EpicFrameworkException(msg));
	}

	public static EpicRuntimeException framework(String msg, Throwable cause) {
		return log_exception( new EpicFrameworkException(msg, cause));
	}

	public static EpicRuntimeException not_implemented() {
		return not_implemented("");
	}
	public static EpicRuntimeException not_implemented(String msg) {
		return log_exception( new EpicNotImplementedException());
	}

	public static EpicRuntimeException null_pointer() {
		return log_exception( new EpicNullPointerException());
	}

	public static EpicRuntimeException invalid_argument(String string) {
		return log_exception( new EpicInvalidArgumentException(string));
	}

	//	public static EpicRuntimeException invalid_argument(String string, Object arg) {
	//		return log_exception( new EpicInvalidArgumentException(string + " (arg=" + arg + ")"));
	//	}

	public static EpicRuntimeException invalid_argument(String string, float arg) {
		return log_exception( new EpicInvalidArgumentException(string + " (arg=" + arg + ")"));
	}

	public static void assertBounds(int lower, int value, int upper) {
		assertBounds(lower, value, upper, "");
	}
	
	public static void assertBounds(int lower, int value, int upper, String msg) {
		if(!(lower <= value && value < upper)) {
			throw EpicFail.invalid_argument("AssertBoundsFailure: (" + lower + " <= " + value+" < " + upper + "). " + msg);
		}
	}

	public static void assertState(int state, int expected) {
		if(!(state == expected)) {
			throw EpicFail.invalid_argument("InvalidState: "+state+", expected: "+expected);
		}
	}

	public static EpicRuntimeException assertionFailed(String msg) {
		throw log_exception ( new EpicAssertionFailedException(msg));
	}

	public static void assertTrue(boolean condition, String conditionText) {
		if(!condition) {
			throw EpicFail.assertionFailed("Assertion '"+conditionText+"' Failed!");
		}
	}

	public static <T> T assertNotNull(T object) {
		return assertNotNull(object, "unknown");
	}
	public static <T> T assertNotNull(T object, String message) {
		if(object == null) {
			throw EpicFail.assertionFailed("Object was null: " + message);
		}
		return object;
	}

	public static void assertEqual(int a, int b, String msg) {
		if(a != b) {
			throw EpicFail.assertionFailed(StringHelper.format("%s - '%d' != '%d'", msg, a, b));
		}
	}

	public static EpicRuntimeException stringFormat(String msg) {
		return new EpicStringFormatException(msg);
	}

	public static EpicRuntimeException noSuchElement() {
		throw log_exception( new EpicNoSuchElementException() );
	}

	public static EpicRuntimeException unexpected() {
		throw log_exception( new EpicUnexpectedDataException() );
	}

	public static EpicRuntimeException not_supported() {
		return log_exception( new EpicNotSupportedException() );
	}

	public static EpicRuntimeException missing_file(String filename, FileNotFoundException e) {
		return log_exception(new EpicMissingFileException(filename, e));
	}
}
