package clueless;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.ImageIcon;

import com.sun.jmx.snmp.SnmpUnknownSubSystemException;

public class ClueLessClient{
    private static int PORT = 8902;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    boolean endGame;
    String name;
    String currentPlayer;
    String secondaryPlayer;
    
    public ClueLessClient(String serverAddress) throws Exception{
    	// Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        endGame = false;
    }
    
    public void play() throws Exception{
    	String response;
    	String msg;
    	String header;
    	String body;
    	Scanner scan = new Scanner(System.in);
    	
    	try{
    		//response = in.readLine();
    		//header = response.split(" ")[0];
    		//System.out.println(response);
    		//if (header == "WELCOME"){
    			
    		//}
    		while (!endGame){
    			response = in.readLine();
    			header = response.split(" ")[0];
    			switch(header){
    				case "MESSAGE":
    					System.out.println(response.substring(8));
    				
    				case "WELCOME":
    					name = response.substring(8);
    	    			System.out.println("ClueLess " + name);
    	    			break;
    				case "ACTION_REQUEST":
    					System.out.println("entered ACTION_REQUEST");
    					System.out.println(name + ", which action would you like to take? choose one: card, move, suggest, or accuse");
    					msg = scan.nextLine();
        				out.println(msg);
    					break;
    				case "MOVE_REQUEST":
    					System.out.println("entered MOVE_REQUEST");
    					System.out.println(response.substring(13));
    					msg = scan.nextLine();
    					out.println("MOVE_REPLY " + msg);
    					break;
    				case "SUGGESTION?":
    					System.out.println("entered SUGGESTION?");
    					System.out.println("You entered the " + response.substring(11) +"would you like to make a suggestion [suggest] or end turn [end_turn]?");
    					msg = scan.nextLine();
    					out.println(msg);
    				case "CURRENT_PLAYER":
    					System.out.println("entered CURRENT_PLAYER");
    					currentPlayer = response.substring(15);
    					System.out.println("current player was updated to " + currentPlayer);
    					break;
    				case "SUGGESTION_PLAYER_REQUEST":
    					System.out.println("entered SUGGESTION_PLAYER_REQUEST");
    					System.out.println("You are located in the room: " + response.substring(26) + "Who do you suggesst committed the murder in this room?");
    					System.out.println("Select a player : Mrs. Peacock, Professor Plum, Miss Scarlet, Mr. Green, Mrs. White, Colonel Mustard");
    					msg = scan.nextLine();
    					out.println("SUGGESTION_PLAYER_REPLY " + msg);
    					break;
    				case "SUGGESTION_WEAPON_REQUEST":
    					System.out.println("SUGGESTION_WEAPON_REQUEST");
    					System.out.println("With what weapon did " + response.substring(25) + " commit the murder?");
    					System.out.println("Pick one: knife, rope, revolver, candlestick, lead pipe, wrench");
    					msg = scan.nextLine();
    					out.println("SUGGESTION_WEAPON_REPLY " + msg);
    				case "DISPROVE_REQUEST":
    					System.out.println("entered DISPROVE_REQUEST");
    					System.out.println(response.substring(17));
    					System.out.println("Select a card to disprove or call skip");
    					msg = scan.nextLine();
    					if (msg == "skip"){
    						out.println("skip");
    					}
    					else{
    						out.println("DISPROVE_REPLY" + msg);
    					}
    					break;
    				case "NO_DISPROVES":
    					System.out.println("entered NO_DISPROVES");
    					System.out.println("Nobody has diproved your suggestion.  Would you like to picke one: end_turn or accuse?");
    					msg = scan.nextLine();
    					out.println(msg);
    					break;
    				case "ACCUSATION_PLAYER_REQUEST":
    					System.out.println("entered ACCUSATION_PLAYER_REQUEST");
    					System.out.println("Select a player accuse : [Mrs. Peacock] [Professor Plum] [Miss Scarlet] [Mr. Green] [Mrs. White] [Colonel Mustard]");
    					msg = scan.nextLine();
    					out.println("ACUSATION_PLAYER_REPLY "+ msg);
    					break;
    				case "ACCUSATION_WEAPON_REQUEST":
    					System.out.println("entered ACCUSATION_WEAPON_REQUEST");
    					System.out.println("What what weapon are you acusing " + response.substring(25) + "of using?");
    					System.out.println("Pick one: knife, rope, revolver, candlestick, lead pipe, wrench");
    					msg = scan.nextLine();
    					out.println("ACCUSATION_WEAPON_REPLY " + msg);
    				case "ACCUSATION_ROOM_REQUEST":
    					System.out.println("entered ACCUSATION_ROOM_REQUEST");
    					System.out.println("In what room did " + response.substring(23) + " commit the murder?");
    					System.out.println("Pick one: sudy, hall, lounge, library, billardRoom, diningRoom, conservatory, ballroom, kitchen");
    					msg = scan.nextLine();
    					out.println("ACCUSATION_ROOM_REPLY " + msg);
    				case "WINNER":
    					//END THE GAME	
    				default:
    					System.out.println("Error, Message did not fit any category");
    					break;
    			}
    				
    				
    			/**if(response.startsWith("MESSAGE")){
    				System.out.println(response);
    			}
    			else if(response.startsWith("ACTION_REQUEST")){
    				System.out.println(response);
    				String msg = scan.nextLine();
    				out.println(msg);
    			}
    			else if(response.startsWith("MOVE_REQUEST")){
    				System.out.println(response);
    				String msg = scan.nextLine();
    				out.println("MOVE_REPLY " + msg);
    				
    			}else{
    				out.println(scan.nextLine());
    			}*/
    		}
    		out.println("QUIT");
    	}
    	finally{
    		socket.close();
    	}
    }
    
    public static void main(String[] args) throws Exception{
    	while (true){
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            ClueLessClient client = new ClueLessClient(serverAddress);
            client.play();
    	}
    }
}