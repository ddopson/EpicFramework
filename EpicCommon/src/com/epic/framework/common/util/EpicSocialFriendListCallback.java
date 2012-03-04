package com.epic.framework.common.util;

import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicSocialFriendListCallback {
	public void onFriendListReceived(EpicFacebookUser[] friends);
	public void onFailedToReceiveFriendList(EpicFrameworkException e);
}