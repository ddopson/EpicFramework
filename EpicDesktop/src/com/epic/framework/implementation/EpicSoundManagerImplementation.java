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

public class EpicSoundManagerImplementation {
	
	public static Player playSound(final EpicSound sound, int loops) {
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
	
	public static void stopSound(Object playerObject) {
		Player player = (Player)playerObject;
		player.close();
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
}
