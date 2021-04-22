package it.unipv.ingsw.c20.scores;

public class Scores{
private int score;
private String name;


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
	
	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public String toString(){
		return name + ": " + score;
	}
}

