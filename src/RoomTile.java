/**
 * A room tile on the game board, which can hold maximum of one game token.
 */
public class RoomTile implements Tile{
	private CluedoGame.Rooms name;
	private GameToken item;
	
	public RoomTile(CluedoGame.Rooms name){
		this.name = name;
	}
	
	public boolean add(Object o) throws CluedoError{
		if(!(o instanceof GameToken))
			throw new CluedoError("Can only add game tokens to this tile");
		if(item!=null)
			throw new CluedoError("Tile already contains an item");
		this.item = (GameToken) o;
		return true;
	}
	
	public GameToken remove(){
		GameToken currentItem = item;
		item = null;
		return currentItem;
	}
	
	public CluedoGame.Rooms name(){
		return name;
	}
}
