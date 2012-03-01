/**
 * @author Derek Johnson
 * 
 * EpicSocialDelegate is a set of callbacks for consumers of EpicSocial.
 *
 */

package com.epic.framework.common.util;

import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public interface EpicSocialDelegate {
	public void onLoggedInToFacebook(EpicFacebookUser facebookUser);
	public void onErrorLoggingIn(EpicFrameworkException e);
	
	public void onWallPostComplete();
	public void onWallPostFailed(EpicFrameworkException e);
}
