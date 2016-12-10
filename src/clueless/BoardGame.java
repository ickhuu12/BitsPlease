package clueless;

/**
*Class for the over-arching playing of the board game. Triggers initialization of 
*objects needed for the game (Players, Rooms, Cards, etc.), sets initial game play conditions, 
*and handles actions players undertake.
*
*@author Jimmy
*@version 1.0
*/	

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import clueless.Card.CardType;


public class BoardGame {
	Random rng = new Random();
	Board currentBoard = new Board();
	Deck deck = new Deck();
	Scanner kb = new Scanner(System.in);
	ArrayList<Card> suspectDeck = deck.createSuspectDeck();	//initiates creation of the three deck types
	ArrayList<Card> roomDeck = deck.createRoomDeck();
	ArrayList<Card> weaponDeck = deck.createWeaponDeck();
	ArrayList<Card> combinedDeck = new ArrayList<Card>(0);	//will be used for combined deck later
	ArrayList<Player> playerList = new ArrayList<Player>();
	private boolean victory = false;
	Card room = new Card();
	Card murderer = new Card();
	Card weapon = new Card();
	Player currentPlayer;
	Player secondaryPlayer;
	static ClientThreadHandler currentThread;
	ClientThreadHandler secondaryThread;
	public static ArrayList<ClientThreadHandler> connectedClients = new ArrayList<ClientThreadHandler>();
	int playerIndex = 0;
	int storedRoom;
	int storedWeapon;
	int storedPlayer;
	String storedCard;
	int adder = 1; //secondary player will be currentIndex+adder;
	
	
	/**
	 * Method to create the list of players.  Also calls methods to load players into their intial
	 * positions and connect all the rooms exits to each other
	 */
	void createPlayerList(){
		Player peacock = new Player("Mrs. Peacock");
		Player plum = new Player("Professor Plum");
		Player green = new Player("Mr. Green");
		Player scarlet = new Player("Miss Scarlet");
		Player white = new Player("Mrs. White");
		Player mustard = new Player("Colonel Mustard");
		playerList.add(peacock);
		playerList.add(plum);
		playerList.add(green);
		playerList.add(scarlet);
		playerList.add(white);
		playerList.add(mustard);
		currentBoard.loadInitialBoard(playerList);
		currentBoard.setUpExits();
		currentPlayer = playerList.get(0);
		secondaryPlayer = playerList.get(1);
		System.out.println("Location of current player" +currentPlayer.name + " " + currentPlayer.location.name);
	}
	
	/**
	*Method loads one card from each deck type into its own Card variable and stored as a winning hand
	*Each card is subsequently removed from its deck
	*/
	void loadWinningCards(){
		int index = rng.nextInt(5);
		murderer = suspectDeck.get(index);
		suspectDeck.remove(index);
		index = rng.nextInt(8);
		room = roomDeck.get(index);
		roomDeck.remove(index);
		index = rng.nextInt(5);
		weapon = weaponDeck.get(index);
		weaponDeck.remove(index);
		System.out.println("The Winning Cards are: " + murderer.name + ", " + room.name + 
				", " + weapon.name);
	}
	
	/**
	*Method combines remainder of each card type deck into one ArrayList deck
	*/
	void combineDecks(){
		combinedDeck.addAll(roomDeck);
		combinedDeck.addAll(suspectDeck);
		combinedDeck.addAll(weaponDeck);
		Collections.shuffle(combinedDeck);
		System.out.println("The 1st element of the combined deck is: " + combinedDeck.get(0).name);
	}
	
	/**
	*Method deals out the combined deck to each player.  Each player gets three cards.
	*/
	void dealCards(){
		for (int count = 0; count < 18; count++){
			int playerIndex = count % 6;
			playerList.get(playerIndex).playerHand.add(combinedDeck.get(count));
		}
	}
	
	String boardDescription(){
		String message = "MESSAGE ";
		for(int i = 0; i < 6 ; i++){
			message = message + "["+playerList.get(i).name + " -> " + playerList.get(i).location.toString() + "] ";
		}
		return message;
		
	}

