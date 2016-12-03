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
	
	/**
	 * Constructor for Card object
	 * 
	 * @param name String name of the new Card
	 * @param type CardType type of the new Card
	 */
	public Card(String name, CardType type){
		this.name = name;
		this.type = type;
	}	
	
	
	/**
	 * Method overrides toString method to print out the name of the active Card
	 * 
	 * @return name String name of the active Card
	 */
	@Override
	public String toString(){
		return this.name;
	}
	
	/**
	 * Method overrides equals method to check for comparison of card name instead of object location
	 * 
	 * @param card Card object that will compared to the active Card
	 * @return isEqual boolean value true if name of active Card and comparison are the same, false otherwise
	 */
	@Override
	public boolean equals(Object card){
		boolean isEqual = false;
			if (card instanceof Card){
				isEqual = (this.name.equals(((Card) card).name)); 
			}
		return isEqual;
	}
}
