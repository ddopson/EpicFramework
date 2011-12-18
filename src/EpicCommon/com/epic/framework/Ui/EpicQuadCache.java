//package com.epic.framework.Ui;
//
//import java.util.Hashtable;
//
//import com.epic.framework.util.EpicLog;
//
//public class EpicQuadCache {
//	private static final Hashtable quadCache = new Hashtable();
//
//	public static EpicBitmap[] getImageQuad(String name) {
//		EpicLog.v("EpicQuadCache.getImageQuad(" + name + ")");
//		if(!quadCache.containsKey(name)) {
//			EpicBitmap orig = EpicBitmap.loadBitmapByName(name);
//			EpicBitmap[] quad = makeQuad(orig);
//			quadCache.put(name, quad);
//		}
//
//		return (EpicBitmap[])quadCache.get(name);
//	}
//
//
//	private static EpicBitmap[] makeQuad(EpicBitmap source) {
//		EpicLog.v("EpicQuadCache.makeQuad(" + source + ")");
//		int width = source.getOriginalWidth();
//		int height = source.getOriginalHeight();
//		int[] orig = new int[width * height];
//		source.getPixels(orig, width, height);
//
//		int[] rot90 = rotate90(orig, width, height);
//		int[] rot180 = rotate90(rot90, width, height);
//		int[] rot270 = rotate90(rot180, width, height);
//
//		return new EpicBitmap[] {
//				source,
//				EpicBitmap.fromPixels(rot90, width, height, source.name + "[rot90]"),
//				EpicBitmap.fromPixels(rot180, width, height, source.name + "[rot180]"),
//				EpicBitmap.fromPixels(rot270, width, height, source.name + "[rot270]")
//		};
//	}
//
//	private static int[] rotate90(int[] oldBits, int width, int height) {
//		int[] newBits = new int[width * height];
//
//		for(int y = 0; y < height; y++) {
//			for(int x = 0; x < width; x++) {
//				int negY = height - 1 - y;
//				int oldPos = (x + width * y);
//				int newPos = (negY + height * x);
//				newBits[newPos] = oldBits[oldPos];
//				//newBits[newPos + 1] = oldBits[oldPos + 1];
//			}
//		}
//		return newBits;
//	}
//}
