/**
 * A doorway tile that allows the player to cross from the hallway to a room
 * or from a room to the hallway.
 */
public class DoorwayTile extends RoomTile{
	private DoorwayTile nextRoom; // the tile on the other side of the door
	
	/**
	 * Creates a doorway from one room to another.
	 * @param name
	 * @param nextRoom
	 */
	public DoorwayTile(Position pos, CluedoGame.Rooms name, DoorwayTile nextRoom){
		super(pos, name);
		this.nextRoom = nextRoom;
	}

	/**
	 * Creates a doorway in a room.
	 * @param name
	 */
	public DoorwayTile(Position pos, CluedoGame.Rooms name){
		super(pos, name);
	}

	/**
	 * Sets the connecting doorway tile.
	 * @param nextRoom
	 * @return
	 */
	public boolean setNext(DoorwayTile nextRoom){
		if(nextRoom == null){
			this.nextRoom = nextRoom;
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if player can move into the next room.
	 * @return
	 */
	public boolean canEnterRoom(){
		// if the doorway is blocked then player cannot move into the next room.
		if(nextRoom.getItem()==null)
			return true;
		return false;
	}
	
	/**
	 * Returns the doorway tile
	 * @return
	 */
	public DoorwayTile nextRoom(){
		return nextRoom;
	}
}
