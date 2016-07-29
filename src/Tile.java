/**
 * A square of the game board which can hold game tokens.
 */
public interface Tile {
	
	/**
	 * Stores a game token in this tile.
	 * @param o
	 * @return
	 */
	public boolean add(Object o);
	
	/**
	 * Removes the game token from this tile.
	 * @return
	 */
	public GameToken remove();
}
