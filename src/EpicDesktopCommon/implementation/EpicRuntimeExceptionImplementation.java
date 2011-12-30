package com.epic.framework.implementation;

public class EpicRuntimeExceptionImplementation extends RuntimeException {
	private static final long serialVersionUID = -1998844813797918411L;
	public EpicRuntimeExceptionImplementation(String className, String msg, Throwable cause) {
		super(msg, cause);
	}
}
