/**
 * A tile of a corner room on the game board where players can use the stairs
 * to move to the room on the opposite corner of the board.
 */
public class CornerRoomTile extends RoomTile{
	private CluedoGame.Rooms oppositeRoom; // The destination of using the stairs
	
	/**
	 * Creates a new corner room tile object.
	 * @param name
	 * @param oppositeRoomName
	 */
	public CornerRoomTile(Position pos, CluedoGame.Rooms name, CluedoGame.Rooms oppositeRoomName){
		super(pos, name);
		this.oppositeRoom = oppositeRoomName;
	}
	
	/**
	 * Returns the name of the opposite corner room.
	 * @return
	 */
	public CluedoGame.Rooms oppositeRoom(){
		return oppositeRoom;
	}
}
