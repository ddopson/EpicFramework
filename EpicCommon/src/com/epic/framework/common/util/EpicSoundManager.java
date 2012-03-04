package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.implementation.EpicSoundManagerImplementation;

public class EpicSoundManager {
	private static Object currentMusicObject = null;
	private static EpicSound currentMusic = null;
	
	public static void playMusic(EpicSound sound) {
		if(currentMusicObject != null) {
			if(currentMusic == sound) {
				return;
			}
			EpicSoundManagerImplementation.stopMusic();
		}
		
		EpicSoundManagerImplementation.playSound(sound);
		currentMusic = sound;
	}
	
	public static void playSound(EpicSound sound) {		
		EpicSoundManagerImplementation.playSound(sound);
	}

	public static void preload(EpicSound[] soundsToPreload) {
	}
	
	public static void stopMusic() {
		if(currentMusicObject != null && currentMusic != null) {
			EpicSoundManagerImplementation.stopSound(currentMusic);
			currentMusic = null;
			currentMusicObject = null;
		}
	}
	
	public static void pauseMusic() {
		EpicSoundManagerImplementation.pauseMusic();
	}
	
	public static void resumeMusic() {
		if(currentMusic != null) EpicSoundManagerImplementation.playMusic(currentMusic);
	}
}