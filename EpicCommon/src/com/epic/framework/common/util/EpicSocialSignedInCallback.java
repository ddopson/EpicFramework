package com.epic.framework.common.util;

import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicSocialSignedInCallback {
	void onSignedIn(EpicFacebookUser user);
	void onSignInFailed(EpicFrameworkException e);
}
