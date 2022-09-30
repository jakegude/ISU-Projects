package hw2;

import api.Defaults;
import api.Outcome;

/**
 * CricketGame simulates a game of cricket
 * @author jakegude
 */
public class CricketGame {
	/**
	 * input for the given bowls per over
	 */
	private int givenBowlsPerOver;
	
	/**
	 * input for the given overs per inning
	 */
	private int givenOversPerInnings;
	
	/**
	 * input for given total number of innings
	 */
	private int givenTotalInnings;
	
	/**
	 * input for the given number of players on each side
	 */
	private int givenNumPlayers;
	
	/**
	 * keeps track of number of bowls
	 */
	private int bowlCount = 0;
	
	/**
	 * keeps track of number of completed innings
	 */
	private int completedInnings = 0;
	
	/**
	 * keeps track of number of overs
	 */
	private int overCount = 0;
	
	/**
	 * keeps track of number of outs
	 */
	private int numOuts = 0;
	
	/**
	 * score of team batting first
	 */
	private int side0Score = 0;
	
	/**
	 * score of team fielding first
	 */
	private int side1Score = 0;
	
	/**
	 * true if game is ended, false if not
	 */
	private boolean isGameEnded = false;
	
	/**
	 * true if ball is in play, false if not
	 */
	private boolean isInPlay;
	
	/**
	 * true if batters are running, false if not
	 */
	private boolean isRunning;
	
//	TESTING CODE
//	/**
//	 * Main method for testing
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		CricketGame g = new CricketGame(2, 3, 4, 6);
//		
//		g = new CricketGame(2, 3, 4, 6);
//		g.bowl(Outcome.WIDE);
//		g.bowl(Outcome.BOUNDARY_FOUR);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.runOut();
//		g.bowl(Outcome.CAUGHT_FLY);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.safe();
//		g.bowl(Outcome.LBW);
//		g.bowl(Outcome.WICKET);
//		System.out.println("expected 1: " + g.getCompletedInnings()); // expected 1
//		System.out.println("expected 0: " + g.getOverCount()); // expected 0
//		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
//		System.out.println("expected 0: " + g.getOuts()); // expected 0
//		System.out.println("expected 10: " + g.side0Score); // expected 10
//		System.out.println("expected 0: " + g.side0Score); // expected 0
//		System.out.println(" ");
//		
//		g.bowl(Outcome.BOUNDARY_FOUR);
//		g.bowl(Outcome.BOUNDARY_SIX);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.NO_BALL);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.runOut();
//		g.bowl(Outcome.CAUGHT_FLY);
//		g.bowl(Outcome.CAUGHT_FLY);
//		g.bowl(Outcome.LBW);
//		System.out.println("expected 2: " + g.getCompletedInnings()); // expected 2
//		System.out.println("expected 0: " + g.getOverCount()); // expected 0
//		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
//		System.out.println("expected 0: " + g.getOuts()); // expected 0
//		System.out.println("expected 10: " + g.side0Score); // expected 10
//		System.out.println("expected 16: " + g.side1Score); // expected 16
//		System.out.println(" ");
//		
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.safe();
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.runOut();
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.runOut();
//		g.bowl(Outcome.BOUNDARY_FOUR);
//		g.bowl(Outcome.WICKET);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.safe();
//		
//		System.out.println("expected 3: " + g.getCompletedInnings()); // expected 3
//		System.out.println("expected 0: " + g.getOverCount()); // expected 0
//		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
//		System.out.println("expected 0: " + g.getOuts()); // expected 0
//		System.out.println("expected 36: " + g.side0Score); // expected 36
//		System.out.println("expected 16: " + g.side1Score); // expected 16
//		
//		//System.out.println(createString(g));
//	}
//	/**
//	 * Creates a string to help testing
//	 * @param g
//	 * @return String
//	 */
//	private static String createString(CricketGame g) {
//	    int side0Score = g.getScore(g.whichSideIsBatting() == 0);
//	    int side1Score = g.getScore(g.whichSideIsBatting() == 1);
//	
//	    StringBuilder sb = new StringBuilder();
//	    sb.append("Innings: ");
//		sb.append(g.getCompletedInnings());
//		sb.append("\nOvers: ");
//		sb.append(g.getOverCount());
//		sb.append("\nBowls: ");
//		sb.append(g.getBowlCount());
//		sb.append("\nOuts: ");
//		sb.append(g.getOuts());
//		sb.append("\nSide0: ");
//		sb.append(side0Score);
//		sb.append("\nSide1: ");
//		sb.append(side1Score);
//		if (g.isInPlay())
//		{
//		  sb.append("\n(in play)");
//		}
//		if (g.isRunning())
//		{
//		  sb.append("\n(running)");
//		}
//		if (g.isGameEnded())
//		{
//		  sb.append("\n(ended)");
//	    }   
//	    return sb.toString();
//	  }
	
