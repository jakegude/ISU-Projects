package hw4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.*;

public class BlockAddiction extends AbstractGame {
	
	/**
	 * BlockAddiction constructor when you want to prefill a certain number of the bottom rows
	 * @param givenHeight
	 * @param givenWidth
	 * @param generator
	 * @param preFillRows
	 */
	public BlockAddiction(int givenHeight, int givenWidth, Generator generator, int preFillRows) {
		super(givenHeight, givenWidth, generator);
		
		for (int i = getHeight() - preFillRows; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1) {
					setBlock(i, j, generator.randomIcon());
				}
			}
		}
	}
	
	/**
	 * BlockAddiction constructor with zero prefilled rows
	 * @param givenHeight
	 * @param givenWidth
	 * @param generator
	 */
	public BlockAddiction(int givenHeight, int givenWidth, Generator generator) {		
		super(givenHeight, givenWidth, generator);
	}
	
	/**
	 * returns the positions to collapse
	 * @return collapse
	 */
	public List<Position> determinePositionsToCollapse() {	    
		ArrayList<Position> collapse = new ArrayList<>();
		ArrayList<Position> dups = new ArrayList<>();
		
		for (int i = 0; i < getHeight(); i++) {
			if (isRowFilled(i)) {
				for (int col = 0; col < getWidth(); col++) {
					dups.add(new Position(i, col));
				}
			}
		}
		
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				ArrayList<Position> temp = new ArrayList<>();

				if (getIcon(row, col) != null) {
					try {
					if (getIcon(row, col).matches(getIcon(row + 1, col))) {
						temp.add(new Position(row + 1, col));
						
						} 
					} catch (ArrayIndexOutOfBoundsException e) {
	
					}
					
					try {
						if (getIcon(row, col).matches(getIcon(row - 1, col))) {
							temp.add(new Position(row - 1, col));
							
						}
					} catch (ArrayIndexOutOfBoundsException e) {
	
					}
					
					try {
						if (getIcon(row, col).matches(getIcon(row, col + 1))) {
							temp.add(new Position(row, col + 1));
							
						}
					} catch (ArrayIndexOutOfBoundsException e) {
	
					}
					
					try {
						if (getIcon(row, col).matches(getIcon(row, col - 1))) {
							temp.add(new Position(row, col - 1));
							
						}
					} catch (ArrayIndexOutOfBoundsException e) {
	
					}
				}
				
				
				if (temp.size() > 1) {
					dups.addAll(temp);
					dups.add(new Position(row, col));
				}
				
			}
		}
		
		for (int i = 0; i < dups.size(); i++) {
			if (!collapse.contains(dups.get(i))) {
				collapse.add(dups.get(i));
			}
		}
	    
		Collections.sort(collapse);
	    return collapse;
	}
	
	/**
	   * Determines whether the given row is completely filled (i.e., all
	   * icons are non-null).
	   * @param row
	   *   the row to be checked
	   * @return
	   *   true if all icons in the row are non-null, false otherwise
	   */
	private boolean isRowFilled(int row) {
	  for (int col = 0; col < getWidth(); ++col) {
		  if (getIcon(row, col) == null) {
			  return false;
	      }
	  }
	  return true;
	}
}
