package com.epic.framework.common.util;

import com.epic.framework.cfg.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.implementation.EpicSoundManagerImplementation;

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

	public static void toggleSounds() {
		PlayerState.toggleSounds();
		if(!PlayerState.soundsEnabled()) {
			EpicSoundManagerImplementation.stopMusic();
		} else {
			EpicSound bg = EpicProjectConfig.getBackgroundMusic();
			if(bg != null) EpicSoundManager.playMusic(bg);
		}
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