package com.epic.framework.common.util.exceptions;

public class EpicNotImlementedException extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public EpicNotImlementedException() {
		super("EpicNotImlementedException", "no message", null);
	}
	public EpicNotImlementedException(String msg) {
		super("EpicNotImlementedException", msg, null);
	}
	public EpicNotImlementedException(String msg, Throwable cause) {
		super("EpicNotImlementedException", msg, null);
	}
}
