package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;

public class EpicSocialImplementation {

	public static String getUniqueUserId() {
		return "unique_user_id";
	}
		
	public static String getPlatformId() {
		return "b";
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
		return "chosen_contact";
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
		epicSocialSignInCompletionHandler.onSignedIn(chooseContact());
	}
	
	public static void viewChallenges() {
	}
	
	public static void viewChallenges(String cached_list) {
	}
	
	public static void viewChallenges(int amount) {
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

}
