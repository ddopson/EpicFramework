package com.epic.framework.common.util.exceptions;

public class EpicJSONException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicJSONException() {
		super("EpicJSONException", "no message", null);
	}
	public EpicJSONException(Throwable cause) {
		super("EpicJSONException", "no message", cause);
	}
	public EpicJSONException(String msg) {
		super("EpicJSONException", msg, null);
	}
	public EpicJSONException(String msg, Throwable cause) {
		super("EpicJSONException", msg, null);
	}
}
