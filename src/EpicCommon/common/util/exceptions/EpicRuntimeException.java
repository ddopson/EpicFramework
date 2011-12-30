package com.epic.framework.common.util.exceptions;

import com.epic.framework.implementation.EpicRuntimeExceptionImplementation;

public class EpicRuntimeException extends EpicRuntimeExceptionImplementation {
	private static final long serialVersionUID = -1998844813797918422L;
	public final String message;
	public final String className;
	protected EpicRuntimeException(String className, String msg, Throwable cause) {
		super(className, msg, cause);
		this.className = className;
		this.message = msg;
	}
}
