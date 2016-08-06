package cluedo.board;

/**
 * Represents a position on the board.
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class Position {

	private int Xpos;
	private int Ypos;
	private char symbol;
	
	/**
	 * Creates a position with a given x, y value.
	 * @param X
	 * @param Y
	 * @param symbol
	 */
	public Position(int X, int Y, char symbol){
		this.Xpos = X;
		this.Ypos = Y;
		this.symbol = symbol;
	}
	
	/**
	 * Returns the x position.
	 * @return
	 */
	public int getX(){
		return this.Xpos;
	}
	
	/**
	 * Returns the y position.
	 * @return
	 */
	public int getY(){
		return this.Ypos;
	}
	
	/**
	 * Returns the character representing this position.
	 * @return
	 */
	public char getSymbol(){
		return this.symbol;
	}
	
}
