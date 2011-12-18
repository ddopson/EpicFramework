package com.epic.framework.util.exceptions;

public class EpicJSONException extends RuntimeException {
	public EpicJSONException(String msg) {
		super(msg);
	}

	public EpicJSONException(String msg, Throwable cause) {
		super(msg + " caused by: " + cause.getClass().getName() + ": " + cause.getMessage());
	}
}
