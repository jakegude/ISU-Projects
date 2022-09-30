package hw4;

import api.*;

public abstract class AbstractPiece implements Piece
{
	/**
	 * Position variable
	 */
  private Position position;
	/**
	 * Cell array variable
	 */
  private Cell [] cells;
  
  /**
   * @return position
   */
  @Override
  public Position getPosition() {
    return position;
  }
  
  /**
   * sets variable cells to a given array of cells
   */
  @Override
  public void setCells(Cell[] givenCells) {
    // deep copy the given array
    cells = new Cell[givenCells.length];
    cells = givenCells.clone();
  }
  
  /**
   * returns a copied array of the original array of cells
   * @return copy
   */
  @Override
  public Cell[] getCells() {
    // deep copy this object's cell array
	  return cells;
  }
  
  /**
   * returns the absolute position of sells
   * @return ret
   */
  @Override
  public Cell[] getCellsAbsolute() {
    Cell[] ret = new Cell[cells.length];
    
    int row;
    int col;
    Icon b;
    
    for (int i = 0; i < ret.length; i++) {
    	row = cells[i].getRow() + getPosition().row();
    	col = cells[i].getCol() + getPosition().col();
    	b = cells[i].getIcon();
    	ret[i] = new Cell(b, new Position(row, col));
    }

    return ret;
  }
  
  /**
   * shifts position of cells down one row
   */
  @Override
  public void shiftDown() {
    setPosition(new Position(getPosition().row() + 1, getPosition().col()));
  }

  /**
   * shifts position of cells left one column
   */
  @Override
  public void shiftLeft() {
	  setPosition(new Position(getPosition().row(), getPosition().col() - 1));
  }

  /**
   * shifts position of cells right one column
   */
  @Override
  public void shiftRight() {
	  setPosition(new Position(getPosition().row(), getPosition().col() + 1));
  }

  /**
   * transforms each piece variable
   */
  public void transform() {
    // TODO Auto-generated method stub  
  }

  /**
   * cycles the icon colors of each cell to the next cell every time this method is called
   */
  @Override
  public void cycle() {
    Icon [] temp = new Icon[cells.length];
    for (int i = 0; i < cells.length; i++) {
    	temp[i] = cells[i].getIcon();
    }
	  
	for (int i = 0; i < cells.length; i++) {
		cells[(i + 1) % cells.length].setIcon(temp[i]);
    }
  }

  /**
   * returns a cloned version of the Piece variable in use
   * @return s
   */
  @Override
  public Piece clone() {
    try {
      // call the Object clone() method to create a shallow copy
      AbstractPiece s = (AbstractPiece) super.clone();

      // then make it into a deep copy (note there is no need to copy the position,
      // since Position is immutable, but we have to deep-copy the cell array
      // by making new Cell objects      
      s.cells = new Cell[cells.length];
      for (int i = 0; i < cells.length; i++) {
        s.cells[i] = new Cell(cells[i]);
      }
      return s;
    } catch (CloneNotSupportedException e) {
      // can't happen, since we know the superclass is cloneable
      return null;
    }    
  }

  protected void setPosition(Position position) {
	this.position = position;
  }
}
