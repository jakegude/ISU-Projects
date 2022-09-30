package edu.iastate.cs472.proj1;

import java.util.Comparator;

/**
 *  
 * @author jakegude
 *
 */

/**
 * This method compares two states in the lexicographical order of the board configuration. 
 * The 3X3 array representing each board configuration is converted into a sequence of nine 
 * digits starting at the 0th row, and within each row, at the 0th column.  For example, the 
 * two states
 * 
 * 	   2 0 3        2 8 1 
 *     1 8 4        7 5 3 
 *     7 6 5        6 0 4 
 *
 * are converted into the sequences <2,0,3,1,8,4,7,6,5>, and <2,8,1,7,5,3,6,0,4>, respectively. 
 * By definition the first state is less than the second one.  
 * 
 * The comparator will be used for maintaining the CLOSED list used in the A* algorithm. 
 */
public class StateComparator implements Comparator<State>
{
	@Override
	public int compare(State s1, State s2)
	{
		int s1value = GetStateValue(s1);
		int s2value = GetStateValue(s2);
		if (s1value < s2value) return -1;
		if (s1value == s2value) return 0;
		return 1;
	} 
	
	public static int GetStateValue(State s) {
		int value = 0;
		value += s.board[ 0 ][ 0 ] * 100000000;
		value += s.board[ 0 ][ 1 ] * 10000000;
		value += s.board[ 0 ][ 2 ] * 1000000;
		value += s.board[ 1 ][ 0 ] * 100000;
		value += s.board[ 1 ][ 1 ] * 10000;
		value += s.board[ 1 ][ 2 ] * 1000;
		value += s.board[ 2 ][ 0 ] * 100;
		value += s.board[ 2 ][ 1 ] * 10;
		value += s.board[ 2 ][ 2 ] * 1;
		return value;		
	}
}