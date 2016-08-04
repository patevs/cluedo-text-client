package cluedo.board;

import cluedo.control.CluedoGame;

/**
 * A room tile on the game board, which can hold a maximum of one game token.
 * 
 * @author Patrick Evans and Maria Legaspi
 * 
 */
public class RoomTile extends Tile{
	
	private CluedoGame.Room name;
	
	/**
	 * Creates a new room tile object to store tokens in.
	 * @param name
	 */
	public RoomTile(Position pos, CluedoGame.Room name, char symbol){
		super(pos, symbol);	
		this.name = name;
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
		if(getToken() != null){
			return getToken().getSymbol(); 
		}
		return symbol;
	}
	
}
