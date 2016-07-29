/**
 * A square of the game board which can hold game tokens.
 */
public interface Tile {
	public boolean add(Object o);
	
	public GameToken remove();
}
