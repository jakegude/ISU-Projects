package edu.iastate.cs472.proj2;

/**
 * 
 * @author jakegude
 *
 */


/**
 * This class implements the Alpha-Beta pruning algorithm to find the best 
 * move at current state.
*/
public class AlphaBetaSearch extends AdversarialSearch {
	
    /**
     * The input parameter legalMoves contains all the possible moves.
     * It contains four integers:  fromRow, fromCol, toRow, toCol
     * which represents a move from (fromRow, fromCol) to (toRow, toCol).
     * It also provides a utility method `isJump` to see whether this
     * move is a jump or a simple move.
     * 
     * Update 03/18: each legalMove in the input now contains a single move
     * or a sequence of jumps: (rows[0], cols[0]) -> (rows[1], cols[1]) ->
     * (rows[2], cols[2]).
     *
     * @param legalMoves All the legal moves for the agent at current step.
     * 'alpha-beta-search-start
     */	
    public CheckersMove makeMove(CheckersMove[] legalMoves) {
        // The checker board state can be obtained from this.board,
        // which is a int 2D array. The numbers in the `board` are
        // defined as
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
    	// this.board ==  current state, legalMoves[] == next possible states
    	int value = Integer.MIN_VALUE;
    	int alpha = value; 
    	int beta = Integer.MAX_VALUE;
    	int player = this.board.getCurrentPlayer();
    	CheckersMove bestMove = new CheckersMove();
    	for (CheckersMove move : legalMoves) {
    		int newval = alphabeta(move, 10, value, beta, player);
    		if (newval > value) {
    			value = newval;
    			bestMove = move;
    		}
    	}
    	return bestMove;
    }
    
    /**
     * recursive method to find best move with alpha beta pruning
     * @param move
     * @param depth
     * @param alpha
     * @param beta
     * @param player
     * @return
     */
    private int alphabeta(CheckersMove move, int depth, int alpha, int beta, int player) {
    	CheckersData temp = board.clone();
    	temp.makeMove(move);
    	if (temp.isTerminal() || depth == 0) {
    		return temp.getScore();
    	}
    	if (player == CheckersData.BLACK) {
    		int value = Integer.MIN_VALUE;
    		CheckersMove[] moves = temp.getLegalMoves(player);
    		for (CheckersMove m : moves) {
    			value = Math.max(value, alphabeta(m, depth-1, alpha, beta, CheckersData.RED));
    			if (value >= beta) {
    				break;
    			}
    			alpha = Math.max(alpha, value);
    		}
    		return value;
    	} else {
    		int value = Integer.MAX_VALUE;
    		CheckersMove[] moves = temp.getLegalMoves(player);
    		for (CheckersMove m : moves) {
    			value = Math.min(value, alphabeta(m, depth-1, alpha, beta, CheckersData.BLACK));
    			if (value <= alpha) {
    				break;
    			}
    			beta = Math.min(value, beta);
       		}
    		return value;
    	}
    }
}




















