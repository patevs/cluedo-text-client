package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.control.CluedoGame;
import cluedo.tokens.GameToken;

/**
 * A room tile on the game board, which can hold a maximum of one game token.
 */
public class RoomTile extends Tile{
	
	private CluedoGame.Room name;
	private GameToken token;
	
	/**
	 * Creates a new room tile object to store tokens in.
	 * @param name
	 */
	public RoomTile(Position pos, CluedoGame.Room name, char symbol){
		super(pos, symbol);	
		this.name = name;
	}
	
	public boolean add(Object o) throws CluedoError{
		if(!(o instanceof GameToken))
			throw new CluedoError("Can only add game tokens to this tile");
		if(token!=null)
			throw new CluedoError("Tile already contains an item");
		this.token = (GameToken) o;
		return true;
	}
	
	public GameToken remove(){
		GameToken currentItem = token;
		token = null;
		return currentItem;
	}
	
	/**
	 * Returns the item stored in this tile
	 * @return
	 */
	public GameToken getToken(){
		if(token != null){
			return token;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the name of this room.
	 * @return
	 */
	public CluedoGame.Room name(){
		return name;
	}
	
	@Override
	public char getSymbol(){
		return symbol;
	}
	
}
