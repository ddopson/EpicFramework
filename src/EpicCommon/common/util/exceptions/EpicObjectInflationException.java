package com.epic.framework.common.util.exceptions;

public class EpicObjectInflationException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicObjectInflationException() {
		super("EpicObjectInflationException", "no message", null);
	}
	public EpicObjectInflationException(Throwable cause) {
		super("EpicObjectInflationException", "no message", cause);
	}
	public EpicObjectInflationException(String msg) {
		super("EpicObjectInflationException", msg, null);
	}
	public EpicObjectInflationException(String msg, Throwable cause) {
		super("EpicObjectInflationException", msg, null);
	}
}
