package hw4;

import api.*;

public class IPiece extends AbstractPiece {
	
	/**
	 * Constructor for IPiece
	 * @param givenPosition
	 * @param icons
	 */
	public IPiece(Position givenPosition, Icon [] icons) {
		try {
			setPosition(givenPosition);
		
			setCells(new Cell[3]);
			getCells()[0] = new Cell(icons[0], new Position(0, 1));
			getCells()[1] = new Cell(icons[1], new Position(1, 1));
			getCells()[2] = new Cell(icons[2], new Position(2, 1));
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
	
	//no transform method needed
	
}
