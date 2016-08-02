package cluedo.board;

/**
 * Represents a position on the board
 * @author Patrick
 *
 */
public class Position {

	private int Xpos;
	private int Ypos;
	private char symbol;
	
	public Position(int X, int Y, char symbol){
		this.Xpos = X;
		this.Ypos = Y;
		this.symbol = symbol;
	}
	
	public int getX(){
		return this.Xpos;
	}
	public int getY(){
		return this.Ypos;
	}
	public char getSymbol(){
		return this.symbol;
	}
	
}
