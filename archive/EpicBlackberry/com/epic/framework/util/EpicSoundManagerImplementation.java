package com.epic.framework.util;

import java.io.InputStream;

import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.epic.framework.Ui.EpicFile;
import com.epic.framework.Ui.EpicSound;
import com.epic.resources.EpicSounds;

public class EpicSoundManagerImplementation {
	public static boolean soundEnabled = true;
	private static Player _musicPlayer;

	public static void playMusic(EpicSound sound) {
		   try
	        {
	        	if(_musicPlayer != null) {
	        		_musicPlayer.stop();
	    			_musicPlayer.deallocate();
	    			_musicPlayer.close();
	    		}

	        	if(sound == null) {
	        		return;
	        	}
	        	
	            // Set InputStream to a midi file included as resource, as specified by
	            // passMusic
	        	
	            InputStream in = EpicFileImplementation.openInputStream(EpicSounds.word_farm_bg.getFilename());
	            // Create a media player with mime type of audio/mp3 using our inputstream
	            _musicPlayer = javax.microedition.media.Manager.createPlayer(in, "audio/mp3");

	            // Ready the data and start playing it.  To loop indefinitely, we set loopcount
	            // to -1.
	            _musicPlayer.realize();
	            _musicPlayer.prefetch();
	            _musicPlayer.setLoopCount(-1);
	            _musicPlayer.start();
	        }
	        catch (Exception e)
	        {
	        	EpicLog.e(e.toString());
	        }		
	}

	public static void playSound(EpicSound sound) {
		// TODO Auto-generated method stub
		
	}

	public static void stopMusic() {
		try {
			_musicPlayer.stop();
		} catch (MediaException e) {
			EpicLog.e(e.toString());
		} catch (Exception ex) {
			EpicLog.e(ex.toString());
		}
	}

	public static void preload(EpicSound[] soundsToPreload) {
		
	}

	public static void pauseMusic() {
		try {
			_musicPlayer.stop();
		} catch (MediaException e) {
			EpicLog.e(e.toString());
		} catch (Exception ex) {
			EpicLog.e(ex.toString());
		}
	}

	public static boolean resumeMusic() {
		try {
			if(_musicPlayer == null) {
				playMusic(EpicSounds.word_farm_bg);
			} else {
				_musicPlayer.start();
			}
			
			return true;
		} catch (MediaException e) {
			EpicLog.e(e.toString());
		} catch (Exception ex) {
			EpicLog.e(ex.toString());
		}
		
		return false;
	}

}
