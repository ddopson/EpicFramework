package com.epic.framework.common.util.exceptions;

public class EpicMissingImageException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicMissingImageException() {
		super("EpicMissingImageException", "no message", null);
	}
	public EpicMissingImageException(Throwable cause) {
		super("EpicMissingImageException", "no message", cause);
	}
	public EpicMissingImageException(String msg) {
		super("EpicMissingImageException", msg, null);
	}
	public EpicMissingImageException(String msg, Throwable cause) {
		super("EpicMissingImageException", msg, cause);
	}
}
