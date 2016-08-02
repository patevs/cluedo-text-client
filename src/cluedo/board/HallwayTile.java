package cluedo.board;

import cluedo.tokens.CharacterToken;

public class HallwayTile extends Tile{
	
	private CharacterToken token; // player in square, may be null

	public HallwayTile(Position pos, char symbol) {
		super(pos, symbol);
	}

	public CharacterToken getToken() {
		return token;
	}

	public void setToken(CharacterToken player) {
		this.token = player;
	}

}
