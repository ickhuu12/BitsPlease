package clueless;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.ImageIcon;

public class ClueLessClient{
    private static int PORT = 8902;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    boolean endGame;
    
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
    	Scanner scan = new Scanner(System.in);
    	try{
    		response = in.readLine();
    		System.out.println(response);
    		if (response.startsWith("WELCOME")){
    			String name = response.substring(8);
    			System.out.println("ClueLess " + name);
    		}
    		while (!endGame){
    			response = in.readLine();
    			if(response.startsWith("MESSAGE")){
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
    			}
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