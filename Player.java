package clueless;

import java.util.ArrayList;

/**
*Class for the Player objects
*
*@author Jimmy
*@version 0.8.5
*/	
class Player {
	private boolean isActive = false;
	public Space location;
	ArrayList<Card> playerHand = new ArrayList<Card>(3);
	
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
}