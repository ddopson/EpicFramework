package com.epic.framework.common.util;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.implementation.EpicSoundManagerImplementation;
import com.epic.resources.EpicSounds;
import com.realcasualgames.words.PlayerState;

public class EpicSoundManager {
	
	public static void playMusic(EpicSound sound) {
		if(PlayerState.soundsEnabled()) {
			EpicSoundManagerImplementation.playMusic(sound);
		}
	}
	
	public static void playSound(EpicSound sound) {		
		if(PlayerState.soundsEnabled()) {
			EpicSoundManagerImplementation.playSound(sound);
		}
	}

	public static void preload(EpicSound musicToPreload, EpicSound[] soundsToPreload) {
		EpicSoundManagerImplementation.preload(musicToPreload, soundsToPreload);
	}
	
	public static void stopMusic() {
		EpicSoundManagerImplementation.stopMusic();
	}
	
	public static void pauseMusic() {
		EpicSoundManagerImplementation.pauseMusic();
	}
	
	public static void resumeMusic() {
		if(PlayerState.soundsEnabled()) {
			EpicSoundManagerImplementation.resumeMusic();
		}
	}

	public static void toggleSounds() {
		if(PlayerState.soundsEnabled()) {
			EpicLog.v("Now enabling sounds...");
			EpicSound bg = EpicProjectConfig.getBackgroundMusic();
			if(bg != null) EpicSoundManager.playMusic(bg);
		} else {
			EpicLog.v("Stopping bg track...");
			stopMusic();
		}
	}
}