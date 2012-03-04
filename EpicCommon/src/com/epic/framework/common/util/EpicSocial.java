/**
 * @author Derek Johnson
 * 
 * EpicSocial provides for a set of social network features for consumers. The initial
 * implementation focuses on Facebook, and provides simple functions for logging in,
 * requesting friend lists, posting to a users timeline, and sending app requests.
 * 
 * For more advanced users, functions are provided to directly pass commands through 
 * to the Facebook Graph API, and return raw JSON results.
 * 
 */

package com.epic.framework.common.util;

import com.epic.framework.common.util.callbacks.EpicSocialDialogCallback;
import com.epic.framework.common.util.callbacks.EpicSocialFriendListCallback;
import com.epic.framework.common.util.callbacks.EpicSocialSignedInCallback;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;
import com.epic.framework.implementation.EpicSocialImplementation;

public class EpicSocial {
	/**
	 * Begins the asynchronous login process to Facebook, using Single Sign On (SSO), 
	 * if available. This method should generally not be called directly, as other
	 * Facebook related utility methods in EpicSocial will handle login transparently.
	 * 
	 * For customers without the Facebook client installed, a browser window will appear
	 * to log in. For users with the client, SSO will be used to log in.
	 * 
	 * @param callback 
	 * 
	 */
	public void beginLoginToFacebook(EpicSocialSignedInCallback callback) {
		EpicSocialImplementation.beginLoginToFacebook(callback);
	}
	
	/**
	 * Begins the asynchronous post to Facebook wall with the given parameters.
	 * 
	 * @param title Main title in Facebook post
	 * @param description Caption given to Facebook post (optional)
	 * @param imageUrl Web-accessible URL for icon file to use for post (optional)
	 */
	public void beginPostToFacebookWallDialog(String title, String description, String imageUrl, EpicSocialDialogCallback callback) {
		if(title == null || title.length() == 0) {
			callback.onDialogFailed(new EpicFrameworkException("Title cannot be null or 0-length."));
		}
		
		EpicSocialImplementation.beginPostToFacebookWall(title, description, imageUrl, callback);
	}
	
	/**
	 * Prompt a Facebook App Requests dialog (https://developers.facebook.com/docs/reference/dialogs/requests/)
	 * 
	 * @param message Message to be displayed alongside request
	 * @param callback
	 * 
	 */
	public void sendFacebookAppRequestsDialog(String message, EpicSocialDialogCallback callback) {
		EpicSocialImplementation.beginInviteFacebookFriends(message, callback);
	}
	
	/**
	 * Begins a Facebook request for a list of the currently signed in users' friends. Results
	 * are parsed into EpicFacebookUser objects for easy of use.
	 * 
	 * @param callback
	 */
	public void getFacebookFriendList(EpicSocialFriendListCallback callback) {
		EpicSocialImplementation.beginGetFacebookFriendList(callback);
	}
	
	// TODO: Implement
	public void sendRawFacebookGraphApiRequest(String action) {}
	
}