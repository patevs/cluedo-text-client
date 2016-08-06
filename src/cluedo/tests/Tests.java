package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import cluedo.board.*;
import cluedo.control.*;
import cluedo.control.CluedoGame.Character;
import cluedo.control.CluedoGame.Room;
import cluedo.tokens.*;

/**
 * Tests the Cluedo game
 * @author Patrick Evans and Maria Legaspi
 *
 */
public class Tests {

	// from http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	//====================================================================================//
	//                                  EQUIPMENT TESTS                                   //
	//====================================================================================//
		
	@Test
	public void testCharactersNotEmpty(){
		CluedoGame game = newGame();
		assertFalse(game.characters().isEmpty());
	}
	
	@Test
	public void testDeckEmpty(){
		TextClient client = smallCluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		assertTrue(game.deck().size()==0); // all cards dealt to players
		for(CharacterToken player: game.players()){
			if(player.isPlayer())
				assertEquals(4, player.getHand().size());
			else
				assertEquals(0, player.getHand().size());
		}
		assertEquals(2, game.unusedCards().size());
	}
	
	
	@Test
	public void testWeaponsNotEmpty(){
		CluedoGame game = newGame();
		assertFalse(game.weapons().isEmpty());
	}
	
	@Test
	public void testRoomsNotEmpty(){
		CluedoGame game = newGame();
		assertFalse(game.rooms().isEmpty());
	}
	
	@Test
	public void testNumCharacters(){
		CluedoGame game = newGame();
		assertEquals(6, game.characters().size());
	}
	
	@Test
	public void testNumWeapons(){
		CluedoGame game = newGame();
		assertEquals(6, game.weapons().size());
	}
	
	@Test
	public void testNumRooms(){
		CluedoGame game = newGame();
		assertEquals(9, game.rooms().size());
	}

	@Test
	public void testCharacterCardName(){
		CharacterToken chara = new CharacterToken("name", CluedoGame.Character.MISS_SCARLETT, true, 0);
		assertTrue(chara.getName().equals("name"));
		assertTrue(chara.getToken().toString().equals("MISS SCARLETT"));
	}
	
	@Test
	public void testPlayerHand(){
		CluedoGame game = newGame();
		assertNotNull(game.players().get(0).getHand());
	}
	
	@Test
	public void testCharacterNames(){
		CluedoGame game = newGame();
		List<CluedoGame.Character> characters = game.characters();
		assertEquals(characters.get(0).toString(), "MISS SCARLETT");
		assertEquals(characters.get(1).toString(), "COLONEL MUSTARD");
		assertEquals(characters.get(2).toString(), "MRS WHITE");
		assertEquals(characters.get(3).toString(), "THE REVEREND GREEN");
		assertEquals(characters.get(4).toString(), "MRS PEACOCK");
		assertEquals(characters.get(5).toString(), "PROFESSOR PLUM");
	}
	
	@Test
	public void testWeaponNames(){
		CluedoGame game = newGame();
		List<WeaponToken> weapons = game.weapons();
		assertEquals(weapons.get(0).toString(), "CANDLESTICK");
		assertEquals(weapons.get(1).toString(), "DAGGER");
		assertEquals(weapons.get(2).toString(), "LEAD PIPE");
		assertEquals(weapons.get(3).toString(), "REVOLVER");
		assertEquals(weapons.get(4).toString(), "ROPE");
		assertEquals(weapons.get(5).toString(), "SPANNER");
	}
	
	@Test
	public void testValidCharacterTokenEquals() {
		CharacterToken character = getPlayer();
		CharacterToken other = getPlayer();
		assertTrue(character.equals(other));
	}
	
	@Test
	public void testInvalidCharacterTokenEquals() {
		CharacterToken character = getPlayer();
		CharacterToken other = getOtherPlayer();
		assertFalse(character.equals(other));
	}

	@Test
	public void testValidCharacterEquals(){
		CluedoGame.Character character = getPlayer().getToken();
		CluedoGame.Character other = getPlayer().getToken();
		assertTrue(character.equals(other));
	}
	
	@Test
	public void testInvalidCharacterEquals(){
		CluedoGame.Character character = getPlayer().getToken();
		CluedoGame.Character other = getOtherPlayer().getToken();
		assertFalse(character.equals(other));
	}
	
	@Test
	public void activePlayers(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		assertEquals(game.players(), game.board().players());
	}
	
