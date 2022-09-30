package hw4;

import api.*;

public class DiagonalPiece extends AbstractPiece {
	
	/**
	 * Constructor for DiagonalPiece
	 * @param givenPosition
	 * @param icons
	 */
	public DiagonalPiece(Position givenPosition, Icon [] icons) {
		try {
			setPosition(givenPosition);
			setCells(new Cell[2]);
			getCells()[0] = new Cell(icons[0], new Position(0, 0));
			getCells()[1] = new Cell(icons[1], new Position(1, 1));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * transform for DiagonalPiece
	 */
	@Override
	public void transform() {		
		if (getCells()[0].getCol() == 0 && getCells()[1].getCol() == 1) {
			getCells()[0].setCol(1);
			getCells()[1].setCol(0);;
		} else {
			getCells()[0].setCol(0);
			getCells()[1].setCol(1);
		}
	}
}
