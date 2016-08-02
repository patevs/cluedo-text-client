package cluedo.tokens;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Character;

/**
 * Character tokens on the board that can be moved by players.
 */
public class CharacterToken implements GameToken{
	
	private String name;
	private CluedoGame.Character token; // character in game
	private int uid; // unique id of player
	
	// position of player on the board
	private int XPos;
	private int YPos;
	 
	public CharacterToken(String name, Character token, int uid) {
		this.name = name;
		this.token = token;
		this.uid = uid;
	}

	public String getName(){
		return name.toString();
	}
	public CluedoGame.Character getToken() {
		return token;
	}
	public int getUid() {
		return uid;
	}
	
	public String toString(){
		return name.toString();
	}


	public int getXPos() {
		return XPos;
	}
	public void setXPos(int xPos) {
		XPos = xPos;
	}
	public int getYPos() {
		return YPos;
	}
	public void setYPos(int yPos) {
		YPos = yPos;
	}


}
