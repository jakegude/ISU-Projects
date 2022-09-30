package edu.iastate.cs472.proj2;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author jakegude
 *
 */

/**
 * This class implements the Monte Carlo tree search method to find the best
 * move at the current state.
 */
public class MonteCarloTreeSearch extends AdversarialSearch {

	private CSNode<CheckersData> root;
	private int iterations = 1000;
	private int curIter = 0;
	
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
     */
    public CheckersMove makeMove(CheckersMove[] legalMoves) {
        // The checker board state can be obtained from this.board,
        // which is an 2D array of the following integers defined below:
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
    	root = new CSNode<>(this.board);
    	root.setBoardPlayer(CheckersData.BLACK);
    	CSNode<CheckersData> curNode = root;
    	while (curIter < iterations) {
    		CSNode<CheckersData> leaf = curNode;
    		if (!leaf.isLeaf()) {
    			leaf = select(leaf);
    		}
    		CSNode<CheckersData> child = expand(leaf);
    		boolean result = simulate(child);
    		backPropogate(result, child);
    		curIter++;
    	}
    	
    	CheckersMove bestMove = new CheckersMove();
    	double curBestScore = -1.0;
    	int bestIndex = -1;
    	for (int j = 0; j < root.getChildren().size(); j++) {
    		if (getCurrentStats(root.getChildren().get(j)) > curBestScore) {
    			curBestScore = getCurrentStats(root.getChildren().get(j));
    			bestIndex = j;
    		}
    	}
    	
    	curIter = 0;
    	bestMove = root.getChildren().get(bestIndex).getPreviousMove();
    	return bestMove;
    }

    /**
     * finds leaf node from current node
     * @param tree,  node to be expanded to find leaf node
     * @return
     */
	private CSNode<CheckersData> select(CSNode<CheckersData> tree) {
		if (tree.getData().isTerminal() || tree.isLeaf()) {
			return tree;
		} 
		else {
			ArrayList<CSNode<CheckersData>> children = tree.getChildren();
			CSNode<CheckersData> bestChild = new CSNode<>();
			double bestScore = -1;
			for (CSNode<CheckersData> n : children) {
				double curScore = getCurrentStats(n);
				if (curScore > bestScore) {
					bestScore = curScore;
					bestChild = n;
				}
			}
			return bestChild;
		}
	}
  
	/**
	 * expands current leaf node to get the leaf nodes children, if leaf is not a terminal node
	 * @param leaf,  node to be expanded upon
	 * @return
	 */
	private CSNode<CheckersData> expand(CSNode<CheckersData> leaf) {
    	if (!leaf.isLeaf()) {
    		return leaf;
    	}
    	else {
    		CheckersData node = leaf.getData().clone();
    		CheckersMove[] moves = node.getLegalMoves(leaf.getBoardPlayer());
    		CSNode<CheckersData> child = new CSNode<>();
			for (CheckersMove m : moves) {
				child.setData(node.doMove(m));
				child.setParent(leaf);
				child.setPreviousMove(m);
				if (leaf.getBoardPlayer() == CheckersData.BLACK) {
					child.setBoardPlayer(CheckersData.RED);
				} else { //leaf.getBoardPlayer() == CheckersData.RED
					child.setBoardPlayer(CheckersData.BLACK);
				}
				leaf.addChild(child);
				node = leaf.getData().clone();
			}
			Random r = new Random();
			return leaf.getChildren().get(r.nextInt(leaf.getChildren().size()));
    	}
	}

	/**
	 * simulates random moves starting from child until reaching a terminal node
	 *  returns true if current player of child wins in terminal state that is found
	 *  return false if current player of child loses in terminal state that is found
	 * @param child
	 * @return
	 */
	private boolean simulate(CSNode<CheckersData> child) {
		if (child.getData().isTerminal()) {
			return true;
		}
		CheckersData board = child.getData().clone();
		int childPlayer = child.getBoardPlayer();
		int simulatingPlayers = -1;
		if (childPlayer == CheckersData.BLACK) {
			simulatingPlayers = CheckersData.RED;
		} else if (childPlayer == CheckersData.RED) {
			simulatingPlayers = CheckersData.BLACK;
		} 
		while (!board.isTerminal()) {
			if (simulatingPlayers == CheckersData.BLACK) {
				CheckersMove[] moves = board.getLegalMoves(simulatingPlayers);
				Random r = new Random();
	    		int randomNum = r.nextInt(moves.length);
	    		board.doMove(moves[randomNum]);
	    		if (board.isTerminal())  {
	    			break;
	    		} else {
	    			simulatingPlayers = CheckersData.RED;
	    		}
			} else { // simulatingPlayers == CheckersData.RED
				CheckersMove[] moves = board.getLegalMoves(simulatingPlayers);
				Random r = new Random();
	    		int randomNum = r.nextInt(moves.length);
	    		board.doMove(moves[randomNum]);
	    		if (board.isTerminal())  {
	    			break;
	    		} else {
	    			simulatingPlayers = CheckersData.BLACK;
	    		}	    		
			}
		}
		if (board.getCurrentPlayer() == child.getBoardPlayer()) return true;
		else return false;
	}
	
	/**
	 * back propagation from node to root
	 * updating scores of each node for its respective player
	 *
	 * @param result
	 * @param node
	 */
	private static void backPropogate(boolean result, CSNode<CheckersData> node) {
		updateStats(result, node);
		if (result) result = false;
		else if (!result) result = true;
		if (node.getParent() != null) {
			node = node.getParent();
			backPropogate(result, node);
		} 
	}
	
	/**
	 * if result is true, player of node won and each of their nodes win and game values will be updated
	 * if result is false, player of node lost and each of their nodes game values will be updated
	 * @param result
	 * @param node
	 */
	public static void updateStats(boolean result, CSNode<CheckersData> node) {
		  node.games++;
		  if (result) node.wins++;
	  }
	
	/**
	 * returns current stats of node
	 * node's wins / games + (sqrt(2) * sqrt(log(num of total games from root / nodes total games)
	 * @param node
	 * @return
	 */
	public Double getCurrentStats(CSNode<CheckersData> node) {
		 return node.wins/node.games + Math.sqrt(2) *  Math.sqrt(Math.log(root.games) / node.games);
	}
	
}