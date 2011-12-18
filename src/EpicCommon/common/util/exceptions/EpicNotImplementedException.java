package com.epic.framework.common.util.exceptions;

public class EpicNotImplementedException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNotImplementedException() {
		super("EpicNotImplementedException", "no message", null);
	}
	public EpicNotImplementedException(Throwable cause) {
		super("EpicNotImplementedException", "no message", cause);
	}
	public EpicNotImplementedException(String msg) {
		super("EpicNotImplementedException", msg, null);
	}
	public EpicNotImplementedException(String msg, Throwable cause) {
		super("EpicNotImplementedException", msg, null);
	}
}
