package cluedo.board;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cluedo.control.CluedoError;
import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Room;
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;
import cluedo.tokens.GameToken;
import cluedo.tokens.WeaponToken;

/**
 * Holds the tokens and logic for moving the tokens around the board.
 * 
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class Board {

	private int height;
	private int width;
	
	private int numPlayers;
	private List<CharacterToken> activePlayers;
	private Card[] solution;
	
	private Tile[][] board; // the board is a 2D array of tiles
	
	public Board(CluedoGame game, String boardFile) {
		this.numPlayers = game.numPlayers();
		this.activePlayers = game.players();
		this.solution = game.Solution();
		this.board = new Tile[25][25];
		
		// reading the board file
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(boardFile));
			for(height=0; scanner.hasNextLine(); height++){
				char[] line = scanner.nextLine().toCharArray();
				for(width=0; width < line.length; width++){
						
					// read the text character
					char c = line[width];
					Position pos = new Position(height, width, c);
					
					// digits are starting positions
					if(Character.isDigit(c)){
						// starting tile
						HallwayTile startTile = new HallwayTile(pos, ' ');
						for(CharacterToken player : activePlayers){
							if(player.getUid() == Character.getNumericValue(c)){
								// set player start location
								startTile.setToken(player);
								player.setXPos(width);
								player.setYPos(height);
							}
						}
						board[height][width] = startTile;
					} else {
						board[height][width] = getTile(c, pos);
					}
				}
			}
		} catch(IOException e) {
			System.out.println("Error processing board file");
		}
	}
	
	/**
	 * Returns true if a given token is in any room
	 * @param token
	 * @return
	 */
	public boolean inRoom(GameToken token){
		int Xpos = token.getXPos();
		int Ypos = token.getYPos();
		Tile t = board[Ypos][Xpos];
		return(t instanceof RoomTile);
	}
	
	/**
	 * Returns true if a given token is in a corner room
	 * @param token
	 * @return
	 */
	public boolean inCornerRoom(GameToken token){
		int Xpos = token.getXPos();
		int Ypos = token.getYPos();
		Tile t = board[Ypos][Xpos];
		if(t instanceof RoomTile){
			RoomTile r = (RoomTile)t;
			if(r.isCornerRoom())
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true if a given token is in any doorway
	 * @param token
	 * @return
	 */
	public boolean inDoorway(GameToken token){
		int Xpos = token.getXPos();
		int Ypos = token.getYPos();
		Tile t = board[Ypos][Xpos];
		return(t instanceof DoorwayTile);
	}

	/**
	 * Returns true if a given player can move north on the board
	 * @param token
	 * @return
	 */
	public boolean canMoveNorth(CharacterToken token){	
		// already in north most square
		if(token.getYPos() - 1 < 0){
			return false;
		}
		
		// cannot move if north is a room, invalid tile, or entrance which is not north
		int xpos = token.getXPos();
		int ypos = token.getYPos();
		Tile t = board[ypos-1][xpos];
		char symbol = t.getSymbol();
		if(t.getToken()!=null){ 
			return false;
		}
		if(inDoorway(token) || inRoom(token)){
			return Character.isUpperCase(symbol) || symbol == 'n' 
					|| symbol == 'e' || symbol == 's' || symbol == 'w';
		}
		if(Character.isUpperCase(symbol) || symbol == 'x' 
				|| symbol == 'e' || symbol == 's' || symbol == 'w'){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if a given player can move east on the board
	 * @param token
	 * @return
	 */
	public boolean canMoveEast(CharacterToken token){	
		// already in east most square
		if(token.getXPos() + 1 >= width){
			return false;
		}
		
		// cannot move if east is a room, invalid tile, or entrance which is not north
		int xpos = token.getXPos();
		int ypos = token.getYPos();
		Tile t = board[ypos][xpos + 1];
		char symbol = t.getSymbol();
		if(t.getToken()!=null){ 
			return false;
		}
		if(inDoorway(token) || inRoom(token)){
			return Character.isUpperCase(symbol) || symbol == 'n' 
					|| symbol == 'e' || symbol == 's' || symbol == 'w';
		}
		if(Character.isUpperCase(symbol) || symbol == 'x' 
				|| symbol == 'n' || symbol == 's' || symbol == 'w'){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if a given player can move south on the board
	 * @param token
	 * @return
	 */
	public boolean canMoveSouth(CharacterToken token){	
		// already in south most square
		if(token.getYPos() + 1 >= height){
			return false;
		}
		
		// cannot move if south is a room, invalid tile, or entrance which is not south
		int xpos = token.getXPos();
		int ypos = token.getYPos();
		Tile t = board[ypos+1][xpos];
		char symbol = t.getSymbol();
		if(t.getToken()!=null){ 
			return false;
		}
		if(inDoorway(token) || inRoom(token)){
			return Character.isUpperCase(symbol) || symbol == 'n' 
					|| symbol == 'e' || symbol == 's' || symbol == 'w';
		}
		if(Character.isUpperCase(symbol) || symbol == 'x' 
				|| symbol == 'n' || symbol == 'e' || symbol == 'w'){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if a given player can move west on the board
	 * @param token
	 * @return
	 */
	public boolean canMoveWest(CharacterToken token){	
		// already in west most square
		if(token.getXPos() - 1 < 0){
			return false;
		}
		
		// cannot move if west is a room, invalid tile, or entrance which is not north
		int xpos = token.getXPos();
		int ypos = token.getYPos();
		Tile t = board[ypos][xpos - 1];
		char symbol = t.getSymbol();
		if(t.getToken()!=null){ 
			return false;
		}
		if(inDoorway(token) || inRoom(token)){
			return Character.isUpperCase(symbol) || symbol == 'n' 
					|| symbol == 'e' || symbol == 's' || symbol == 'w';
		}
		if(Character.isUpperCase(symbol) || symbol == 'x' 
				|| symbol == 'n' || symbol == 'e' || symbol == 's'){
			return false;
		}
		return true;
	}
	
	/**
	 * Moves player one position up
	 * @param player
	 */
	public void moveNorth(CharacterToken player) {
		Point newPos = new Point(player.getXPos(), player.getYPos()-1);
		move(newPos, player);
	}	

	/**
	 * Moves player one position to the right
	 * @param player
	 */
	public void moveEast(CharacterToken player){
		Point newPos = new Point(player.getXPos()+1, player.getYPos());
		move(newPos, player);
	}
	
	/**
	 * Moves player one position down
	 * @param player
	 */
	public void moveSouth(CharacterToken player) {
		Point newPos = new Point(player.getXPos(), player.getYPos()+1);
		move(newPos, player);
	}
	
	/**
	 * Moves player one position left
	 * @param player
	 */
	public void moveWest(CharacterToken player) {
		Point newPos = new Point(player.getXPos()-1, player.getYPos());
		move(newPos, player);
	}
	
	/**
	 * Moves player to opposite room.
	 * @param player
	 */
	public void useStairs(CharacterToken player){
		Tile tile = getTile(player.getXPos(), player.getYPos());
		if(tile instanceof RoomTile){
			RoomTile rTile = (RoomTile)tile;
			if(rTile.isCornerRoom()){
				CluedoGame.Room opposite = rTile.oppositeRoomPos();
				moveIntoRoom(player, opposite);
			}
		}
	}
	
	/**
	 * Sets player's position within the token and the board.
	 * @param newPos
	 * @param player
	 */
	public void move(Point newPos, GameToken player){
		board[player.getYPos()][player.getXPos()].setToken(null);	// set original pos to null
		// change player position
		player.setXPos(newPos.x);
		player.setYPos(newPos.y);
		board[newPos.y][newPos.x].setToken(player);	// set player in new position on board
	}
	
	/**
	 * Moves player to a given position on the board.
	 * @param newPos
	 * @param player
	 */
	public void move(Position newPos, GameToken player){
		move(new Point(newPos.getX(), newPos.getY()), player);
	}
	
	public Tile getTile(int x, int y){
		return board[y][x];
	}
	
	public void moveIntoRoom(GameToken token, Room r) {
		//char symbol = getRoomSymbol(r);
		for(int i=0; i<board[0].length; i++){
			for(int j=0; j<board.length; j++){
				Tile t = board[i][j];
				//if(t.getSymbol() == symbol && t.getToken() == null){
				if(t instanceof RoomTile){
					RoomTile rTile = (RoomTile)t;
					if(rTile.name() == r && t.getToken() == null){
						token.setXPos(j);
						token.setYPos(i);
						t.setToken(token);
						return; // moved character into room
					}
				}		
			}
		}
	}

	public char getRoomSymbol(Room r) {
		switch(r.toString()){
			case "KITCHEN" :
				return 'K';
			case "BALL_ROOM":
				return 'B';
			case "CONSERVATORY":
				return 'C';
			case "DINING_ROOM":
				return 'N';
			case "BILLIARD_ROOM":
				return 'I';
			case "LIBRARY":
				return 'L';
			case "LOUNGE":
				return 'O';
			case "HALL":
				return 'H';
			case "STUDY":
				return 'L';
			default:
				return (Character) null;
		}
	}
	
	/**
	 * Return the tile that corresponds to a specific character
	 * @param c tile character
	 * @param p position on board
	 * @return tile
	 */
	private Tile getTile(char c, Position p) {
		switch(c){
			case 'x':
				return new WallTile(p);
			case 'n':
			case 'e':
			case 's':
			case 'w':
				return new DoorwayTile(p, c);
			case ' ':
				return new HallwayTile(p, c);
			case 'K':
				return new RoomTile(p, Room.KITCHEN, 'K');
			case 'B':
				return new RoomTile(p, Room.BALL_ROOM, 'B');
			case 'C':
				return new RoomTile(p, Room.CONSERVATORY, 'C');
			case 'N':
				return new RoomTile(p, Room.DINING_ROOM, 'N');
			case 'I':
				return new RoomTile(p, Room.BILLIARD_ROOM, 'I');
			case 'L':
				return new RoomTile(p, Room.LIBRARY, 'L');
			case 'O':
				return new RoomTile(p, Room.LOUNGE, 'O');
			case 'H':
				return new RoomTile(p, Room.HALL, 'H');
			case 'S':
				return new RoomTile(p, Room.STUDY, 'S');
			default:
				throw new CluedoError("Error: tile character not recognised");
		}
	}
	
	/**
	 * Prints the state of the board from the 2D board array
	 */
	private void printBoard(){
		for(int i=0; i<board[0].length; i++){
			for(int j=0; j<board.length; j++){
				if(board[i][j] != null){
					System.out.print(board[i][j].getSymbol());
				} else {
					System.out.print(" ");
				}		
			}
			System.out.print("\n");
		}
		System.out.println("\n");
		System.out.println("\n");
	}
	
	public String toString(){
		this.printBoard();
		return " ";
	}

	public List<CharacterToken> players() {
		return activePlayers;
	}

}
