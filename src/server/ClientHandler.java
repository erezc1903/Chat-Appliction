package server;
import java.io.*; 
import java.util.*;
import java.net.*; 

//ClientHandler class 
class ClientHandler implements Runnable  { 
	
	Scanner scn = new Scanner(System.in); 
	private String name; 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	Socket s; 
	boolean isloggedin; 
	   
	// constructor 
	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 
		this.isloggedin=true; 
	} 
	
	@Override
	public void run() { 
	
		String received; 
		while (!s.isClosed())  { 
			
			try { 
				// receive the string 
				received = dis.readUTF(); 
				   
				System.out.println(received); 
				   
				if(received.equals("logout")){ 
					this.isloggedin=false; 
					this.s.close(); 
					break; 
				} 
		   
				// break the string into message and recipient part 
				StringTokenizer st = new StringTokenizer(received, "#"); 
				String MsgToSend = null;
				String recipient = null;
				if(st.countTokens() > 0) {
					MsgToSend = st.nextToken(); 
					recipient = st.nextToken(); 
				}
				else {
					continue;
				}
				
				// search for the recipient in the connected devices list. 
				// ar is the vector storing client of active users 
				for (ClientHandler mc : Server.ar) { 
					// if the recipient is found, write on its 
					// output stream 
					if (mc.name.equals(recipient) && mc.isloggedin==true)  { 
						mc.dos.writeUTF(this.name+" : "+MsgToSend); 
						break; 
					} 
				} 
			} catch (SocketException se) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {      
				e.printStackTrace(); 
			}    
		} 
		try { 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 	           
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 