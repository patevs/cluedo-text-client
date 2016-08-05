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
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;
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
	private static Board board;
	private static boolean endTurn = false; // ends the turn after making an accusation or false suggestion
	private static boolean gameWon = false;
	private static boolean canUseStairs = false; // makes sure player can only use stairs at start of turn
	
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
			while (!tokens.contains(token)) {
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
	 * Gets suspected murder elements from player for an accusation.
	 * @param player
	 * @param board
	 * @return
	 */
	private static Card[] makeAccusation(CharacterToken player){
		Card[] result = new Card[3];
		
		// Gets the suspect
		result[0] = getSuspect().getToken();
		result[1] = (Card)getCrimeScene();
		result[2] = getWeapon().token();

		System.out.println("You accuse " + result[0].toString() + " of committing the crime in the " + result[1].toString() +
				" with the " + result[2].toString());
		System.out.println("\n");
		
		return result;
	}
	
	/**
	 * Checks if the player's accusation is correct. Prints message and returns true if player is correct.
	 * @param results
	 * @param board
	 * @return
	 */
	public static boolean checkAccusation(Card[] results){
		Card[] solution = game.Solution();

		// checks each card by name
		for(int i = 0; i < 3; i++){
			if(!(results[i].toString().equals(solution[i].toString()))){
				System.out.println("+-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+");
				System.out.println("|Y|o|u| |L|o|s|e| |Y|o|u| |L|o|s|e| |Y|o|u| |L|o|s|e| |Y|o|u| |L|o|s|e|");
				System.out.println("+-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+ +-+-+-+ +-+-+-+-+");
				return false;
			}
		}
		System.out.println("+-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+");
		System.out.println("|Y|o|u| |W|i|n| |Y|o|u| |W|i|n| |Y|o|u| |W|i|n| |Y|o|u| |W|i|n| |Y|o|u| |W|i|n|");
		System.out.println("+-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+ +-+-+-+");
		System.out.println("\n");
		System.out.println("The crime was committed by " + solution[0].toString() + 
				" in the " + solution[1].toString() + " with the " + solution[2].toString());
		System.out.println("\n");
		return true;
	}
	
	/**
	 * Gets suspected murder elements from player for a suggestion.
	 */
	private static Card[] makeSuggestion(CharacterToken player){
		// Crime scene
		RoomTile crimeScene = (RoomTile)(board.getTile(player.getXPos(), player.getYPos()));
		System.out.println("Suggested crime scene is: " + crimeScene.name());
		
		Card[] result = new Card[3];
		
		// Gets the suspect
		CharacterToken suspect = getSuspect();
		result[0] = suspect.getToken();
		
		// get the room
		result[1] = (Card)CluedoGame.getRoom(crimeScene.name().toString());
		
		WeaponToken weapon = getWeapon();
		result[2] = (Card)weapon;
		
		board.moveIntoRoom(suspect, crimeScene.name());
		board.moveIntoRoom(weapon, crimeScene.name());
				
		System.out.println("You suggest the crime was committed in the " + result[1] +
				" by " + result[0].toString() + " with the " + result[2].toString());
		return result;
	}
	
	/**
	 * Checks a given suggestion made by a player and returns true if refuted.
	 * @param suggestion
	 * @param board
	 * @return
	 */
	private static boolean checkSuggestion(Card[] suggestion){
		for (CharacterToken p : board.players()) {
			if(p.isPlayer()){
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
		}
		System.out.println("Your suggestion cannot be refuted");
		return false;
	}
	
	/**
	 * Asks player for the room.
	 * @return
	 */
	private static CluedoGame.Room getCrimeScene(){
		// set up the tokens
		ArrayList<CluedoGame.Room> crimeScenes = new ArrayList<CluedoGame.Room>();
		
		// adding all characters to the suspects list
		System.out.print("Rooms: ");
		int count = 1;
		for(CluedoGame.Room r : CluedoGame.rooms()){
			System.out.print(count + ") " + toCamelCase(r.toString()) + " ");
			crimeScenes.add(r);
			count++;
		}
		
		// retry if the player enters an invalid token
		CluedoGame.Room scene = crimeScenes.get(inputNumber("\n Suggest a crime scene: (num)", 1, 
				crimeScenes.size()) - 1);
		
		return scene;
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
	private static CharacterToken getSuspect(){
		// set up the tokens
		ArrayList<CharacterToken> suspects = new ArrayList<CharacterToken>();
		
		// adding all characters to the suspects list
		System.out.print("Suspects: ");
		int count = 1;
		for(CharacterToken c : game.players()){
			System.out.print(count + ") " + toCamelCase(c.getToken().toString()) + " ");
			suspects.add(c);
			count++;
		}
		
		// retry if the player enters an invalid token
		CharacterToken suspect = suspects.get(inputNumber("\n Suggest a murderer: (num)", 1, suspects.size()) - 1);
		return suspect;
	}
	
	/**
	 * Executes a given choice made by a player on their turn
	 * @param choice
	 * @param player
	 * @param board
	 */
	private static void executeChoice(String choice, CharacterToken player){
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
			case "Use stairs.":
				board.useStairs(player);
				player.setRemainingSteps(0);
				break;
			case "Make suggestion.":
				player.suggested(true);
				if(checkSuggestion(makeSuggestion(player))) // if refuted, player's turn ends
					endTurn = true;
				break;
			case "Make accusation.":
				if(!checkAccusation(makeAccusation(player))){
					player.isPlayer(false);
					// displays game end message if all players have lost
					if(!game.activePlayers()){
						viewGameEnd(player);
					}
				}else{
					// displays game end message if player accused correctly
					gameWon = true;
					viewGameEnd(player);
				}
				endTurn = true;
				break;
			case "View help":
				viewHelp();
				break;
			case "End turn.":
				player.setRemainingSteps(0);
				endTurn = true;
				break;
			default:
				throw new CluedoError("Error: Choice not recognised");
		}
		canUseStairs = false;
	}

	/**
	 * Gets a players option choice on their turn
	 * @param player
	 * @param board
	 */
	private static String getPlayerChoice(CharacterToken player) {
		System.out.println("\n (player " + player.getUid() + ": " + player.getToken() + ") you have " 
				+ player.getRemainingSteps() + " step(s) remaining");
		System.out.println("Please make a choice: ");
		// get player options
		List<String> options = playerOptions(player);
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
	private static List<String> playerOptions(CharacterToken player){
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
			if(board.inCornerRoom(player) && canUseStairs)
				options.add("Use stairs.");
			if(!player.hasSuggested()){
				options.add("Make suggestion.");
			}
		}
		options.add("Make accusation.");
		options.add("Look at hand.");
		options.add("View help");
		options.add("End turn.");
		return options;
	}
	
	/**
	 * Shows game end message.
	 * @param player
	 */
	private static void viewGameEnd(CharacterToken player){
			  System.out.println("______  _______ _______ _______       _____  _        _ _______  _____");
			  System.out.println("|       |     | |  |  | |            |     |  \\      /  |       |     \\");
			  System.out.println("|  ____ |_____| |  |  | |______      |     |   \\    /   |______ |_____/");
			  System.out.println("|     | |     | |  |  | |            |     |    \\  /    |       |    \\");
			  System.out.println("|_____| |     | |  |  | |______      |_____|     \\/     |______ |     \\_");      
		if(gameWon){   
			System.out.println("\t\t\t" + player.getName() + " solved the crime!");
		}
		else{
			System.out.println("\t\t\tThe crime goes unsolved");
		}
		gameWon = true;
	}
	
	/**
	 * Prints out a helper guide for the player
	 */
	public static void viewHelp(){
		System.out.println("----------------- CLUEDO GUIDE -------------------");
		System.out.println("Players' tokens are represented by unqiue ID numbers");
		System.out.println();
		System.out.println("'x' represents a wall tile");
		System.out.println();
		System.out.println("CAPITAL LETTERS represent a room, where: ");
		System.out.println("- 'K' represents (K)itchen");
		System.out.println("- 'B' represents (B)all Room");
		System.out.println("- 'C' represents (C)onservatory");
		System.out.println("- 'I' represents B(I)lliard Room");
		System.out.println("- 'L' represents (L)ibrary");
		System.out.println("- 'S' represents (S)tudy");
		System.out.println("- 'H' represents (H)all");
		System.out.println("- 'O' represents L(O)unge");
		System.out.println("- 'N' represents Di(N)ing Room");
		System.out.println();
		System.out.println("'n', 's', 'w' and 'e' represent a doorway to a room, where: ");
		System.out.println("- 'n' means only a \"Move North\" can enter the room");
		System.out.println("- 's' means only a \"Move South\" can enter the room");
		System.out.println("- 'w' means only a \"Move West\" can enter the room");
		System.out.println("- 'e' means only a \"Move East\" can enter the room");
		System.out.println("Weapons are represented by symbols, where: ");
		System.out.println("- '+' represents Candlestick");
		System.out.println("- '-' represents Dagger");
		System.out.println("- '/' represents Lead Pipe");
		System.out.println("- '*' represents Revolver");
		System.out.println("- '=' represents Rope");
		System.out.println("- '?' represents Spanner");
		System.out.println("------------------------------------------------------");
		System.out.println();
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

		// check the file exists
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
			
		// create a new cluedo game
		game = new CluedoGame(nplayers, players, boardName);
		board = CluedoGame.board();
		
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
		// loop until game ends
		while(!gameWon){
			for(CharacterToken player: game.players()){
				if(!player.isPlayer()) // only give turns to players
					continue;
				// roll the dice
				int roll = die.nextInt(6) + 1;
				player.setRemainingSteps(roll);
				board.toString(); // print the board
				System.out.println("\n");
				// print players roll
				System.out.print("(player " + player.getUid() + ": " + player.getToken() + ") rolls a " + roll);
				player.suggested(false); // resets players suggestion field
				while(player.getRemainingSteps() >= 0 && !endTurn){
					executeChoice(getPlayerChoice(player), player);
				}
				endTurn = false; // reset for next player
				canUseStairs = true;
				System.out.println("\n");
				System.out.println("\n");
				System.out.println("\n");
				System.out.println("\n");
			}
		}
	}

	//============================//
	// Helper methods for testing //
	//============================//
	
	/**
	 * Associates this text client with a game.
	 * @param game
	 */
	public void setGame(CluedoGame newGame) {
		game = newGame;
	}
	
	/**
	 * Associates this text client with a board.
	 * @param game
	 */
	public void setBoard(Board newBoard) {
		board = newBoard;
	}

	/**
	 * Returns the game associated with this text client.
	 * @param game
	 */
	public CluedoGame game() {
		return TextClient.game;
	}
	
	/**
	 * Returns the board associated with this text client.
	 * @param game
	 */
	public Board board(){
		return TextClient.board;
	}
}
