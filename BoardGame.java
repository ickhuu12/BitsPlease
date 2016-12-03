package clueless;

/**
*Class for the over-arching playing of the board game. Triggers initialization of 
*objects needed for the game (Players, Rooms, Cards, etc.), sets initial game play conditions, 
*and handles actions players undertake.
*
*@author Jimmy
*@version 0.4
*/	

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

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
		
		System.out.println("Location of Mrs. Peacock: " + peacock.location.name);
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
	
	void gamePlayLoop(){
		int turn = 0;
		while (!victory){
			int playerIndex = turn % 6;
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
	
	void handleAction(String choice, int playerIndex){
		switch(choice){
			case "card":
				System.out.println("Your cards are: " + playerList.get(playerIndex).playerHand.get(0).name 
						+ ", " + playerList.get(playerIndex).playerHand.get(1).name 
						+ ", " + playerList.get(playerIndex).playerHand.get(2).name);
				break;
			case "move":
				System.out.println("Where to move?");
				String decision = kb.nextLine();
				
				//currentBoard.movePlayer(playerList.get(PlayerIndex), );
				break;
			case "suggest":
				handleSuggestion(playerIndex);
				break;
			case "accuse":
				handleAccused(playerIndex);
				break;
			default:
				break;
		}
	}

	private void handleSuggestion(int playerIndex) {
		System.out.println("Your room is: " + playerList.get(playerIndex).location.name + ". Who committed the murder?");
		String murdererGuess = kb.nextLine();
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

	private void handleAccused(int playerIndex) {
		System.out.println("Your room is: " + playerList.get(playerIndex).location.name + ". Who committed the murder?");
		String murdererGuess = kb.nextLine();
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
}