	/**
	 * Creates a game with default parameters
	 */
	public CricketGame() {
		givenBowlsPerOver = Defaults.DEFAULT_BOWLS_PER_OVER;
		givenOversPerInnings = Defaults.DEFAULT_OVERS_PER_INNINGS;
		givenTotalInnings = Defaults.DEFAULT_NUM_INNINGS;
		givenNumPlayers = Defaults.DEFAULT_NUM_PLAYERS;
	}
	
	/**
	 * Creates a game of cricket
	 * @param givenBowlsPerOver
	 * @param givenOversPerInnings
	 * @param givenTotalInnings
	 * @param givenNumPlayers
	 */
	public CricketGame(	int givenBowlsPerOver, int givenOversPerInnings,
						int givenTotalInnings, int givenNumPlayers) {
		this.givenBowlsPerOver = givenBowlsPerOver;
		this.givenOversPerInnings = givenOversPerInnings;
		this.givenTotalInnings = givenTotalInnings;
		this.givenNumPlayers = givenNumPlayers;
		
		if (givenTotalInnings % 2 == 1) {
			this.givenTotalInnings += 1;
		}
	}
	
	/**
	 * simulates a bowl in a cricket game with various outcomes
	 * @param outcome
	 */
	public void bowl(Outcome outcome) {
		//outcome conditionals
		if(outcome == Outcome.BOUNDARY_FOUR && !isInPlay && !isGameEnded) {
			bowlCount += 1;
			if (whichSideIsBatting() == 0) {
				side0Score += 4;
			}
			if (whichSideIsBatting() == 1) {
				side1Score += 4;
			}
		}
		else if(outcome == Outcome.BOUNDARY_SIX && !isInPlay && !isGameEnded) {
			bowlCount += 1;
			if (whichSideIsBatting() == 0) {
				side0Score += 6;
			}
			if (whichSideIsBatting() == 1) {
				side1Score += 6;
			}
		}
		else if(outcome == Outcome.WIDE && !isInPlay && !isGameEnded) {
			if (whichSideIsBatting() == 0) {
				side0Score += 1;
			}
			if (whichSideIsBatting() == 1) {
				side1Score += 1;
			}
		}
		else if(outcome == Outcome.WICKET && !isInPlay && !isGameEnded) {
			numOuts += 1;
			bowlCount += 1;
		}
		else if(outcome == Outcome.LBW && !isInPlay && !isGameEnded) {
			numOuts += 1;
			bowlCount += 1;
		}
		else if(outcome == Outcome.NO_BALL && !isInPlay && !isGameEnded) {
			if (whichSideIsBatting() == 0) {
				side0Score += 1;
			}
			if (whichSideIsBatting() == 1) {
				side1Score += 1;
			}
		}
		else if(outcome == Outcome.HIT && !isInPlay && !isGameEnded) {
			isInPlay = true;
			bowlCount+=1;
		}
		else if(outcome == Outcome.CAUGHT_FLY && !isInPlay && !isGameEnded) {
			numOuts += 1;
			bowlCount += 1;
		}
		
		//bowl, over, and inning count conditionals
		if (bowlCount == givenBowlsPerOver) {
			bowlCount = 0;
			overCount += 1;
		}
		
		if (((overCount == givenOversPerInnings) || (numOuts == (givenNumPlayers - 1))) && !isInPlay) {
			numOuts = 0;
			bowlCount = 0;
			overCount = 0;
			completedInnings += 1;
		}
		
		if (completedInnings == givenTotalInnings && !isInPlay) {
			isGameEnded = true;
		}	
	}
	
