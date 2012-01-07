package com.epic.framework.implementation;

import org.xmlvm.iphone.SKProduct;
import org.xmlvm.iphone.SKRequest;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;
import com.realcasualgames.words.Challenge;
import com.realcasualgames.words.ScreenConnect;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;

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

	public static String chooseContact() {
		return "testing@wordfarmgame.com";
	}
	
	public static void selectFromEmailList(String[] strings) {
	}

	public static void signOut() {
	}

	public static boolean supportsFacebookPost() {
		return false;
	}

	public static void postToFacebook(String title, String url, String caption, String imageUrl) {
		
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
		EpicSocialTabbedView s = new EpicSocialTabbedView(new ListDataSource[3]);
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
	}

	public static void showAchievements() {
		
		String[] titles = new String[Challenge.challenges.length];
		String[] subtitles = new String[Challenge.challenges.length];
		EpicBitmap[] images = new EpicBitmap[Challenge.challenges.length];
		Challenge[] orderedChallenges = Challenge.getOrderedArray();
		
		for(int i = 0; i < orderedChallenges.length; ++i) {
			titles[i] = orderedChallenges[i].getTitle();
			subtitles[i] = orderedChallenges[i].getProgress();
			images[i] = orderedChallenges[i].getImage();
		}
		
		EpicChallengesTableView s = new EpicChallengesTableView(titles, subtitles, images);
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

	public static void viewChallenges(int defaultListLength, Object[] platformResponseObject) {
		EpicSocialTabbedView s = new EpicSocialTabbedView(platformResponseObject);
		Main.navc.pushViewController(s, true);
		Main.navc.setNavigationBarHidden(false, true);
	}

}
