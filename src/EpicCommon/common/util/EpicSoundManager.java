package com.epic.framework.common.util;

import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.implementation.EpicSoundManagerImplementation;
import com.realcasualgames.words.PlayerState;

public class EpicSoundManager {
	
	public static void playMusic(EpicSound sound) {
		if(!PlayerState.soundsEnabled()) return;
		EpicSoundManagerImplementation.playMusic(sound);
	}
	
	public static void playSound(EpicSound sound) {		
		if(!PlayerState.soundsEnabled()) return;
		EpicSoundManagerImplementation.playSound(sound);
	}

	public static void preload(EpicSound[] soundsToPreload) {
		EpicSoundManagerImplementation.preload(soundsToPreload);
	}
	
	public static void stopMusic() {
		EpicSoundManagerImplementation.stopMusic();
	}
	
	public static void pauseMusic() {
		EpicSoundManagerImplementation.pauseMusic();
	}
	
	public static boolean resumeMusic() {
		if(!PlayerState.soundsEnabled()) return false;
		return EpicSoundManagerImplementation.resumeMusic();
	}
}