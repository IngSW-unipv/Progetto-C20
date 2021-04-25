package it.unipv.ingsw.c20.scores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class will read the text file that memorize players' score.
 * 
 * @author Mattia Seravalli, Filippo Tagliaferri.
 *
 */
public class ScoreReader {

	private ArrayList<Scores> scores;
	private static final String highscorefile = "res/scores/text.txt";

	/**
	 * The class constructor, it will loads the score file and creates a new array of scores.
	 */
	public ScoreReader() {
	    scores = new ArrayList<Scores>();
	    loadScoreFile();
	}
	
	
	/**
	 * This method will return the score's list of players
	 * @return array list of scores.
	 */
	public ArrayList<Scores> getScores() {
	    return scores;
	}
	
	
	/**
	 * This method will add a new score.
	 * @param name Name of the player.
	 * @param score Score of the player.
	 */
	public void addScore(String name, int score) {
		this.scores.add(new Scores(name, score));
		
		sortScore();
		updateScoreFile();
		loadScoreFile();
	}
	
	
	/**
	 * This method will sort the text file, so that we have a list from the bigger to the smaller score.
	 */
	public void sortScore(){
		Collections.sort(scores, new ScoresComparator());
		while(this.scores.size() > 8){
			this.scores.remove(this.scores.size()-1);
		}
	}
	
	
	/**
	 * This method will load the text file in witch scores are memorized.
	 */
	public void loadScoreFile() {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(highscorefile));
			String line = reader.readLine();
			String name = "";
			int score = 0;
			
			while(this.scores.size() > 0){
				this.scores.remove(this.scores.size()-1);
			}
			
			//Read only 8 best players
			for(int i = 0; i < 8; i ++) {
				
				if(line != null) {
					String[] splitString = line.split(":");
					name = splitString[0];
					score= Integer.valueOf((splitString[1].split(" "))[1]);
					this.scores.add(new Scores(name, score));
					line = reader.readLine();
				}
				
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will update the text files, in witch scores are memorized, with a new score.
	 */
	public void updateScoreFile() {
		
		try {
		    FileWriter myWriter = new FileWriter(highscorefile);
		    for(Scores s : this.scores){
		    	myWriter.write(s.toString() + "\n");
		    }
		    myWriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
}
