package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicNullPointerException extends EpicRuntimeException {
	public EpicNullPointerException(String msg) {
		super(msg);
	}
}
