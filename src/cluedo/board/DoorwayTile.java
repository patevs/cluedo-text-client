package cluedo.board;

/**
 * A doorway tile that allows the player to cross from the hallway to a room
 * or from a room to the hallway.
 */
public class DoorwayTile extends Tile{
	
	
	/**
	 * Creates a doorway from one room to another.
	 */
	public DoorwayTile(Position pos, char symbol){
		super(pos, symbol);
	}

}
