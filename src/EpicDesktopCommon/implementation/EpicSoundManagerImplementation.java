package com.epic.framework.implementation

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.epic.framework.Ui.EpicPlatform;
import com.epic.framework.implementation.EpicSound;
import com.epic.resources.EpicFiles;

public class EpicSoundManagerImplementation {

	public static void playMusic(final EpicSound sound) {
		playSound(sound, true);
	}

	public static void playSound(EpicSound sound) {
		playSound(sound, false);
	}
	
	public static void playSound(final EpicSound sound, boolean isMusic) {
		EpicPlatform.runInBackground(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub

				EpicLog.i("Playing Sound: '" + sound.name + "'");
				String filename = "./sounds/" + sound.getFilename();
				javazoom.jl.player.Player p;
				try {
					p = new Player(new FileInputStream(filename));
					p.play();
				} catch (FileNotFoundException e) {
					throw EpicFail.missing_image(sound.name, e);
				}
				catch (JavaLayerException e) {
					EpicLog.e("SOUND_ERROR: playing sound '" + sound.name + "', got ", e);
				}
			}
		});
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
}
