package cluedo.board;

/**
 * A square of the game board which can hold game tokens.
 */
public abstract class Tile {
	
	private Position pos;
	protected char symbol;
	
	public Tile(Position pos, char symbol) {
		this.pos = pos;
		this.symbol = symbol;
	}
	public Position getPos(){
		return pos;
	}
	public char getSymbol(){
		return symbol;
	}
}
