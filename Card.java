package clueless;

/**
*Class for the Card objects, defining type and name.
*
*@author Jimmy
*@version 1.0
*/	

class Card {
	enum CardType{
		Room, Player, Weapon
	}
	
	protected String name;
	protected CardType type;
	
	public Card(){}
	
	public Card(String name, CardType type){
		this.name = name;
		this.type = type;
	}	
}
