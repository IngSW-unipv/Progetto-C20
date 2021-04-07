package it.unipv.ingsw.c20.system;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class that creates the game's music
 *  
 * @author 
 * 
 *
 */
public class Music {

	/**
	 * This method creates the music selected by the inserted path
	 * @param res String that describes the path.
	 * @param loop Integer that describes how many times the sound have to be reproduced.
	 */
	public static void musicActor(String res, int loop){
        try {
            File file = new File(res);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.loop(loop);
          
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

}
