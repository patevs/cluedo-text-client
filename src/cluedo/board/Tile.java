package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.tokens.GameToken;

/**
 * A square of the game board which can hold game tokens.
 * 
 * @author Patrick Evans and Maria Legaspi
 * 
 */
public abstract class Tile {
	
	private Position pos;
	protected char symbol;
	protected GameToken token; // player in square, may be null
	
	/**
	 * Creates a tile with a given position and symbol.
	 * @param pos
	 * @param symbol
	 */
	public Tile(Position pos, char symbol) {
		this.pos = pos;
		this.symbol = symbol;
	}

	/**
	 * Stores a token in this tile.
	 * @param o
	 * @return
	 */
	public boolean setToken(GameToken o){
		if(token!=null && o!=null)
			throw new CluedoError("Tile already contains an item");
		this.token = (GameToken) o;
		return true;
	}
	
	/**
	 * Returns the token in this tile.
	 * @return
	 */
	public GameToken getToken() {
		return token;
	}
	
	/**
	 * Removes the token at this tile.
	 * @return
	 */
	public GameToken remove(){
		GameToken currentItem = token;
		token = null;
		return currentItem;
	}
	
	/**
	 * Returns the position of this tile in the board.
	 * @return
	 */
	public Position getPos(){
		return pos;
	}
	
	/**
	 * Returns the symbol of this tile for printing the board.
	 * @return
	 */
	public char getSymbol(){
		return symbol;
	}
}
