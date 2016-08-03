package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.tokens.GameToken;

/**
 * A square of the game board which can hold game tokens.
 */
public abstract class Tile {
	
	private Position pos;
	protected char symbol;
	protected GameToken token; // player in square, may be null
	
	public Tile(Position pos, char symbol) {
		this.pos = pos;
		this.symbol = symbol;
	}

	public boolean setToken(GameToken o){
		if(token!=null && o!=null)
			throw new CluedoError("Tile already contains an item");
		this.token = (GameToken) o;
		return true;
	}
	
	public GameToken getToken() {
		return token;
	}
	
	public GameToken remove(){
		GameToken currentItem = token;
		token = null;
		return currentItem;
	}
	
	public Position getPos(){
		return pos;
	}
	
	public char getSymbol(){
		return symbol;
	}
}
