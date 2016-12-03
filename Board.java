package clueless;

import java.util.ArrayList;

/**
*Class for the Board object
*@author Jimmy
*@version 1.0
*/	
class Board {
	public String name;
	public static boolean makeRoom = true;
	public Space study = new Space("Study", makeRoom);	//Following lines set up the 21 spaces of the game
	public Space hall = new Space("Hall", makeRoom);
	public Space lounge = new Space("Lounge", makeRoom);
	public Space library = new Space("Library", makeRoom);
	public Space billiard = new Space("Billard Room", makeRoom);
	public Space dining = new Space("Dining Room", makeRoom);
	public Space conserv = new Space("Conservatory", makeRoom);
	public Space ballroom = new Space("Ballroom", makeRoom);
	public Space kitchen = new Space("Kitchen", makeRoom);
	public Space studyHallHallway = new Space("Study-Hall Hallway");
	public Space hallLoungeHallway = new Space("Hall-Lounge Hallway");
	public Space studyLibraryHallway = new Space("Study-Library Hallway");
	public Space hallBilliardHallway = new Space("Hall-Billard Room Hallway");
	public Space loungeDiningHallway = new Space("Lounge-Dining Room Hallway");
	public Space libraryBilliardHallway = new Space("Library-Billiard Room Hallway");
	public Space billiardDiningHallway = new Space("Billard Room-Dining Room Hallway");
	public Space libraryConserHallway = new Space("Library-Conservatory Hallway");
	public Space billiardBallroomHallway = new Space("Billard Room-Ballroom Hallway");
	public Space diningKitchenHallway = new Space("Dining Room-Kitchen Hallway");
	public Space conservBallroomHallway = new Space("Conservatory-Ballroom Hallway");
	public Space ballroomKitchenHallway = new Space("Ballroom-Kitchen Hallway");
	
	/**
	 * Method to attach all rooms and hallways to each other
	 * Each room will keep a list of rooms/hallways it can exit to (not that can access it)
	 */
	public void setUpExits(){
		//first row of board (three rooms and two hallways)
		study.pathsToLeave.add(studyHallHallway);
		study.pathsToLeave.add(billiard);
		study.pathsToLeave.add(studyLibraryHallway);
		studyHallHallway.pathsToLeave.add(study);
		studyHallHallway.pathsToLeave.add(hall);
		hall.pathsToLeave.add(studyHallHallway);
		hall.pathsToLeave.add(hallBilliardHallway);
		hall.pathsToLeave.add(hallLoungeHallway);
		hallLoungeHallway.pathsToLeave.add(hall);
		hallLoungeHallway.pathsToLeave.add(lounge);
		lounge.pathsToLeave.add(hallLoungeHallway);
		lounge.pathsToLeave.add(billiard);
		lounge.pathsToLeave.add(diningKitchenHallway);
		
		//second row (three hallways)
		studyLibraryHallway.pathsToLeave.add(study);
		studyLibraryHallway.pathsToLeave.add(library);
		hallBilliardHallway.pathsToLeave.add(hall);
		hallBilliardHallway.pathsToLeave.add(billiard);
		loungeDiningHallway.pathsToLeave.add(lounge);
		loungeDiningHallway.pathsToLeave.add(dining);
		
		//third row (three rooms and two hallways)
		library.pathsToLeave.add(studyLibraryHallway);
		library.pathsToLeave.add(libraryConserHallway);
		library.pathsToLeave.add(libraryBilliardHallway);
		libraryBilliardHallway.pathsToLeave.add(library);
		libraryBilliardHallway.pathsToLeave.add(billiard);
		billiard.pathsToLeave.add(libraryBilliardHallway);
		billiard.pathsToLeave.add(hallBilliardHallway);
		billiard.pathsToLeave.add(billiardDiningHallway);
		billiard.pathsToLeave.add(billiardBallroomHallway);
		billiardDiningHallway.pathsToLeave.add(billiard);
		billiardDiningHallway.pathsToLeave.add(dining);
		dining.pathsToLeave.add(loungeDiningHallway);
		dining.pathsToLeave.add(billiardDiningHallway);
		dining.pathsToLeave.add(diningKitchenHallway);
		
		//fourth row (three hallways)
		libraryConserHallway.pathsToLeave.add(library);
		libraryConserHallway.pathsToLeave.add(conserv);
		billiardBallroomHallway.pathsToLeave.add(billiard);
		billiardBallroomHallway.pathsToLeave.add(ballroom);
		diningKitchenHallway.pathsToLeave.add(dining);
		diningKitchenHallway.pathsToLeave.add(kitchen);
		
		//fifth row (three rooms and two hallways)
		conserv.pathsToLeave.add(libraryConserHallway);
		conserv.pathsToLeave.add(billiard);
		conserv.pathsToLeave.add(conservBallroomHallway);
		conservBallroomHallway.pathsToLeave.add(conserv);
		conservBallroomHallway.pathsToLeave.add(ballroom);
		ballroom.pathsToLeave.add(conservBallroomHallway);
		ballroom.pathsToLeave.add(billiardBallroomHallway);
		ballroom.pathsToLeave.add(ballroomKitchenHallway);
		ballroomKitchenHallway.pathsToLeave.add(ballroom);
		ballroomKitchenHallway.pathsToLeave.add(kitchen);
		kitchen.pathsToLeave.add(diningKitchenHallway);
		kitchen.pathsToLeave.add(billiard);
		kitchen.pathsToLeave.add(ballroomKitchenHallway);		
	}
	
	/**
	*Method to place the players on the game board.  Locations are not randomized
	*
	*@param playerList ArrayList of the 6 players in game
	*/
	void loadInitialBoard(ArrayList<Player> playerList) {
		hall.occupiedBy.add(playerList.get(0));
		kitchen.occupiedBy.add(playerList.get(1));
		conserv.occupiedBy.add(playerList.get(2));
		library.occupiedBy.add(playerList.get(3));
		dining.occupiedBy.add(playerList.get(4));
		lounge.occupiedBy.add(playerList.get(5));
		playerList.get(0).location = hall;
		playerList.get(1).location = kitchen;
		playerList.get(2).location = conserv;
		playerList.get(3).location = library;
		playerList.get(4).location = dining;
		playerList.get(5).location = lounge;
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
