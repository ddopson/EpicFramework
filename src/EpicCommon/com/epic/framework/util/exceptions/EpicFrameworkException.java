package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicFrameworkException extends EpicRuntimeException {
	public EpicFrameworkException(String msg) {
		super(msg);
	}
	public EpicFrameworkException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
