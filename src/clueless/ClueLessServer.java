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
    	//ArrayList<ClientThreadHandler> clientThreadList = new ArrayList<ClientThreadHandler>();
        ServerSocket listener = new ServerSocket(8902);
        System.out.println("ClueLess Server is Running");
        try {
            while (true) {
                BoardGame game = new BoardGame();
                game.createPlayerList();
                game.loadWinningCards();
                game.combineDecks();
                game.dealCards();
                BoardGame.ClientThreadHandler player1 = game.new ClientThreadHandler(listener.accept(), "Mrs. Peacock");
                BoardGame.ClientThreadHandler player2 = game.new ClientThreadHandler(listener.accept(), "Professor Plum");
                BoardGame.ClientThreadHandler player3 = game.new ClientThreadHandler(listener.accept(), "Mr. Green");
                BoardGame.ClientThreadHandler player4 = game.new ClientThreadHandler(listener.accept(), "Miss Scarlet");
                BoardGame.ClientThreadHandler player5 = game.new ClientThreadHandler(listener.accept(), "Mrs. White");
                BoardGame.ClientThreadHandler player6 = game.new ClientThreadHandler(listener.accept(), "Colonel Mustard");
                BoardGame.connectedClients.add(player1);
                BoardGame.connectedClients.add(player2);
                BoardGame.connectedClients.add(player3);
                BoardGame.connectedClients.add(player4);
                BoardGame.connectedClients.add(player5);
                BoardGame.connectedClients.add(player6);
                BoardGame.currentThread = player1;
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


