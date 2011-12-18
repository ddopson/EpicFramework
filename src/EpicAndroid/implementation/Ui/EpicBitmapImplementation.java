package com.epic.framework.implementation.Ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Debug;

import com.epic.framework.cfg.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.implementation.util.ArchPlatform;

public class EpicBitmapImplementation {
	private static final boolean LOG_RECYCLING_VERBOSE = false && !EpicProjectConfig.isReleaseMode;
	
	private static final int MEMORY_PAD = 2 * 1024 * 1024; // 1 MiB
	private static final int MEMORY_MIN_FREE = 1 * 1024 * 1024; // 1 MiB
	public static int nRecycled = 0;
	public static int bRecycled = 0;
	public static int nLoaded = 0;
	public static int nTemp = 0;
	public static int bTemp = 0;
	public static int bLoaded = 0;

	public static void loadBitmap(EpicBitmap epicBitmap) {
		try {
			Object bitmap = loadBitmap(epicBitmap, epicBitmap.getInternalWidth(), epicBitmap.getInternalHeight());
			epicBitmap.setPlatformObject(bitmap);
		}
		catch(java.lang.OutOfMemoryError e) {
			throw EpicFail.framework("THIS_SHOULDNT BE POSSIBLE");
		}
	}
	private static BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
	private static Bitmap _loadAndroidBitmap(EpicBitmap epicBitmap) {
		checkMemory("load", 4 * epicBitmap.internal_width * epicBitmap.internal_height);
		//int sampleSize = 1;
		while(true) {
			try {
				bitmapFactoryOptions.inScaled = false;
				bitmapFactoryOptions.inPurgeable = true;
				//bitmapFactoryOptions.inSampleSize = sampleSize;
				Bitmap b = BitmapFactory.decodeResource(EpicApplication.getAndroidContext().getResources(), epicBitmap.android_id, bitmapFactoryOptions);
				bLoaded += bsize(b);
				nLoaded++;
				return b;
			}
			catch(java.lang.OutOfMemoryError e) {
				EpicLog.e("OUT_OF_MEMORY(loading)");
				ArchPlatform.logMemoryStats();
				ensureSufficientFreeMemory(4 * epicBitmap.internal_width * epicBitmap.internal_height);
				//sampleSize *= 2;
			}
		}
	}

	private static Bitmap _scaleBitmap(Bitmap source, int width, int height) {
		checkMemory("scale", 4 * width * height);
		while(true) {
			try {
				return Bitmap.createScaledBitmap(source, width, height, true);
			}
			catch(java.lang.OutOfMemoryError e) {
				EpicLog.e("OUT_OF_MEMORY(scaling)");
				ArchPlatform.logMemoryStats();
				ensureSufficientFreeMemory(4 * width * height);
			}
		}
	}
	
	private static int bsize(Bitmap b) {
		return b.getRowBytes() * b.getHeight();
	}

	public static Object loadBitmap(EpicBitmap epicBitmap, int width, int height) {
		Bitmap defaultBitmap = null;
		nLoaded++;
		while(true) {
			defaultBitmap = (epicBitmap.platformObject != null) ? (Bitmap)epicBitmap.platformObject : _loadAndroidBitmap(epicBitmap);
			if(defaultBitmap == null) {
				throw EpicFail.missing_image(epicBitmap.name);
			}
			if(defaultBitmap.getWidth() != width || defaultBitmap.getHeight() != height) {
				EpicLog.i("Scaling BitmapImpl of '" + epicBitmap.name + "' from " + defaultBitmap.getWidth() + "x" + defaultBitmap.getHeight() + " to " + width + "x" + height);
				Bitmap scaledBitmap = null;
				scaledBitmap = _scaleBitmap(defaultBitmap, width, height);
				bLoaded += bsize(scaledBitmap);
				nLoaded++;
				EpicFail.assertNotNull(scaledBitmap);
				if(epicBitmap.platformObject == null) {
					defaultBitmap.recycle();
					nTemp++;
					nLoaded--;
					bTemp += bsize(defaultBitmap);
					bLoaded -= bsize(defaultBitmap);
				}	
				return scaledBitmap;

			}
			else {
				return defaultBitmap;
			}
		}
	}

