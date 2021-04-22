package it.unipv.ingsw.c20.scores;

import java.util.Comparator;

public class ScoresComparator implements Comparator<Scores>{

	@Override
	public int compare(Scores s1, Scores s2) {
		// TODO Auto-generated method stub
		return s1.getScore() > s2.getScore() ? -1 : s1.getScore() == s2.getScore() ? 0 : 1;
	}

}
