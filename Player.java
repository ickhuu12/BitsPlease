package clueless;

/**
*Class for the Player objects
*
*@author Jimmy
*@version 0.8
*/	
class Player {
	private boolean isActive = false;
	public Space location;
	
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