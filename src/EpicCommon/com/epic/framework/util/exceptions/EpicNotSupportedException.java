package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicNotSupportedException extends EpicRuntimeException {
	public EpicNotSupportedException() {
		super("");
	}
	public EpicNotSupportedException(String msg) {
		super(msg);
	}
	public EpicNotSupportedException(String msg, Throwable t) {
		super(msg,t);
	}
}
