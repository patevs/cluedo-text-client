package cluedo.board;

import java.awt.Point;

import cluedo.control.CluedoError;
import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Room;

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
	
	/**
	 * Returns whether this room is a corner room.
	 * @return
	 */
	public boolean isCornerRoom(){
		if(name.equals(CluedoGame.Room.KITCHEN)||name.equals(CluedoGame.Room.CONSERVATORY)||
				name.equals(CluedoGame.Room.LOUNGE)||name.equals(CluedoGame.Room.STUDY))
			return true;
		return false;
	}
	
	public CluedoGame.Room oppositeRoomPos(){
		if(!isCornerRoom())
			return null;
		switch(this.name().toString()){
		case "Kitchen":
			return CluedoGame.Room.STUDY;
		case "Study":
			return CluedoGame.Room.KITCHEN;
		case "Conservatory":
			return CluedoGame.Room.LOUNGE;
		case "Lounge":
			return CluedoGame.Room.CONSERVATORY;
		default:
			throw new CluedoError("Could not find opposite corner room.");
		}
	}
	
	@Override
	public char getSymbol(){
		if(getToken() != null){
			return getToken().getSymbol(); 
		}
		return symbol;
	}
	
}
