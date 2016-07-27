
public class WeaponToken implements GameToken{
	public Weapon name;
	public WeaponToken(Weapon name){
		this.name=name;
	}
	
	public String getName(){
		return name.toString();
	}
	
	public String toString(){
		return name.toString();
	}
}
