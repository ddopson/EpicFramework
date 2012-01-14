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
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.Challenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.PushResponder;
import com.realcasualgames.words.ScreenConnect;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;
import com.realcasualgames.words.WordsHttp;

public class EpicSocialImplementation {

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
		return EpicPlatform.isAndroid();
	}

	public static void postToFacebook(String title, String url, String caption, String imageUrl) {
		EpicLog.v("Sending native request to post to wall.");
		EpicPlatformImplementationNative.postToWall(title);
	}

	public static void beginLogin(EpicSocialSignInCompletionHandler epicSocialSignInCompletionHandler) {
		// epicSocialSignInCompletionHandler.onSignedIn(chooseContact());
		EpicPlatform.changeScreen(new ScreenConnect(epicSocialSignInCompletionHandler));
	}
	
	public static void viewChallenges() {
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
		EpicPlatformImplementationNative.postToWall(title);
		callback.onClick();
	}

	public static void showAchievements() {
		
		String[] titles = new String[Challenge.challenges.length];
		String[] subtitles = new String[Challenge.challenges.length];
		EpicBitmap[] images = new EpicBitmap[Challenge.challenges.length];
		boolean[] completed = new boolean[Challenge.challenges.length];
		Challenge[] orderedChallenges = Challenge.getOrderedArray();
		
		for(int i = 0; i < orderedChallenges.length; ++i) {
			titles[i] = orderedChallenges[i].getTitle() + " - " + orderedChallenges[i].getProgress();
			subtitles[i] = orderedChallenges[i].getDescription();
			images[i] = orderedChallenges[i].getImage();
			completed[i] = orderedChallenges[i].isComplete();
		}
		
		EpicChallengesTableView s = new EpicChallengesTableView(titles, subtitles, completed, images);
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

	public static void viewChallenges(int defaultListLength, String platformResponseObject) {
		EpicSocialTabbedView s = new EpicSocialTabbedView(platformResponseObject);
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

	private static void nativecbFacebookLoginFinishedWithId(String username) {
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
	
	private static void nativecbLoadChallengeDetails(String payload) {
		// TODO: parse payload for challenge id
		String challengeId = payload;
		EpicPlatform.changeScreen(new ScreenOnlineChallengeDetails(challengeId, null));
	}
	
	private static void nativecbSetAPNID(String apnId) {
		EpicLog.i("Got APN of " + apnId);
		 PlayerState.setAPNID(apnId);
	}
}
