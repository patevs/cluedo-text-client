package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.tokens.GameToken;

/**
 * An empty tile that cannot store tokens.
 */
public class WallTile extends Tile{
	
	/**
	 * Creates a wall tile object.
	 * @param pos
	 */
	public WallTile(Position pos){
		super(pos, 'x');
	}
	
	/**
	 * Returns null as cannot store game tokens.
	 */
	public GameToken getToken() {
		return null;
	}
	
	/**
	 * Returns false as cannot store game tokens.
	 */
	public boolean setToken(GameToken token) {
		return false;
	}
}