	@Test
	public void testGameEquipment(){
		CluedoGame game = newGame();
		assertEquals(game.weapons().size(), 6);
		assertEquals(game.rooms().size(), 9);
	}
	
	@Test
	public void testRoomSymbols(){
		Board board = newBoard(newGame());
		assertEquals('K', board.getRoomSymbol(Room.KITCHEN));
		assertEquals('B', board.getRoomSymbol(Room.BALL_ROOM));
		assertEquals('C', board.getRoomSymbol(Room.CONSERVATORY));
		assertEquals('I', board.getRoomSymbol(Room.BILLIARD_ROOM));
		assertEquals('L', board.getRoomSymbol(Room.LIBRARY));
		assertEquals('S', board.getRoomSymbol(Room.STUDY));
		assertEquals('H', board.getRoomSymbol(Room.HALL));
		assertEquals('O', board.getRoomSymbol(Room.LOUNGE));
		assertEquals('N', board.getRoomSymbol(Room.DINING_ROOM));
	}
	
	@Test
	public void testRoomNames(){
		List<Room> all = getAllRooms();
		assertTrue(all.contains(CluedoGame.Room.KITCHEN));
		assertTrue(all.contains(CluedoGame.Room.BALL_ROOM));
		assertTrue(all.contains(CluedoGame.Room.CONSERVATORY));
		assertTrue(all.contains(CluedoGame.Room.BILLIARD_ROOM));
		assertTrue(all.contains(CluedoGame.Room.LIBRARY));
		assertTrue(all.contains(CluedoGame.Room.STUDY));
		assertTrue(all.contains(CluedoGame.Room.HALL));
		assertTrue(all.contains(CluedoGame.Room.LOUNGE));
		assertTrue(all.contains(CluedoGame.Room.DINING_ROOM));
	}
	
	//====================================================================================//
	//                                      MOVE TESTS                                    //
	//====================================================================================//

	@Test
	public void testValidMove(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = getPlayer();
		//System.out.println(player.getXPos() + ", " + player.getYPos());
		CharacterToken othPlayer = getOtherPlayer();
		board.move(getRoomPos(), player);
		//System.out.println(player.getXPos() + ", " + player.getYPos());
		board.moveIntoRoom(othPlayer, getRoom());
		System.out.println(player.getXPos() + ", " + player.getYPos());
		System.out.println(othPlayer.getXPos() + ", " + othPlayer.getYPos());
		RoomTile pOneTile = (RoomTile)(board.getTile(player.getXPos(), player.getYPos()));
		RoomTile pTwoTile = (RoomTile)(board.getTile(othPlayer.getXPos(), othPlayer.getYPos()));
		assertEquals(pOneTile.name().toString(), pTwoTile.name().toString());
	}

	@Test
	public void testInvalidMoveIntoNullRoom(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = getPlayer();
		try{
			board.moveIntoRoom(player, null);
			fail();
		}
		catch(CluedoError e){
		}
	}

	@Test
	public void testInvalidMoveNullIntoRoom(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = null;
		try{
			board.moveIntoRoom(player, getRoom());
			fail();
		}
		catch(CluedoError e){
		}
	}
	
	@Test
	public void testValidStairs(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = getPlayer();
		board.moveIntoRoom(player, getOtherRoom()); // getOtherRoom = kitchen = corner room
		try{
			board.useStairs(player);
			RoomTile r = (RoomTile) board.getTile(player.getXPos(), player.getYPos());
			assertTrue(r.isCornerRoom());
		}
		catch(CluedoError e){
			fail();
		}
	}
	
	@Test
	public void testInvalidStairs(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = getPlayer();
		board.moveIntoRoom(player, getRoom()); // getRoom = ballroom = not a corner room
		RoomTile r = (RoomTile) board.getTile(player.getXPos(), player.getYPos());
		assertFalse(r.isCornerRoom());
		try{
			board.useStairs(player);
			fail();
		}
		catch(CluedoError e){
		}
	}
	
