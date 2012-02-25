package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.implementation.EpicSocialImplementation;

public class EpicSocial {	
	
	/*
	 * FACEBOOK FUNCTIONS
	 */
	
	public static boolean supportsFacebookPost() {
		/* Determines if Facebook is available on the device. Set in each platform implementation file */
		return EpicSocialImplementation.supportsFacebookPost();
	}
	
	public static void postToFacebook(String title, String url, String caption, String imageUrl, EpicClickListener callback) {
		if(supportsFacebookPost()) EpicSocialImplementation.postToFacebook(title, url, caption, imageUrl, callback);
	}

	public static void getFacebookFriendList() {
		/* Prompts return of Facebook friend list through an async method. A bit random still. */
		EpicSocialImplementation.getFacebookFriendList();
	}
	
	public static void promptFacebookLogin(EpicScreen screen) {
		EpicSocialImplementation.promptFacebookLogin(screen);
	}
	
	/*
	 * LOCAL CONTACT FUNCTIONS
	 */
	
	public static String getEmailList() {
		/* Returns a comma delimited list of all users email contacts */
		// NOTE: Users of this are highly encouraged/required to ensure users give
		//		 permission before using this.
		
		return EpicSocialImplementation.getEmailList();
	}
}
