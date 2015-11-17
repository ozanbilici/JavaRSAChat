package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

import pack.Pack;

public class ServerThread extends Thread{
	private Socket socket;
	public String username;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	
	public void run() {
		try {
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			
			Pack pack = new Pack();
			
			pack = (Pack) oin.readObject();	
			this.username = pack.getUsername();
			
			Server.addHashMap(pack);
			
			while(true) {
				Message msg = (Message) oin.readObject();
				
				String username = msg.getToWhom();
				
				Pack pack1 = Server.getHashMap(username);
				oout.writeObject(pack1);
				
				if(pack1 == null) {
					Message newMsg = Server.getQueMap(this.username);
					
					if(newMsg != null) {
						Server.deleteQueMap(this.username);
					}
					
					oout.writeObject(newMsg);					
				} else {
					msg = (Message) oin.readObject();
					
					Server.addQueMap(msg);
					
					Message newMsg = Server.getQueMap(this.username);
					
					oout.writeObject(newMsg);
				}
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
