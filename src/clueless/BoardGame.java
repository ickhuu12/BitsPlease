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
	
	Method for the overarching gameplay loop.  Does not end until victory condition is met
	
	void gamePlayLoop(){
		int turn = 0;
		while (!victory){
			int playerIndex = turn % 6;
			
			//if statement checks if a player is human and not eliminated, skips turn if either condition is false
			if (playerList.get(playerIndex).checkPerson() && !playerList.get(playerIndex).checkElimination()){
				System.out.println(playerList.get(playerIndex).name + "'s Turn");
				System.out.println("Which action would you like to take?");
				String choice = kb.nextLine().toLowerCase();
				handleAction(choice, playerIndex);
				if (choice.equals("exit")){
					victory = true;
				}
			}turn++;
		}
	}	
*/	
	

	
	
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
	
	String handleMoveReply(String location){
		Space movingTo = currentPlayer.location.validOption(location);
		String msg = null;
		if (movingTo != null){
			if(movingTo.isRoom() || (!movingTo.isRoom() && movingTo.isEmpty())){
				System.out.println("Space unoccupied, Moving " +currentPlayer.name +" to " + movingTo.name);
				msg = "MESSAGE Space unoccupied, Moving to " + movingTo.name;
				broadcast(msg);
				currentBoard.movePlayer(currentPlayer, movingTo);
			}else{
				msg = sendMoveRequest();
			}
		}
		return msg;
	}
	
	String switchCurrentPlayer(){
		boolean done = false;
		while(!done){
			int playerIndex = turns % 6;
			if (playerList.get(playerIndex).checkPerson() && !playerList.get(playerIndex).checkElimination()){
				currentPlayer = playerList.get(playerIndex);
				done = true;
			}
			turns++;
		}
		return "CURRENT_PLAYER " + currentPlayer.name;
	}
	
	void sendPlayerSuggestionRequest(){
		
	}
	
	void broadcast(String message){
		System.out.println("Broadcasting message using System.out: "+ message);
		for(int i = 0; i < connectedClients.size(); i++){
			connectedClients.get(i).output.println(message + "---broacasted message");
		}
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
    			output.println("ACTION_REQUEST Mrs. Peacock, which action would you like to take? card, move, suggest, or accuse ");
    		}else{
    			output.println("MESSAGE It is Mrs. Peacock's turn.  Waiting for her to take a action.");
    		}
    		
    		while (true){
    			String command = input.readLine();
    			
    			if (command.startsWith("move")){
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
