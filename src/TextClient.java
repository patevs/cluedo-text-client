
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;


/**
 * TextClient is the main class of the Cluedo Game program.
 *  All input and output to the user is done through this class.
 * 
 */
public class TextClient {

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
	private static ArrayList<Player> inputPlayers(int nplayers) {
		// set up the tokens
		ArrayList<String> tokens = new ArrayList<String>();	
		// adding all characters to the tokens list
		Set<CluedoGame.Characters> characters = (Set) EnumSet.allOf(CluedoGame.Characters.class);
		for(CluedoGame.Characters c : characters){
			tokens.add(c.toString().toLowerCase());
		}
		
		// get player input data
		ArrayList<Player> players = new ArrayList<Player>();	
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
			// create a new player
			players.add(new Player(name, token));
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
		System.out.println("***** Cluedo Game *****");
		System.out.println(" Patrick Evans, 2016 ");
		System.out.println(" ");
		
		// get number of players in game
		int nplayers = inputNumber("Enter number of players (3-6)");
		// throw exception or exit?
		if(nplayers < 3 || nplayers > 6){
			throw new CluedoError("Invalid number of players: " + nplayers);
		}
		ArrayList<Player> players = inputPlayers(nplayers);
			
		// create a new cluedo game
		CluedoGame game = new CluedoGame(players, boardName);
	}

}
