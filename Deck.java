package clueless;

/**
*Class for creation of each of the three decks, each deck composed of
*a singular Card type.
*
*@author Jimmy
*@version 1.0
*/	

import java.util.ArrayList;
import clueless.Card.CardType;

class Deck {
	ArrayList<Card> suspectDeck = new ArrayList<Card>();
	ArrayList<Card> roomDeck = new ArrayList<Card>();
	ArrayList<Card> weaponDeck = new ArrayList<Card>();
	
	/**
	*Method creates cards for Player-type Card deck
	*
	*@return suspectDeck ArrayList containing all the cards of the Player type
	*/
	ArrayList<Card> createSuspectDeck(){
		Card peacock = new Card("Mrs. Peacock", CardType.Player);
		Card plum = new Card("Professor Plum", CardType.Player);
		Card scarlet = new Card("Miss Scarlet", CardType.Player);
		Card green = new Card("Mr. Green", CardType.Player);
		Card white = new Card("Mrs. White", CardType.Player);
		Card mustard = new Card("Colonel Mustard", CardType.Player);
		suspectDeck.add(peacock);
		suspectDeck.add(plum);
		suspectDeck.add(scarlet);
		suspectDeck.add(green);
		suspectDeck.add(white);
		suspectDeck.add(mustard);
		return suspectDeck;
	}
	
	/**
	*Method creates cards for Room-type Card deck
	*
	*@return roomDeck ArrayList containing all the cards of the Room type
	*/
	ArrayList<Card> createRoomDeck(){
		Card study = new Card("Study", CardType.Room);
		Card hall = new Card("Hall", CardType.Room);
		Card lounge = new Card("Lounge", CardType.Room);
		Card library = new Card("Library", CardType.Room);
		Card billardRoom = new Card("Billard Room", CardType.Room);
		Card diningRoom = new Card("Dining Room", CardType.Room);
		Card conservatory = new Card("Conservatory", CardType.Room);
		Card ballroom = new Card("Ballroom", CardType.Room);
		Card kitchen = new Card("Kitchen", CardType.Room);
		roomDeck.add(study);
		roomDeck.add(hall);
		roomDeck.add(lounge);
		roomDeck.add(library);
		roomDeck.add(billardRoom);
		roomDeck.add(diningRoom);
		roomDeck.add(conservatory);
		roomDeck.add(ballroom);
		roomDeck.add(kitchen);
		return roomDeck;
	}

	/**
	*Method creates cards for Weapon-type Card deck
	*
	*@return weaponDeck ArrayList containing all the cards of the Weapon type
	*/
	ArrayList<Card> createWeaponDeck(){
		Card knife = new Card("Knife", CardType.Weapon);
		Card rope = new Card("Rope", CardType.Weapon);
		Card revolver = new Card("Revolver", CardType.Weapon);
		Card candlestick = new Card("Candlestick", CardType.Weapon);
		Card leadPipe = new Card("Lead Pipe", CardType.Weapon);
		Card wrench = new Card("Wrench", CardType.Weapon);
		weaponDeck.add(knife);
		weaponDeck.add(rope);
		weaponDeck.add(revolver);
		weaponDeck.add(candlestick);
		weaponDeck.add(leadPipe);
		weaponDeck.add(wrench);
		return weaponDeck;
	}
}
