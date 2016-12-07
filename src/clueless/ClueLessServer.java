package clueless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClueLessServer {

    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8902);
        System.out.println("ClueLess Server is Running");
        try {
            while (true) {
                BoardGame game = new BoardGame();
                ClientThreadHandler player1 = new ClientThreadHandler(listener.accept(), "player1");
                ClientThreadHandler player2 = new ClientThreadHandler(listener.accept(), "player2");
                ClientThreadHandler player3 = new ClientThreadHandler(listener.accept(), "player3");
                ClientThreadHandler player4 = new ClientThreadHandler(listener.accept(), "player4");
                ClientThreadHandler player5 = new ClientThreadHandler(listener.accept(), "player5");
                ClientThreadHandler player6 = new ClientThreadHandler(listener.accept(), "player6");
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
	Socket socket;
    BufferedReader input;
    PrintWriter output;
    
	public ClientThreadHandler(Socket socket, String name){
		this.socket = socket;
		this.name = name;
		try{
			input = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + name);
			output.println("MESSAGE Waiting for opponent to connect");
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
