package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.implementation.EpicSocialImplementation;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.WordsHttp;

public class EpicSocial {
	
	// True framework functions
	
	public interface EpicSocialSignInCompletionHandler {
		void onSignedIn(String identity);
	}
	
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
	
	/*
	 * END FACEBOOK FUNCTIONS
	 */
	
	public static String getEmailList() {
		/* Returns a comma delimited list of all users email contacts */
		// NOTE: Users of this are highly encouraged/required to ensure users give
		//		 permission before using this.
		
		return EpicSocialImplementation.getEmailList();
	}

	public static String getPlatformId() {
		/* Returns a single character describing the platform (i = iOS, a = Android, b = BlackBerry, w = Web) */
		
		return EpicSocialImplementation.getPlatformId();
	}
	
	// Belong in game delegate
	
	// Belong fully in game
	
	public static String getIdentity() {
		return PlayerState.getIdentity();
	}

	
	
	
	
	

	public static void signIn(final EpicSocialSignInCompletionHandler doAfter) {
		if(PlayerState.getIdentity() == null) {
			EpicLog.i("PLAYER IDENTITY REQUESTED");
			EpicSocialImplementation.beginLogin(new EpicSocialSignInCompletionHandler() {
				public void onSignedIn(String identity) {
					onSignInComplete(identity, null, null);
					
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
	
	public static void onSignInComplete(String identity, String displayName, String fbid) {
		EpicLog.i("PLAYER IDENTITY CHOSEN: '" + identity + "'");
		String un = "";
		if(displayName != null) {
			un = displayName;
		} else if(identity.contains("@")) {
			un = identity.split("@")[0];
		} else {
			un = identity;
		}
		
		EpicNotification n = new EpicNotification("Welcome to Word Farm!", new String[] { "You are now logged in." }, EpicImages.icon_cow);
		// EpicPlatform.doToastNotification("Welcome to Word Farm, " + identity + "!", 3000);
		EpicPlatform.doToastNotification(n);
		PlayerState.setIdentityWithFacebookId(identity, displayName, fbid);
		if(fbid != null) {
			PlayerState.setFBID(fbid);
			EpicLog.v("Set FBID: " + fbid);
		}
		
		WordsHttp.syncAccount(new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {	
				EpicLog.i("Account sync complete.");
				EpicPlatform.repaintScreen();
			}

			public void handleFailure(Exception e) {
				EpicLog.w("Failure to call syncAccount on the remote service");
			}
		});
		
		if(EpicSocialImplementation.friendList != null) {
			EpicSocialImplementation.searchFriendList(EpicSocialImplementation.friendList);
		} else {
			EpicLog.i("Friends list still null when signing in...");
		}
	}
	
	public static void switchUser(final EpicSocialSignInCompletionHandler doAfter) {
		EpicLog.i("PLAYER IDENTITY SWITCH REQUESTED");
		EpicSocialImplementation.beginLogin(new EpicSocialSignInCompletionHandler() {
			public void onSignedIn(String identity) {
				EpicLog.i("NEW PLAYER IDENTITY CHOSEN: '" + identity + "'");
				EpicNotification n = new EpicNotification("Welcome to Word Farm!", new String[] { "You are now logged in." }, EpicImages.icon_cow);
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

	public static void signOut() {
		if(PlayerState.canLogOut()) {
			PlayerState.logOut();
			EpicLog.i("PLAYER LOGGED OUT");
			EpicPlatform.doToastNotification(new EpicNotification("Logged Out", new String[] { "You have successfully logged out." }, EpicImages.icon_cow));
			EpicPlatform.repaintScreen();
		} else {
			EpicPlatform.doToastNotification(new EpicNotification("Problem Logging Out", new String[] { "You cannot log out until you sync your latest scores.", "Please complete a game and try again." }, EpicImages.icon_cow, 5));
		}
	}

	public static void togglePush(boolean pushEnabled) {
		EpicSocialImplementation.togglePush(pushEnabled);
	}

	public static void getFacebookFriendList() {
		EpicSocialImplementation.getFacebookFriendList();
	}

	public static void showAchievements() {
		EpicSocialImplementation.showAchievements();
	}

	public static void viewChallenges(int defaultListLength, String response) {
		EpicSocialImplementation.viewChallenges(defaultListLength, response);
	}

	public static void viewChallenges(int defaultListLength) {
		EpicSocialImplementation.viewChallenges(defaultListLength);
	}

	public static void promptFacebookLogin(EpicScreen screen) {
		EpicSocialImplementation.promptFacebookLogin(screen);
	}
}
