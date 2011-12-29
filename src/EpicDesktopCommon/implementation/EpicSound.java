package com.epic.framework.implementation


public class EpicSound {

	private static final int VALUE_NOT_SET = -1;
	public String filename;
	public int id;

	public EpicSound(String filename) {
		this.filename = filename;
		this.id = VALUE_NOT_SET;
	}

}
