
import java.util.ArrayList;
import java.util.List;

public class CluedoGame {
	
	
	private int numberOfPlayers;
	private List<Player> activePlayers;
	private List<Weapon> weapons;
	private Board gameBoard;
	private List<Card> remainingCards;
	private Card[] solution;

	public CluedoGame(List<Player> players, String boardFile) {
		this.numberOfPlayers = players.size();
		this.activePlayers = players;
		this.weapons = getWeapons();
		this.solution = getSolution();

	}
	
	/**
	 * This method gets a random solution to the cluedo game.
	 *  The solution contains one Character card, one Room card, 
	 *  and one weapon card.
	 * @return solution
	 */
	private Card[] getSolution() {
		Card[] solution = new Card[3];
		solution[0] = Characters.getRandom();
		solution[1] = Rooms.getRandom();
		solution[2] = Weapons.getRandom();
		return solution;
	}

	/** 
	 * This method returns a list of all the weapons in
	 *  the game
	 * @return a list of all weapons
	 */
	private static List<Weapon> getWeapons(){
		List<Weapon> weapons = new ArrayList<Weapon>();
		// iterate over each weapon and add each one to the list
		for(Weapons w: Weapons.values()){
			Weapon weapon = new Weapon(w.toString());
			weapons.add(weapon);
		}
		// return the list of all weapons
		return weapons;
	}
	
	/**
	 * Represents the six characters in the game
	 */
	public enum Characters implements Card {
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
	public enum Weapons implements Card {
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
	public enum Rooms implements Card {
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
