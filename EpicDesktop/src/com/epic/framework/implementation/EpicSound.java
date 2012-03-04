package com.epic.framework.implementation;

import com.epic.framework.common.Ui2.EpicObject;


public class EpicSound extends EpicObject {

	private static final int VALUE_NOT_SET = -1;
	public String filename;
	public int id;

	public EpicSound(String filename) {
		this.filename = filename;
		this.id = VALUE_NOT_SET;
	}

}
