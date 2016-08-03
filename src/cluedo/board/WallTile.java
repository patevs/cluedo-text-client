package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.tokens.GameToken;

public class WallTile extends Tile{
	
	public WallTile(Position pos){
		super(pos, 'x');
	}
	
	public GameToken getToken() {
		throw new CluedoError("Wall tiles do not store game tokens");
	}

	
	public void setToken(GameToken token) {
		throw new CluedoError("Wall tiles do not store game tokens");
	}
}
