package com.epic.framework.common.Ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.implementation.EpicBitmapImplementation;

public class EpicBitmap {
	public final String name;
	public final String extension;
	public final int android_id;
	public final int width;
	public final int height;
	public final int internal_width;
	public final int internal_height;
	public final int lpad;
	public final int tpad;
	public final int rpad;
	public final int bpad;


	//	public class EpicBitmapInstance {
	//		public final int iwidth;
	//		public final int iheight;
	//		Object platformObject;
	//		public EpicBitmapInstance(int width, int height, Object platformObject) {
	//			this.iwidth = width;
	//			this.iheight = height;
	//			
	//		}
	//	}

	public Object platformObject = null;
	public boolean defaultSizeTouched = false;
	public int lastRender = -1;

	private static final HashMap<String, EpicBitmap> allBitmaps = new HashMap<String, EpicBitmap>();
	HashMap<Integer, Object> resizeCache = null;

	private static int globalPixelsInUse = 0;
	private static int globalPixelsInUseScaled = 0;

	public EpicBitmap(String name, String extension, int android_id, int width, int height, int lpad, int tpad, int rpad, int bpad) {
		  this(name, extension, android_id, width, height, lpad, tpad, rpad, bpad, true);
		 }
		 public EpicBitmap(String name, String extension, int android_id, int width, int height, int lpad, int tpad, int rpad, int bpad, boolean register) {
		  this.name = name;
		  this.extension = extension;
		  this.android_id = android_id;
		  this.width = width;
		  this.height = height;
		  this.lpad = lpad;
		  this.tpad = tpad;
		  this.rpad = rpad;
		  this.bpad = bpad;
		  this.internal_height = height - tpad - bpad;
		  this.internal_width = width - lpad - rpad;
		  if(register) {
		   if(allBitmaps.containsKey(name)) {
		    throw EpicFail.framework("Two bitmaps, same name. name=" + name);
		   }
		   allBitmaps.put(name, this);
		  }
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
		return this.name + "." + this.extension;
	}

	public String getFilename(String baseDirectory) {
		return baseDirectory + "/" + this.name + "." + this.extension;
	}

	public String getExtension() {
		return this.extension;
	}

	public int getAndroidId() {
		return this.android_id;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getInternalWidth() {
		return internal_width;
	}

	public int getInternalHeight() {
		return internal_height;
	}

	public int getLpad() {
		return lpad;
	}

	public int getTpad() {
		return tpad;
	}

	public int getRpad() {
		return rpad;
	}

	public int getBpad() {
		return bpad;
	}

	private static final Object loadingLock = new Object();

	public Object getPlatformObject(int desiredWidth, int desiredHeight) {
		synchronized(loadingLock) {
			this.lastRender = EpicStopwatch.getMonotonicN();
			if(this.height == desiredHeight && this.width == desiredWidth) {
				defaultSizeTouched = true;
				if(platformObject == null) {
					EpicBitmapImplementation.loadBitmap(this);
					EpicFail.assertNotNull(platformObject);
					//				EpicStopwatch.reportBitmapLoad(this, width, height, false);
					//				pixelsInUse += width * height;
					globalPixelsInUse += internal_width * internal_height;
				}
				return platformObject;
			}
			else {
				// resize logic
				if(this.resizeCache == null) {
					this.resizeCache = new HashMap<Integer, Object>();
				}
				int neededInternalWidth = this.getInternalWidth() * desiredWidth / this.width;
				int neededInternalHeight = this.getInternalHeight() * desiredHeight / this.height;
				int key = make_key(neededInternalWidth, neededInternalHeight);
				if(!this.resizeCache.containsKey(key)) {
					EpicLog.w("EpicBitmap - Scaling '" + this.getFilename() + "'" 
							+ " from " + this.getWidth() + "x" + this.getHeight() 
							+ " to " + desiredWidth + "x" + desiredHeight
					);
					Object scaledPlatformObject = EpicBitmapImplementation.loadBitmap(this, neededInternalWidth, neededInternalHeight);
					globalPixelsInUse += neededInternalWidth * neededInternalHeight;
					globalPixelsInUseScaled += neededInternalWidth * neededInternalHeight;
					EpicFail.assertNotNull(scaledPlatformObject);
					resizeCache.put(key, scaledPlatformObject);
				}
				Object bitmapObj = this.resizeCache.get(key);
				EpicFail.assertNotNull(bitmapObj);
				return bitmapObj;
			}
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

	private int width_from_key(int key) {
		return key & 0x3FFF;
	}
	private int height_from_key(int key) {
		return key >> 15;
	}
	private int make_key(int w, int h) {
		return (h << 15) + w;
	}
	public int recycle() {
		synchronized(loadingLock) {
			EpicLog.w("Recycling bitmap '" + name + "'");
			int reclaimed = 0;
			if(platformObject != null) {
				EpicBitmapImplementation.recycle(platformObject);
				globalPixelsInUse -= internal_width * internal_height;
				reclaimed += internal_width * internal_height;
//				EpicLog.w("Recycling " + name + " which is : " + internal_width + " x " + internal_height);
				platformObject = null;
			}
			if(resizeCache != null) {
				for(Integer key : resizeCache.keySet()) {
					Object value = resizeCache.get(key);
					EpicBitmapImplementation.recycle(value);
					int w = width_from_key(key);
					int h = height_from_key(key);
					globalPixelsInUse -= w * h;
					globalPixelsInUseScaled -= w * h;
//					EpicLog.w("Recycling " + name + " resized to : " + w + " x " + h);
					reclaimed += w * h;
				}
				resizeCache.clear();
			}
			return reclaimed;
		}
	}

	public boolean isLoaded() {
		return platformObject != null || (resizeCache != null && resizeCache.size() > 0);
	}
	public static EpicBitmap loadBitmapFromUrl(String url) {
		return EpicBitmapImplementation.loadBitmapFromUrl(url);
	}
}