	/**
	 * Method to handle accusation.  Starts by getting user input, and checks each of the three components against
	 * the predetermined victory cards.  Will eliminate player if they guess wrong, end game if they guess right
	 * 
	 * @param playerIndex int for current player in playerList
	 */
	Boolean handleAccused() {
		Card roomCard = roomDeck.get(storedRoom);
		Card murdererCard = suspectDeck.get(storedPlayer);
		Card weaponCard = weaponDeck.get(storedWeapon);
		
		if (roomCard == room && murdererCard == murderer && weaponCard == weapon){
			broadcast("WINNER " + currentPlayer.name + "solved the murder! Solution: "
					+ storedPlayer + " committed the murder in the " + storedRoom +" with the " 
					+ storedWeapon );
			return true;
		}
		else{
			sendMessageToCurrent("MESSAGE you have guessed incorrectly you are eliminated.");
			playerList.get(playerIndex).eliminate();
			broadcast("MESSAGE " + currentPlayer.name + " accusation was wrong. They are eliminated from the game");
			startNewTurn();
			return false;
		}
		
	}
	
	
	
	/**
	 * Method to move a player who has been accused/suggestion of being the murderer to the room
	 * where accusation/suggestion is taking place
	 * 
	 * @param murderGuess String name of the person to be moved
	 * @param location Space location they will be moved to
	 */
	private void forceMovePlayer(int player, Space location){
		currentBoard.movePlayer(playerList.get(player), location);
		broadcast("MESSAGE " + playerList.get(player).name + " has been forced to move to " + location.toString());
	}
	
	String handleWHO_IS_HERE(){
		System.out.println("Figuring out who is here");
		String msg = null;
		for(int i = 0; i < connectedClients.size(); i++ ){
			msg = msg + connectedClients.get(i).threadName + " ";
		}
		return msg;
	}
	String sendMoveRequest(){
		System.out.println("Sending move request");
		String msg = "MOVE_REQUEST " + currentPlayer.location.pathsToLeave.toString();
		return msg;
	}
	
