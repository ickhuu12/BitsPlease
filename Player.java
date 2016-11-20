/**
 *Class for the Player object
 *@author Jimmy
 *@version 0.5
 */

class Player {
	private boolean isActive = false;
	public Space Location;
	
  /**
  *Method to determine if the board is active
  *@return isActive boolean returning true if the player is active, false otherwise
  */
  public boolean checkActive(){
	  return isActive;
	}
	
  /**
  *Method to set player as active
  */
	public void setActive(){
		isActive = true;
	}
}
