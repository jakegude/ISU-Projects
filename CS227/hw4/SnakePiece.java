package hw4;

import api.*;

public class SnakePiece extends AbstractPiece {
	
	/**
	 * private variable to hold counter in transform method
	 */
	private int counter = 0;

	/**
	 * Constructor for Snake piece
	 * @param givenPosition
	 * @param icons
	 */
	public SnakePiece(Position givenPosition, Icon [] icons) {
		try {
			setPosition(givenPosition);
			setCells(new Cell[4]);
			getCells()[0] = new Cell(icons[0], new Position(0, 0));
		    getCells()[1] = new Cell(icons[1], new Position(1, 0));
		    getCells()[2] = new Cell(icons[2], new Position(1, 1));
		    getCells()[3] = new Cell(icons[3], new Position(1, 2));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}		
	}
	
	/**
	 * Transform for snake piece
	 */
	@Override
	public void transform() {
		if (counter % 12 == 0) {
			getCells()[0].setRowCol(0, 1);
			getCells()[1].setRowCol(0, 0);
			getCells()[2].setRowCol(1, 0);
			getCells()[3].setRowCol(1, 1);
			
			counter += 1;
			
		} else if (counter % 12 == 1) {			
			getCells()[0].setRowCol(0, 2);
			getCells()[1].setRowCol(0, 1);
			getCells()[2].setRowCol(0, 0);
			getCells()[3].setRowCol(1, 0);
			
			counter += 1;
			
		} else if (counter % 12 == 2) {
			getCells()[0].setRowCol(1, 2);
			getCells()[1].setRowCol(0, 2);
			getCells()[2].setRowCol(0, 1);
			getCells()[3].setRowCol(0, 0);
						
			counter += 1;
		
		} else if (counter % 12 == 3) {			
			getCells()[0].setRowCol(1, 1);
			getCells()[1].setRowCol(1, 2);
			getCells()[2].setRowCol(0, 2);
			getCells()[3].setRowCol(0, 1);
			
			counter += 1;
			
		} else if (counter % 12 == 4) {
			getCells()[0].setRowCol(1, 0);
			getCells()[1].setRowCol(1, 1);
			getCells()[2].setRowCol(1, 2);
			getCells()[3].setRowCol(0, 2);
			
			counter += 1;
			
		} else if (counter % 12 == 5) {
			getCells()[0].setRowCol(2, 0);
			getCells()[1].setRowCol(1, 0);
			getCells()[2].setRowCol(1, 1);
			getCells()[3].setRowCol(1, 2);
			
			counter += 1;
			
		} else if (counter % 12 == 6) {
			getCells()[0].setRowCol(2, 1);
			getCells()[1].setRowCol(2, 0);
			getCells()[2].setRowCol(1, 0);
			getCells()[3].setRowCol(1, 1);
			
			counter += 1;
			
		} else if (counter % 12 == 7) {
			getCells()[0].setRowCol(2, 2);
			getCells()[1].setRowCol(2, 1);
			getCells()[2].setRowCol(2, 0);
			getCells()[3].setRowCol(1, 0);
			
			counter += 1;
			
		} else if (counter % 12 == 8) {
			getCells()[0].setRowCol(1, 2);
			getCells()[1].setRowCol(2, 2);
			getCells()[2].setRowCol(2, 1);
			getCells()[3].setRowCol(2, 0);
			
			counter += 1;
			
		} else if (counter % 12 == 9) {
			getCells()[0].setRowCol(1, 1);
			getCells()[1].setRowCol(1, 2);
			getCells()[2].setRowCol(2, 2);
			getCells()[3].setRowCol(2, 1);
			
			counter += 1;
			
		} else if (counter % 12 == 10) {
			getCells()[0].setRowCol(1, 0);
			getCells()[1].setRowCol(1, 1);
			getCells()[2].setRowCol(1, 2);
			getCells()[3].setRowCol(2, 2);
			
			counter += 1;
			
		} else if (counter % 12 == 11) {			
			getCells()[0].setRowCol(0, 0);
			getCells()[1].setRowCol(1, 0);
			getCells()[2].setRowCol(1, 1);
			getCells()[3].setRowCol(1, 2);
			
			counter += 1;
		}
	}
}