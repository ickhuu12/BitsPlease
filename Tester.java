package clueless;

import java.util.*;

public class Tester {

	public static void main(String[] args) {
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		Player player5 = new Player();
		Player player6 = new Player();
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		playerList.add(player5);
		playerList.add(player6);
		
		Board board = new Board();
		board.loadInitialBoard(playerList);
		
		System.out.println("Room 1 is a room: " + board.room1.isRoom());
		System.out.println("Hallway 1 is a room: " + board.hallway1.isRoom());
		
		System.out.println("After iniatlization, Room 1 is empty: " + board.room1.isEmpty());
		board.movePlayer(player1, board.room2);
		System.out.println("After Player 1 moves out of Room 1, Room 1 is empty: " + board.room1.isEmpty());

	}

}
