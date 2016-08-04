package cluedo.tokens;

import java.util.ArrayList;
import java.util.List;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Character;

/**
 * Character tokens on the board that can be moved by players.
 * Represents a player in the game.
 * 
 * @author Patrick Evans and Maria Legaspi
 * 
 */
public class CharacterToken extends GameToken{
	
	private String name;
	private CluedoGame.Character token; // character in game
	private int uid; // unique id of player
	
	private List<Card> hand = new ArrayList<Card>(); // represents the players hand of cards  
	
	// number of remaining steps for a turn
	private int stepsRemaining;
	 
	/**
	 * Creates a character token which holds a set of cards and represents the player on the board.
	 * @param name
	 * @param token
	 * @param uid
	 */
	public CharacterToken(String name, Character token, int uid) {
		this.name = name;
		this.token = token;
		this.uid = uid;
	}

	/**
	 * Returns the name of the character.
	 */
	public String getName(){
		return name.toString();
	}
	
	/**
	 * Returns the character associated with this token.
	 * @return
	 */
	public CluedoGame.Character getToken() {
		return token;
	}
	
	/**
	 * Returns the uid of this character token.
	 * @return
	 */
	public int getUid() {
		return uid;
	}
	
	/**
	 * Returns the player's cards.
	 * @return
	 */
	public List<Card> getHand() {
		return hand;
	}
	
	/**
	 * Adds a card to the player's hand.
	 * @param c
	 */
	public void addCard(Card c){
		hand.add(c);
	}
	
	/**
	 * Sets the amount of steps the player can move.
	 * @param steps
	 */
	public void setRemainingSteps(int steps) {
		this.stepsRemaining = steps;
	}
	
	/**
	 * Returns the amount of steps the player can move.
	 * @return
	 */
	public int getRemainingSteps(){
		return this.stepsRemaining;
	}

	/**
	 * Returns a string representation of this character.
	 */
	public String toString(){
		return name.toString();
	}
}
