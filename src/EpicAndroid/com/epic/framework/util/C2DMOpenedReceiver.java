package com.epic.framework.util;

import com.epic.framework.Ui.EpicAndroidActivity;
import com.epic.framework.Ui.EpicApplication;
import com.epic.framework.Ui.EpicPlatform;
import com.epic.framework.Ui.EpicTimer;
import com.realcasualgames.words.OnlineChallenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class C2DMOpenedReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		EpicLog.w("Opened Receiver called");
		EpicLog.i("User clicked notification. Message: " + intent.getStringExtra("alert"));

        Intent launch = new Intent(Intent.ACTION_MAIN);
        launch.setClass(EpicApplication.getAndroidContext(), EpicAndroidActivity.class);
        launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        String message = intent.getStringExtra("message");
        final String[] parts = message.split(":");
        if(parts[0].equals("challenge")) {
        	launch.putExtra("launchScreen", "challenge");
        	launch.putExtra("challengeId", parts[1]);
        } else if(parts[0].equals("complete")) {
        	// complete:your_score:their_score:wager:rank_award:their_id:their_name:challenge_id
        	launch.putExtra("launchScreen", "complete");
        	launch.putExtra("challengeId", parts[7]);
        }	
       
        EpicApplication.getAndroidContext().startActivity(launch);        

        EpicPlatform.clearNotifications();
	}
}
