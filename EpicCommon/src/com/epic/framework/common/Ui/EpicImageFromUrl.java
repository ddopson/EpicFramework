package com.epic.framework.common.Ui;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.EpicBitmapImplementation;

@EpicInflatableClass(ignoreSuperclass=true, inflationArguments=EpicImageFromUrl.class)
public class EpicImageFromUrl extends EpicImage {
	public String url;
	
	@EpicFieldInflation(ignore=true)
	public EpicImageInstance defaultInstance;

	public static EpicImageFromUrl initialize(EpicImageFromUrl args) {
		return new EpicImageFromUrl(args.url);
	}
	
	public EpicImageFromUrl () {
		super("");
	}
	public EpicImageFromUrl(String url) {
		super(url);
		this.url = url;
	}

	@Override
	public EpicImageInstance getInstance(int desiredWidth, int desiredHeight) {
		if(defaultInstance == null) {
			defaultInstance = EpicBitmapImplementation.loadImageFromUrl(this);
		}
		EpicImageInstance instance;
		if(defaultInstance.width == desiredWidth && defaultInstance.height == desiredHeight) {
			instance = defaultInstance; 
		} else {
			instance = _lookupInstance(defaultInstance, desiredWidth, desiredHeight);
			if(instance.platformObject == null) {
				instance.platformObject = EpicBitmapImplementation.scaleImage(defaultInstance, instance);
			}
		}
		
		return instance;
	}

}
