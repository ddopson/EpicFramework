package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicInvalidArgumentException extends EpicRuntimeException {
	public EpicInvalidArgumentException(String msg) {
		super(msg);
	}
}
