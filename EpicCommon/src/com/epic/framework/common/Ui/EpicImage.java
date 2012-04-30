package com.epic.framework.common.Ui;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicObject;
import com.epic.framework.common.util.EpicLog;

@EpicInflatableClass(inflatable=false)
public abstract class EpicImage extends EpicObject {
	public String name;
	public int lastRender = -1;
	public EpicImageInstance[] instances;

	public EpicImage(String name) {
		this.name = name;
	}

	public abstract EpicImageInstance getInstance(int desiredWidth, int desiredHeight);


	protected EpicImageInstance _lookupInstance(EpicImageInstance defaultInstance, int desiredWidth, int desiredHeight) {
		EpicImageInstance instance = null;
		if (defaultInstance.height == desiredHeight && defaultInstance.width == desiredWidth) {
			return defaultInstance;
		} else if (this.instances != null) {
			int l = instances.length;
			for(int i = 0; i < l; i++) {
				if(instances[i].width == desiredWidth && instances[i].height == desiredHeight) {
					instance = instances[i];
					break;
				}
			}
		}

		// New Size Case: If we've never seen this size before, create a new EpicBitmapInstance container for it
		if(instance == null) {				
			instance = new EpicImageInstance(
					this,                             // parent
					defaultInstance.hasAlpha,         // hasAlpha
					desiredWidth,                     // width
					desiredHeight,                    // height 
					defaultInstance.lpad * desiredWidth / defaultInstance.width,      // lpad
					defaultInstance.tpad * desiredHeight / defaultInstance.height,    // tpad
					defaultInstance.rpad * desiredWidth / defaultInstance.width,      // rpad
					defaultInstance.bpad * desiredHeight / defaultInstance.height     // bpad
					);

			// Extend the array
			int l = (this.instances == null) ? 0 : this.instances.length;
			EpicImageInstance[] inext = new EpicImageInstance[l+1];
			for(int i = 0; i < l; i++) {
				inext[i] = this.instances[i];
			}
			inext[l] = instance;
			this.instances = inext;
		}
		return instance;
	}
}
