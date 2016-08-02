package cluedo.board;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cluedo.control.CluedoGame;
import cluedo.control.CluedoGame.Room;
import cluedo.tokens.Card;
import cluedo.tokens.CharacterToken;

public class Board {

	private int height;
	private int width;
	
	private int numPlayers;
	private List<CharacterToken> activePlayers;
	private Card[] solution;
	
	private Tile[][] board; // the board is a 2D array of tiles
	
	public Board(CluedoGame game, String boardFile) {
		this.numPlayers = game.getNumPlayers();
		this.activePlayers = game.getPlayers();
		this.solution = game.Solution();
		this.board = new Tile[25][25];
		
		// reading the board file
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(boardFile));
			for(height=0; scanner.hasNextLine(); height++){
				char[] line = scanner.nextLine().toCharArray();
				for(width=0; width < line.length; width++){
						
					char c = line[width];
					Position pos = new Position(height, width, c);
					
					if(Character.isDigit(c)){
						for(CharacterToken player : activePlayers){
							if(player.getUid() == Character.getNumericValue(c)){
								HallwayTile t = new HallwayTile(pos, c);
								t.setToken(player);
								player.setXPos(width);
								player.setYPos(height);
								board[height][width] = t;
							}
						}
					} else {
						board[height][width] = getTile(c, pos);
					}
				}
			}
		} catch(IOException e) {
			System.out.println("Error processing board file");
		}
		printBoard();
	}
	
	/**
	 * Prints the state of the board
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
				return null;
		}
		
	}

}
