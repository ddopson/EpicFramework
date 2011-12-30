package com.epic.framework.implementation;

import java.util.HashMap;

import org.xmlvm.iphone.AVAudioPlayer;
import org.xmlvm.iphone.AVAudioPlayerDelegate;
import org.xmlvm.iphone.NSBundle;
import org.xmlvm.iphone.NSError;
import org.xmlvm.iphone.NSErrorHolder;
import org.xmlvm.iphone.NSURL;

import com.epic.framework.common.Ui.EpicSound;
import com.epic.framework.common.util.EpicLog;

public class EpicSoundManagerImplementation {
	private static boolean       playing = false;
	private static boolean initialized = false;
    private static AVAudioPlayer audioPlayer;
    private static HashMap<String, AVAudioPlayer> players = new HashMap<String, AVAudioPlayer>();
	
	public static void playMusic(EpicSound sound) {
		if(!initialized) {
			initAudioPlayer(sound.name, true);
			initialized = true;
		}
		
		audioPlayer.play();
		playing = true;
	}

	public static void playSound(EpicSound sound) {
		if(players.containsKey(sound.name)) {
			try {
				AVAudioPlayer p = players.get(sound.name);
				p.prepareToPlay();
				p.play();
				EpicLog.v("Playing sound: " + sound.name + "." + sound.extension);
			} catch(Exception e) {
				EpicLog.e("Problem playing sound: " + sound.name + "." + sound.extension + ":: " + e.toString());
			}
		} else {
			EpicLog.w("Couldn't find sound: " + sound.name + "." + sound.extension);
		}
	}

	public static void stopMusic() {
		if(playing) {
			audioPlayer.stop();
		}
	}

	public static void preload(EpicSound[] soundsToPreload) {
		for(EpicSound sound : soundsToPreload) {
			EpicLog.i("Preloading sound: " + sound.name);
			AVAudioPlayer p = initAudioPlayer(sound.name.substring(0, sound.name.length() - 4), false);
			if(p != null) {
				players.put(sound.name, p);
				EpicLog.i("Preloaded sound: " + sound.name);
			}
		}
	}

	public static void pauseMusic() {
		if(playing) {
			audioPlayer.pause();
			playing = false;
		}
	}

	public static boolean resumeMusic() {
		if(!playing) {
			if(!initialized) {
				audioPlayer = initAudioPlayer("word_farm_bg", true);
				initialized = true;
			}
			
			audioPlayer.play();
			
			return true;
		}
		
		return false;
	}
	
	/**
     * This method is called to initialize the AVAudioPlayer object
     */
    private static AVAudioPlayer initAudioPlayer(String audioFile, boolean loop) {
        /**
         * A NSBundle object represents location in file system. Using pathForResource(),
         * a file with specified extension and name can be looked for in a given bundle directory.
         * Here, mainBundle() returns NSBundle object corresponding to the directory where
         * application executable is residing.
         */
    	try {
    		NSURL url = NSURL.fileURLWithPath(NSBundle.mainBundle().pathForResource(audioFile, "mp3"));
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
	        if(loop) newPlayer.setNumberOfLoops(-1);
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
	        
	        return newPlayer;
    	} catch(Exception e) {
    		EpicLog.e(e.toString());
    		return null;
    	}
    }
}
