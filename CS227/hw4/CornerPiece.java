package hw4;

import api.*;

public class CornerPiece extends AbstractPiece {
	
	/**
	 * Constructor for CornerPiece
	 * @param givenPosition
	 * @param icons
	 */
	public CornerPiece(Position givenPosition, Icon [] icons) {

		try {
			setPosition(givenPosition);
			setCells(new Cell[3]);
			getCells()[0] = new Cell(icons[0], new Position(0, 0) );
		    getCells()[1] = new Cell(icons[1], new Position(1, 0));
		    getCells()[2] = new Cell(icons[2], new Position(1, 1));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Transform for corner piece
	 */
	@Override
	public void transform() {
		if (getCells()[0].getRow() == 0 && getCells()[0].getCol() == 0) {
			getCells()[0].setCol(1);
			getCells()[1].setRow(0);
			getCells()[2].setCol(0);
		} else if (getCells()[0].getRow() == 0 && getCells()[0].getCol() == 1) {
			getCells()[0].setRow(1);
			getCells()[1].setCol(1);
			getCells()[2].setRow(0);
		} else if (getCells()[0].getRow() == 1 && getCells()[0].getCol() == 1) {
			getCells()[0].setCol(0);
			getCells()[1].setRow(1);
			getCells()[2].setCol(1);
		} else if (getCells()[0].getRow() == 1 && getCells()[0].getCol() == 0) {
			getCells()[0].setRow(0);
			getCells()[1].setCol(0);
			getCells()[2].setRow(1);
		}
	}
}