package cluedo.tokens;

import java.util.ArrayList;
import java.util.List;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Character;

/**
 * Character tokens on the board that can be moved by players.
 * Represents a player in the game
 */
public class CharacterToken extends GameToken{
	
	private String name;
	private CluedoGame.Character token; // character in game
	private int uid; // unique id of player
	
	private List<Card> hand = new ArrayList<Card>(); // represents the players hand of cards  
	
	// number of remaining steps for a turn
	private int stepsRemaining;
	 
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
	public List<Card> getHand() {
		return hand;
	}
	public void addCard(Card c){
		hand.add(c);
	}
	
	public void setRemainingSteps(int steps) {
		this.stepsRemaining = steps;
	}
	public int getRemainingSteps(){
		return this.stepsRemaining;
	}

	public String toString(){
		return name.toString();
	}
}
