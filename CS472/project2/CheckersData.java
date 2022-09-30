package edu.iastate.cs472.proj2;

/**
 * 
 * @author jakegude
 * 
 */

import java.util.ArrayList;

/**
 * An object of this class holds data about a game of checkers.
 * It knows what kind of piece is on each square of the checkerboard.
 * Note that RED moves "up" the board (i.e. row number decreases)
 * while BLACK moves "down" the board (i.e. row number increases).
 * Methods are provided to return lists of available legal moves.
 */
public class CheckersData {

  /*  The following constants represent the possible contents of a square
      on the board.  The constants RED and BLACK also represent players
      in the game. */
    static final int EMPTY = 0;
    static final int RED = 1;
    static final int RED_KING = 2;
   	static final int BLACK = 3;
 	static final int BLACK_KING = 4;

    int[][] board;  // board[r][c] is the contents of row r, column c.
    int curplayer = 0; //0 == red (human), 1 == black (AI)
    int score = -1;


    /**
     * Constructor.  Create the board and set it up for a new game.
     */
    CheckersData() {
        this.board = new int[8][8];
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.board[ i ][ j ] = 0;
			}
		}
        setUpGame();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //to string method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            sb.append(8 - i).append(" ");
            for (int n : row) {
                if (n == 0) {
                    sb.append(" ");
                } else if (n == 1) {
                    sb.append(ANSI_RED + "R" + ANSI_RESET);
                } else if (n == 2) {
                    sb.append(ANSI_RED + "K" + ANSI_RESET);
                } else if (n == 3) {
                    sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
                } else if (n == 4) {
                    sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("  a b c d e f g h");

        return sb.toString();
    }

    /**
     * Set up the board with checkers in position for the beginning
     * of a game.  Note that checkers can only be found in squares
     * that satisfy  row % 2 == col % 2.  At the start of the game,
     * all such squares in the first three rows contain black squares
     * and all such squares in the last three rows contain red squares.
     */
    void setUpGame() {
    	// Set up the board with pieces BLACK, RED, and EMPTY
    	for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if ( row % 2 == col % 2 ) {
                  if (row < 3)
                     board[row][col] = BLACK;
                  else if (row > 4)
                     board[row][col] = RED;
                  else
                     board[row][col] = EMPTY;
               }
               else {
                  board[row][col] = EMPTY;
               }
            }
    	}
    }

    /**
     * Return the contents of the square in the specified row and column.
     */
    int pieceAt(int row, int col) {
        return board[row][col];
    }

    /**
     * Make the specified move.  It is assumed that move
     * is non-null and that the move it represents is legal.
     *
     * Update 03/18: make a single move or a sequence of jumps
     * recorded in rows and cols.
     *
     */
    void makeMove(CheckersMove move) {
    	if (move.isDoubleJump()) {
    		score+=7;
    	} else if (move.isJump()) {
    		score+=3;
    	} 
    	int l = move.rows.size();
        for(int i = 0; i < l-1; i++)
            makeMove(move.rows.get(i), move.cols.get(i), move.rows.get(i+1), move.cols.get(i+1));
    }

    /**
     * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
     * assumed that this move is legal.  If the move is a jump, the
     * jumped piece is removed from the board.  If a piece moves to
     * the last row on the opponent's side of the board, the
     * piece becomes a king.
     *
     * @param fromRow row index of the from square
     * @param fromCol column index of the from square
     * @param toRow   row index of the to square
     * @param toCol   column index of the to square
     */
    void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            // The move is a jump.  Remove the jumped piece from the board.
            int jumpRow = (fromRow + toRow) / 2;  // Row of the jumped piece.
            int jumpCol = (fromCol + toCol) / 2;  // Column of the jumped piece.
            board[jumpRow][jumpCol] = EMPTY;
        }
        if (toRow == 0 && board[toRow][toCol] == RED)
            board[toRow][toCol] = RED_KING;
        if (toRow == 7 && board[toRow][toCol] == BLACK)
            board[toRow][toCol] = BLACK_KING;
    }
    
    //same as make move but also returns the board after move is made
    CheckersData doMove(CheckersMove move) {
		makeMove(move);
    	return this;
    }

    /**
     * Return an array containing all the legal CheckersMoves
     * for the specified player on the current board.  If the player
     * has no legal moves, null is returned.  The value of player
     * should be one of the constants RED or BLACK; if not, null
     * is returned.  If the returned value is non-null, it consists
     * entirely of jump moves or entirely of regular moves, since
     * if the player can jump, only jumps are legal moves.
     *
     * @param player color of the player, RED or BLACK
     */
    CheckersMove[] getLegalMoves(int player) {

        if (player != RED && player != BLACK)
            return null;

        int playerKing;  // The constant representing a King belonging to player.
        if (player == RED)
            playerKing = RED_KING;
        else
            playerKing = BLACK_KING;

        ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  // Moves will be stored in this list.

       
        //double moves only
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerKing) {
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2) ) {
                    	CheckersMove newMove = new CheckersMove(row, col, row+2, col+2);
                    	if (canDoubleJump(player, newMove, row+2, col+2, row+3, col+3, row+4, col+4)) {
                    		newMove.addMove(row+4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col+2, row-3, col+3, row-4, col+4)) {
                    		newMove.addMove(row-4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col+2, row+3, col-3, row+4, col-4)) {
                    		newMove.addMove(row+4, col-4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col+2, row-3, col-3, row-4, col-4)) {
                    		newMove.addMove(row-4, col-4);
                    		moves.add(newMove);
                        }
                    }
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
                		CheckersMove newMove = new CheckersMove(row, col, row-2, col+2);
                    	if (canDoubleJump(player, newMove, row-2, col+2, row+3, col+3, row+4, col+4)) {
                    		newMove.addMove(row+4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col+2, row-3, col+3, row-4, col+4)) {
                    		newMove.addMove(row-4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col+2, row+3, col-3, row+4, col-4)) {
                    		newMove.addMove(row+4, col-4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col+2, row-3, col-3, row-4, col-4)) {
                    		newMove.addMove(row-4, col-4);
                    		moves.add(newMove);
                        }
                    }
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
                    	CheckersMove newMove = new CheckersMove(row, col, row+2, col-2);
                    	if (canJump(player, row+2, col-2, row+3, col+3, row+4, col+4)) {
                    		newMove.addMove(row+4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col-2, row-3, col+3, row-4, col+4)) {
                    		newMove.addMove(row-4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col-2, row+3, col-3, row+4, col-4)) {
                    		newMove.addMove(row+4, col-4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row+2, col-2, row-3, col-3, row-4, col-4)) {
                    		newMove.addMove(row-4, col-4);
                    		moves.add(newMove);
                        }
                    }
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
                    	CheckersMove newMove = new CheckersMove(row, col, row-2, col-2);
                    	if (canDoubleJump(player, newMove, row-2, col-2, row+3, col+3, row+4, col+4)) {
                    		newMove.addMove(row+4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col-2, row-3, col+3, row-4, col+4)) {
                    		newMove.addMove(row-4, col+4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col-2, row+3, col-3, row+4, col-4)) {
                    		newMove.addMove(row+4, col-4);
                    		moves.add(newMove);
                        }
                        if (canDoubleJump(player, newMove, row-2, col-2, row-3, col-3, row-4, col-4)) {
                    		newMove.addMove(row-4, col-4);
                    		moves.add(newMove);
                        }
                    }   
                }
            }
        }
        
        //single jumps
        if (moves.size() == 0) {
        	 for (int row = 0; row < 8; row++) {
	            for (int col = 0; col < 8; col++) {
	                if (board[row][col] == player || board[row][col] == playerKing) {
	                    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
	                        moves.add(new CheckersMove(row, col, row+2, col+2));
	                    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
	                        moves.add(new CheckersMove(row, col, row-2, col+2));
	                    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
	                        moves.add(new CheckersMove(row, col, row+2, col-2));
	                    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
	                        moves.add(new CheckersMove(row, col, row-2, col-2));
	                }
	            }
	        }
        }
       
        //single board moves, no jumps or double jumps
        if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player || board[row][col] == playerKing) {
                        if (canMove(player,row,col,row+1,col+1))
                            moves.add(new CheckersMove(row,col,row+1,col+1));
                        if (canMove(player,row,col,row-1,col+1))
                            moves.add(new CheckersMove(row,col,row-1,col+1));
                        if (canMove(player,row,col,row+1,col-1))
                            moves.add(new CheckersMove(row,col,row+1,col-1));
                        if (canMove(player,row,col,row-1,col-1))
                            moves.add(new CheckersMove(row,col,row-1,col-1));
                    }
                }
            }
        }

        /* If no legal moves have been found, return null.  Otherwise, create
         an array just big enough to hold all the legal moves, copy the
         legal moves from the ArrayList into the array, and return the array. */

        if (moves.size() == 0)
            return null;
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    }

    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     *
     * Update 03/18: Note that each CheckerMove may contain multiple jumps. 
     * Each move returned in the array represents a sequence of jumps 
     * until no further jump is allowed.
     *
     * @param player The player of the current jump, either RED or BLACK.
     * @param row    row index of the start square.
     * @param col    col index of the start square.
     */
    CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
        if (player != RED && player != BLACK)
            return null;
        int playerKing;  // The constant representing a King belonging to player.
        if (player == RED)
            playerKing = RED_KING;
        else
            playerKing = BLACK_KING;
        ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  // The legal jumps will be stored in this list.
        if (board[row][col] == player || board[row][col] == playerKing) {
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.add(new CheckersMove(row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.add(new CheckersMove(row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.add(new CheckersMove(row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.add(new CheckersMove(row, col, row-2, col-2));
        }
        if (moves.size() == 0)
            return null;
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    } 

    /**
     * This is called by the two previous methods to check whether the
     * player can legally jump from (r1,c1) to (r3,c3).  It is assumed
     * that the player has a piece at (r1,c1), that (r3,c3) is a position
     * that is 2 rows and 2 columns distant from (r1,c1) and that 
     * (r2,c2) is the square between (r1,c1) and (r3,c3).
     */
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false;  // (r3,c3) is off the board.

        if (board[r3][c3] != EMPTY)
            return false;  // (r3,c3) already contains a piece.

        if (player == RED ) {
            if (board[r1][c1] == RED && r3 > r1)
                return false;  // Regular red piece can only move up.
            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
                return false;  // There is no black piece to jump.
            return true;  // The jump is legal.
        }
        else {
            if (board[r1][c1] == BLACK && r3 < r1)
                return false;  // Regular black piece can only move downn.
            if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
                return false;  // There is no red piece to jump.
            return true;  // The jump is legal.
        }

    }
    
    /**
     * returns true if player can double jump to move
     * prevmove is taken as an already valid first jump
     * 
     * @param player
     * @param prevmove
     * @param r1 row of where the player will land of first jump
     * @param c1 col of where the player will land of first jump
     * @param r2 row of the player being jumped
     * @param c2 col of the player being jumped
     * @param r3 row of where the player will land of second jump
     * @param c3 col of wehre the player will land of second jump
     * @return
     */
    private boolean canDoubleJump(int player, CheckersMove prevmove, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false;  // (r3,c3) is off the board.

        if (board[r3][c3] != EMPTY)
            return false;  // (r3,c3) already contains a piece.
        
        if (prevmove.rows.get(1) != r1 && prevmove.cols.get(1) != c1)
        	return false;

        if (player == RED) {
           if (board[r1][c1] == EMPTY && r3 > r1)
                return false;  // Regular red piece can only move up.
            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
                return false;  // There is no black piece to jump.
            return true;  // The jump is legal.
        }
        else {
            if (board[r1][c1] == EMPTY && r3 < r1)
                return false;  // Regular black piece can only move downn.
            if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
                return false;  // There is no red piece to jump.
            return true;  // The jump is legal.
        }
        
//        if (player == RED ) {
//            if (board[r1][c1] == RED && r3 > r1)
//                return false;  // Regular red piece can only move up.
//            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
//                return false;  // There is no black piece to jump.
//            return true;  // The jump is legal.
//        }
//        else {
//            if (board[r1][c1] == BLACK && r3 < r1)
//                return false;  // Regular black piece can only move downn.
//            if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
//                return false;  // There is no red piece to jump.
//            return true;  // The jump is legal.
//        }

    }
    
    /**
     *  returns true if player can move from (r1, c1) to (r2, c2)
     * @param player
     * @param r1
     * @param c1
     * @param r2
     * @param c2
     * @return
     */
    private boolean canMove(int player, int r1, int c1, int r2, int c2) {     
	   if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
	      return false;  // (r2,c2) is off the board.
	      
	   if (board[r2][c2] != EMPTY)
	      return false;  // (r2,c2) already contains a piece.
	
	   if (player == RED) {
	      if (board[r1][c1] == RED && r2 > r1)
	          return false;  // Regualr red piece can only move down.
	       return true;  // The move is legal.
	   }
	   else {
	      if (board[r1][c1] == BLACK && r2 < r1)
	          return false;  // Regular black piece can only move up.
	       return true;  // The move is legal.
	   }
    }
    
    //returns current player of board
    public int getCurrentPlayer() {
    	if (curplayer % 2 == 0) return RED;
    	return BLACK;
    }
    
    //returns true if board is in a terminal state
    public boolean isTerminal() {
    	curplayer = this.getCurrentPlayer();
    	CheckersData tempBoard = this;
    	CheckersMove[] redMoves = tempBoard.getLegalMoves(RED);
    	CheckersMove[] blackMoves = tempBoard.getLegalMoves(BLACK);
    	if (redMoves == null || blackMoves == null) return true;
    	else return false;
    }
    
    //returns score of current board
    public int getScore() {
    	return score;
    }
    
    //returns calculated score of current board
    public int calculateScore() {
    	if (score != -1) {
    		if (curplayer == RED) {
    			score = 1 - score;
    		} else { 
    			return score;
    		}
    	}
    	return score;
    }
    
    //created clone of current checkersdata object
    @Override 
    public CheckersData clone() {
        CheckersData data = new CheckersData();
        for (int i = 0; i < 8; i++) {
        	for (int j = 0; j < 8; j++) {
        		data.board[i][j] = this.board[i][j];
        	}
        }
        return data;
    }

}
