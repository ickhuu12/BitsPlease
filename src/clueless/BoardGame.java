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
//import edu.lmu.cs.networking.Game;
//import edu.lmu.cs.networking.Game.Player;

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
	int turns = 0;
	int playerIndex = 0;
	String storedRoom;
	String storedWeapon;
	String storedPlayer;
	String storedCard;
	int adder = 0; //secondary player will be currentIndex+adder;
	
	
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
	
	
	/**
	 * Method to handle the action selected by the player
	 * 
	 * @param choice string representing user input
	 * @param playerIndex index of the currently active player in playerList
	 */
	void handleAction(String choice, int playerIndex){
		switch(choice){
			case "card":	//prints out the cards if the hand
				System.out.println("Your cards are: " + playerList.get(playerIndex).playerHand.get(0).name 
						+ ", " + playerList.get(playerIndex).playerHand.get(1).name 
						+ ", " + playerList.get(playerIndex).playerHand.get(2).name);
				break;
			case "move":	//allows the player to move to adjacent room
				System.out.println("Where to move?");
				System.out.println(playerList.get(playerIndex).location.pathsToLeave);
				String decision = kb.nextLine();
				Space movingTo = playerList.get(playerIndex).location.validOption(decision);
				if (movingTo != null){
					if(movingTo.isRoom() || (!movingTo.isRoom() && movingTo.isEmpty())){
						currentBoard.movePlayer(playerList.get(playerIndex), movingTo);
					}else{
						System.out.println("Sorry, " + movingTo.name + " is already occupied by " + movingTo.occupiedBy.toString());
					}
				}
				break;
			case "suggest":	//player makes suggestion
				handleSuggestion(playerIndex);
				break;
			case "accuse": //player makes accusation
				handleAccused(playerIndex);
				break;
			default:
				break;
		}
	}


	
	/**
	 * Method to handle suggestion.  Starts by getting user input, creates a Card object for each of the three
	 * components of the suggestion, and then in the for loop gives the other players the opportunity to disprove
	 * the suggestion
	 * 
	 * @param playerIndex int for current player in playerList (NOT used for other players when needing to disprove suggestion)
	 */
	private void handleSuggestion(int playerIndex) {
		System.out.println("Your room is: " + playerList.get(playerIndex).location.name + ". Who committed the murder?");
		String murdererGuess = kb.nextLine();
		forceMovePlayer(murdererGuess, playerList.get(playerIndex).location);
		System.out.println("And with what weapon was the deed committed?");
		String weaponGuess = kb.nextLine();
		Card roomCard = new Card(playerList.get(playerIndex).location.name, CardType.Room);
		Card murderCard = new Card(murdererGuess, CardType.Player);
		Card weaponCard = new Card(weaponGuess, CardType.Weapon);
		System.out.println(playerList.get(playerIndex).name + " has suggested " + murdererGuess + " with the " + weaponGuess + 
				" in the " + playerList.get(playerIndex).location.name);
		int startPosition = playerIndex;
		for (int suggestOrder = 0; suggestOrder < 5; suggestOrder++){
			startPosition++;
			int newIndex = startPosition % 6;
			Player suggestProofPlayer = playerList.get(newIndex);
			if (playerList.get(newIndex).checkPerson()){
				if (suggestProofPlayer.playerHand.contains(roomCard) || suggestProofPlayer.playerHand.contains(weaponCard)
						|| suggestProofPlayer.playerHand.contains(murderCard)){ 
					System.out.println("Choose a card to disprove: " + suggestProofPlayer.playerHand);
					String proof = kb.nextLine();
					System.out.println(suggestProofPlayer.name + " has disproven " + playerList.get(playerIndex).name + "'s suggest of "
							+ proof);
					return;
				}
			}
		}
		System.out.println("No one disproved that suggestion.");
	}

	/**
	 * Method to handle accusation.  Starts by getting user input, and checks each of the three components against
	 * the predetermined victory cards.  Will eliminate player if they guess wrong, end game if they guess right
	 * 
	 * @param playerIndex int for current player in playerList
	 */
	private void handleAccused(int playerIndex) {
		System.out.println("Your room is: " + playerList.get(playerIndex).location.name + ". Who committed the murder?");
		String murdererGuess = kb.nextLine();
		forceMovePlayer(murdererGuess, playerList.get(playerIndex).location);
		System.out.println("And with what weapon was the deed committed?");
		String weaponGuess = kb.nextLine();
		if (playerList.get(playerIndex).location.name.equalsIgnoreCase(room.name) && murdererGuess.equalsIgnoreCase(murderer.name) &&
				weaponGuess.equalsIgnoreCase(weapon.name)){
			System.out.println(playerList.get(playerIndex).name + " has solved the murder!");
			System.out.println("It was " + murderer.name + " in the " + room.name + " with the " + weapon.name);
			victory = true;
		}else{
			System.out.println("You have guessed incorrectly.  You are eliminated");
			playerList.get(playerIndex).eliminate();
		}
	}
	
	/**
	 * Method to move a player who has been accused/suggestion of being the murderer to the room
	 * where accusation/suggestion is taking place
	 * 
	 * @param murderGuess String name of the person to be moved
	 * @param location Space location they will be moved to
	 */
	private void forceMovePlayer(String murderGuess, Space location){
		for (Player player : playerList){
			if (player.name.equals(murderGuess)){
				currentBoard.movePlayer(player, location);
				broadcast(murderGuess + " has been forced to move to " + location.toString());
			}
		}
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
		return "MESSAGE " + currentPlayer.name + "has suggested that " + storedPlayer + 
				"committed the murder in the " + currentPlayer.location.toString() + " with a " + storedWeapon;
	}
	
	void sendDisproveRequest(){
		String message = "DISPROVE_REQUEST " +  handleCard(secondaryPlayer);
		sendMessagetoSecondary(message);
	}
	
	String sendAccusationWeaponRequest(){
		return "ACCUSATION_WEAPON_REQUEST" + currentPlayer.name;
	}
	
	Boolean disproven(String card){
		storedCard = card;
		if((card == storedWeapon) || (card == storedRoom) || (card == storedPlayer)){
			return true;
		}
		else{
			return false;
		}
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
	
	String switchSecondaryPlayer(){
		adder++;
		int index = (adder + playerIndex) % 5;
		secondaryPlayer = playerList.get(index);
		return secondaryPlayer.name;	
	}
	
	void broadcast(String message){//header should be part of the message already
		System.out.println("Broadcasting message using System.out: "+ message);
		for(int i = 0; i < connectedClients.size(); i++){
			connectedClients.get(i).output.println(message + "---broacasted message");
		}
	}
	
	void sendMessagetoSecondary(String message){
		System.out.println("Sending secondary player " + secondaryPlayer.name + " message: " + message);
		connectedClients.get(adder+playerIndex % 5).output.println(message);
	}
	
	void sendMessageToCurrent(String message){
		System.out.println("Sending current player " + currentPlayer.name + " message: " + message);
		connectedClients.get(playerIndex).output.println(message);
	}
	
	void startNewTurn(){
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
    		
    		while (true){
    			String command = input.readLine();
    			String header = command.split(" ")[0];
    			
    			switch(header){
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
    						storedRoom = currentPlayer.location.toString();
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
    					if(validPlayer(player)){
    						//succeeded in selecting a player
    						storedPlayer = player;
    						output.println(sendSuggesitonWeaponRequest());
    					}
    					else{
    						output.println("MESSAGE Error: please suggestt a valid player");
    						output.println(sendSuggestionPlayerRequest());
    					}
    					break;
    				case "SUGGESTION_WEAPON_REPLY":
    					System.out.println("entered SUGGESTION_WEAPON_REPLY");
    					String weapon = command.substring(24);
    					if(validWeapon(weapon)){
    						storedWeapon = weapon;
    						broadcast(sendSuggestionMessage());
    						forceMovePlayer(storedPlayer,currentPlayer.location);
    						broadcast(switchSecondaryPlayer());
    						sendDisproveRequest();
    					}
    					else{
    						
    					}
    					break;
    				case "DISPROVE_REPLY":
    					System.out.println("entered DISPROVE_REPLY");
    					if(disproven(command.substring(14))){
    						sendMessageToCurrent("MESSAGE your suggestion has been disproven by " + secondaryPlayer.name + "with the card: " + storedCard);//include the card name
    						//broadcast to everyone that the suggestion has been disproven
    						broadcast("MESSAGE suggestion has been disproven by " + secondaryPlayer.name);
    						startNewTurn();
    					}
    					else{
    						output.println("MESSAGE suggestion not disproven, asking next player to disprove suggestion.");
    						broadcast(switchSecondaryPlayer());
    						sendDisproveRequest();
    					}
    					break;
    				case "skip":
    					System.out.println("entered skip");
    						startNewTurn();
    					break;
    				case "end_turn":
    					System.out.println("entered end_turn");
    						startNewTurn();
    					break;
    				case "accuse":
    					System.out.println("entered accuse");
    					//send ACUSTAIONT_PLAYER REQUEST ask who committed the murder and list player options
    						output.println("ACCUSATION_PLAYER_REQUEST");
    					break;
    				case "ACCUSATION_PLAYER_REPLY":
    					System.out.println("entered ACCUSATION_PLAYER_REPLY");
    					String accusedPlayer = command.substring(24);
    					if(validPlayer(accusedPlayer){
    						storedPlayer = accusedPlayer;
    						
    						
    					}
    					//verify valid player, store in memory
    					//ask what weapon and list weapons
    					break;
    				case "ACCUSATION_WEAPON_REPLY":
    					System.out.println("entered ACCUSATION_WEAPON_REPLY");
    					//read weapon, store varible,
    					//send ACUSATION_ROOM_REQUEST
    				case "ACCUSATION_ROOM_REPLY":
    					//read accused room
    					//compare to winning cards
    					//if correct broadcast WINNER currentPlayerName
    					//if false broadcast message name of player was wrong, the yahve been eliminated
    					//mark elimination in player list
    					//Start next turn
    					break;
    				
    			}
    			
    			/**if (command.startsWith("move")){
    				System.out.println("client selected move");
    				output.println(sendMoveRequest());
    			}
    			if (command.startsWith("MOVE_REPLY")){
    				System.out.println(this.threadName + " selected room : " + command);
    				String location = command.substring(11);
    				output.println(handleMoveReply(location));
    				
    			}
    			if (command.startsWith("suggest")){
    				sendPlayerSuggestionRequest();
    			}
    			
    			if (command.startsWith("WHO_IS_HERE")){
    				output.println(handleWHO_IS_HERE());
    			}*/

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
