package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicNoSuchElementException extends EpicRuntimeException {
	public EpicNoSuchElementException() {
		super("");
	}
	public EpicNoSuchElementException(String text) {
		super(text);
	}
}
