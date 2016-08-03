package cluedo.tokens;

import cluedo.control.CluedoGame.Weapon;

/**
 * Weapon token on the board that can be moved by players.
 */
public class WeaponToken extends GameToken{
	
	private Weapon name;
	
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
