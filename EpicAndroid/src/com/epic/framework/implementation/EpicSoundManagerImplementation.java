package com.epic.framework.implementation;

import java.util.Hashtable;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.util.EpicLog;

public class EpicSoundManagerImplementation {
	
	private static final int MAX_CONCURRENT_STREAMS = 16;

	private static Hashtable<String, Integer> soundBoard = new Hashtable<String, Integer>();
	private static SoundPool soundPool = new SoundPool(MAX_CONCURRENT_STREAMS, AudioManager.STREAM_MUSIC, 0);
	private static MediaPlayer mp;

	private static int getSoundId(EpicSound sound) {
		if(!soundBoard.containsKey(sound.name)) {
			int soundId = soundPool.load(EpicApplication.getAndroidContext(), sound.android_id, 1);
			soundBoard.put(sound.name, soundId);
			EpicLog.v("Sound " + sound.name + " loaded into id=" + soundId);
		}
		
		return soundBoard.get(sound.name);
	}
		

	public static void playMusic(EpicSound sound) {
		if(mp != null && mp.isPlaying()) { // && mp.isPlaying()) {
			mp.stop();
			// mp.release();
		}
		
		mp = MediaPlayer.create(EpicApplication.getAndroidContext(), sound.android_id);
		mp.start();
		mp.setLooping(true);
	}
	
	public static void playSound(EpicSound sound) {		
		int soundId = getSoundId(sound);
		int result = soundPool.play(soundId, 1, 1, 0, 0, 1);
		
		if(result == 0) EpicLog.e("EpicSoundManager.playSound() - Problem playing " + sound.name);
		
	}

	public static void preload(EpicSound[] soundsToPreload) {
		EpicLog.v("EpicSoundManager.preload()");
		
		for(int i = 0; i < soundsToPreload.length; ++i) {
			if(!soundBoard.containsKey(soundsToPreload[i])) {
				int soundId = soundPool.load(EpicApplication.getAndroidContext(), soundsToPreload[i].android_id, 1);
				soundBoard.put(soundsToPreload[i].name, soundId);
				EpicLog.v("Sound " + soundsToPreload[i].name + " preloaded into id=" + soundId);
			}
		}
	}


	public static void stopMusic() {
		if(mp != null && mp.isPlaying()) { // && mp.isPlaying()) {
			mp.stop();
			//mp.release();
		}
	}
	
	public static void pauseMusic() {
		if(mp != null && mp.isPlaying()) {
			mp.pause();
		}
	}
	
	public static boolean resumeMusic() {
		if(mp != null && mp.getCurrentPosition() > 0) {
			mp.start();
			return true;
		} else {
			playMusic(EpicProjectConfig.getBackgroundMusic());
		}
		
		return false;
	}
}