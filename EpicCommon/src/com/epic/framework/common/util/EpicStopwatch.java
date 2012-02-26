package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.implementation.EpicTimeImplementation;
import static com.epic.framework.common.EpicConfig.DEBUG_EPICSTOPWATCH;

public class EpicStopwatch {
	private static class Counter {
		public final String name;
		public long value = 0;
		long valueFromPaint = 0;
		long paintStart = 0;
		long frameMax = 0;
		long frameValue = 0;

		public Counter(String name) {
			this.name = name;
		}
		public void reset() {
			value = 0;
			valueFromPaint = 0;
			frameMax = 0;
			frameValue = 0;
		}
		public void paintStart() {
			paintStart = value;
		}
		public void paintFinish() {
			frameValue = value - paintStart;
			valueFromPaint += frameValue;
			if(frameValue > frameMax) {
				frameMax = frameValue;
			}
		}
		public String pctFromRender() {
			if(value == 0) {
				return "--%";
			}
			return 100 * valueFromPaint / value + "%";
		}
	}

	private static final short[] frameTimes = new short[1024];
	private static int n_buckets = 0;
	public static final int BUCKET_OTHER = n_buckets++;
	public static final int BUCKET_BG_CLEAR = n_buckets++;
	public static final int BUCKET_APP_RENDER = n_buckets++;
	public static final int BUCKET_BLIT_SCREEN = n_buckets++;
	public static final int BUCKET_DEBUG_RENDER = n_buckets++;
	public static final int BUCKET_APP_CODE = n_buckets++;
	private static final long[] buckets = new long[n_buckets];

	private static int nFramesRendered = 0;
	private static int monotonicN = 0;
	private static int nFramesComputed = 0;

	private static long startTime = 0;
	private static long startTimeSystem = 0;
	private static long lastTime = 0;
	private static long lastReset = 0;

	private static int pixelsInPlay_thisFrame;
	private static int pixelsInPlay_lastFrame;
	private static int pixelsInPlay_total;
	private static int pixelsInPlay_lastReset;


	private static Counter pixelsPushed = new Counter("pixels");
	private static Counter bytesAllocated = new Counter("byteAlloc");
	private static Counter bytesAllocated2 = new Counter("byteAlloc2");
	private static Counter objectsAllocated = new Counter("objAlloc");

	private static Counter[] allCounters = new Counter[] {
		pixelsPushed,
		bytesAllocated,
		bytesAllocated2,
		objectsAllocated,
	};

	private static boolean resetPending = true;
	static EpicClickListener frameUpdateAction = null;

	public static void reset() {
		resetPending = true;
	}
	private static void doReset() {
		startTime = lastTime = EpicTimeImplementation.getMicroTime();
		startTimeSystem = System.currentTimeMillis();
		for(int i = 0; i < n_buckets; i++) {
			buckets[i] = 0;
		}
		nFramesRendered = 0;
		nFramesComputed = 0;
		pixelsInPlay_lastFrame = pixelsInPlay_thisFrame = pixelsInPlay_total = 0;
		pixelsInPlay_lastReset = monotonicN;
		for(Counter counter : allCounters) {
			counter.reset();
		}
		resetPending = false;
		lastReset = System.currentTimeMillis();
	}

	public static void attribute(int bucket) {
		long time = EpicTimeImplementation.getMicroTime();
		buckets[bucket] += time - lastTime;
		lastTime = time;
	}

	public static void incrementFramesComputed() {
		nFramesComputed++;
	}

	public static String getDebugString() {
		if(DEBUG_EPICSTOPWATCH) {
			return StringHelper.join("\n", getDebugStringArray());
		} else {
			return "";
		}
	}

	public static String[] getDebugStringArray() {
		if(DEBUG_EPICSTOPWATCH) {
			long totalTime = EpicTimeImplementation.getMicroTime() - startTime;
			if(totalTime == 0) {
				return new String[] {" --- "};
			}
			long totalTime2 = 1000 * (System.currentTimeMillis() - startTimeSystem);
			StringBuffer buff = new StringBuffer(nFramesRendered * 5);
			for(int i = 0; i < nFramesRendered; i++) {
				buff.append(i > 0 ? ", " : "");
				buff.append(frameTimes[i]);
			}
			String[] debugStrings = new String[] {
					"TotTime=" + _seconds(totalTime) + " vs. " + _seconds(totalTime2),
					"FramesR=" + nFramesRendered + " (" + _fps(nFramesRendered, totalTime) + ")",
					"FramesC=" + nFramesComputed + " (" + _fps(nFramesComputed, totalTime) + ")",
					"BgClear=" + _pct(buckets[BUCKET_BG_CLEAR], totalTime),
					"AppRend=" + _pct(buckets[BUCKET_APP_RENDER], totalTime),
					"BlitScr=" + _pct(buckets[BUCKET_BLIT_SCREEN], totalTime),
					"DbugRdr=" + _pct(buckets[BUCKET_DEBUG_RENDER], totalTime),
					"AppCode=" + _pct(buckets[BUCKET_APP_CODE], totalTime),
					"OtherTm=" + _pct(buckets[BUCKET_OTHER], totalTime),
					"PixelsPerFrame=" + _mil(4*pixelsPushed.frameValue) + "MB (" + _mil(4*pixelsPushed.frameMax) + "MB max, " + _mil(4*pixelsPushed.value / nFramesRendered) + "MB avg)",
					"AllocPerFrame=" + (bytesAllocated.value / nFramesRendered),
					"AllocTotal=" + _mil(bytesAllocated.value),
					"AllocTotal2=" + _mil(bytesAllocated2.value),
					"ObjAlloc=" + _mil(1000*objectsAllocated.value) + "k (" + objectsAllocated.pctFromRender() + ") apf=" + objectsAllocated.valueFromPaint / nFramesRendered,
					"PixelsLoaded=" + _mil(4*EpicBitmap.getGlobalPixelCount()) + "MB (" + _mil(4*EpicBitmap.getGlobalPixelCountScaled()) + "MB scaled)",
					"RenderTimes=" + buff.toString()
			};
			return debugStrings;
		} else {
			return new String[] { "EpicStopwatch[DISABLED]" };
		}
	}

