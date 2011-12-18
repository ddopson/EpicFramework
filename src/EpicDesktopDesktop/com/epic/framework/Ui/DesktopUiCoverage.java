package com.epic.framework.Ui;

import java.awt.Dimension;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;

import com.epic.framework.util.EpicLog;
import com.epic.framework.util.EpicRandom;
import com.epic.framework.util.EpicStopwatch;
import com.epic.framework.util.StringHelper;
import com.realcasualgames.words.*;
import com.google.monitoring.runtime.instrumentation.*;
import com.epic.framework.Ui.EpicPlatform.EpicTestHook;

public class DesktopUiCoverage {
	private static JFrame mainFrame;
	private static boolean fast = false;
	private static boolean hasRepainted = false;
	private static void sleep(int millis) {
		try {
			if(fast) {
				hasRepainted = false;
				EpicPlatform.repaintScreen();
				while(!hasRepainted) {
					Thread.yield();
				}
			}
			else {
				Thread.sleep(millis);
			}
		} catch (InterruptedException e) {
			EpicLog.w("Sleep Interrupted");
		}
	}

	private static String getRandomWord(ScreenGame screenGame) {
		List<String> words = screenGame.game.findAllWords();
		int i = EpicRandom.nextInt(words.size());
		return words.get(i);
	}
	private static void advanceTime(ScreenGame screenGame, int seconds) {
		seconds -= screenGame.game.currentTick / ScreenGame.TICKS_PER_SECOND;
		for(int t = 0; t < seconds*screenGame.TICKS_PER_SECOND; t++) {
//						screenGame.timer.doTickNow();
		}		
	}
	public static void main(String[] args) {
		AllocationRecorder.addSampler(new Sampler() {
			public void sampleAllocation(int count, String desc, Object newObj, long size) {
				EpicStopwatch.reportAllocation(count, desc, newObj, size);
				//				System.out.println("I just allocated the object " + newObj + " of type " + desc + " whose size is " + size);
			}
		});
		mainFrame = new JFrame();
		mainFrame.setContentPane(EpicNativeGameFrame.get());
		EpicPlatform.initialize(EpicNativeGameFrame.get());
		EpicPlatform.epicTestHook = new EpicTestHook() {
			public void onPaintFinished() {
				hasRepainted = true;
				EpicPlatform.onPlatformTimerTick();
			}
		};
		//		EpicSimulator.setScreenSize(new Dimension(480,360));
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		sleep(200);
		testScreenNursery();
		testScreenScores();
		testTrophyRoom();
		testScreenBuyTokens();
		testScreenAdvertisement();
		testScreenGame();
		mainFrame.dispose();
		scanBitmaps();

		System.exit(0);
	}

	private static void testScreenNursery() {
		ScreenNursery sn = new ScreenNursery();
		EpicPlatform.changeScreen(sn);
		sleep(200);
	}

	private static void testScreenScores() {
		ScreenScores screen = new ScreenScores(9999, "words", "bonus1");
		EpicPlatform.changeScreen(screen);
		sleep(1000);
	}

	private static void testTrophyRoom() {
		for(Challenge challenge : Challenge.challenges) {
			ScreenTrophyRoomDetails s = new ScreenTrophyRoomDetails(challenge);
			EpicPlatform.changeScreen(s);
			sleep(50);
		}
		ScreenTrophyRoom screen = new ScreenTrophyRoom();
		EpicPlatform.changeScreen(screen);
		sleep(200);
	}

	private static void testScreenBuyTokens() {
		ScreenBuyTokens screen = new ScreenBuyTokens(new ScreenMainMenu());
		EpicPlatform.changeScreen(screen);
		sleep(200);
	}

	private static void testScreenAdvertisement() {
		ScreenAdvertisement screen = new ScreenAdvertisement(new ScreenScores(232, "", ""));
		EpicPlatform.changeScreen(screen);
		EpicPlatform.timeAtScreenChange = 34;
		sleep(200);
	}

