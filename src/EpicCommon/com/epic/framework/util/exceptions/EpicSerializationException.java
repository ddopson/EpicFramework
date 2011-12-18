package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicSerializationException extends EpicRuntimeException {
	public EpicSerializationException() {
		super("");
	}
	public EpicSerializationException(Exception cause) {
		super("", cause);
	}
	public EpicSerializationException(String msg) {
		super(msg);
	}
}
