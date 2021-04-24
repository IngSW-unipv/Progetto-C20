package it.unipv.ingsw.c20.system;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music implements Runnable{
	
	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean music) {
		Music.music = music;
	}

		
	private Clip clip;
	private static boolean music = true;
	private int loop;
	
	public Music(String musicFileName, int loop){
		
		this.loop = loop;
	    try {
	    	this.clip = AudioSystem.getClip();
			this.clip.open(AudioSystem.getAudioInputStream(new File(musicFileName)));
			this.clip.loop(loop);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();
		
	}

	public void run() {
		while(true){
			if (music) {
				if(!this.clip.isRunning()){
					if(this.loop == Clip.LOOP_CONTINUOUSLY){
						this.clip.loop(this.loop);
					}
					if(this.clip.getFramePosition() == this.clip.getFrameLength()){
						this.clip.close();
						return;
					}
				}
				
			}else{
				if(this.loop == Clip.LOOP_CONTINUOUSLY){
					this.clip.stop();	
				}else{
					this.clip.close();
					return;
				}
			}	
		}
	}

}