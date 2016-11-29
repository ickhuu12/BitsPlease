package clueless;

import java.util.ArrayList;

/**
*Class for the Space objects
*@author Jimmy
*@version 0.5
*/	
class Space {
	private boolean isRoom = false;
	
	ArrayList<Player> occupiedBy = new ArrayList<Player>(0);
	ArrayList<Space> accessPoints = new ArrayList<Space>(2);
	
	/**
	*Basic constructor (use for hallway creations)
	*/
	public Space(){}

	/**
	*Overloaded constructor (use for room creations)
	*
	*@param makeRoom boolean value from Board class specifying room to be created
	*/
	Space(boolean makeRoom) {
		this.isRoom = true;
	}

	/**
	*Method to determine if the board is active
	*
	*@return isEmpty true if occupiedBy ArrayList is empty, false otherwise
	*/
	boolean isEmpty(){
		return occupiedBy.isEmpty();
	}
	
	/**
	*Method to determine if the board is active
	*
	*@return isRoom true if space is a room, false if hallway
	*/
	boolean isRoom(){
		return isRoom;
	}
}
