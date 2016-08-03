package cluedo.board;

import cluedo.tokens.CharacterToken;

public class HallwayTile extends Tile{

	public HallwayTile(Position pos, char symbol) {
		super(pos, symbol);
	}

	@Override
	public char getSymbol(){
		if(super.token != null){
			return Character.forDigit(((CharacterToken)token).getUid(), 10);
		} else {
			return ' ';
		}
	}

}
