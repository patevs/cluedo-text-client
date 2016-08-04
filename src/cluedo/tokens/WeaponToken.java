package cluedo.tokens;

import cluedo.control.CluedoGame.Weapon;

/**
 * Weapon token on the board that can be moved by players.
 */
public class WeaponToken extends GameToken implements Card{
	
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

	@Override
	public char getSymbol() {
		switch(getName()){
			case "CANDLESTICK":
				return '+';
			case "DAGGER":
				return '-';
			case "LEAD PIPE":
				return '/';
			case "REVOLVER":
				return '*';
			case "ROPE":
				return '=';
			case "SPANNER":
				return '?';
			default:
				return (Character) null;
		}
	}
}
