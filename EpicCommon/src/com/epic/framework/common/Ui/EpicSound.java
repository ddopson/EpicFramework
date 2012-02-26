package com.epic.framework.common.Ui;

public class EpicSound {
	public final String name;
	public final String extension;
	public final int android_id;
	public Object platformObject;
	
	public EpicSound(String name, String extension, int android_id) {
		this.name = name;
		this.extension = extension;
		this.android_id = android_id;
	}
	
	// DDOPSON-2011-10-15 - oops, bug in prebuild.pl adds mp3 into the name.  lazy lazy hack...
	public EpicSound(String name, int android_id) {
		int dotpos = name.indexOf('.');
		this.name = name.substring(0, dotpos);
		this.android_id = android_id;
		this.extension = name.substring(dotpos + 1);
	}
	
	public String getFilename() {
		return this.name + "." + this.extension;
	}
}
