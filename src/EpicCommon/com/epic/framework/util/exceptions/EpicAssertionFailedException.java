package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicAssertionFailedException extends EpicRuntimeException {
	public EpicAssertionFailedException(String msg) {
		super(msg);
	}
}
