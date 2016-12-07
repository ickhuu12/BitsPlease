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
	public Space(String name){
		this.name = name;
	}

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
	
	/**
	* 
	* @param destination String value representing name of the room to be moved to
	* @return room a link to the room object to be moved to it exists as an exit, null Space otherwise
	*/
	public Space validOption(String destination){
		for (Space room : pathsToLeave){
			if (room.name.equals(destination)){
				return room;
			}
		}
		return null;
	}
	
	/**
	* Method to print out the name of the active Space
	* 
	* @return name String name of the active Space
	*/
	@Override
	public String toString(){
		return this.name;
	}
	
	/**
	* Method overrides equals method to check for comparison of space name instead of object location
	* 
	* @param space Space object that will compared to the active Space
	* @return isEqual boolean value true if name of active Space and comparison are the same, false otherwise
	*/
	@Override
	public boolean equals(Object space){
		boolean isEqual = false;
			if (space instanceof Space){
				isEqual = (this.name.equals(((Space) space).name)); 
			}
		return isEqual;
	}
}
