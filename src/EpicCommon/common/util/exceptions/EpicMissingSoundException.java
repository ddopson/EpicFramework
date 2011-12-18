package com.epic.framework.common.util.exceptions;

public class EpicMissingSoundException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicMissingSoundException() {
		super("EpicMissingSoundException", "no message", null);
	}
	public EpicMissingSoundException(Throwable cause) {
		super("EpicMissingSoundException", "no message", cause);
	}
	public EpicMissingSoundException(String msg) {
		super("EpicMissingSoundException", msg, null);
	}
	public EpicMissingSoundException(String msg, Throwable cause) {
		super("EpicMissingSoundException", msg, null);
	}
}
