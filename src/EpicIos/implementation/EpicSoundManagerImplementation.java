package com.epic.framework.implementation;

import org.xmlvm.iphone.AVAudioPlayer;
import org.xmlvm.iphone.AVAudioPlayerDelegate;
import org.xmlvm.iphone.NSBundle;
import org.xmlvm.iphone.NSError;
import org.xmlvm.iphone.NSErrorHolder;
import org.xmlvm.iphone.NSURL;

import com.epic.framework.common.Ui.EpicSound;

public class EpicSoundManagerImplementation {
	private static boolean       playing = false;
	private static boolean initialized = false;
    private static AVAudioPlayer audioPlayer;
	
	public static void playMusic(EpicSound sound) {
		if(!initialized) initAudioPlayer(sound.name);
		audioPlayer.play();
		playing = true;
	}

	public static void playSound(EpicSound sound) {
		// TODO: sounds not yet supported--need to find iOS SoundPool equiv or manage a pool of audio
		// 		 players and hope they play correctly
	}

	public static void stopMusic() {
		if(playing) {
			audioPlayer.stop();
		}
	}

	public static void preload(EpicSound[] soundsToPreload) {
		// TODO: this is used on Android because the first time we play it gets skipped. Perhaps iOS doesn't have
		//       this issue so until we see it, we might skip on this.
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
				initAudioPlayer("word_farm_bg");
			}
			
			audioPlayer.play();
			
			return true;
		}
		
		return false;
	}
	
	/**
     * This method is called to initialize the AVAudioPlayer object
     */
    private static void initAudioPlayer(String audioFile) {
        /**
         * A NSBundle object represents location in file system. Using pathForResource(),
         * a file with specified extension and name can be looked for in a given bundle directory.
         * Here, mainBundle() returns NSBundle object corresponding to the directory where
         * application executable is residing.
         */
        NSURL url = NSURL.fileURLWithPath(NSBundle.mainBundle().pathForResource(audioFile, "mp3"));
        NSErrorHolder errorHolder = new NSErrorHolder();
         
        /*
         * Initialize an audio player to play the audio file
         */
        audioPlayer = AVAudioPlayer.audioPlayerWithContentsOfURL(url, errorHolder);
        if (audioPlayer == null) {
            System.out.println("Error initializing player: " + errorHolder.description());
        }
 
        /**
         * Set indefinite number of loops
         */
        audioPlayer.setNumberOfLoops(-1);
        audioPlayer.setDelegate(new AVAudioPlayerDelegate() {
			public void audioPlayerEndInterruption(AVAudioPlayer player) {
			}
			
			public void audioPlayerDidFinishPlaying(AVAudioPlayer player, boolean successfully) {
			}
			
			public void audioPlayerDecodeErrorDidOccur(AVAudioPlayer player, NSError error) {
			}
			
			public void audioPlayerBeginInterruption(AVAudioPlayer player) {
			}
		});
        
        initialized = true;
    }
}
