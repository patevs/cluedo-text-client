package cluedo.board;

import cluedo.control.CluedoError;
import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Room;

public class StaircaseTile extends RoomTile{

	public StaircaseTile(Position pos, Room name, char symbol) {
		super(pos, name, symbol);
	}
	
	public Room oppositeRoom(){
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
}
