package clueless;

/**
*Class for the Card objects, defining type and name.
*
*@author Jimmy
*@version 1.1
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
	
	
	@Override
	public String toString(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object card){
		boolean isEqual = false;
			if (card instanceof Card){
				isEqual = (this.name.equals(((Card) card).name)); 
			}
		return isEqual;
	}
}
