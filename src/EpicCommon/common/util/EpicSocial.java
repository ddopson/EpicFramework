package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.implementation.EpicSocialImplementation;

public class EpicSocial {
	public static String getIdentity() {
		return PlayerState.getIdentity();
	}

	public interface EpicSocialSignInCompletionHandler {
		void onSignedIn(String identity);
	}
	
	public static boolean supportsFacebookPost() {
		return EpicSocialImplementation.supportsFacebookPost();
	}
	
	public static void postToFacebook(String title, String url, String caption, String imageUrl, EpicClickListener callback) {
		if(supportsFacebookPost()) EpicSocialImplementation.postToFacebook(title, url, caption, imageUrl, callback);
	}

	public static void signIn(final EpicSocialSignInCompletionHandler doAfter) {
		if(PlayerState.getIdentity() == null) {
			EpicLog.i("PLAYER IDENTITY REQUESTED");
			EpicSocialImplementation.beginLogin(new EpicSocialSignInCompletionHandler() {
				public void onSignedIn(String identity) {
					EpicLog.i("PLAYER IDENTITY CHOSEN: '" + identity + "'");
					EpicNotification n = new EpicNotification("Welcome to Word Farm!", new String[] { "You are now playing as " + identity }, EpicImages.icon);
					// EpicPlatform.doToastNotification("Welcome to Word Farm, " + identity + "!", 3000);
					EpicPlatform.doToastNotification(n);
					PlayerState.setIdentity(identity);
					if(doAfter != null) {
						doAfter.onSignedIn(identity);
					}
				}
			});
		}
		else {
			doAfter.onSignedIn(PlayerState.getIdentity());
		}
	}
	
	public static void switchUser(final EpicSocialSignInCompletionHandler doAfter) {
		EpicLog.i("PLAYER IDENTITY SWITCH REQUESTED");
		EpicSocialImplementation.beginLogin(new EpicSocialSignInCompletionHandler() {
			public void onSignedIn(String identity) {
				EpicLog.i("NEW PLAYER IDENTITY CHOSEN: '" + identity + "'");
				EpicNotification n = new EpicNotification("Welcome to Word Farm!", new String[] { "You are now playing as " + identity }, EpicImages.icon);
				//EpicPlatform.doToastNotification("Welcome to Word Farm, " + identity + "!", 3000);
				EpicPlatform.doToastNotification(n);
				PlayerState.setIdentity(identity);
				if(doAfter != null) {
					doAfter.onSignedIn(identity);
				}
			}
		});
	
	}
	
	public static boolean isLoggedIn() {
		return PlayerState.getIdentity() != null;
	}

	public static String getEmailList() {
		return EpicSocialImplementation.getEmailList();
	}

	public static String getPlatformId() {
		return EpicSocialImplementation.getPlatformId();
	}
	
	public static String getDisplayNameFromEmail(String email) {
		return EpicSocialImplementation.getDisplayNameFromEmail(email);
	}

	public static String[] getDisplayNamesFromEmails(String[] names_to_lookup) {
		return names_to_lookup;
		// return EpicSocialImplementation.getDisplayNamesFromEmails(names_to_lookup);
	}

//	public static String chooseContact() {
//		return EpicSocialImplementation.chooseContact();
//	}

	public static void onContactEmailReturned(String[] strings) {
		EpicSocialImplementation.selectFromEmailList(strings);
	}

	public static void signOut() {
		if(PlayerState.canLogOut()) {
			PlayerState.logOut();
			EpicLog.i("PLAYER LOGGED OUT");
			EpicPlatform.doToastNotification(new EpicNotification("Logged Out", new String[] { "You have successfully logged out." }, EpicImages.icon));
			EpicPlatform.repaintScreen();
		} else {
			EpicPlatform.doToastNotification(new EpicNotification("Problem Logging Out", new String[] { "You cannot log out until you sync your latest scores.", "Please complete a game and try again." }, EpicImages.icon, 5));
		}
	}
}
