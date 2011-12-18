package com.epic.framework.Ui;

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
	
	public EpicSound(String name, int android_id) {
		this.name = name;
		this.android_id = android_id;
		this.extension = "mp3";
	}
	
	public String getFilename() {
		// DDOPSON-2011-10-15 - oops, bug in prebuild.pl adds mp3 into the name.  lazy lazy hack...
		return this.name; // + "." + this.extension;
	}
}
