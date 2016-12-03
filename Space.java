package clueless;

import java.util.ArrayList;

/**
*Class for the Space objects that form the basis of the rooms and hallways
*
*@author Jimmy
*@version 1.0
*/	
class Space {
	private boolean isRoom = false;
	protected String name;
	
	ArrayList<Player> occupiedBy = new ArrayList<Player>(0);
	ArrayList<Space> pathsToLeave = new ArrayList<Space>(0);
	
	/**
	*Basic constructor (use for hallway creations)
	*/
	public Space(){	}

	/**
	*Overloaded constructor (use for room creations)
	*
	*@param name String value to be used as the name for the room
	*@param makeRoom boolean value from Board class specifying room to be created
	*/
	Space(String name, boolean makeRoom) {
		this.name = name;
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
