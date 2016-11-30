package clueless;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BoardGame {
	Random rng = new Random();
	Board currentBoard = new Board();
	Deck deck = new Deck();
	ArrayList<Card> suspectDeck = deck.createSuspectDeck();	//initiates creation of the three deck types
	ArrayList<Card> roomDeck = deck.createRoomDeck();
	ArrayList<Card> weaponDeck = deck.createWeaponDeck();
	ArrayList<Card> combinedDeck = new ArrayList<Card>(0);	//will be used for combined deck later
	
	/**
	*Method loads one card from each deck type into its own Card variable and stored as a winning hand
	*Each card is subsequently removed from its deck
	*/
	void loadWinningCards(){
		int index = rng.nextInt(5);
		Card murderer = suspectDeck.get(index);
		suspectDeck.remove(index);
		index = rng.nextInt(8);
		Card room = roomDeck.get(index);
		roomDeck.remove(index);
		index = rng.nextInt(5);
		Card weapon = weaponDeck.get(index);
		weaponDeck.remove(index);
		System.out.println("The Winning Cards are: " + murderer.name + " " + room.name + 
				" " + weapon.name);
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
}
