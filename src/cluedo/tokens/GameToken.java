package cluedo.tokens;
/**
 * Tokens displayed on the board.
 */
public abstract class GameToken {
	
	private int XPos;
	private int YPos;
	
	public int getXPos() {
		return XPos;
	}
	public void setXPos(int Xpos){
		this.XPos = Xpos;
	}
	public int getYPos(){
		return YPos;
	}
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