	@Test
	public void testValidPrintBoard(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		board.printBoard();
		assertEquals("xxxxxxxxx1xxxxx2xxxxxxxxx\n" + 
					 "KKKKKSx   BBBBB   xCCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  nCCCCC\n" +
					 "KKKKKK  eBBBBBBBw   CCCCO\n" +
					 "xKKKnK  BBBBBBBBB       3\n" +
					 "        BnBBBBnBB       x\n" +
					 "x                  IIIIII\n" +
					 "NNNNN              eIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNw  xxxxxx   IIIInI\n" +
					 "NNNNNNNN  xxxxxx        x\n" +
					 "NNNNNNNN  xxxxxx   LLsLLx\n" +
					 "NNNNNNnN  xxxxxx  LLLLLLL\n" +
					 "x         xxxxxx  eLLLLLL\n" +
					 "6                 LLLLLLL\n" +
  					 "x        HHsssHH   LLLLLx\n" +
  					 "COOOOOs  HHHHHHH        4\n" +
  					 "OOOOOOO  HHHHHHw        x\n" +
  					 "OOOOOOO  HHHHHHH  sSSSSSK\n" +
  					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
  					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
  					 "OOOOOOx5xHHHHHHHx SSSSSSS\n", outContent.toString());
	}
	
	@Test
	public void testValidPrintBoard_1(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		CharacterToken player = game.players().get(0);
		board.printBoard();
		assertEquals("xxxxxxxxx1xxxxx2xxxxxxxxx\n" + 
					 "KKKKKSx   BBBBB   xCCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  nCCCCC\n" +
					 "KKKKKK  eBBBBBBBw   CCCCO\n" +
					 "xKKKnK  BBBBBBBBB       3\n" +
					 "        BnBBBBnBB       x\n" +
					 "x                  IIIIII\n" +
					 "NNNNN              eIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNw  xxxxxx   IIIInI\n" +
					 "NNNNNNNN  xxxxxx        x\n" +
					 "NNNNNNNN  xxxxxx   LLsLLx\n" +
					 "NNNNNNnN  xxxxxx  LLLLLLL\n" +
					 "x         xxxxxx  eLLLLLL\n" +
					 "6                 LLLLLLL\n" +
					 "x        HHsssHH   LLLLLx\n" +
					 "COOOOOs  HHHHHHH        4\n" +
					 "OOOOOOO  HHHHHHw        x\n" +
					 "OOOOOOO  HHHHHHH  sSSSSSK\n" +
					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
					 "OOOOOOx5xHHHHHHHx SSSSSSS\n", outContent.toString());
		board.moveSouth(player);
		outContent.reset();
		board.printBoard();
		assertEquals("xxxxxxxxx xxxxx2xxxxxxxxx\n" + 
					 "KKKKKSx  1BBBBB   xCCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  CCCCCC\n" +
					 "KKKKKK  BBBBBBBBB  nCCCCC\n" +
					 "KKKKKK  eBBBBBBBw   CCCCO\n" +
					 "xKKKnK  BBBBBBBBB       3\n" +
					 "        BnBBBBnBB       x\n" +
					 "x                  IIIIII\n" +
					 "NNNNN              eIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNN  xxxxxx   IIIIII\n" +
					 "NNNNNNNw  xxxxxx   IIIInI\n" +
					 "NNNNNNNN  xxxxxx        x\n" +
					 "NNNNNNNN  xxxxxx   LLsLLx\n" +
					 "NNNNNNnN  xxxxxx  LLLLLLL\n" +
					 "x         xxxxxx  eLLLLLL\n" +
					 "6                 LLLLLLL\n" +
  					 "x        HHsssHH   LLLLLx\n" +
  					 "COOOOOs  HHHHHHH        4\n" +
  					 "OOOOOOO  HHHHHHw        x\n" +
  					 "OOOOOOO  HHHHHHH  sSSSSSK\n" +
  					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
  					 "OOOOOOO  HHHHHHH  SSSSSSS\n" +
  					 "OOOOOOx5xHHHHHHHx SSSSSSS\n", outContent.toString());
	}
	
	//====================================================================================//
	//                           ACCUSATION + SUGGESTION TESTS                            //
	//====================================================================================//
	
	@Test
	public void testValidAccusation(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		Card[] solution= game.Solution();
		assertTrue(client.checkAccusation(solution, game.players().get(0)));
		assertTrue(game.activePlayers()); // players still active
	}
	
	@Test
	public void testInvalidAccusation(){
		TextClient client = cluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		Card[] solution= game.Solution();
		Card[] guess = solution.clone();
		// change the room so the guess is wrong
		List<Room> rooms = getAllRooms();
		for(Room r: rooms){
			if(!(r.name().equals(solution[1].toString()))){
					guess[1] = r;
					break;
			}
		}
		assertFalse(client.checkAccusation(guess, game.players().get(0)));
	}
	
