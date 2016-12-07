package clueless;

import java.util.ArrayList;

/**
*Class for the Player objects
*
*@author Jimmy
*@version 1.0
*/	
class Player {
	public String name;
	private boolean isActive = false;
	public Space location;
	private boolean isEliminated = false;
	private boolean isPerson = true;
	ArrayList<Card> playerHand = new ArrayList<Card>(3);
	
	
	/**
	 * Constructor that assigns name to each player
	 * 
	 * @param name string name of the player
	 */
	Player(String name){
		this.name = name;
	}
	
	/**
	*Method to determine if the board is active
	*
	*@return isActive boolean returning true if the player is active, false otherwise
	*/
	public boolean checkActive(){	
		return isActive;
	}
	
	/**
	*Method to set Player object as active 
	*/
	public void setActive(){
		isActive = true;
	}
	
	/**
	*Method to determine if the board is active
	*
	*@return isPerson boolean returning true if the player is a human player, false otherwise
	*/
	public boolean checkElimination(){	
		return isEliminated;
	}
	
	/**
	 * Method to set Player object as a human player
	 */
	public void eliminate(){
		isEliminated = true;
	}
	
	/**
	*Method to determine if the board is active
	*
	*@return isPerson boolean returning true if the player is a human player, false otherwise
	*/
	public boolean checkPerson(){	
		return isPerson;
	}
	
	/**
	 * Method to set Player object as a human player
	 */
	public void setHuman(){
		isPerson = true;
	}
}