package com.epic.framework.implementation;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;

import org.xmlvm.iphone.AVAudioPlayer;
import org.xmlvm.iphone.AVAudioPlayerDelegate;
import org.xmlvm.iphone.NSBundle;
import org.xmlvm.iphone.NSError;
import org.xmlvm.iphone.NSErrorHolder;
import org.xmlvm.iphone.NSURL;

import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;

public class EpicSoundManagerImplementation {
    private static EpicSound currentMusic = null;
    private static AVAudioPlayer currentMusicPlayer = null;
    private static HashMap<String, AVAudioPlayer> soundPlayers = new HashMap<String, AVAudioPlayer>();
	
	public static void playSound(EpicSound sound) {
		AVAudioPlayer player = getPlayerForSound(sound, false);
		player.play();
	}

	public static void playMusic(EpicSound sound) {
		EpicLog.i("Request to play music " + sound.name);
		if(currentMusic != sound) {
			currentMusic = sound;
			currentMusicPlayer = getPlayerForSound(sound, true);
		}
		resumeMusic();
	}


	public static void stopMusic() {
		if(currentMusicPlayer != null) {
			currentMusicPlayer.stop();
		}
	}

	public static void preload(EpicSound musicToPreload, EpicSound[] soundsToPreload) {
		for(EpicSound sound : soundsToPreload) {
			getPlayerForSound(sound, false);
			//	EpicLog.i("Preloaded sound: " + sound.name);
		}
		
		getPlayerForSound(musicToPreload, true);
	}

	public static void pauseMusic() {
		if(currentMusicPlayer != null) {
			currentMusicPlayer.pause();
		}
	}

	private static void resumeMusic() {
		if(currentMusicPlayer != null) {
			currentMusicPlayer.play();
			EpicLog.v("Resuming music.");
		} else {
			EpicLog.w("Music player is null, so not playing.");
		}
	}
	
	private static AVAudioPlayer getPlayerForSound(EpicSound sound, boolean loop) {
		if(!soundPlayers.containsKey(sound.name)) {
			soundPlayers.put(sound.name, createPlayerForSound(sound, loop));
		}
		
		return soundPlayers.get(sound.name);
	}
	
	/**
     * This method is called to initialize the AVAudioPlayer object
     */
    private static AVAudioPlayer createPlayerForSound(EpicSound sound, boolean loop) {
    	if(sound == null) {
    		return null;
    	}
        /**
         * A NSBundle object represents location in file system. Using pathForResource(),
         * a file with specified extension and name can be looked for in a given bundle directory.
         * Here, mainBundle() returns NSBundle object corresponding to the directory where
         * application executable is residing.
         */
    	try {
    		NSBundle bundle = NSBundle.mainBundle();
    		String path = bundle.pathForResource(sound.name, sound.extension);
    		EpicFail.assertNotNull(path, "Sound File Not Found for '" + sound.name + "'");
    		NSURL url = NSURL.fileURLWithPath(path);
    		EpicFail.assertNotNull(url, "url can't be null. path=" + path);
    		NSErrorHolder errorHolder = new NSErrorHolder();
         
	        /*
	         * Initialize an audio player to play the audio file
	         */
	        AVAudioPlayer newPlayer = AVAudioPlayer.audioPlayerWithContentsOfURL(url, errorHolder);
	        if (newPlayer == null) {
	            System.out.println("Error initializing player: " + errorHolder.description());
	        }
	 
	        /**
	         * Set indefinite number of loops
	         */
	        newPlayer.setDelegate(new AVAudioPlayerDelegate() {
				public void audioPlayerEndInterruption(AVAudioPlayer player) {
				}
				
				public void audioPlayerDidFinishPlaying(AVAudioPlayer player, boolean successfully) {
				}
				
				public void audioPlayerDecodeErrorDidOccur(AVAudioPlayer player, NSError error) {
				}
				
				public void audioPlayerBeginInterruption(AVAudioPlayer player) {
				}
			});
	        
	        if(loop) newPlayer.setNumberOfLoops(-1);
	        
	        return newPlayer;
    	} catch(Exception e) {
    		EpicLog.e(e.toString());
    		return null;
    	}
    }
}
