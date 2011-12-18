package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicUnexpectedDataException extends EpicRuntimeException {
	public EpicUnexpectedDataException() {
		super("");
	}
	public EpicUnexpectedDataException(String msg) {
		super(msg);
	}
	public EpicUnexpectedDataException(String msg, Throwable t) {
		super(msg, t);
	}
}
