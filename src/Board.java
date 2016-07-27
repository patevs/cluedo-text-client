import java.util.ArrayList;
import java.util.List;

public class Board {

	private int height;
	private int width;

	private List<Position> board;
	
	public Board(CluedoGame cluedoGame, String boardFile) {
		board = new ArrayList<Position>();
	}

}
