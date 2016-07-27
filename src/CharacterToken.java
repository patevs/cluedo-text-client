
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
