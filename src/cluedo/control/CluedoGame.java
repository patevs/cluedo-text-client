package cluedo.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cluedo.board.Board;
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;
import cluedo.tokens.WeaponToken;

public class CluedoGame {
	
	
	private int numberOfPlayers; // number of players in game
	private List<CharacterToken> activePlayers; // players still active in game
	private static List<WeaponToken> weapons; // all weapons in game
	private static List<Room> rooms; // all rooms in the game
	
	private static List<Card> deck; // represents the deck of all cards
	private static List<Card> unusedCards; // unused cards left after deal
	private static Card[] solution; // random game solution
	
	private static Board gameBoard; // the game board


	public CluedoGame(List<CharacterToken> players, String boardFile) {
		this.numberOfPlayers = players.size();
		this.activePlayers = players;
		CluedoGame.weapons = getWeapons();
		CluedoGame.rooms = getRooms();
		CluedoGame.solution = getSolution();
		CluedoGame.gameBoard = new Board(this, boardFile);
		CluedoGame.deck = getDeck();
		dealCards();
	}

	/**
	 * Get the number of players in the game
	 * @return num of players
	 */
	public int numPlayers(){
		return numberOfPlayers;
	}
	
	/**
	 * Get the current active players in the game
	 * @return
	 */
	public List<CharacterToken> players(){
		return activePlayers;
	}
	public static List<WeaponToken> weapons(){
		return weapons;
	}
	public static List<Room> rooms(){
		return rooms;
	}
	public static List<Card> deck(){
		return deck;
	}
	public static Board board(){
		return gameBoard;
	}
	public List<Card> unusedCards(){
		return unusedCards;
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
	
	/**
	 * returns the game solution
	 * @return game solution
	 */
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
	
	/**
	 * Returns a list of all the rooms in the game
	 * @return a list of all rooms
	 */
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
	 * Returns a list of all the cards in the game deck
	 * @return list of all cards
	 */
	private List<Card> getDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(Arrays.asList(Character.values()));
		deck.addAll(Arrays.asList(Weapon.values()));
		deck.addAll(Arrays.asList(Room.values()));
		deck.removeAll(Arrays.asList(Solution()));
		return deck;
	}
	
	/**
	 * Deals the cards evenly to all player leaving out left over cards
	 */
	private void dealCards() {
		// remove unused cards from deck so remaining cards can be delt evenly
		int numUnused = deck.size() % numPlayers();
		unusedCards = new ArrayList<Card>();
		for(int i=0; i<numUnused; i++){
			unusedCards.add(getCardFromDeck());
		}
		// deal cards evenly to players
		int numCardsToDeal = deck.size() / numPlayers();
		for(CharacterToken player: players()){
			for(int i=0; i<numCardsToDeal; i++){
				player.addCard(getCardFromDeck());
			}
		}
	}
	
	/**
	 * Gets a random card from the remaining deck of cards
	 * @return random card from deck
	 */
	private Card getCardFromDeck() {
		Card result = deck.get(new Random().nextInt(deck.size()));
		deck.remove(result);
		return result;
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