	private static int recycleOldestBitmaps() {
		int oldest = Integer.MAX_VALUE;
		int bytesReclaimed = 0;
		int bitmaps = 0;
		for(EpicBitmap bitmap : EpicBitmap.getAllBitmaps()) {
			if(bitmap.lastRender < oldest && bitmap.isLoaded()) {
				oldest = bitmap.lastRender;
			}
		}
		int age = EpicStopwatch.getMonotonicN() - oldest;
		if(age == 0) {
			throw EpicFail.framework("ACK.  refusing to recycle bitmaps from the current frame in " + EpicPlatform.currentScreen.toString() + ".");
		} else if(oldest == Integer.MAX_VALUE) {
			EpicLog.e("ACK.  there are no loaded bitmaps to recycle");			
		}
		
		if(!EpicProjectConfig.isReleaseMode) EpicLog.w("BITMAP_RECYCLING: recycling the cohort of age=" + age);
		for(EpicBitmap bitmap : EpicBitmap.getAllBitmaps()) {
			if(bitmap.lastRender == oldest) {
				bitmaps++;
				bytesReclaimed += 4 * bitmap.recycle();
			}
		}
		if(!EpicProjectConfig.isReleaseMode) EpicLog.w("BITMAP_RECYCLING: recycled " + bitmaps + " bitmaps worth " + mil(bytesReclaimed) + ").  age=" + age);
		return bytesReclaimed;
	}

	private static void ensureSufficientFreeMemory(int minBytes) {
		EpicLog.w("Trying to free up " + minBytes + " bytes...");
		int bytesReclaimed = 0;
		while(bytesReclaimed < minBytes) {
			bytesReclaimed += recycleOldestBitmaps();
			checkMemory("gc", 0);
		}
		System.gc();
	}
	
	private static String mil(long i) {
		return StringHelper.formatInt_mib(i);
	}
	
	private static void checkMemory(String src, int aboutToAlloc) {
		Runtime rt = Runtime.getRuntime();
		long javaFree = rt.freeMemory();
		long javaUsed = rt.totalMemory() - rt.freeMemory();
		long nativeHeapAlloc = Debug.getNativeHeapAllocatedSize();
		long nativeHeapSize = Debug.getNativeHeapSize();
		long nativeHeapFree = Debug.getNativeHeapFreeSize();
		if(LOG_RECYCLING_VERBOSE) EpicLog.w("MEMORY-USED(" + src + "): " + mil(javaUsed + nativeHeapAlloc) + " = " + mil(javaUsed) + " + " + mil(nativeHeapAlloc) + ".  " 
					+ "jf=" + mil(javaFree)
					+ ", nhs=" + mil(nativeHeapSize)
					+ ", nhf=" + mil(nativeHeapFree) 
					+ ", recycled=" + nRecycled + " (" + mil(bRecycled) + ")"
					+ ", loaded=" + nLoaded + " (" + mil(bLoaded) + ")"
					+ ", temprec=" + nTemp + " (" + mil(bTemp) + ")"
		);
		if(nativeHeapAlloc + aboutToAlloc + MEMORY_PAD > rt.maxMemory()) {
			long needed = (nativeHeapAlloc + aboutToAlloc + MEMORY_PAD) - rt.maxMemory();
			if(needed < MEMORY_MIN_FREE) {
				needed = MEMORY_MIN_FREE;
			}
			ensureSufficientFreeMemory((int)needed);
		}
//		if(javaUsed + nativeHeapAlloc > 25000000) {
//			ArchPlatform.logMemoryStats();
//		}
	}

	public static void recycle(Object bitmapObject) {
		Bitmap bitmap = (Bitmap)bitmapObject;
		if(LOG_RECYCLING_VERBOSE) EpicLog.w("Low-level-recycle of a " + bitmap.getWidth() + " x " + bitmap.getHeight() + " bitmap worth + " + 4 * bitmap.getWidth() * bitmap.getHeight() + " = " + bsize(bitmap) + " bytes");
		bRecycled += bsize(bitmap);
		bLoaded -= bsize(bitmap);
		nRecycled++;
		nLoaded--;
		bitmap.recycle();
	}

	public static void onLowMemory() {
		recycleOldestBitmaps();
	}

	public static Object getImageBuffer(int width, int height) {
		return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	}

	public static Object getBufferCanvasObject(Object platformObject) {
		Bitmap bitmap = (Bitmap)platformObject;
		return new Canvas(bitmap);
	}
	
	public static EpicBitmap loadBitmapFromUrl(String src) {
		  try {
		   URL url = new URL(src);
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setDoInput(true);
		   connection.connect();
		   InputStream input = connection.getInputStream();
		   Bitmap myBitmap = BitmapFactory.decodeStream(input);
		   EpicBitmap epicBitmap = new EpicBitmap("src", "URL", -1, myBitmap.getWidth(), myBitmap.getHeight(), 0, 0, 0, 0, false);
		   epicBitmap.platformObject = myBitmap;
		   return epicBitmap;
		  } catch (Exception e) {
		   EpicLog.e(e.toString());
		   return null;
		  }
		}
}
