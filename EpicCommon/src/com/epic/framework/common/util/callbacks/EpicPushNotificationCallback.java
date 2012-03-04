package com.epic.framework.common.util.callbacks;

import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicPushNotificationCallback {
	public void onPushIdReceived(String pushId);
	public void onErrorGettingPushId(EpicFrameworkException e);
}
