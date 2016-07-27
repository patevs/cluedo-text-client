
public class Position {

	private int Xpos;
	private int Ypos;
	private String symbol;
	
	public Position(int X, int Y, String symbol){
		this.Xpos = X;
		this.Ypos = Y;
		this.symbol = symbol;
	}
	
	public int getX(){
		return this.Xpos;
	}
	public int getY(){
		return this.Ypos;
	}
	public String getSymbol(){
		return this.symbol;
	}
	
}
