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
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;


/**
 * TextClient is the main class of the Cluedo Game program.
 *  All input and output to the user is done through this class.
 * 
 */
public class TextClient {
	
	private static int uid = 0;

	public TextClient() {
		
	}
	
	/**
	 * Get an integer from System.in
	 */
	private static int inputNumber(String msg) {
		// print message
		System.out.print(msg + " ");
		while (true) {
			// create a reader on user input from System.in
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				// read a line of input
				String s = in.readLine();
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
			players.add(new CharacterToken(name, player, ++uid));
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
		return camelString;
	}
	/**
	 * This is a helper method for converting strings to camel case
	 */
	private static String toProperCase(String s) {
		// simply capitalize the first letter of the string and return  
		return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
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
		int nplayers = inputNumber("Enter number of players (3-6)");
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
		CluedoGame game = new CluedoGame(players, boardName);
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
				// roll the dice
				int roll = die.nextInt(6) + 1;
				player.setRemainingSteps(roll);
				board.toString(); // print the board
				System.out.println("(player " + player.getUid() + ": " + player.getToken() + ") rolls a " + roll);
				while(player.getRemainingSteps() > 0){
					executeChoice(getPlayerChoice(player, board), player, board);
				}
			}
			boo = false;
		}
	}
	
	/**
	 * Executes a given choice made by a player on their turn
	 * @param choice
	 * @param player
	 * @param board
	 */
	private static void executeChoice(String choice, CharacterToken player, Board board){
		switch(choice){
			case "Look at hand.":
				System.out.println("Your hand: " + player.getHand().toString());
				break;
			case "Move south.":
				player.setRemainingSteps(player.getRemainingSteps() - 1);
				board.moveSouth(player);
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
		return options.get(inputNumber("Select option number") - 1);
	}
	
	/**
	 * Returns a list of options avaiable to a player o their turn
	 * @param player
	 * @param board
	 * @return list of options
	 */
	private static List<String> playerOptions(CharacterToken player, Board board){
		List<String> options = new ArrayList<String>();
		options.add("Look at hand.");
		if(board.inRoom(player)){
			if(player.getRemainingSteps() > 0){
				options.add("Exit room.");
			}
		}
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
		options.add("View help");
		options.add("End turn.");
		return options;
	}
}
