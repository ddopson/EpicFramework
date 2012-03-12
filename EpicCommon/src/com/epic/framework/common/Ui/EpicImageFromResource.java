package com.epic.framework.common.Ui;

import java.util.HashMap;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.common.util.exceptions.EpicMissingImageException;
import com.epic.framework.implementation.EpicBitmapImplementation;

@EpicInflatableClass(inflatable=false)
public class EpicImageFromResource extends EpicImageInstance {
	// Instance Data
	public String plat;
	public String extension;
	public int android_id;

	private static HashMap<String, EpicImageFromResource> allImageResources = new HashMap<String, EpicImageFromResource>();

	public EpicImageFromResource(String name, String plat, String extension, int android_id, boolean hasAlpha, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		super(name, hasAlpha, width, height, lpad, tpad, rpad, bpad);
		this.plat = plat;
		this.extension = extension;
		this.android_id = android_id;
		allImageResources.put(name, this);
	}

	public static EpicImage lookupByNameOrThrow(String name) {
		EpicImageFromResource image = allImageResources.get(name);
		if(image == null) {
			throw new EpicMissingImageException(name);
		}
		return image;
	}


	public int lastRender = -1;
	EpicImageInstance[] instances;

	public String getFilename() {
		if(this.plat == null) {
			return this.name + "." + this.extension;
		} else {
			return this.name + "_" + this.plat + "." + this.extension;
		}
	}

	public String getFilename(String baseDirectory) {
		if(this.plat == null) {
			return baseDirectory + "/" + this.name + "." + this.extension;
		} else {
			return baseDirectory + "/" + this.name + "_" + this.plat + "." + this.extension;
		}
	}

	private static final Object loadingLock = new Object();

	public EpicImageInstance getInstance(int desiredWidth, int desiredHeight) {
		synchronized(loadingLock) {
			this.lastRender = EpicStopwatch.getMonotonicN();

			EpicImageInstance instance = null;
			if (this.height == desiredHeight && this.width == desiredWidth) {
				instance = this;
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
						hasAlpha,                         // hasAlpha
						desiredWidth,                     // width
						desiredHeight,                    // height 
						lpad * desiredWidth / width,      // lpad
						tpad * desiredHeight / height,    // tpad
						rpad * desiredWidth / width,      // rpad
						bpad * desiredHeight / height     // bpad
						);
				EpicLog.w("EpicBitmap - Scaling: " + this.getFilename() + " to " + desiredWidth + "x" + desiredHeight);

				// Extend the array
				int l = (this.instances == null) ? 0 : this.instances.length;
				EpicImageInstance[] inext = new EpicImageInstance[l+1];
				for(int i = 0; i < l; i++) {
					inext[i] = this.instances[i];
				}
				inext[l] = instance;
				this.instances = inext;
			}

			if(instance.platformObject == null) {
				instance.platformObject = EpicFail.assertNotNull(EpicBitmapImplementation.loadBitmap(this, instance));
			}
			return instance;
		}
	}
}
