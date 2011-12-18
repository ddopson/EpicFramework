package com.epic.framework.implementation.util;

import com.epic.framework.common.util.EpicLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class C2DMRegistrationReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		EpicLog.w("Registration Receiver called");
		if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
			EpicLog.w("Received registration ID");
			final String registrationId = intent
					.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");

			EpicLog.w("dmControl: registrationId = " + registrationId
					+ ", error = " + error);
			PlayerState.getState().setPushId(registrationId);
		}
	}
}
