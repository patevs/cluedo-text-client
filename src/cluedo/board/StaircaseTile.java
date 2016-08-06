package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Room;

/**
 * Represents a staircase tile.
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class StaircaseTile extends RoomTile{

	/**
	 * Creates a staircase tile at a given position in a room.
	 * @param pos
	 * @param name
	 * @param symbol
	 */
	public StaircaseTile(Position pos, Room name, char symbol) {
		super(pos, name, symbol);
	}
}
