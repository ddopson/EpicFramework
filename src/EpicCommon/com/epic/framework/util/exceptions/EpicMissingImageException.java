package com.epic.framework.util.exceptions;

import com.epic.framework.util.EpicRuntimeException;

public class EpicMissingImageException extends EpicRuntimeException {
	public EpicMissingImageException(String imageName) {
		super("Missing Image: " + imageName);
	}
	public EpicMissingImageException(String imageName, Exception cause) {
		super("Missing Image: " + imageName, cause);
	}
}
