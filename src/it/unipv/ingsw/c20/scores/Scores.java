package it.unipv.ingsw.c20.scores;


/**
 * This class describes the score of a single player.
 * 
 * @author Mattia Seravalli, Filippo Tagliaferri.
 *
 */
public class Scores{
	
	private int score; //Score that a player gained.
	private String name; //The name of the player.

	/**
	 * Score's constructor
	 * 
	 * @param name The name of the player.
	 * @param score Score that the player gained.
	 */
	public Scores(String name, int score) {
	    this.score = score;
	    String t = "";
	    if(name.contains(":")){
	    	String[] s = name.split(":");
	    	for(String temp : s){
	    		t = t + temp;
	    	}
	    name = t;
	    }
	    this.name = name;
	}
	
	//Getters and setters
	
	/**
	 * This method returns the player's score.
	 * @return player's score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * This method returns the player's name.
	 * @return Player's name.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString(){
		return name + ": " + score;
	}
}

