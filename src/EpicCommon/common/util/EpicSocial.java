package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.implementation.EpicSocialImplementation;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.WordsHttp;

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
					onSignInComplete(identity, null);
					
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
	
	public static void onSignInComplete(String identity, String fbid) {
		EpicLog.i("PLAYER IDENTITY CHOSEN: '" + identity + "'");
		String un = "";
		if(identity.contains("@")) {
			un = identity.split("@")[0];
		} else {
			un = identity;
		}
		
		EpicNotification n = new EpicNotification("Welcome to Word Farm!", new String[] { "You are now playing as " + un }, EpicImages.icon);
		// EpicPlatform.doToastNotification("Welcome to Word Farm, " + identity + "!", 3000);
		EpicPlatform.doToastNotification(n);
		PlayerState.setIdentity(identity);
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

	public static void togglePush(boolean pushEnabled) {
		EpicSocialImplementation.togglePush(pushEnabled);
	}

	public static void getFacebookFriendList() {
		EpicSocialImplementation.getFacebookFriendList();
	}

	public static void promptForFbConnect() {
		// TODO Auto-generated method stub
		
	}

	public static void promptForFbConnectScores() {
		// TODO Auto-generated method stub
		
	}
}
