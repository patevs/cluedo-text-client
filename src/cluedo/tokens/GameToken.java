package cluedo.tokens;
/**
 * Tokens displayed on the board.
 * 
 * @author Patrick Evans and Maria Legaspi
 * 
 */
public abstract class GameToken {
	
	// position of the game token on the board.
	private int XPos;
	private int YPos;
	
	/**
	 * Returns the x position of the game token on the board.
	 * @return
	 */
	public int getXPos() {
		return XPos;
	}

	/**
	 * Sets the x position of the game token on the board.
	 * @return
	 */
	public void setXPos(int Xpos){
		this.XPos = Xpos;
	}

	/**
	 * Returns the y position of the game token on the board.
	 * @return
	 */
	public int getYPos(){
		return YPos;
	}

	/**
	 * Sets the y position of the game token on the board.
	 * @return
	 */
	public void setYPos(int Ypos){
		this.YPos = Ypos;
	}
	
	/**
	 * Returns the name of this game token.
	 * @return
	 */
	public abstract String getName();
	
	@Override
	public abstract String toString();
}
