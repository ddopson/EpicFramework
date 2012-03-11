package com.epic.framework.implementation;

import java.util.Hashtable;

import android.media.AudioManager;
import android.media.SoundPool;

import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.util.EpicLog;

public class EpicSoundManagerImplementation {

	private static final int MAX_CONCURRENT_STREAMS = 16;

	private static Hashtable<String, Integer> soundBoard = new Hashtable<String, Integer>();
	private static SoundPool soundPool = new SoundPool(MAX_CONCURRENT_STREAMS, AudioManager.STREAM_MUSIC, 0);

	private static int getSoundId(EpicSound sound) {
		if(!soundBoard.containsKey(sound.name)) {
			int soundId = soundPool.load(EpicApplication.getAndroidContext(), sound.android_id, 1);
			soundBoard.put(sound.name, soundId);
			EpicLog.v("Sound " + sound.name + " loaded into id=" + soundId);
		}

		return soundBoard.get(sound.name);
	}

	public static void stopSound(Object currentMusicObject) {
		Integer soundId = (Integer)currentMusicObject;
		soundPool.pause(soundId);
	}

	public static Object playSound(EpicSound sound, int loops) {
		int soundId = getSoundId(sound);
		soundPool.play(
				soundId, // sound ID
				1.0f,    // left-volume
				1.0f,    // right-volume
				0,       // priority
				loops,   // loops
				1.0f);   // rate
		return Integer.valueOf(soundId);
	}
}