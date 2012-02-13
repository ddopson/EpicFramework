package com.epic.framework.common.util.exceptions;

public class EpicAssertionFailedException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicAssertionFailedException() {
		super("EpicAssertionFailedException", "no message", null);
	}
	public EpicAssertionFailedException(Throwable cause) {
		super("EpicAssertionFailedException", "no message", cause);
	}
	public EpicAssertionFailedException(String msg) {
		super("EpicAssertionFailedException", msg, null);
	}
	public EpicAssertionFailedException(String msg, Throwable cause) {
		super("EpicAssertionFailedException", msg, cause);
	}
}
