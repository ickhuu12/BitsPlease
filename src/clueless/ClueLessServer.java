package clueless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ClueLessServer {
    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
    	// an ArrayList to keep the list of the client threads
    	ArrayList<ClientThreadHandler> clientThreadList = new ArrayList<ClientThreadHandler>();
        ServerSocket listener = new ServerSocket(8902);
        System.out.println("ClueLess Server is Running");
        try {
            while (true) {
                BoardGame game = new BoardGame();
                game.createPlayerList();
                game.loadWinningCards();
                game.combineDecks();
                game.dealCards();
               
                ClientThreadHandler player1 = new ClientThreadHandler(listener.accept(), "Mrs. Peacock");
                ClientThreadHandler player2 = new ClientThreadHandler(listener.accept(), "Professor Plum");
                ClientThreadHandler player3 = new ClientThreadHandler(listener.accept(), "Mr. Green");
                ClientThreadHandler player4 = new ClientThreadHandler(listener.accept(), "Miss Scarlet");
                ClientThreadHandler player5 = new ClientThreadHandler(listener.accept(), "Mrs. White");
                ClientThreadHandler player6 = new ClientThreadHandler(listener.accept(), "Colonel Mustard");
                clientThreadList.add(player1);
                clientThreadList.add(player2);
                clientThreadList.add(player3);
                clientThreadList.add(player4);
                clientThreadList.add(player5);
                clientThreadList.add(player6);
                player1.start();
                player2.start();
                player3.start();
                player4.start();
                player5.start();
                player6.start();
            }
        } finally {
            listener.close();
        }
    }
}

class ClientThreadHandler extends Thread{
	String name;
	//ClientThreadHandler nextPlayer;
	Socket socket;
    BufferedReader input;
    PrintWriter output;
    
	public ClientThreadHandler(Socket socket, String name){
		this.socket = socket;
		this.name = name;
		ClientThreadHandler nextPlayer;
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
     * Accepts notification of who the opponent is.
     */
//    public void setOpponent(ClientThreadHandler nextPlayer) {
//        this.nextPlayer = nextPlayer;
//    }
	
    /**
     * The run method of this thread.
     */
    public void run() {
    	try{
    		// The thread is only started after everyone connects.
    		output.println("MESSAGE All players connected");
    		//tell the first player it's thier turn
    		//repeadedly get commands from the client and process them
    		//while(true){
        //} catch (IOException e) {
            //System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
/**
 * I need to figure out how to transition between threads and executing the game loop.
 * 
 */
