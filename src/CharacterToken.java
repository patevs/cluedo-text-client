/**
 * Character tokens on the board that can be moved by players.
 */
public class CharacterToken implements GameToken{
	public Character name;
	public CharacterToken(Character name){
		this.name=name;
	}
	
	public String getName(){
		return name.toString();
	}
	
	public String toString(){
		return name.toString();
	}
}
