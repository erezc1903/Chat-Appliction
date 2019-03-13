package server;

import java.io.*; 
import java.util.*; 
import java.net.*; 

//Server class 
public class Server  { 

	// Vector to store active clients 
	static Vector<ClientHandler> ar = new Vector<>(); 
	
	public static void main(String[] args) throws IOException  { 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(1234); // TODO write a config file to get port number and more
		   
		Socket s; 
		   
		// running infinite loop for getting 
		// client request 
		while (true)  { 
			// Accept the incoming request 
			s = ss.accept(); 
			
			System.out.println("New client request received : " + s); 
			  
			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			 
			String uname = dis.readUTF(); 
			
			System.out.println("Creating a new handler for this client..." + uname); // TODO implement a log system instead system.out...
			
			// Create a new handler object for handling this request. 
			ClientHandler mtch = new ClientHandler(s,uname, dis, dos); 
			
			// Create a new Thread with this object. 
			Thread t = new Thread(mtch); 
			   
			System.out.println("Adding this client to active client list"); 
			
			// add this client to active clients list 
			ar.add(mtch); 
			
			// start the thread. 
			t.start(); 
			
			// increment i for new client. 
			// i is used for naming only, and can be replaced 
			// by any naming scheme 
		} 
	} 
} 
