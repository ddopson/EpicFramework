package com.epic.framework.implementation;

import java.util.HashMap;

import com.epic.framework.common.util.EpicFacebookUser;
import com.epic.framework.common.util.EpicSocialDialogCallback;
import com.epic.framework.common.util.EpicSocialFriendListCallback;
import com.epic.framework.common.util.EpicSocialSignedInCallback;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;

public class EpicSocialImplementation {
	
	// Callback for notifications (optional, but would be odd not to use)
	public static EpicSocialSignedInCallback signInCallback = null;
	public static EpicSocialDialogCallback dialogCallback = null;
	public static EpicSocialFriendListCallback friendListCallback = null;
	
	public static void beginLoginToFacebook(EpicSocialSignedInCallback callback) {
		signInCallback = callback;
		EpicPlatformImplementationNative.loginToFacebook();
	}
	
	public static void beginPostToFacebookWall(String title, String description, String imageUrl, EpicSocialDialogCallback callback) {
		dialogCallback = callback;
		EpicPlatformImplementationNative.postToFacebook(title, description, imageUrl);
	}

	public static void beginGetFacebookFriendList(EpicSocialFriendListCallback callback) {
		friendListCallback = callback;
		EpicPlatformImplementationNative.getFacebookFriendList();
	}
	
	public static void beginInviteFacebookFriends(String message, EpicSocialDialogCallback callback) {
		dialogCallback = callback;
		EpicPlatformImplementationNative.requestFacebookFriends(message);
	}
	
	/*
	 * Native return functions 
	 */
	
	@SuppressWarnings("unused")
	private static void nativecbFacebookLoginFinishedWithId(HashMap<Object, Object> properties) {
		EpicFacebookUser user = new EpicFacebookUser();
		if(properties.containsKey("id")) {
			user.facebookId = (String) properties.get("id");	
		} else {
			if(signInCallback != null) {
				signInCallback.onSignInFailed(new EpicFrameworkException("Facebook ID is not set in response."));
			}
			return;
		}
		
		if(properties.containsKey("name")) {
			user.displayName = (String) properties.get("name");	
		} else {
			if(signInCallback != null) {
				signInCallback.onSignInFailed(new EpicFrameworkException("Facebook display name is not set in response."));
			}
			return;
		}
		
		if(signInCallback != null) {
			signInCallback.onSignedIn(user);
		}
	}
	
	@SuppressWarnings("unused")
	private static void nativeCbFacebookPostComplete() {
		if(dialogCallback != null) {
			dialogCallback.onDialogComplete();
		}
	}
	
	@SuppressWarnings("unused")
	private static void nativeCbFacebookAddFriendComplete() {
		if(dialogCallback != null) {
			dialogCallback.onDialogComplete();
		}
	}
	
	@SuppressWarnings("unused")
	private static void nativeCbFacebookFriendList(EpicFacebookUser[] friends) {
		if(friendListCallback != null) {
			friendListCallback.onFriendListReceived(friends);
		}
	}
}
