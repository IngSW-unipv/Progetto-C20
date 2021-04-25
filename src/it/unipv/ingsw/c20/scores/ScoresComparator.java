package it.unipv.ingsw.c20.scores;

import java.util.Comparator;
/**
 * This class compares its two arguments for order. Returns a negative integer,zero, 
 * or a positive integer as the first argument is less than, equal to, or greater than the second.
 * 
 */
public class ScoresComparator implements Comparator<Scores>{

	@Override
	public int compare(Scores s1, Scores s2) {
		
		return s1.getScore() > s2.getScore() ? -1 : s1.getScore() == s2.getScore() ? 0 : 1;
	}

}
