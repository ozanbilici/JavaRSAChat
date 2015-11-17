package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import message.Message;

import pack.Pack;
import rsa.RSAEncDec;


public class Client {

	private static Key publicKey;
	private static Key privateKey;

	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException {
		if(args.length < 2) {
			System.out.println("Please enter IP Adress and username as argumants");
			System.exit(0);
		}
		
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(512);
			KeyPair kp = kpg.genKeyPair();
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();	
			
			
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Socket socket = null;
		try {
			socket = new Socket(args[0], 9881);
			
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			
			Pack pack = new Pack();
			String myusername = args[1];
			pack.setUsername(myusername);
			pack.setPublicKey(publicKey);
			
			oout.writeObject(pack);
			
			while(true) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("-------------------[+]MESSAGE SENDING------------------------");
				System.out.println("Who do you want to send message?");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				
				String username = in.readLine();
				
				Message msg = new Message();
				msg.setToWhom(username);
				
				// ask if there that user
				oout.writeObject(msg);
				
				// get the user packet
				Pack newPack = (Pack) oin.readObject();
				
				// if there is no username like that
				if(newPack == null) {
					System.out.println("There is no username in the server");
					System.out.println("-------------------[-]MESSAGE SENDING------------------------");
					System.out.println("-------------------------------------------------------------");
					
					// check if user have message 
					Message newMsg = (Message) oin.readObject();
					System.out.println("-------------------------------------------------------------");
					System.out.println("-------------------[+]READING MESSAGE-------------------------");	
					if(newMsg != null) {
						System.out.println(newMsg.getFromWho());
						String newMessage = RSAEncDec.rsaDecrypt(newMsg.getSealedData(), privateKey);
						System.out.println(newMessage);
					} else {
						System.out.println("You do not have new message");
					}	
					System.out.println("-------------------[-]READING MESSAGE-------------------------");	
					System.out.println("-------------------------------------------------------------");
				} else {
					System.out.println("What message do you want to send?");
					
					String message = in.readLine();
					System.out.println("-------------------[-]MESSAGE SENDING------------------------");
					System.out.println("-------------------------------------------------------------");					
					msg = new Message();
					msg.setToWhom(username);
					// encrypt the message
					msg.setSealedData(RSAEncDec.rsaEncrypt(message, newPack.publicKey));
					msg.setFromWho(myusername);
					oout.writeObject(msg);
					
					// check if user have message 
					Message newMsg = (Message) oin.readObject();
					System.out.println("-------------------------------------------------------------");
					System.out.println("-------------------[+]READING MESSAGE-------------------------");	
					if(newMsg != null) {
						System.out.println(newMsg.getFromWho());
						String newMessage = RSAEncDec.rsaDecrypt(newMsg.getSealedData(), privateKey);
						System.out.println(newMessage);
					} else {
						System.out.println("You do not have new message");
					}
					System.out.println("-------------------[-]READING MESSAGE-------------------------");
					System.out.println("-------------------------------------------------------------");					
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
