package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicUnhandledCaseException extends EpicRuntimeException {
	public EpicUnhandledCaseException() {
		super("unhandled case");
	}
}
