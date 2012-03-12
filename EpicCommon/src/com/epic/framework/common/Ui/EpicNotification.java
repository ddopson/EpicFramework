package com.epic.framework.common.Ui;

public class EpicNotification {

	public static final int NOTIFICATION_TOP_PAD = 10;
	public static final int NOTIFICATION_HEIGHT = 80;
	public static final int NOTIFICATION_WIDTH = 680;
	public static final int NOTIFICATION_LEFT_PAD = 60;
	public EpicClickListener clickCallback;
	public String title;
	public String[] messages;
	public EpicImage icon;
	public int duration;
	public boolean minimized;
	
	public EpicNotification(String title, String[] messages, EpicImage icon, int duration) {
		this.title = title;
		this.messages = messages;
		this.icon = icon;
		this.duration = duration;
	}
	
	public EpicNotification(String title, String[] messages, EpicImage icon) {
		this(title, messages, icon, 3);
	}
	
	public EpicNotification(String title, String[] messages) {
		this(title, messages, null);
	}	
}
