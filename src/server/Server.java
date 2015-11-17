package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import message.Message;

import pack.Pack;

public class Server {
	public static HashMap<String, Pack> currentUsers = new HashMap<String, Pack>();
	public static HashMap<String, Message> messageQue = new HashMap<String, Message>();
	
	public static void main(String args[]) {
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(9881);
		
		
			while(true) {
				new ServerThread(ssocket.accept()).start();
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ssocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void addHashMap(Pack pack) {
		currentUsers.put(pack.getUsername(), pack);
	}
	
	public static Pack getHashMap(String username) {
		return currentUsers.get(username);
	}
	
	public static void addQueMap(Message msg) {
		 messageQue.put(msg.getToWhom(), msg);
	}
	
	public static Message getQueMap(String username) {
		return messageQue.get(username);
	}	
	
	public static void deleteQueMap(String username) {
		messageQue.remove(username);
	}
	
}
