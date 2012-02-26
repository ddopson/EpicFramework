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
			EpicSoundManagerImplementation.stopSound(currentMusicObject);
		}
		currentMusicObject = EpicSoundManagerImplementation.playSound(sound, -1);
		currentMusic = sound;
	}
	
	public static void playSound(EpicSound sound) {		
		EpicSoundManagerImplementation.playSound(sound, 1);
	}

	public static void preload(EpicSound[] soundsToPreload) {
	}
	
	public static void stopMusic() {
		if(currentMusicObject != null) {
			EpicSoundManagerImplementation.stopSound(currentMusicObject);
			currentMusic = null;
			currentMusicObject = null;
		}
	}
	
	public static void pauseMusic() {
	}
	
	public static void resumeMusic() {
	}
}