	Boolean handleMoveReply(String location){
		Space movingTo = currentPlayer.location.validOption(location);
		String msg = null;
		if (movingTo != null){
			if(movingTo.isRoom() || (!movingTo.isRoom() && movingTo.isEmpty())){
				msg = "MESSAGE Space unoccupied, Moving " +currentPlayer.name +" to " + movingTo.name;
				System.out.println(msg);
				broadcast(msg);
				currentBoard.movePlayer(currentPlayer, movingTo);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	String handleCard(Player player){
		return "MESSAGE Your cards are: " + player.playerHand.get(0).name 
				+ ", " + player.playerHand.get(1).name 
				+ ", " + player.playerHand.get(2).name;
	}
	
	String sendSuggestionPlayerRequest(){
		return "SUGGESTION_PLAYER_REQUEST " + currentPlayer.location.toString();
	}
	
	String sendSuggesitonWeaponRequest(){
		return "SUGGESTION_WEAPON_REQUEST " + currentPlayer.name;
	}
	
	String sendSuggestionMessage(){
		return "MESSAGE " + currentPlayer.name + "has suggested that " + suspectDeck.get(storedPlayer) + 
				"committed the murder in the " + currentPlayer.location.toString() + " with a " + weaponDeck.get(storedWeapon);
	}
	
	void handleDisprove(){
		Card roomCard = new Card(playerList.get(playerIndex).location.name, CardType.Room);
		Card murderCard = suspectDeck.get(storedPlayer);
		Card weaponCard = weaponDeck.get(storedWeapon);
		System.out.println("roomCard is: " +roomCard.name);
		System.out.println("murderCard is: " +murderCard.name);
		System.out.println("weaponCard is: " +weaponCard.name);
		
			
		Boolean foundProof = false;
		while(!foundProof){
			if(switchSecondaryPlayer()){
				if(secondaryPlayer.playerHand.contains(roomCard) || secondaryPlayer.playerHand.contains(weaponCard)
						|| secondaryPlayer.playerHand.contains(murderCard)){ 
					String message = "DISPROVE_REQUEST Choose a card to disprove: " + secondaryPlayer.playerHand;
					sendMessageToSecondary(message);	
					foundProof = true;
				}
				else{
					broadcast("MESSAGE " + secondaryPlayer.name + " doesn't have any proof. Moving on to next player");
					//switchSecondaryPlayer();
				}	
			}
			else{
				broadcast("MESSAGE all players have been asked and no one has disproved " + currentPlayer.name + "'s suggestion");
			}
		}
	}
	
	String sendAccusationWeaponRequest(){
		return "ACCUSATION_WEAPON_REQUEST" + currentPlayer.name;
	}
	
	Boolean validPlayer(String player){
		if(player == "Mrs. Peacock"
				|| player == "Professor Plum"
				|| player == "Miss Scarlet"
				|| player == "Mr. Green"
				|| player == "Mrs. White"
				|| player == "Colonel Mustard"){
			return true;
		}
		else{
			return false;
		}
	}
	
	Boolean validWeapon(String weapon){
		if (weapon == "Knife"
				|| weapon == "Rope"
				|| weapon == "Revolver"
				|| weapon == "Candlestick"
				|| weapon == "Lead Pipe"
				|| weapon == "Wrench"){
			return true;
		}
		else{
			return false;
		}
	}
	
	Boolean validRoom(String room){
		if(room == "Study"
			|| room == "Hall"
			|| room == "Lounge"
			|| room == "Library"
			|| room == "Billard Room"
			|| room == "Dining Room"
			|| room == "Conservatory"
			|| room == "Ballroom"
			|| room == "Kitchen"){
			return true;
		}
		else{
			return false;
		}
			
	}
	
	String switchCurrentPlayer(){
		boolean done = false;
		adder = 0; //reset secondary player adder
		if(playerIndex == 5){
			playerIndex = 0;
		}
		else{
			playerIndex++;
		}
		while(!done){
			if (!playerList.get(playerIndex).checkElimination()){
				currentPlayer = playerList.get(playerIndex);
				done = true;
			}
			else{
				playerIndex++;
			}
		}
		return "CURRENT_PLAYER " + currentPlayer.name;
	}
	
	Boolean switchSecondaryPlayer(){
		System.out.println("Switching Secondary Player");
		adder++;
		if (adder == connectedClients.size()){
			sendMessageToCurrent("NO_DISPROVES");
			return false;
		}
		else{
			System.out.println("adder = " + adder + ", playerIndex = " + playerIndex);
			int index = (adder + playerIndex) % (connectedClients.size()-1);
			secondaryPlayer = playerList.get(index);
			return true;
		}	
	}
	
	void broadcast(String message){//header should be part of the message already
		System.out.println("Broadcasting message using System.out: "+ message);
		for(int i = 0; i < connectedClients.size(); i++){
			connectedClients.get(i).output.println(message + "---broacasted message");
		}
	}
	
	void sendMessageToSecondary(String message){
		System.out.println("Sending secondary player " + secondaryPlayer.name + " message: " + message);
		connectedClients.get(adder+playerIndex % 5).output.println(message);
	}
	
	void sendMessageToCurrent(String message){
		System.out.println("Sending current player " + currentPlayer.name + " message: " + message);
		connectedClients.get(playerIndex).output.println(message);
	}
	
	void startNewTurn(){
		adder = 0;
		broadcast(switchCurrentPlayer());
		sendMessageToCurrent("ACTION_REQUEST");
	}
	
class ClientThreadHandler extends Thread{
	String threadName;
	Socket socket;
    BufferedReader input;
    PrintWriter output;
    String currentThreadName;
    
	public ClientThreadHandler(Socket socket, String name){
		this.socket = socket;
		this.threadName = name;

		try{
			input = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + name);
			output.println("MESSAGE Waiting for next player to connect");
		} catch (IOException e){
			System.out.println("Player " + name + " died" + e);
		}
		
	}
	
    /**
     * The run method of this thread.
     */
    public void run() {
    	try{
    		// The thread is only started after everyone connects.
    		output.println("MESSAGE All players connected");
    		
    		//initiate the game loop
    		if (threadName == "Mrs. Peacock"){
    			//send your move message
    			output.println("ACTION_REQUEST");
    		}else{
    			output.println("MESSAGE It is Mrs. Peacock's turn.  Waiting for her to take a action.");
    		}
    		
    		while (!victory){
    			String command = input.readLine();
    			String header = command.split(" ")[0];
    			
    			switch(header){
    				case "update":
    					output.println(boardDescription() + " ---From " + this.threadName);
    					output.println("ACTION_REQUEST");
    				break;
    				case "move":
    					System.out.println("entered move");
    					output.println(sendMoveRequest());
    					break;
    				case "MOVE_REPLY":
    					System.out.println("entered MOVE_REPLY");
    					String location = command.substring(11);
    					System.out.println(this.threadName + " selected room: " + location);
    					if(handleMoveReply(location)){//if move is successful
    						if(playerList.get(playerIndex).location.isRoom()){
    							//if the location moved to is a room then prompt suggestion
    							output.println("SUGGESTION? " + location);
    						}
    						else{	//player location is not a room so switch players
    							startNewTurn();
    						}
    					}
    					else{ //move was not successful
    						output.println("MESSAGE Error: move failed");
    						output.println(sendMoveRequest());  // send another move request
    					}
    					break;
    				case "card":
    					System.out.println("entered card");
    					output.println(handleCard(currentPlayer));
    					output.println("ACTION_REQUEST");
    					break;
    				case "suggest":
    					System.out.println("entered suggest");
    					if (currentPlayer.location.isRoom()){
    						output.println(sendSuggestionPlayerRequest());
    					}
    					else{
    						output.println("MESSAGE You can't suggest anything because you are not in a room please select another action");
    						output.println("ACTION_REQUEST");
    					}
    					break;
    				case "SUGGESTION_PLAYER_REPLY":
    					System.out.println("entered SUGGESTION_PLAYER_REPLY");
    					String player = command.substring(24);
    					storedPlayer = Integer.parseInt(player) -1;
    					output.println(sendSuggesitonWeaponRequest());
    					break;
    				case "SUGGESTION_WEAPON_REPLY":
    					System.out.println("entered SUGGESTION_WEAPON_REPLY");
    					String weapon = command.substring(24);
    					storedWeapon = Integer.parseInt(weapon) -1;
;    					broadcast(sendSuggestionMessage());
    					forceMovePlayer(storedPlayer,currentPlayer.location);
    					handleDisprove();
    					break;
    				case "DISPROVE_REPLY":
    					System.out.println("entered DISPROVE_REPLY");
    					String response = command.substring(15);
    					if (response == "1"){
    						broadcast("MESSAGE your suggestion has been disproven by " + secondaryPlayer.name);//include the card name
    						startNewTurn();
    					}
    					else{
    						startNewTurn();
    					}
    					break;
    				case "end_turn":
    					System.out.println("entered end_turn");
    						startNewTurn();
    					break;
    				case "accuse":
    					System.out.println("entered accuse");
    					output.println("ACCUSATION_PLAYER_REQUEST");
    					break;
    				case "ACCUSATION_PLAYER_REPLY":
    					System.out.println("entered ACCUSATION_PLAYER_REPLY");
    					String accusedPlayer = command.substring(24);
    					storedPlayer = Integer.parseInt(accusedPlayer);
    					output.println("ACCUSATION_WEAPON_REQUEST " + suspectDeck.get(storedPlayer).name);
    					break;
    				case "ACCUSATION_WEAPON_REPLY":
    					System.out.println("entered ACCUSATION_WEAPON_REPLY");
    					String accusedWeapon = command.substring(24);
    					storedWeapon = Integer.parseInt(accusedWeapon) -1;
    					output.println("ACCUSATION_ROOM_REQUEST " + suspectDeck.get(storedPlayer).name);
    				case "ACCUSATION_ROOM_REPLY":
    					String accusedRoom = command.substring(22);
    					storedRoom = Integer.parseInt(accusedRoom);
    					handleAccused();
    					break;
    				default:
    					System.out.println("Error, message did not fit any category: ");
    					System.out.println(command);
    			}
    		}
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
/**
 * I need to figure out how to transition between threads and executing the game loop.
 * 
 */
}
