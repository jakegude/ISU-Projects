package ChatBotPackage;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import ChatBotPackage.Client;

public class Server {
	
	private ArrayList<Client> Clients = new ArrayList<>();
	
	public static void main(String [] args) throws IOException {

		ServerSocket myserver = null;
		int clientnum = 0;
		
		try {
			myserver = new ServerSocket(4444);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4444");
			System.exit(-1);
		}
				
		while (true) {
			Socket newclient = null;
			
			try {
				System.out.println("Waiting for client " + (clientnum + 1) + " to connect");
				newclient = myserver.accept();
				
				System.out.println("Sever connected to client " + ++clientnum);
				
				ClientHandler t = new ClientHandler(newclient, clientnum);
				t.start();
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);	
			}
		}
	}
}

class ClientHandler extends Thread {
	
	Socket s;
	int num;
	
	ClientHandler(Socket s, int n){
		this.s = s;
		num = n;
	}

	public void run() {
		DataOutputStream serverout = null;
		DataInputStream serverin = null;
		
		try {
			serverin = new DataInputStream(s.getInputStream());
			serverout = new DataOutputStream(s.getOutputStream());
			
			serverout.writeUTF("What is your name?");
			String clientname = serverin.readUTF();
			
			serverout.writeUTF("Please enter access code:");
			String accesscode = "cs319spring2020";
			String code = serverin.readUTF();
			
			if (!code.equals(accesscode)) {
				while (!code.equals(accesscode)) {
					serverout.writeUTF("Please enter the correct access code:");
					code = serverin.readUTF();
				}
			}
			serverout.writeUTF("Connected");
			
			while (s.isConnected()) {
				String clientmessage = serverin.readUTF();
				serverout.writeUTF(clientname + " says: " + clientmessage);
				System.out.println("Message from " + clientname + ": " + clientmessage);
			}
			serverout.writeUTF(clientname + " has disconnected");
			System.out.println(clientname + " has disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}