	@Test
	public void testEndGame(){
		TextClient client = miniCluedoGame();
		CluedoGame game = client.game();
		Board board = client.board();
		Card[] solution= game.Solution();
		Card[] guess = solution.clone();
		// change the room so the guess is wrong
		List<Room> rooms = getAllRooms();
		for(Room r: rooms){
			if(!(r.name().equals(solution[1].toString()))){
					guess[1] = r;
					break;
			}
		}
		assertFalse(client.checkAccusation(guess, game.players().get(0)));
		assertFalse(game.activePlayers()); // All player have lost
	}
	
	//====================================================================================//
	//                                  HELPER METHODS                                    //
	//====================================================================================//
	
	// from http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	/**
	 * Prints to a stream so output can be compared to strings.
	 */
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	/**
	 * Returns a solution with Colonel Mustard, Ballroom, Rope.
	 */
	private Card[] getSolution(){
		Card[] solution = new Card[3];
		solution[0] = getPlayer().getToken();
		solution[1] = getRoom();
		solution[2] = getWeaponToken();
		return solution;
	}
	
	/**
	 * Returns a test character.
	 * @return
	 */
	private CharacterToken getPlayer(){
		return new CharacterToken("test1", CluedoGame.Character.COLONEL_MUSTARD, true, 1);
	}
	
	/**
	 * Returns a second test character.
	 * @return
	 */
	private CharacterToken getOtherPlayer(){
		return new CharacterToken("test2", CluedoGame.Character.MISS_SCARLETT, true, 1);
	}
	
	/**
	 * Returns a random test character.
	 * @return
	 */
	private CharacterToken getRandomPlayer(){
		int index = (int)(Math.random()*6);
		return getAllCharacters().get(index);
	}
	
	/**
	 * Returns a non-player character.
	 */
	private CharacterToken getNonPlayer(){
		return new CharacterToken("test1", CluedoGame.Character.MRS_PEACOCK, false, 1);
	}
	
	/**
	 * Returns a list of all six players.
	 * @return
	 */
	private List<CharacterToken> getAllCharacters(){
		List<CharacterToken> characters = new ArrayList<CharacterToken>();
		characters.add(new CharacterToken("test1", CluedoGame.Character.COLONEL_MUSTARD, true, 1));
		characters.add(new CharacterToken("test2", CluedoGame.Character.MISS_SCARLETT, true, 2));
		characters.add(new CharacterToken("test3", CluedoGame.Character.MRS_PEACOCK, true, 3));
		characters.add(new CharacterToken("test4", CluedoGame.Character.MRS_WHITE, true, 4));
		characters.add(new CharacterToken("test5", CluedoGame.Character.PROFESSOR_PLUM, true, 5));
		characters.add(new CharacterToken("test6", CluedoGame.Character.THE_REVEREND_GREEN, true, 6));
		return characters;
	}
	
	/**
	 * Returns a list of four players.
	 * @return
	 */
	private List<CharacterToken> getHalfCharacters(){
		List<CharacterToken> characters = new ArrayList<CharacterToken>();
		characters.add(new CharacterToken("test1", CluedoGame.Character.COLONEL_MUSTARD, true, 1));
		characters.add(new CharacterToken("test2", CluedoGame.Character.MISS_SCARLETT, true, 2));
		characters.add(new CharacterToken("test3", CluedoGame.Character.MRS_PEACOCK, true, 3));
		characters.add(new CharacterToken("test4", CluedoGame.Character.MRS_WHITE, true, 4));
		characters.add(new CharacterToken("test5", CluedoGame.Character.PROFESSOR_PLUM, false, 5));
		characters.add(new CharacterToken("test6", CluedoGame.Character.THE_REVEREND_GREEN, false, 6));
		return characters;
	}
	
	/**
	 * Returns a list of one player.
	 * @return
	 */
	private List<CharacterToken> getOneCharacter(){
		List<CharacterToken> characters = new ArrayList<CharacterToken>();
		characters.add(new CharacterToken("test1", CluedoGame.Character.COLONEL_MUSTARD, true, 1));
		characters.add(new CharacterToken("test2", CluedoGame.Character.MISS_SCARLETT, false, 2));
		characters.add(new CharacterToken("test3", CluedoGame.Character.MRS_PEACOCK, false, 3));
		characters.add(new CharacterToken("test4", CluedoGame.Character.MRS_WHITE, false, 4));
		characters.add(new CharacterToken("test5", CluedoGame.Character.PROFESSOR_PLUM, false, 5));
		characters.add(new CharacterToken("test6", CluedoGame.Character.THE_REVEREND_GREEN, false, 6));
		return characters;
	}
	
