package com.epic.framework.common.util;

import java.util.LinkedList;

import com.epic.framework.implementation.EpicPlatformImplementation;

public class EpicPushNotifications {

	public static LinkedList<EpicPushNotificationListener> listeners = new LinkedList<EpicPushNotificationListener>();
	
	public static void getPushId(EpicPushNotificationCallback callback) {
		EpicPlatformImplementation.getPushId(callback);
	}
	
	public static void addPushListener(EpicPushNotificationListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	// NOTE: This function should be called by native code.
	public static void onMessageReceived(String payload) {
		if(listeners.isEmpty()) {
			EpicLog.w("Push message reeceived, but no listeners registered. Are you sure you registered " +
					"your EpicPushNotificationListener with EpicPushNotification.addPushListener()?");
		} else {
			for(EpicPushNotificationListener l : listeners) {
				l.onMessageReceived(payload);
			}
		}
	}
}
