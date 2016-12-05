# BitsPlease
Clue-less

@Author BitsPlease

Welcome to the game of Clueless! Clueless is a game developed by four students at Johns Hopkins University as part of a semester long project.
The game is very similar to the popular board game "Clue" but with some distinct differences. Please fully read through this README before attempting to play.


Clueless is a game where (2-6) players try to figure out the three main facts of a murder: the murderer, the location of the murder, and the murder weapon. This is done by having each player move through the rooms and gather evidence.

	Starting the Game
		To start the game, ensure that you have moved all of the associated files into a workspace directory. The game can be 		launched using the command line prompt
		
		# java Tester.java

		Please note the # indicates you must have administrative permissions for the game to function properly. The game has been 		tested to run within Eclipse and NetBeans.

	Game Items
			Six Suspect Tokens
			Six Murder Weapons
			21 Cards
			Secret Envelope

	Game Setup
		The game automatically takes all cards and sorts by type and shuffles without the users being able to see them.
		The game server takes one suspect card, one weapon card, and one room card and puts them inside of a the secret envelope. 
		The rest of the cards are shuffled together and dealt clockwise to the players until all cards are dealt. The murder 		weapons 	are placed in rooms randomly, no more than one per room. The suspect tokens are placed on assigned starting 		squares. 	Each 	player than takes a suspect token that has not already been chose by another player. The game now begins.

	Player Movement
		* Rooms are laid out in a 3x3 grid with a hallway separating each pair of adjacent rooms
		* One person is allowed to occupy a hallway at a time
		* You have the following moving options on your turn if you are in a room:
			Option 1: Move from the room to a hallway (if not occupied)
			Option 2: Move diagonally in a secret passageway to a diagonally opposite room (if there is one)
			Option 3: If you have been summoned to a room by a player making a suggestion, you may stay in the room and make a 			suggestion or move as described in option 1 and 	2.
		* You have the following moving options if you are in a hallway:
		* Move into one of the two rooms accessible from that hallway and make a suggestion.
		* If all exits are blocked (all hallways occupied) and you are not in a corner room with a secret passageway, and you 			aren't summoned to a room by another player you 	lose your turn unless you are making an accusation
		* First move must be to the hallway that is adjacent to your home square
		* Inactive players are moved into a room by someone making a suggestion
		* A player may only make a suggestion in the room they are currently in. Suspects are moved to the suggestion room
		* A player may make an accusation at any time during their turn

	Winning the game
		If a player thinks they have solved the case by eliminating all the false possibilities and have not had any suggestions 		disproved this turn, the player can end the turn by making an accusation. 
		The player uses a button to announce that they are making an accusation, and types in the final guess of the murderer, the 		murder weapon, and the murder location. 
		Once this is done, the player may see the contents of the secret envelope. 
		If the player is correct, all other players are shown the contents of the envelope. The player is now the winner. However, 		if 	the player is wrong then they are eliminated from the game. The player must stay connected to the game to disprove the 		suggestions of others.
	
	Troubleshooting
		The game is set up to run as part of a local area network. Please sure you are properly connected to your local area 		network before attempting to connect to other players. Further troubleshooting questions can be addressed to 		bitsplease@gmail.com
	
	Licensing
		 The game of Cluless is an interactive online game
	    Copyright (C) 2016  BitsPlease
	
	    This program is free software: you can redistribute it and/or modify
	    it under the terms of the GNU General Public License as published by
	    the Free Software Foundation, either version 3 of the License, or
	    (at your option) any later version.
	
	    This program is distributed in the hope that it will be useful,
	    but WITHOUT ANY WARRANTY; without even the implied warranty of
	    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	    GNU General Public License for more details.
	
	    You should have received a copy of the GNU General Public License
	    along with this program.  If not, see <http://www.gnu.org/licenses/>.
		