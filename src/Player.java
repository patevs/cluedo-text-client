
public class Player {
	
	private String name;
	private String token; // character in game
	
	public Player(String name){
		this.name = name;
	}

	public Player(String name, String token) {
		this.name = name;
		this.token = token;
	}

	public String getName(){
		return name;
	}

	public String getToken() {
		return token;
	}
}
