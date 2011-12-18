package com.epic.framework.common.util.exceptions;

public class EpicMissingFileException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicMissingFileException() {
		super("EpicMissingFileException", "no message", null);
	}
	public EpicMissingFileException(Throwable cause) {
		super("EpicMissingFileException", "no message", cause);
	}
	public EpicMissingFileException(String msg) {
		super("EpicMissingFileException", msg, null);
	}
	public EpicMissingFileException(String msg, Throwable cause) {
		super("EpicMissingFileException", msg, null);
	}
}
