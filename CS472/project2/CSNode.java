package edu.iastate.cs472.proj2;

/**
 * @author jakegude
 */

import java.util.ArrayList;

/**
 * Child-sibling node type for an n-ary tree.
 */
@SuppressWarnings("hiding")
public class CSNode<CheckersData>
{
  ArrayList<CSNode<CheckersData>> children; 
  CSNode<CheckersData> parent;
  CheckersData board;
  CheckersMove prevmove;
  int boardPlayer;
  double wins;
  double games;
  
  //empty constructor
  public CSNode(){}
  
  //copy constructor
  public CSNode(CSNode<CheckersData> node) {
	  	this.board = node.board;
		this.parent = node.parent;
		this.prevmove = node.prevmove;
		this.children = node.children;
		this.wins = node.wins;
		this.games = node.games;
		this.boardPlayer = node.boardPlayer;
  }
  
//root constructor
  public CSNode(CheckersData board) {
	  this.children = new ArrayList<>();
	  this.board = board;
	  this.parent = null;
	  this.prevmove = null;
	  this.wins = 0;
	  this.games = 0;
  }
  
//not root constructor
  public CSNode(CheckersData data, CSNode<CheckersData> parent, CheckersMove prevmove) {
		this.board = data;
		this.parent = parent;
		this.prevmove = prevmove;
		this.children = new ArrayList<>();
		this.wins = 0;
		this.games = 0;
  }
  
  //true if node is leaf, false if is not
  public boolean isLeaf() {
	  if (this.children == null || this.children.size() == 0) return true;
	  else return false;
  }
  
  //returns children of current node
  public ArrayList<CSNode<CheckersData>> getChildren(){
    return this.children;
  }
  
  //adds a child to current node
  public void addChild(CSNode<CheckersData> child) {
	  if (this.children == null) {
		  this.children = new ArrayList<>();
		  this.children.add(child);
	  } else {
		   this.children.add(child);
	  }
  }
  
  //returns data of current node
  public CheckersData getData() {
    return this.board;
  }
  
  //sets board of current node
  public void setData(CheckersData data) {
    this.board = data;
  }

  //returns parent of current node
  public CSNode<CheckersData> getParent(){
	  return this.parent;
  }
	  
  //sets parent of current node
  public void setParent(CSNode<CheckersData> parent) {
	  this.parent = parent;
  }
  
  //returns previous move made to get board of current node
  public CheckersMove getPreviousMove(){
	  return this.prevmove;
  }
	  
  //sets previous move made to get board of current node
  public void setPreviousMove(CheckersMove prevmove) {
	  this.prevmove = prevmove;
  }
  
  //returns current player of board of current node
  public int getBoardPlayer(){
	  return this.boardPlayer;
  }
	  
  //sets current player of board of current node
  public void setBoardPlayer(int boardPlayer) {
	  this.boardPlayer = boardPlayer;
  }
  
}