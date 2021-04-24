package it.unipv.ingsw.c20.scores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ScoreReader {

	private ArrayList<Scores> scores;
	private static final String highscorefile = "res/scores/text.txt";

	public ScoreReader() {
	    scores = new ArrayList<Scores>();
	    loadScoreFile();
	}
	
	public ArrayList<Scores> getScores() {
	    return scores;
	}
	
	public void addScore(String name, int score) {
		this.scores.add(new Scores(name, score));
		sortScore();
		updateScoreFile();
		loadScoreFile();
	}
	
	public void sortScore(){
		Collections.sort(scores, new ScoresComparator());
		while(this.scores.size() > 10){
			this.scores.remove(this.scores.size()-1);
		}
	}
	
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
			
			while (line != null) {
				
				String[] splitString = line.split(":");
				name = splitString[0];
				score = Integer.valueOf((splitString[1].split(" "))[1]);
				this.scores.add(new Scores(name, score));
				line = reader.readLine();
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
