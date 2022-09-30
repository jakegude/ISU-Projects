package ChatBotPackage;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	Socket serverSocket;
	
	public Client() throws IOException {
		try {
			serverSocket = new Socket("localhost", 4444);			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
		DataInputStream in = new DataInputStream(serverSocket.getInputStream());
		Scanner sc = new Scanner(System.in);
		
		Thread toserver = new Thread(new Runnable() {
			public void run() {
				String write = "";
				while (true) {
					try {
						write = sc.nextLine();
						out.writeUTF(write);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread fromserver = new Thread(new Runnable() {
			public void run() {
				while (true) {
					String write = "";
					while (true) {
						try {
							write = in.readUTF();
							System.out.println(write);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		fromserver.start();
		toserver.start();
		
	}
	
	public static void main(String [] args) throws UnknownHostException, IOException {
		Client c = new Client();
	}
	
}