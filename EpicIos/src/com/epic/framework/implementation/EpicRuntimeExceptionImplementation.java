package com.epic.framework.implementation;

public class EpicRuntimeExceptionImplementation extends RuntimeException {
	private static final long serialVersionUID = 0L;
	protected EpicRuntimeExceptionImplementation(String className, String msg, Throwable cause) {
		super(msg, cause);
	}
}
