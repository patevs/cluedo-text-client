package cluedo.board;

import cluedo.tokens.CharacterToken;

/**
 * A hallway tile.
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class HallwayTile extends Tile{

	/**
	 * Creates a hallway tile.
	 * @param pos
	 * @param symbol
	 */
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
