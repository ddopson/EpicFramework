package com.epic.framework.common.util.exceptions;

public class EpicNotSupportedException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNotSupportedException() {
		super("EpicNotSupportedException", "no message", null);
	}
	public EpicNotSupportedException(Throwable cause) {
		super("EpicNotSupportedException", "no message", cause);
	}
	public EpicNotSupportedException(String msg) {
		super("EpicNotSupportedException", msg, null);
	}
	public EpicNotSupportedException(String msg, Throwable cause) {
		super("EpicNotSupportedException", msg, cause);
	}
}
