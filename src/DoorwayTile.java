
public class DoorwayTile extends RoomTile{
	private CluedoGame.Rooms nextRoom;
	
	public DoorwayTile(CluedoGame.Rooms name, CluedoGame.Rooms nextRoom){
		super(name);
		this.nextRoom = nextRoom;
	}

	public CluedoGame.Rooms nextRoom(){
		return nextRoom;
	}
}
