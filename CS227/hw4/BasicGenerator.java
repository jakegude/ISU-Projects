package hw4;

import java.util.Random;
import api.*;

/**
 * Generator for Piece objects in BlockAddiction. Icons are 
 * always selected uniformly at random, and the Piece types
 * are generated with the following probabilities:
 * <ul>
 * <li>LPiece - 10%
 * <li>DiagonalPiece - 25%
 * <li>CornerPiece - 15%
 * <li>SnakePiece - 10%
 * <li>IPiece - 40%
 * </ul>
 * The initial position of each piece is based on its
 * vertical size as well as the width of the grid (given
 * as an argument to getNext).  The initial column is always
 * width/2 - 1.  The initial row is:
 *  * <ul>
 * <li>LPiece - row = -2
 * <li>DiagonalPiece - row = -1
 * <li>CornerPiece - row = -1
 * <li>SnakePiece - row = -1
 * <li>IPiece - row = -2
 * </ul>
 * 
 */
public class BasicGenerator implements Generator
{
	/**
	 * Random variable to generate the probability for each Piece
	 */
	private Random rand;

  /**
   * Constructs a BasicGenerator that will use the given
   * Random object as its source of randomness.
   * @param givenRandom
   *   instance of Random to use
   */
  public BasicGenerator(Random givenRandom)
  {
    rand = givenRandom;
  }
  
  /**
   * Gets the next Piece
   * @param width
   */
  @Override
  public Piece getNext(int width)
  {
    int col = width / 2 - 1;
    Icon [] icon = new Icon[4];
    Random r = new Random(100);;
    
    for (int i = 0; i < icon.length; i++) {
    	icon[i] = new Icon(Icon.COLORS[r.nextInt(Icon.COLORS.length)]);
    }
    
    if (rand.nextInt() >= 60) {
    	return new IPiece(new Position(-2, col), icon);
    } else if (rand.nextInt() >= 50) {
    	return new SnakePiece(new Position(-1, col), icon);
    } else if (rand.nextInt() >= 35) {
    	return new CornerPiece(new Position(-1, col), icon);
    } else if (rand.nextInt() >= 10) {
    	return new DiagonalPiece(new Position(-1, col), icon);
    } else {
    	return new LPiece(new Position(-2, col), icon);
    }    
  }

  /**
   * Random icon generator
   */
  @Override
  public Icon randomIcon()
  {
    return new Icon(Icon.COLORS[rand.nextInt(Icon.COLORS.length)]);
  }

}
