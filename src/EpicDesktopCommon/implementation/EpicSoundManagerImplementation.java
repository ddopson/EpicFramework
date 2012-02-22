package com.epic.framework.implementation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.resources.EpicFiles;

public class EpicSoundManagerImplementation {
	private static Player music = null;
	public static void playMusic(final EpicSound sound) {
		if(music != null) {
			music.close();
		}
//		music = playSound(sound, true);
	}

	public static void playSound(EpicSound sound) {
		playSound(sound, false);
	}
	
	public static Player playSound(final EpicSound sound, boolean isMusic) {
		final Player p = getPlayer(sound);
		EpicPlatform.runInBackground(new Runnable() {
			public void run() {
				EpicLog.i("Playing Sound: '" + sound.name + "'");
				try {
					p.play();
				} catch (JavaLayerException e) {
					EpicLog.e("SOUND_ERROR: playing sound '" + sound.name + "', got ", e);
				}
			}
		});
		return p;
	}
	
	private static Player getPlayer(final EpicSound sound) {
		try {
			String filename = "./resources/" + sound.getFilename();
			Player p = new Player(new FileInputStream(filename));
			return p;
		} catch (FileNotFoundException e) {
			throw EpicFail.missing_image(sound.name, e);
		}
		catch (JavaLayerException e) {
			EpicLog.e("SOUND_ERROR: playing sound '" + sound.name + "', got ", e);
			return null;
		}
	}

	public static void preload(EpicSound[] soundsToPreload) {
	
	}

	public static void stopMusic() {
	}

	public static void pauseMusic() {
		// TODO Auto-generated method stub

	}

	public static boolean resumeMusic() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void preload(EpicSound musicToPreload, EpicSound[] soundsToPreload) {
	}
}
