/**
 * A tile of a corner room on the game board.
 */
public class CornerRoomTile extends RoomTile{
	private CluedoGame.Rooms oppositeRoom;
	
	public CornerRoomTile(CluedoGame.Rooms name, CluedoGame.Rooms oppositeRoomName){
		super(name);
		this.oppositeRoom = oppositeRoomName;
	}
	
	public CluedoGame.Rooms oppositeRoom(){
		return oppositeRoom;
	}
}
