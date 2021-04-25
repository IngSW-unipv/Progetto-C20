package it.unipv.ingsw.c20.system;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class will manages the music in the game.
 * @author Mattia Seravalli
 *
 */
public class Music implements Runnable{
		
	private Clip clip;
	private static boolean music = true;
	private int loop;
	
	/**
	 * Music's constructor, it will start the music and the thread that is going to handle it
	 * @param musicFileName location of the music
	 * @param loop loop of the song
	 */
	public Music(String musicFileName, int loop){
		
		this.loop = loop;
	    try {
	    	
	    	//Open the input stream with the clip
	    	this.clip = AudioSystem.getClip();
			this.clip.open(AudioSystem.getAudioInputStream(new File(musicFileName)));
			
			//With gainControl we will control the music volume.
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f); 		//Reduces volume by 10 decibels.
			
			this.clip.loop(loop);
			
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	    
		Thread thread = new Thread(this);
		thread.start();
		
	}

	
	/**
	 * Controls if the music is ended
	 */
	public void run() {
		
		while(true){
			if (music) {
				if(!this.clip.isRunning()){
					if(this.loop == Clip.LOOP_CONTINUOUSLY){
						this.clip.loop(this.loop);
					}
					if(this.clip.getFramePosition() == this.clip.getFrameLength()){
						//Close the stream if the music has come to end.
						this.clip.close();
						return;
					}
				}
			}else{
				if(this.loop == Clip.LOOP_CONTINUOUSLY){
					//Stop the thread.
					this.clip.stop();	
				}else{
					//Close the stream.
					this.clip.close();
					return;
				}
			}	
		}
	}
	
	
	/**
	 * Music getter
	 * @return the boolean that indicate if the audio is on or off
	 */
	public static boolean isMusic() {
		return music;
	}

	
	/**
	 * Music setter
	 * @param music true or false
	 */
	public static void setMusic(boolean music) {
		Music.music = music;
	}

}