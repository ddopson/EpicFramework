package com.epic.framework.common.Ui;

import java.util.Collection;
import java.util.HashMap;

import com.epic.framework.common.Ui2.ClassEpicBitmap;
import com.epic.framework.common.Ui2.EpicObject;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.implementation.EpicBitmapImplementation;

public class EpicBitmap extends EpicBitmapInstance {
	// Statics
	private static final HashMap<String, EpicBitmap> allBitmaps = new HashMap<String, EpicBitmap>();
	private static int globalPixelsInUse = 0;
	private static int globalPixelsInUseScaled = 0;

	// Instance Data
	public final String name;
	public final String plat;
	public final String extension;
	public final int android_id;
	public final boolean opaque;
	EpicBitmapInstance[] instances;
	public boolean defaultSizeTouched = false;
	public int lastRender = -1;


	public static void register(String name, String plat, String extension, int android_id, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		EpicBitmap existing = allBitmaps.get(name);
		if(existing != null) {
			String[] pp = EpicPlatform.platformPrecedence;
			int l = pp.length;
			for(int i = 0; i < l; i++) {
				if(pp[i].equals(plat)) {
					break;
				} else if(pp[i].equals(existing.plat)) {
					return;
				}
			}
		}
		EpicBitmap epicBitmap = new EpicBitmap(name, plat, extension, android_id, width, height, lpad, tpad, rpad, bpad);
		allBitmaps.put(name, epicBitmap);
		Registry.register("images/" + name, epicBitmap);
	}
	
	public EpicBitmap(String name, String plat, String extension, int android_id, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		super(null, width, height, lpad, tpad, rpad, bpad);
		this.name = name;
		this.plat = plat;
		this.extension = extension;
		this.android_id = android_id;
		this.opaque = extension.equals("jpg") ? true : false;
	}

	public static EpicBitmap lookupByName(String name) {
		return allBitmaps.get(name);
	}

	public static EpicBitmap lookupByNameOrThrow(String name) {
		EpicBitmap bitmap = allBitmaps.get(name);
		if(bitmap == null) {
			throw EpicFail.framework("Bitmap with name '" + name + "' is not defined.");
		}
		return bitmap;
	}

	public String getName() {
		return this.name;
	}

	public String getFilename() {
		return this.name + "_" + this.plat + "." + this.extension;
	}

	public String getFilename(String baseDirectory) {
		return baseDirectory + "/" + this.name + "_" + this.plat + "." + this.extension;
	}

	public String getExtension() {
		return this.extension;
	}

	public int getAndroidId() {
		return this.android_id;
	}

	private static final Object loadingLock = new Object();

	public EpicBitmapInstance getInstance(int desiredWidth, int desiredHeight) {
		synchronized(loadingLock) {
			this.lastRender = EpicStopwatch.getMonotonicN();

			EpicBitmapInstance instance = null;
			if (this.height == desiredHeight && this.width == desiredWidth) {
				defaultSizeTouched = true;
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
				instance = new EpicBitmapInstance(
						this,                             // parent
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
				EpicBitmapInstance[] inext = new EpicBitmapInstance[l+1];
				for(int i = 0; i < l; i++) {
					inext[i] = this.instances[i];
				}
				inext[l] = instance;
				this.instances = inext;
			}

			if(instance.platformObject == null) {
				instance.platformObject = EpicFail.assertNotNull(EpicBitmapImplementation.loadBitmap(instance));
				globalPixelsInUse += instance.iwidth * instance.iheight;
				if(instance != this) {
					globalPixelsInUseScaled += instance.iwidth * instance.iheight;
				}
			}
			return instance;
		}
	}

	public EpicBitmap setPlatformObject(Object platformObject) {
		this.platformObject = platformObject;
		return this;
	}

	public static int getGlobalPixelCount() {
		return globalPixelsInUse;
	}

	public static int getGlobalPixelCountScaled() {
		return globalPixelsInUseScaled;
	}

	public static Collection<EpicBitmap> getAllBitmaps() {
		return allBitmaps.values();
	}

	public void markForRecycle() {

	}

	public int recycle() {
		synchronized(loadingLock) {
			EpicLog.w("Recycling bitmap '" + name + "'");
			int reclaimed = 0;
			if(platformObject != null) {
				EpicBitmapImplementation.recycle(platformObject);
				globalPixelsInUse -= iwidth * iheight;
				reclaimed += iwidth * iheight;
				//				EpicLog.w("Recycling " + name + " which is : " + internal_width + " x " + internal_height);
				platformObject = null;
			}
			if(this.instances != null) {
				for(EpicBitmapInstance instance : this.instances) {
					EpicBitmapImplementation.recycle(instance.platformObject);
					int w = instance.iwidth;
					int h = instance.iheight;
					globalPixelsInUse -= w * h;
					globalPixelsInUseScaled -= w * h;
					//					EpicLog.w("Recycling " + name + " resized to : " + w + " x " + h);
					reclaimed += w * h;
				}
				this.instances = null;
			}
			return reclaimed;
		}
	}

	public boolean isLoaded() {
		return this.platformObject != null || this.instances != null;
	}
	public static EpicBitmap loadBitmapFromUrl(String url) {
		return EpicBitmapImplementation.loadBitmapFromUrl(url);
	}
}
