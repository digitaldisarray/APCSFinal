/**
 * 
 * 
 * Coded following a youtube tutorial
 */

package xyz.disarray.game.util;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class EpicSound {
	public void playEpicSound(String epicMusicLocation, int times) {
		try {
			File epicPath = new File(epicMusicLocation);
			if(epicPath.exists()) {
				AudioInputStream epicAudio = AudioSystem.getAudioInputStream(epicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(epicAudio);
				clip.start();
		        clip.loop(times -1); 
		        
			}else {
				System.out.println("Epic gamer music not found ):");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Finished");
	}
	
	public void loopEpicSound(String epicMusicLocation) {
		try {
			File epicPath = new File(epicMusicLocation);
			if(epicPath.exists()) {
				AudioInputStream epicAudio = AudioSystem.getAudioInputStream(epicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(epicAudio);
				clip.start();
		        clip.loop(Clip.LOOP_CONTINUOUSLY); 
		        System.out.println("Joe");

			}else {
				System.out.println("Epic gamer music not found ):");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Finished");
	}
}
