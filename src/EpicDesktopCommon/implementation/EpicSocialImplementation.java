package com.epic.framework.implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;

public class EpicSocialImplementation {
	private static String hostname = null;
	public static Object friendList;
	public static String getHostname() {
		if(hostname == null) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				hostname = addr.getHostName();
			} catch (UnknownHostException e) {
				EpicLog.w("Failed to get local hostname", e);
			}		
		}
		return hostname;
	}
	public static String getUniqueUserId() {
		if(getHostname().contains("Dave-Dopson")) {
			return "ddopson@gmail.com";
		}
		else if(getHostname().contains("ddopson")) {
				return "ddopson@gmail.com";
		}
		else if(getHostname().contains("powpow")){
			return "derekj212@gmail.com";
		}
		else {
			return "unknown-user";
		}
	}

	public static String getPlatformId() {
		return "w";
	}

	public static String getDisplayNameFromEmail(String email) {
		return email;
	}

	public static String getEmailList() {
		return "derek@epicapplications.com,wowthatisrandom@gmail.com,laurajgunderson@gmail.com,ddopson@gmail.com";
	}

	public static String[] getDisplayNamesFromEmails(String[] names_to_lookup) {
//		String[] results = new String[names_to_lookup.length];
//		for(int i = 0; i < names_to_lookup.length; ++i) {
//			results[i] = EpicSocial.getDisplayNameFromEmail(names_to_lookup[i]);
//		}

		// TODO: Skip this for now
		return names_to_lookup;
	}
	public static String chooseContact() {
		return "wowthatisrandom@gmail.com";
	}
	public static void selectFromEmailList(String[] strings) {
		
	}
	public static void beginLogin(EpicSocialSignInCompletionHandler epicSocialSignInCompletionHandler) {
		epicSocialSignInCompletionHandler.onSignedIn(getUniqueUserId());
	}
	public static void postToFacebook(String title, String url, String caption,
			String imageUrl, EpicClickListener callback) {
		
	}
	public static boolean supportsFacebookPost() {
		return false;
	}
	public static void getFacebookFriendList() {
		// TODO Auto-generated method stub
		
	}
	public static void togglePush(boolean pushEnabled) {
		// TODO Auto-generated method stub
		
	}
	public static void viewChallenges(int defaultListLength) {
		// TODO Auto-generated method stub
		
	}
	public static void promptForTokens() {
		// TODO Auto-generated method stub
		
	}
	public static void searchFriendList(Object friendList2) {
		// TODO Auto-generated method stub
		
	}
	public static void promptFacebookLogin() {
		// TODO Auto-generated method stub
		
	}
	public static void showAchievements() {
		// TODO Auto-generated method stub
		
	}
	public static void viewChallenges(int defaultListLength, String response) {
		// TODO Auto-generated method stub
		
	}
}
