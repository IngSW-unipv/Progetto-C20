package it.unipv.ingsw.c20.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music implements Runnable{

public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

private File soundFile;
private Thread thread;
private List<Clip> clip;
private Clip singleClip;
private boolean music;
private int loop;

/**
* Private because of the singleton
*/
public Music(){
	this.music = true;
	this.clip = new ArrayList<Clip>();
}

/**
* Play a siren sound
*/
public void play(String musicFileName, int loop){

    this.soundFile = new File(musicFileName);    
	if (!music) {
    } else {
	    try {
    		this.clip.add(AudioSystem.getClip());
			this.clip.get(this.clip.size()-1).open(AudioSystem.getAudioInputStream(this.soundFile));
			this.clip.get(this.clip.size()-1).loop(loop);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    thread = new Thread(this);
    thread.setName("SoundPlayer");
    thread.start();
}

/**
* Invoked when the thread kicks off
*/
public void run() {
	for(Clip c : this.clip){
		if (!music) {
	        c.stop();
	    } else {
	        c.loop(loop);
	    }
	}
	
}

public void volume(float volume){

    //TODO NEED HELP HERE
    /*
    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(-50.0f); // Reduce volume IN DECIBELS
    clip.start();
        */
    }
}