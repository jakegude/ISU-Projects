package hw4;

import api.*;

public class LPiece extends AbstractPiece {
	
	/**
	 * Constructor for LPiece
	 * @param givenPosition
	 * @param icons
	 */
	public LPiece(Position givenPosition, Icon [] icons) {
		try {
			setPosition(givenPosition);
		
			setCells(new Cell[4]);
			getCells()[0] = new Cell(icons[0], new Position(0, 0));
			getCells()[1] = new Cell(icons[1], new Position(0, 1));
			getCells()[2] = new Cell(icons[2], new Position(1, 1));
			getCells()[3] = new Cell(icons[3], new Position(2, 1));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Transform for LPiece
	 */
	@Override
	public void transform() {
		if (getCells()[0].getCol() == 0) {
			getCells()[0].setCol(2);

		} else {
			getCells()[0].setCol(0);
		}
	}
}
