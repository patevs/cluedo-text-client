package cluedo.control;

import java.util.ArrayList;
import java.util.List;

import cluedo.board.Board;
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;
import cluedo.tokens.WeaponToken;

public class CluedoGame {
	
	
	private int numberOfPlayers; // number of players in game
	private List<CharacterToken> activePlayers; // players still active in game
	private List<WeaponToken> weapons; // all weapons in game
	private List<Room> rooms; // all rooms in the game
	private Card[] solution; // random game solution
	private Board gameBoard; // the game board


	public CluedoGame(List<CharacterToken> players, String boardFile) {
		this.numberOfPlayers = players.size();
		this.activePlayers = players;
		this.weapons = getWeapons();
		this.rooms = getRooms();
		this.solution = getSolution();
		this.gameBoard = new Board(this, boardFile);
	}

	/**
	 * Get the number of players in the game
	 * @return num of players
	 */
	public int getNumPlayers(){
		return numberOfPlayers;
	}
	
	/**
	 * Get the current active players in the game
	 * @return
	 */
	public List<CharacterToken> getPlayers(){
		return activePlayers;
	}
	
	/**
	 * This method gets a random solution to the cluedo game.
	 *  The solution contains one Character card, one Room card, 
	 *  and one weapon card.
	 * @return solution
	 */
	private static Card[] getSolution() {
		Card[] solution = new Card[3];
		solution[0] = Character.getRandom();
		solution[1] = Room.getRandom();
		solution[2] = Weapon.getRandom();
		return solution;
	}
	
	public Card[] Solution(){
		return solution;
	}

	/** 
	 * This method returns a list of all the weapons in
	 *  the game
	 * @return a list of all weapons
	 */
	private static List<WeaponToken> getWeapons(){
		List<WeaponToken> weapons = new ArrayList<WeaponToken>();
		// iterate over each weapon and add each one to the list
		for(Weapon w: Weapon.values()){
			WeaponToken weapon = new WeaponToken(w);
			weapons.add(weapon);
		}
		// return the list of all weapons
		return weapons;
	}
	
	private static List<Room> getRooms() {
		List<Room> rooms = new ArrayList<Room>();
		// iterate over each room and add each one to the list
		for(Room r: Room.values()){
			rooms.add(r);
		}
		// return the list of all rooms
		return rooms;
	}
	
	/**
	 * Represents the six characters in the game
	 */
	public enum Character implements Card {
		MISS_SCARLETT,
		COLONEL_MUSTARD,
		MRS_WHITE,
		THE_REVEREND_GREEN,
		MRS_PEACOCK,
		PROFESSOR_PLUM;

		public static Card getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}

		@Override
		public String toString() {
			return this.name().replaceAll("_", " ");
		}
	}

	/**
	 * Represents the six weapons in the game
	 */
	public enum Weapon implements Card {
		CANDLESTICK,
		DAGGER,
		LEAD_PIPE,
		REVOLVER,
		ROPE,
		SPANNER;

		public static Card getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}

		@Override
		public String toString() {
			return this.name().replaceAll("_", " ");
		}
	}

	/**
	 * Represents the nine rooms in the game
	 */
	public enum Room implements Card {
		KITCHEN,
		BALL_ROOM,
		CONSERVATORY,
		BILLIARD_ROOM,
		LIBRARY,
		STUDY,
		HALL,
		LOUNGE,
		DINING_ROOM;

		public static Card getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}

		@Override
		public String toString() {
			return this.name().replaceAll("_", " ");
		}
	}
}
