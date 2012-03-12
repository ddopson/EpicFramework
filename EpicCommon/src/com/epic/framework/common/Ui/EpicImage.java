package com.epic.framework.common.Ui;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicObject;

@EpicInflatableClass(inflatable=false)
public abstract class EpicImage extends EpicObject {
	public String name;

	public EpicImage(String name) {
		this.name = name;
	}
	
	public abstract EpicImageInstance getInstance(int desiredWidth, int desiredHeight);
}
