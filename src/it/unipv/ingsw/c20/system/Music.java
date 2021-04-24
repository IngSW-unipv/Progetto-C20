package it.unipv.ingsw.c20.system;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music{

public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

private File soundFile;
private List<Clip> clip;
private boolean music;
private int loop;

/**
* Private because of the singleton
*/
public Music(){
	this.music = true;
	//this.clip = new ArrayList<Clip>();
	this.clip = new CopyOnWriteArrayList<Clip>();
}

/**
* Play a siren sound
*/
public void play(String musicFileName, int loop){

    this.soundFile = new File(musicFileName);    
	if (music) {
	    try {
	    	this.clip.add(AudioSystem.getClip());
			this.clip.get(this.clip.size()-1).open(AudioSystem.getAudioInputStream(this.soundFile));
			this.clip.get(this.clip.size()-1).loop(loop);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public void tick() {
	for(Clip c : this.clip){
		if (!music) {
	        c.stop();
	    } else {
	        c.loop(loop);
			if(!c.isActive()){
				this.clip.remove(c);
			}
	    }

		System.out.println(this.clip.size());
	}
	
}

}