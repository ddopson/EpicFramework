package com.epic.framework.implementation;

import com.epic.framework.common.util.EpicFacebookUser;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicSocialFriendListCallback {
	public void onFriendListReceived(EpicFacebookUser[] friends);
	public void onFailedToReceiveFriendList(EpicFrameworkException e);
}