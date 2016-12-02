package clueless;

import java.util.ArrayList;

/**
*Class for the Board object
*@author Jimmy
*@version 0.4
*/	
class Board {
	public static boolean makeRoom = true;
	public Space room1 = new Space(makeRoom);	//Following lines set up the 21 spaces of the game
	public Space room2 = new Space(makeRoom);
	public Space room3 = new Space(makeRoom);
	public Space room4 = new Space(makeRoom);
	public Space room5 = new Space(makeRoom);
	public Space room6 = new Space(makeRoom);
	public Space room7 = new Space(makeRoom);
	public Space room8 = new Space(makeRoom);
	public Space room9 = new Space(makeRoom);
	public Space hallway1 = new Space();
	public Space hallway2 = new Space();
	public Space hallway3 = new Space();
	public Space hallway4 = new Space();
	public Space hallway5 = new Space();
	public Space hallway6 = new Space();
	public Space hallway7 = new Space();
	public Space hallway8 = new Space();
	public Space hallway9 = new Space();
	public Space hallway10 = new Space();
	public Space hallway11 = new Space();
	public Space hallway12 = new Space();
	
	/**
	*Method to place the players on the game board.  Locations are not randomized
	*
	*@param playerList ArrayList of the 6 players in game
	*/
	void loadInitialBoard(ArrayList<Player> playerList) {
		room1.occupiedBy.add(playerList.get(0));
		room2.occupiedBy.add(playerList.get(1));
		room3.occupiedBy.add(playerList.get(2));
		room4.occupiedBy.add(playerList.get(3));
		room5.occupiedBy.add(playerList.get(4));
		room6.occupiedBy.add(playerList.get(5));
		playerList.get(0).location = room1;
		playerList.get(1).location = room2;
		playerList.get(2).location = room3;
		playerList.get(3).location = room4;
		playerList.get(4).location = room5;
		playerList.get(5).location = room6;
	}
	
	/**
	*Method to place the players on the game board.  Locations are not randomized
	*
	*@param player the player who will be moving
	*@param space the location the player will move to
	*/
	public void movePlayer(Player player, Space space){
		player.location.occupiedBy.remove(player);	//remove player from their current location
		player.location = space;
		space.occupiedBy.add(player);
	}
}
