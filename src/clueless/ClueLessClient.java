package clueless;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClueLessClient{
    private static int PORT = 8902;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    public ClueLessClient(String serverAddress) throws Exception{
    	// Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void play() throws Exception{
    	String response;
    	try{
    		response = in.readLine();
    		if (response.startsWith("WELCOME")){
    			String name = response.substring(8);
    			System.out.println("ClueLess " + name);
    		}
    		while (true){
    			response = in.readLine();
    			if(response.startsWith("MESSAGE")){
    				System.out.println(response);
    			}
    		
    		}
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