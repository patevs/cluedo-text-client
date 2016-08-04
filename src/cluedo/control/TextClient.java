package cluedo.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.board.Board;
import cluedo.board.RoomTile;
import cluedo.board.Tile;
import cluedo.control.CluedoGame.Room;
import cluedo.control.CluedoGame.Weapon;
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;
import cluedo.tokens.GameToken;
import cluedo.tokens.WeaponToken;


/**
 * TextClient is the main class of the Cluedo Game program.
 *  All input and output to the user is done through this class.
 * 
 * @author Patrick Evans and Maria Legaspi
 */
public class TextClient {
	
	private static int uid = 0;
	private static CluedoGame game;
	
	public TextClient() {
		
	}
	
	/**
	 * Get an integer from System.in
	 * @param max 
	 * @param min 
	 */
	private static int inputNumber(String msg, int min, int max) {
		// print message
		System.out.print(msg + " ");
		while (true) {
			// create a reader on user input from System.in
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				// read a line of input
				String s = in.readLine();
				
				int i = Integer.parseInt(s);
				if(i > max || i < min) return inputNumber(msg, min, max);
				// return as an integer
				return Integer.parseInt(s);
			} catch (IOException e) {
				System.out.println("Please enter a number!");
			}
		}
	}
	
	/**
	 * Get an string from System.in
	 */
	private static String inputString(String msg) {
		// print the message
		System.out.print(msg + " ");
		while (true) {
			// create a reader on user input from System.in
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				// return the read string
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}
	
	/** 
	 * Get input data for player information before starting the game.
	 * 
	 * @param nplayers
	 * @return
	 */
	private static ArrayList<CharacterToken> inputPlayers(int nplayers) {
		// set up the tokens
		ArrayList<String> tokens = new ArrayList<String>();	
		// adding all characters to the tokens list
		Set<CluedoGame.Character> characters = (Set<CluedoGame.Character>) EnumSet.allOf(CluedoGame.Character.class);
		for(CluedoGame.Character c : characters){
			tokens.add(c.toString().toLowerCase());
		}
		
		// get player input data
		ArrayList<CharacterToken> players = new ArrayList<CharacterToken>();	
		for (int i = 1; i <= nplayers; i++) {
			// get player name
			String name = inputString("Player #" + i + " name? ");
			// list remaining tokens
			listTokens(tokens, "remaining tokens: ");
			// get player token
			String token = inputString("Player #" + i + " token?").toLowerCase();
			System.out.println();
			// retry if the player enters an invalid token
			if (!tokens.contains(token)) {
				listTokens(tokens, "Invalid token! Must be one of: ");
				token = inputString("Player #" + i + " token?").toLowerCase();
			}
			// remove the selected token
			tokens.remove(token);
			
			// get the character matching the chosen token
			CluedoGame.Character player = null;
			for(CluedoGame.Character c : characters){
				if(c.toString().toLowerCase().equals(token)){
					player = c;
				}
			}
			if(player == null) throw new CluedoError("Character cannot be null");
			// create a new player character
			players.add(new CharacterToken(name, player, true, ++uid));
		}
		// spare characterTokens
		for(String playerName: tokens){
			for(CluedoGame.Character c : characters){
				if(c.toString().toLowerCase().equals(playerName)){
					players.add(new CharacterToken("computer", c, false, ++uid));
				}
			}
		}
		// return the list of players
		return players;
	}
	
	/**
	 *  This method simply prints all of the tokens in a list.
	 *   A message can also be displayed before the list of tokens.
	 * @param tokens
	 * @param msg
	 */
	private static void listTokens(ArrayList<String> tokens, String msg){
		boolean firstTime = true;
		// print the message
		System.out.print(msg + " ");
		// loop over each token and print
		for (String t : tokens) {
			if (!firstTime) {
				System.out.print(", ");
			}
			firstTime = false;
			// print the token in camel case
			System.out.print("\"" + toCamelCase(t) + "\"");
		}
		System.out.println();
	}
	
	/** This method is used to convert strings into camel case
	 * 		i.e. "camel case" turns to "Camel Case" 
	 * 
	 * @param s
	 * @return
	 */
	private static String toCamelCase(String s){
		// Split the string into parts
		String[] parts = s.split(" ");
		String camelString = "";
		// build the camelString from each part
		for(String part: parts){
			camelString += toProperCase(part) + " ";
		}
		// return the string
		return camelString.trim();
	}
	/**
	 * This is a helper method for converting strings to camel case
	 */
	private static String toProperCase(String s) {
		// simply capitalize the first letter of the string and return  
		return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}

	/**
	 * Gets suspected murder elements from player.
	 */
	private static Card[] makeSuggestion(CharacterToken player, Board board){
		// Crime scene
		RoomTile crimeScene = (RoomTile)(board.getTile(player.getXPos(), player.getYPos()));
		System.out.println("Suggested crime scene is: " + crimeScene.name());
		
		Card[] result = new Card[3];
		
		// Gets the suspect
		result[0] = (Card)getSuspect();
		result[1] = (Card)getWeapon();
		
		String roomName = crimeScene.name().toString();
		for(Room r : CluedoGame.rooms()){
			if(r.name().equals(roomName)){
				result[2] = (Card)r;
			}
		}
		
		//board.moveIntoRoom(player, (Room)result[2]);
		//board.moveIntoRoom((GameToken) result[1], (Room)result[2]);
		
		return result;
	}
	
	private static boolean checkSuggestion(Card[] suggestion, Board board){
		for (CharacterToken p : board.players()) {
			for(Card c : p.getHand()){
				for(int i=0; i<suggestion.length; i++){
					if(c.equals(suggestion[i])){
						System.out.println(p.getName() + " has the card "
								+ c.toString());
						return true;
					}
				}
			}
		}
		System.out.println("Your suggestion cannot be refutted");
		return false;
	}
	
	/**
	 * Asks player for the suspected murder weapon.
	 * @return
	 */
	private static WeaponToken getWeapon(){
		// set up the tokens
		ArrayList<WeaponToken> weapons = new ArrayList<WeaponToken>();	
		// adding all characters to the tokens list
		System.out.print("Weapons: ");
		int count = 1;
		for(WeaponToken w : CluedoGame.weapons()){
			System.out.print(count + ") " + toCamelCase(w.toString()) + " ");
			weapons.add(w);
			count++;
		}
		WeaponToken suspect = weapons.get(inputNumber("\n Suggest a weapon: (num)", 1, weapons.size()) - 1);
		return suspect;
	}
	
	/**
	 * Asks player for the suspect.
	 * @return
	 */
	private static CluedoGame.Character getSuspect(){
		// set up the tokens
		ArrayList<CluedoGame.Character> suspects = new ArrayList<CluedoGame.Character>();
		
		// adding all characters to the suspects list
		System.out.print("Suspects: ");
		int count = 1;
		for(CluedoGame.Character c : CluedoGame.characters()){
			System.out.print(count + ") " + toCamelCase(c.toString()) + " ");
			suspects.add(c);
			count++;
		}
		
		// retry if the player enters an invalid token
		CluedoGame.Character suspect = suspects.get(inputNumber("\n Suggest a murderer: (num)", 1, suspects.size()) - 1);
		
		return suspect;
	}
	
	/**
	 * Moves a token to the same room as a given player.
	 * @param player
	 * @param token
	 * @param crimeScene
	 * @param board
	 */
	private static void moveCrimeTokens(CharacterToken player, GameToken token, RoomTile crimeScene, Board board){
		Tile current = board.getTile(token.getXPos(), token.getYPos());
		if(current != crimeScene){
			Tile destination = nextTile(board, player.getXPos(), player.getYPos());
			if(destination==null){
				throw new CluedoError("Could not find space in the room for token");
			}
			board.move(destination.getPos(), token);
		}
	}
	
	/**
	 * Finds a tile near the given coordinates.
	 * @param board
	 * @param x
	 * @param y
	 * @return
	 */
	private static Tile nextTile(Board board, int x, int y){
		Tile destination = board.getTile(x, y);
		for(int i = -2; i < 3; i++){
			for(int j = -2; j < 3; j++){
				if(x+i >= 0 && x+i < 25 && y+j>=0 && y+j < 25){
					Tile tile = board.getTile(x, y);
					if(tile.getToken()==null && tile.getClass() == destination.getClass())
						return tile;
				}
			}
		}
		return null;
	}
	/**
	 * Executes a given choice made by a player on their turn
	 * @param choice
	 * @param player
	 * @param board
	 */
	private static void executeChoice(String choice, CharacterToken player, Board board){
		switch(choice){
			case "Move North.":
				player.setRemainingSteps(player.getRemainingSteps() - 1);
				board.moveNorth(player);
				board.toString();
				break;
			case "Move East.":
				player.setRemainingSteps(player.getRemainingSteps() - 1);
				board.moveEast(player);
				board.toString();
				break;
			case "Move South.":
				player.setRemainingSteps(player.getRemainingSteps() - 1);
				board.moveSouth(player);
				board.toString();
				break;
			case "Move West.":
				player.setRemainingSteps(player.getRemainingSteps() - 1);
				board.moveWest(player);
				board.toString();
				break;
			case "Look at hand.":
				System.out.println("Your hand: " + player.getHand().toString());
				break;
			case "Make suggestion.":
				checkSuggestion(makeSuggestion(player, board), board);
				break;
			case "Make accusation.":
				break;
			case "View help":
				break;
			case "End turn.":
				player.setRemainingSteps(0);
				break;
			default:
				throw new CluedoError("Error: Choice not recognised");
		}
	}

	/**
	 * Gets a players option choice on their turn
	 * @param player
	 * @param board
	 */
	private static String getPlayerChoice(CharacterToken player, Board board) {
		System.out.println("\n (player " + player.getUid() + ": " + player.getToken() + ") you have " 
				+ player.getRemainingSteps() + " step(s) remaining");
		System.out.println("Please make a choice: ");
		// get player options
		List<String> options = playerOptions(player, board);
		for(int i=0; i<options.size(); i++){
			System.out.println((i+1) + ") " + options.get(i));
		}
		// return player choice
		return options.get(inputNumber("Select option number", 1, options.size()) - 1);
	}
	
	/**
	 * Returns a list of options available to a player o their turn
	 * @param player
	 * @param board
	 * @return list of options
	 */
	private static List<String> playerOptions(CharacterToken player, Board board){
		List<String> options = new ArrayList<String>();
		if(player.getRemainingSteps() > 0){
			if(board.canMoveNorth(player)){
				options.add("Move North.");
			}
			if(board.canMoveEast(player)){
				options.add("Move East.");
			}
			if(board.canMoveSouth(player)){
				options.add("Move South.");
			}
			if(board.canMoveWest(player)){
				options.add("Move West.");
			}
		}
		if(board.inRoom(player)){
			options.add("Make suggestion.");
		}
		options.add("Make accusation.");
		options.add("Look at hand.");
		options.add("View help");
		options.add("End turn.");
		return options;
	}

	public static void main(String[] args){
		// check number of arguments
		if (args.length != 1) {
			System.out.println("command: java TextClient gameBoard.txt");
			System.exit(1);
		}

		// get program argument (board file)
		String boardName = args[0];
		File file = new File("./" + boardName);

		// check the file exsitance
		if (!file.exists()) {
			System.out.println(boardName + " does not exist in current directory.");
			System.exit(2);
		}

		// check file type
		if (!boardName.toLowerCase().endsWith(".txt")) {
			System.out.println("Only .txt file is accepted.");
			System.exit(3);
		}	                                   
		                                   
		// Print banner
		System.out.println(" \t_______ ___     __   __ _______ ______  _______   _______ _______ ____ ___ ");
		System.out.println("\t|     __|   |   |  | |  |       |      ||       | |       |  _    |    |   |");
		System.out.println("\t|    |  |   |   |  | |  |    ___|  _    |   _   | |____   | | |   ||   |   |_ _"); 
		System.out.println("\t|    |  |   |   |  |_|  |   |___| | |   |  | |  |  ____|  | | |   ||   |    _  |");
		System.out.println("\t|    |  |   |___|       |    ___| |_|   |  |_|  | | ______| |_|   ||   |   | | |");
		System.out.println("\t|    |__|       |       |   |___|       |       | | |_____|       ||   |   |_| |");
		System.out.println("\t|_______|_______|_______|_______|______||_______| |_______|_______||___|_______|");
		System.out.println("\t          +-+-+-+-+-+-+-+ +-+-+-+-+-+ +-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+");
		System.out.println("\t          |P|a|t|r|i|c|k| |E|v|a|n|s| |&| |M|a|r|i|a| |L|e|g|a|s|p|i|");
		System.out.println("\t          +-+-+-+-+-+-+-+ +-+-+-+-+-+ +-+ +-+-+-+-+-+ +-+-+-+-+-+-+-+");
		System.out.println(" ");
		
		// get number of players in game
		int nplayers = inputNumber("Enter number of players (3-6)", 3, 6);
		// throw exception or exit?
		if(nplayers < 3 || nplayers > 6){
			throw new CluedoError("Invalid number of players: " + nplayers);
		}
		// get player information
		ArrayList<CharacterToken> players = inputPlayers(nplayers);
		// print player information
		System.out.println("Player infomation: ");
		for(CharacterToken c: players){
			System.out.println("player " + c.getUid() + ": " + c.getToken() + " played by " + c.getName());
		}
		
		// TODO:add extra tokens to board
			
		// create a new cluedo game
		game = new CluedoGame(players, boardName);
		Board board = game.board();
		
		System.out.println("\nCards have been dealt, the game begins!");
		// print out any unused cards
		if(!game.unusedCards().isEmpty()){
			System.out.print(" Extra cards: ");
			boolean firstTime = true;
			for(Card c : game.unusedCards()){
				if (!firstTime) {
					System.out.print(", ");
				}
				firstTime = false;
				System.out.print(toCamelCase(c.toString()));
			}
		}
		System.out.println(" ");
		
		// now the game begins
		Random die = new Random();
		boolean boo = true;
		// loop until game ends
		while(boo){
			for(CharacterToken player: game.players()){
				if(!player.isPlayer()) // only give turns to players
					continue;
				// roll the dice
				int roll = die.nextInt(6) + 1;
				player.setRemainingSteps(roll);
				board.toString(); // print the board
				System.out.print("(player " + player.getUid() + ": " + player.getToken() + ") rolls a " + roll);
				while(player.getRemainingSteps() > 0){
					executeChoice(getPlayerChoice(player, board), player, board);
				}
			}
			//boo = false;
		}
	}
}