	/**
	 * returns the bowl count
	 * @return bowlCount
	 */
	public int getBowlCount() {
		return bowlCount;
	}
	
	/**
	 * returns the amount of completed innings
	 * @return completedInnings
	 */
	public int getCompletedInnings() {
		return completedInnings;
	}
	
	/**
	 * returns the number of outs
	 * @return numOuts
	 */
	public int getOuts() {
		return numOuts;
	}
	
	/**
	 * returns the number of overs
	 * @return overCount
	 */
	public int getOverCount() {
		return overCount;
	}
	
	/**
	 * returns the score of the team that is up to bat if true, if false returns the team
	 * that is fielding
	 * @param battingSide
	 * @return if battingSide is TRUE score1
	 * 			else return score0
	 */
	public int getScore(boolean battingSide) {
		if ((whichSideIsBatting() == 0 && battingSide) || (whichSideIsBatting() == 1 && !battingSide)) {
			return side0Score;
		}
		else {
			return side1Score;
		}
	}
	
	/**
	 * returns true if game is ended, returns false if not
	 * @return isGameEnded
	 */
	public boolean isGameEnded() {
		if (((completedInnings == givenTotalInnings) || 
			(completedInnings >= 3 && side1Score > side0Score)) && !isInPlay) {
			isGameEnded = true;
			return true;
		}
		else {
			isGameEnded = false;
			return false;
		}
	}
	
	/**
	 * will return true or false depending on if the ball is currently in play
	 * @return boolean
	 */
	public boolean isInPlay() {
		return isInPlay;
	}
	
	/**
	 * returns true if the player is running or false if player is not
	 * @return isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * if player is running, runner will get out
	 * if player is not running or game is ended does nothing
	 */
	public void runOut() {
		if (isRunning && !isGameEnded) {
			numOuts += 1;
			isRunning = false;
			isInPlay = false;
		}
		
	}
	
	/**
	 * if ball was in play and batters are running, they are safe and 1 is added to score
	 */
	public void safe() {
		if (isRunning && !isGameEnded && isInPlay) {
			if (whichSideIsBatting() == 0) {
				side0Score += 1;
				isInPlay = false;
				isRunning = false;
			}
			if (whichSideIsBatting() == 1) {
				side1Score += 1;
				isInPlay = false;
				isRunning = false;
			}
		}
		else {
			isInPlay = false;
			isRunning = false;
			return;
		}
		
		if (((overCount == givenOversPerInnings) || (numOuts == (givenNumPlayers - 1))) && !isInPlay) {
			numOuts = 0;
			bowlCount = 0;
			overCount = 0;
			completedInnings += 1;
		}
	}
	
	/**
	 * if the ball is in play the running will try to run to score
	 */
	public void tryRun() {
		if (isRunning && whichSideIsBatting() == 0 && isInPlay) {
			side0Score += 1;
		}
		
		if (isRunning && whichSideIsBatting() == 1 && isInPlay) {
			side1Score += 1;
		}
		
		if (isInPlay && !isGameEnded) {
			isRunning = true;
		}
		if (!isInPlay || !isGameEnded) {
			return;
		}
		
		if (((overCount == givenOversPerInnings) || (numOuts == (givenNumPlayers - 1))) && !isInPlay) {
			numOuts = 0;
			bowlCount = 0;
			overCount = 0;
			completedInnings += 1;
		}
	}
	
	/**
	 * returns which side is batting depending on which inning
	 * @return 1 or 0 depending on the side that is at bat
	 */
	public int whichSideIsBatting() {
		if (completedInnings % 2 == 0) {
			return 0;
		}
		else {
			return 1;
		}
	}	
}