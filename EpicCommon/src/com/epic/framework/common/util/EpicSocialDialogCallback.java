package com.epic.framework.common.util;

import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicSocialDialogCallback {
	public void onDialogComplete();
	public void onDialogFailed(EpicFrameworkException e);
}