	private static void testScreenGame() {
		ScreenGame screenGame = new ScreenGame();
		EpicBitmapPreloader pl = new EpicBitmapPreloader();
		ScreenGame.preload(pl);
		ScreenGame.preload_tod_images(pl, 1);
		ScreenGame.preload_tod_images(pl, 2);
		WordsGame game = screenGame.game;
		EpicPlatform.changeScreen(screenGame);
		sleep(200);
		for(int j = 0; j < 5; j++) {
			String word = getRandomWord(screenGame);
			for(int i = 0; i < word.length(); i++) {
				game.tryLetter(word.charAt(i));
				sleep(200);
			}
			game.tryLetter('q');
			sleep(200);
			game.backspace();
			sleep(200);
			game.submitWord();
			sleep(200);
		}
		screenGame.displayTornado();
		sleep(3000);
		advanceTime(screenGame, 65);
		for(int j = 0; j < 5; j++) {
			String word = getRandomWord(screenGame);
			for(int i = 0; i < word.length(); i++) {
				game.tryLetter(word.charAt(i));
				sleep(100);
			}
			game.submitWord();
			sleep(100);
		}
		advanceTime(screenGame, 85);
		for(int j = 0; j < 5; j++) {
			String word = getRandomWord(screenGame);
			for(int i = 0; i < word.length(); i++) {
				game.tryLetter(word.charAt(i));
				sleep(100);
			}
			game.submitWord();
			sleep(100);
		}
		while(game.bombsExploded == 0) {
			String word = getRandomWord(screenGame);
			for(int i = 0; i < word.length(); i++) {
				game.tryLetter(word.charAt(i));
				sleep(100);
			}
			game.submitWord();
			sleep(100);
		}
		advanceTime(screenGame, 93);
		sleep(4000);
		EpicPlatform.onPlatformTouchFinished(50, 50);
	}

	private static String mib(int pixels) {
		return StringHelper.formatInt_mib(pixels);
	}

	private static void scanBitmaps() {
		int totalSize = 0;
		int totalStock = 0;
		int totalMin = 0;
		int totalUntouched = 0;
		ArrayList<String> bitmapsUntouched = new ArrayList<String>();
		ArrayList<String> bitmapsMultiple = new ArrayList<String>();
		ArrayList<String> bitmapsSingle = new ArrayList<String>();
		for(EpicBitmap bitmap : EpicBitmap.getAllBitmaps()) {
			int stockSize = bitmap.internal_width * bitmap.internal_height;
			int myTotalSize = 0;
			int n = 0;
			int biggest = 0;
			ArrayList<String> lines = new ArrayList<String>();
			if(bitmap.defaultSizeTouched) {
				lines.add("BITMAP_SIZE\t" + bitmap.name + "\t" + bitmap.internal_width + "\t" + bitmap.internal_height);
				int s = bitmap.internal_width * bitmap.internal_height;
				myTotalSize += s;
				n++;
				if(s > biggest) biggest = s;
			}
			if(bitmap.resizeCache != null) {
				for(Entry<Integer, Object> entry : bitmap.resizeCache.entrySet()) {
					int w = entry.getKey() & 0x7FFF;
					int h = (entry.getKey() >> 15);
					lines.add("BITMAP_RESIZE\t" + bitmap.name + "\t" + w + "\t" + h);
					myTotalSize += w * h;
					int s = w * h;
					if(s > biggest) biggest = s;
					n++;
				}
			}
			
			if(n == 0) {
				bitmapsUntouched.add(bitmap.getFilename());
				totalUntouched += stockSize;
			}
			else if(n == 1) {
				bitmapsSingle.add(bitmap.getFilename());
				for(String line : lines) {
					System.out.println(line);
				}
			}
			else if(n > 1) {
				bitmapsMultiple.add(bitmap.getFilename());
			}
			totalStock += stockSize;
			totalSize += myTotalSize;
			if(myTotalSize > 0 && stockSize > biggest * 2) {
				System.out.println("BIGBIGBIG: " + bitmap.name + " is " + myTotalSize + " / " + stockSize + " (" + String.format("%.1f", (1.0f * myTotalSize / stockSize)) + ")");
			}
			totalMin += biggest;
		}
		System.out.println("UNTOUCHED: " + StringHelper.join(", ", bitmapsUntouched));
		System.out.println("MULTI_SIZE: " + StringHelper.join(", ", bitmapsMultiple));
		System.out.println("Total Min (single biggest used per image):   " + totalMin + " (" + mib(4*totalMin) + ")");
		System.out.println("Total Bitmap Size (all used per image):      " + totalSize + " (" + mib(4*totalSize) + ")");
		System.out.println("Total Stock Size (default size per image):   " + totalStock + " (" + mib(4*totalStock) + ")");
		System.out.println("Total Untouched (default size of untouched): " + totalUntouched + " (" + mib(4*totalUntouched) + ")");

	}
}

