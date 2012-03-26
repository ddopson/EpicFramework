package com.epic.framework.common.Ui;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.EpicBitmapImplementation;

@EpicInflatableClass(inflatable=false)
public class EpicImageFromUrl extends EpicImage {
	String url;
	
	public EpicImageFromUrl(String url) {
		super(url);
		this.url = url;
	}

	public EpicImageInstance defaultInstance;

	@Override
	public EpicImageInstance getInstance(int desiredWidth, int desiredHeight) {
		if(defaultInstance == null) {
			defaultInstance = EpicBitmapImplementation.loadImageFromUrl(this);
		}
		EpicImageInstance instance;
		if(defaultInstance.width == desiredWidth && defaultInstance.height == desiredHeight) {
			instance = defaultInstance; 
		} else {
			throw EpicFail.not_implemented();
		}
		
		return instance;
	}

}
