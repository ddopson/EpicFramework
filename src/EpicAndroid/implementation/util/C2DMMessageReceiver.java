package com.epic.framework.implementation.util;

import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.implementation.Ui.EpicApplication;

public class C2DMMessageReceiver extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
		if(!PlayerState.getState().pushEnabled) {
			EpicLog.w("Push disabled, skipping...");
			return;
		}
		
		String action = intent.getAction();
		EpicLog.w("Message Receiver called");
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			EpicLog.i("Received message");
			final String payload = intent.getStringExtra("message");
			EpicLog.i("dmControl: payload = " + payload);
			PushResponder.handlePush(payload);
			logPushExtras(intent);
			
			final String[] parts = payload.split(":");

			if(parts[0].equals("challenge")) {
		    	// challenge:<challenge id(int)>:<their name(String)>:<wager(int)>
		    	String name = "";
		    	try {
		    		String[] ppp = parts[2].split("@");
		    		name = ppp[0];
		    	} catch(Exception e) {
		    		name = parts[2];
		    	}
		    	
		    	String tokenAmount = "";
		    	try {
		    		int tc = Integer.parseInt(parts[3]);
		    		tokenAmount = StringHelper.formatIntegerWithCommas(tc);
		    	} catch(Exception e) {
		    		EpicLog.e(e.toString());
		    		tokenAmount = parts[3];
		    	}
		    	
		    	createChallengeNotification(intent.getStringExtra("alert"), name, tokenAmount, payload);
		    	
		        PlayerState.getState().incrementOpenChallenges();
			} else if(parts[0].equals("complete")) {
            	// complete:your_score:their_score:wager:rank_award:their_id:their_name:challenge_id
            	
            	String name = "";
            	try {
            		String[] ppp = parts[6].split("@");
            		name = ppp[0];
            	} catch(Exception e) {
            		name = parts[6];
            	}
            	
            	int your_score = Integer.parseInt(parts[1]);
            	int their_score = Integer.parseInt(parts[2]);
            	
            	String header = "";
            	if(your_score > their_score) {
            		header = "You won against " + name;
            	} else if(your_score == their_score) {
            		header = "You tied with " + name;
            	} else {
            		header = "You lost to " + name;
            	}
            	
            	createCompleteNotification(intent.getStringExtra("alert"), header, payload);
            }
		}
	}
	
	private void createCompleteNotification(String alert, String header, String rawMessage) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) EpicApplication.getAndroidContext().getSystemService(ns);
		
		Notification n = new Notification(EpicImages.icon.android_id, alert, System.currentTimeMillis());
		n.flags = n.flags | Notification.FLAG_AUTO_CANCEL;
        n.number = PlayerState.getState().incrementUnclickedCompletions() + PlayerState.getState().unclickedChallenges;
		Context context = EpicApplication.getAndroidContext();
		
		String contentTitle = "Your challenge is complete!";
        String contentText = "";
        int total = PlayerState.getState().unclickedChallenges + PlayerState.getState().unclickedCompletions; 
        if(total > 1) {        	
        	if(PlayerState.getState().unclickedChallenges > 0) {
        		contentText += (PlayerState.getState().unclickedChallenges == 1 ? "1 challenge" : PlayerState.getState().unclickedChallenges + " challenges");
        		if(PlayerState.getState().unclickedCompletions > 0) {
        			contentText += ", ";
        		}
        	}
        	
        	if(PlayerState.getState().unclickedCompletions > 0) {
        		contentText += PlayerState.getState().unclickedCompletions + (PlayerState.getState().unclickedCompletions == 1 ? " game" : " games") + " complete";
        	}
        } else {
            contentText = header;
        }

		Intent notificationIntent = new Intent("com.realcasualgames.words.NOTIFICATION_CLICKED");
        notificationIntent.putExtra("message", rawMessage);
        notificationIntent.addCategory("com.realcasualgames.words");
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		n.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, n);		
	}
	private void createChallengeNotification(String alert, String name, String tokens, String rawMessage) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) EpicApplication.getAndroidContext().getSystemService(ns);
       
        Notification n = new Notification(EpicImages.icon.android_id, alert, System.currentTimeMillis());
        n.flags = n.flags | Notification.FLAG_AUTO_CANCEL;
        n.number = PlayerState.getState().incrementUnclickedChallenge() + PlayerState.getState().unclickedCompletions;
        Context context = EpicApplication.getAndroidContext();
        
        String contentTitle = "You have been challenged";

        String contentText = "";
        int total = PlayerState.getState().unclickedChallenges + PlayerState.getState().unclickedCompletions; 
        if(total > 1) {        	
        	if(PlayerState.getState().unclickedChallenges > 0) {
        		contentText += (PlayerState.getState().unclickedChallenges == 1 ? "1 challenge" : PlayerState.getState().unclickedChallenges + " challenges");
        		if(PlayerState.getState().unclickedCompletions > 0) {
        			contentText += ", ";
        		}
        	}
        	
        	if(PlayerState.getState().unclickedCompletions > 0) {
        		contentText += PlayerState.getState().unclickedCompletions + (PlayerState.getState().unclickedCompletions == 1 ? " game" : " games") + " complete";
        	}
        } else {
            contentText = "by " + name + " for " + tokens + " tokens";
        }
        
        Intent notificationIntent = new Intent("com.realcasualgames.words.NOTIFICATION_CLICKED");
        notificationIntent.putExtra("message", rawMessage);
        notificationIntent.addCategory("com.realcasualgames.words");
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        n.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
       
        final int HELLO_ID = 1;

        mNotificationManager.notify(HELLO_ID, n);
	}

	private void logPushExtras(Intent intent) {
      Set<String> keys = intent.getExtras().keySet();
      for (String key : keys) {
         EpicLog.i("Push Notification Extra: ["+key+" : " + intent.getStringExtra(key) + "]");
      }
	}
}