	/**
	 * Returns a position in the ballroom.
	 */
	private Point getRoomPos(){
		return new Point(12,4);
	}
	
	/**
	 * Returns a second position in the billiard room.
	 * @return
	 */
	private Point getOthRoomPos(){
		return new Point(21,11);
	}
	
	/**
	 * Returns a test room.
	 */
	private Room getRoom(){
		return CluedoGame.Room.BALL_ROOM;
	}
	
	/**
	 * Returns a second test room.
	 */
	private Room getOtherRoom(){
		return CluedoGame.Room.KITCHEN;
	}
	
	/**
	 * Returns a random test room.
	 */
	private Room getRandomRoom(){
		int index = (int)(Math.random()*6);
		return getAllRooms().get(index);
	}
	
	private List<Room> getAllRooms(){
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(CluedoGame.Room.KITCHEN);
		rooms.add(CluedoGame.Room.BALL_ROOM);
		rooms.add(CluedoGame.Room.CONSERVATORY);
		rooms.add(CluedoGame.Room.BILLIARD_ROOM);
		rooms.add(CluedoGame.Room.LIBRARY);
		rooms.add(CluedoGame.Room.STUDY);
		rooms.add(CluedoGame.Room.HALL);
		rooms.add(CluedoGame.Room.LOUNGE);
		rooms.add(CluedoGame.Room.DINING_ROOM);
		return rooms;
	}
	
	/**
	 * Returns a test weapon.
	 * @return
	 */
	private WeaponToken getWeaponToken(){
		return new WeaponToken(CluedoGame.Weapon.ROPE);
	}
	
	/**
	 * Returns a second test weapon.
	 * @return
	 */
	private WeaponToken getOtherWeaponToken(){
		return new WeaponToken(CluedoGame.Weapon.CANDLESTICK);
	}
	
	/**
	 * Returns a random weapon.
	 * @return
	 */
	private WeaponToken getRandomWeapon(){
		int index = (int)(Math.random()*6);
		return getAllWeapons().get(index);
	}
	
	/**
	 * Returns the list of all weapons.
	 * @return
	 */
	private List<WeaponToken> getAllWeapons(){
		List<WeaponToken> weapons = new ArrayList<WeaponToken>();
		weapons.add(new WeaponToken(CluedoGame.Weapon.CANDLESTICK));
		weapons.add(new WeaponToken(CluedoGame.Weapon.DAGGER));
		weapons.add(new WeaponToken(CluedoGame.Weapon.LEAD_PIPE));
		weapons.add(new WeaponToken(CluedoGame.Weapon.REVOLVER));
		weapons.add(new WeaponToken(CluedoGame.Weapon.ROPE));
		weapons.add(new WeaponToken(CluedoGame.Weapon.SPANNER));
		return weapons;
	}
	
	/**
	 * Creates a text client associated with a game and board.
	 * @return
	 */
	private TextClient cluedoGame(){
		TextClient client = new TextClient();
		CluedoGame game = newGame();
		client.setGame(game);
		client.setBoard(game.board());
		return client;
	}
	
	/**
	 * Creates a text client associated with a small game (4 players) and a board.
	 * @return
	 */
	private TextClient smallCluedoGame(){
		TextClient client = new TextClient();
		CluedoGame game = newSmallGame();
		client.setGame(game);
		client.setBoard(game.board());
		return client;
	}
	
	/**
	 * Creates a text client associated with a game with one player and a board.
	 * @return
	 */
	private TextClient miniCluedoGame(){
		TextClient client = new TextClient();
		CluedoGame game = newMiniGame();
		client.setGame(game);
		client.setBoard(game.board());
		return client;
	}
	
	/**
	 * Creates a game with 6 players.
	 * @return
	 */
	private CluedoGame newGame(){
		return new CluedoGame(6, getAllCharacters(), "gameBoard.txt");
	}
	
	/**
	 * Creates a game with 4 players.
	 * @return
	 */
	private CluedoGame newSmallGame(){
		return new CluedoGame(4, getHalfCharacters(), "gameBoard.txt");
	}
	
	/**
	 * Creates a game with 4 players.
	 * @return
	 */
	private CluedoGame newMiniGame(){
		return new CluedoGame(1, getOneCharacter(), "gameBoard.txt");
	}
	
	/**
	 * Creates a board.
	 * @return
	 */
	private Board newBoard(CluedoGame game){
		return new Board(game, "gameBoard.txt");
	}
}

