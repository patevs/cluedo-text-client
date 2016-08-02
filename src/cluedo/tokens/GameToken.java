package cluedo.tokens;
/**
 * Tokens displayed on the board.
 */
public interface GameToken {
	
	/**
	 * Returns the name of this game token.
	 * @return
	 */
	public String getName();
	
	@Override
	public String toString();
}