	public static String[] getDebugStringArray2() {
		if(DEBUG_EPICSTOPWATCH) {
			long totalTime = EpicTimeImplementation.getMicroTime() - startTime;
			if(totalTime == 0) {
				return new String[] {" --- "};
			}
			long totalTime2 = 1000 * (System.currentTimeMillis() - startTimeSystem);
			String[] debugStrings = new String[] {
					"TotTime=" + _seconds(totalTime) + " vs. " + _seconds(totalTime2),
					"FramesR=" + nFramesRendered + " (" + _fps(nFramesRendered, totalTime) + ")",
					"FramesC=" + nFramesComputed + " (" + _fps(nFramesComputed, totalTime) + ")",
					"BgClear=" + _combined(buckets[BUCKET_BG_CLEAR], totalTime),
					"AppRend=" + _combined(buckets[BUCKET_APP_RENDER], totalTime),
					"BlitScr=" + _combined(buckets[BUCKET_BLIT_SCREEN], totalTime),
					"DbugRdr=" + _combined(buckets[BUCKET_DEBUG_RENDER], totalTime),
					"AppCode=" + _combined(buckets[BUCKET_APP_CODE], totalTime),
					"OtherTm=" + _combined(buckets[BUCKET_OTHER], totalTime),
			};
			return debugStrings;
		} else {
			return new String[] { "EpicStopwatch[DISABLED]" };
		}
	}

	private static String _mil(long n) {
		return "" + n / 1000000 + "." + (n / 100000) % 10;
	}

	private static String _fps(int frames, long micros) {
		long _f = 1000000L * frames;
		int i = (int)(_f / micros);
		int r = (int)(_f % micros);
		return i + "." + r + "fps";
	}
	private static String _seconds(long micros) {
		int s = (int)(micros / 1000000);
		int r = (int)((micros % 1000000) / 100000);
		return s + "." + r + "s";
	}

	private static String _pct(long num, long denom) {
		int pct = (int)(100 * num / denom);
		return pct + "%";
	}

	private static String _combined(long micros, long total) {
		return _seconds(micros) + " (" + _pct(micros, total) + ")";
	}

	private static long javaHeapUsed() {
		return 0;
		//		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();		
	}

	private static long frameStartTime = 0;

	private static long allocBytes = 0;
	private static long startJavaHeapUsed;
	public static void paintStart() {
		if(DEBUG_EPICSTOPWATCH) {
			for(Counter counter : allCounters) {
				counter.paintStart();
			}
			startJavaHeapUsed = javaHeapUsed();
			frameStartTime = System.currentTimeMillis();
			EpicStopwatch.attribute(BUCKET_OTHER);
		}
	}
	public static void paintFinish() {
		if(DEBUG_EPICSTOPWATCH) {
			long time = System.currentTimeMillis();
			frameTimes[nFramesRendered] = (short)(time - frameStartTime);
			EpicStopwatch.attribute(BUCKET_APP_RENDER);
			if(resetPending) {
				doReset();
			}
			else {
				nFramesRendered++;
				monotonicN++;
				if(frameUpdateAction != null) {
					frameUpdateAction.onClick();
				}
				long finishJavaHeapUsed = javaHeapUsed();
				if(finishJavaHeapUsed > startJavaHeapUsed) {
					bytesAllocated.value += finishJavaHeapUsed - startJavaHeapUsed;
				}
				for(Counter counter : allCounters) {
					counter.paintFinish();
				}
			}
			if(System.currentTimeMillis() > lastReset + 10000) {
				EpicLog.i(EpicStopwatch.getDebugString());
				doReset();
			}
		}
	}

	public static void setFrameUpdateAction(EpicClickListener action) {
		frameUpdateAction = action;
	}

	// DDOPSON-2011-07-10 - note that "pixels" != width * height due to cropping and padding and such.  It's the actual pixel work
	public static void reportPixelsPushed(EpicBitmap image, int width, int height, int pixels) {
		if(DEBUG_EPICSTOPWATCH) {
			pixelsPushed.value += pixels;
			if(image.lastRender != monotonicN) {
				if(image.lastRender < pixelsInPlay_lastReset) {
					pixelsInPlay_total += width * height;
				}
				image.lastRender = monotonicN;
			}
		}
	}

	public static int getMonotonicN() {
		return monotonicN;
	}

	public static void reportAllocation(int count, String desc, Object newObj, long size) {
		if(DEBUG_EPICSTOPWATCH) {
			if(count > 100) {
				System.out.println("ALLOC: type='" + desc + "', count=" + count + ", sz=" + size + ", obj=" + newObj);
			}
			objectsAllocated.value += 1;
			bytesAllocated2.value += size;
		}
	}
}
