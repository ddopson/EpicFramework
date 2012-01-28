package com.epic.framework.implementation;

import org.xmlvm.iphone.SKProduct;
import org.xmlvm.iphone.SKRequest;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicSocial;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.Challenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.PushResponder;
import com.realcasualgames.words.ScreenConnect;
import com.realcasualgames.words.ScreenGame;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;
import com.realcasualgames.words.WordsHttp;

public class EpicSocialImplementation {

	public static String friendList;
	
	public static String getUniqueUserId() {
		return "unique_user_id";
	}
		
	public static String getPlatformId() {
		return "i";
	}
	
	public static String getDisplayNameFromEmail(String email) {
		return email;
	}
	
	public static String getEmailList() {
		return "";
	}
	
	public static String[] getDisplayNamesFromEmails(String[] names_to_lookup) {
		return new String[] { };
	}
//
//	public static String chooseContact() {
//		return "testing@wordfarmgame.com";
//	}
	
	public static void promptFacebookLogin() {
		EpicPlatform.changeScreen(new ScreenMainMenu());
		EpicPlatform.doToastNotification(new EpicNotification("Connecting to Facebook", new String[] { "Please wait while we complete your login." }, EpicImages.game_word_puzzle_achievementmedal_publicchallenge, 2));
		EpicPlatformImplementationNative.loginToFacebook();
	}
	
	public static void selectFromEmailList(String[] strings) {
	}

	public static void signOut() {
	}

	public static boolean supportsFacebookPost() {
		return EpicPlatform.isAndroid() || EpicPlatform.isIos();
	}

	public static void beginLogin(EpicSocialSignInCompletionHandler epicSocialSignInCompletionHandler) {
		// epicSocialSignInCompletionHandler.onSignedIn(chooseContact());
		EpicPlatform.changeScreen(new ScreenConnect(epicSocialSignInCompletionHandler));
	}
	
	public static void viewChallenges() {
		viewChallenges(25);
	}
	
	public static void viewChallenges(String cached_list) {
	}
	
	public static void viewChallenges(int amount) {
		EpicSocialTabbedView s = new EpicSocialTabbedView(null);
//		Main.window.setRootViewController(s);
//		Main.window.makeKeyAndVisible();
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}
	
	public static void processResponse(final String response) {
	}
	

	protected static void displayOnlineLeaderboard(final String cached_list) {
	}

	public static void issueChallenge() {
	}
	
	public static void selectWagerAndSendChallengeTo(final String opponent_id) {
	}
	
	public static void onProcessContactChosen(String email) {
	}

	public static void promptForTokens() {
	}

	public static void getFacebookFriendList() {
	}

	public static void togglePush(boolean pushEnabled) {
	}

	public static void postToFacebook(String title, String url, String caption, String imageUrl, EpicClickListener callback) {
		EpicLog.v("Sending native request to post to wall.");
		EpicPlatformImplementationNative.postToFacebook(title);
		callback.onClick();
	}

	public static void showAchievements() {
		EpicAchievementsTabView s = new EpicAchievementsTabView();
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

	public static void viewChallenges(int defaultListLength, String platformResponseObject) {
		EpicSocialTabbedView s = new EpicSocialTabbedView(platformResponseObject);
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

	private static void nativecbFacebookLoginFinishedWithId(String username) {
		if(PlayerState.getIdentity() != null && PlayerState.getState().fbid != null) {
			EpicLog.w("Skipping login from Facebook due to identity being set already.");
			return;
		}
		
		final String[] parts = username.split("#");
		EpicLog.i("FB callback returned username to java: " + parts[0] + " and id: " + parts[1]);
		EpicPlatform.changeScreen(new ScreenMainMenu());
		
		WordsHttp.checkForFacebookAccount(parts[1], new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				EpicLog.i("Got response from FB ID check");
				if(response.body != null && response.body.length() > 0) {
					EpicSocial.onSignInComplete(response.body, parts[1]);
					EpicLog.i("Found existing account... signing in.");
				} else {
					EpicSocial.onSignInComplete(parts[0] + "@wordfarmgame.com", parts[1]);
					EpicLog.i("No account found, creating new.");
				}
			}
			
			public void handleFailure(Exception e) {
				EpicSocial.onSignInComplete(parts[0] + "@wordfarmgame.com", parts[1]);
				EpicLog.e("Error getting accounts for FBID: " + e.toString());
			}
		});
	}
	
	private static void nativecbNotificationReceived(String payload) {
		PushResponder.handlePushWhileOpen(payload);
	}
	
	private static void nativeCbFacebookAddFriendComplete() {
		PlayerState.onChallengeComplete(31);
	}
	
	private static void nativecbLoadChallengeDetails(String payload) {
		// TODO: parse payload for challenge id
		
		final String[] parts = payload.split(":");
		String challengeId = "";
	    if(parts[0].equals("challenge")) {
	    	// challenge:<challenge id(int)>:<their name(String)>:<wager(int)>
	    	challengeId = parts[1];
	    } else if(parts[0].equals("complete")) {
	    	// complete:your_score:their_score:wager:rank_award:their_id:their_name:challenge_id
	    	challengeId = parts[7];
	    }
	    
		EpicPlatform.changeScreen(new ScreenOnlineChallengeDetails(challengeId, null));
	}
	
	private static void nativecbSetAPNID(String apnId) {
		EpicLog.i("Got APN of " + apnId);
		 PlayerState.setAPNID(apnId);
	}
	
	private static void nativeCbFacebookFriendList(String friendsString) {
		friendList = friendsString;
		if(PlayerState.getIdentity() != null) {
			searchFriendList(friendsString);
		} else {
			EpicLog.w("No identity, not loading friends list yet.");
		}
	}
	
	private static void nativeCbFacebookPostComplete() {
		PlayerState.updateLocalTokens(500);
		PlayerState.onChallengeComplete(30);
	}

	public static void searchFriendList(String friendsString) {
		EpicLog.i("Searching friend list of length: " + friendsString.length());
		WordsHttp.findFriends(friendsString, new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				String[] parts = response.body.split(";");
				EpicLog.v("Found " + parts.length + " friends");
//				final String[] names = new String[parts.length];
//				final String[] ids = new String[parts.length];
//				
//				for(int i = 0; i < parts.length; ++i) {
//					String[] pieces = parts[i].split(":");
//					names[i] = pieces[0];
//					ids[i] = pieces[1];
//				}
//				
				EpicNotification n = new EpicNotification(parts.length + " friends playing Word Farm!", new String[] { "Click here to challenge them!" });
				n.clickCallback = new EpicClickListener() {
					public void onClick() {
						EpicSocialImplementation.viewChallenges();
					}
				};
				EpicPlatform.doToastNotification(n);
			}
			
			public void handleFailure(Exception e) {
				EpicLog.e("Failure processing friends list: " + e.toString());
				EpicPlatform.doToastNotification(new EpicNotification("Problem Searching For Friends", new String[] { "Please try again later or contact support."}));
			}
		});
	}
}
