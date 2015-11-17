package rsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class RSAEncDec {

	public static SealedObject rsaEncrypt(String temp, Key publicKey) throws IllegalBlockSizeException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		String msg = new String(temp);
		
		// Get an instance of the Cipher for RSA encryption/decryption
		Cipher c = Cipher.getInstance("RSA");
		// Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
		c.init(Cipher.ENCRYPT_MODE, publicKey); 
		
		// Encrypt that message using a new SealedObject and the Cipher we created before
		SealedObject myEncyptedMessage = new SealedObject( msg, c);
		
		return myEncyptedMessage;
	}
	
	public static String rsaDecrypt(SealedObject myEncyptedMessage, Key privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, IOException {
		
		// Get an instance of the Cipher for RSA encryption/decryption
		Cipher dec = Cipher.getInstance("RSA");
		// Initiate the Cipher, telling it that it is going to Decrypt, giving it the private key
		dec.init(Cipher.DECRYPT_MODE, privateKey);		
		
		// Tell the SealedObject we created before to decrypt the data and return it
		String message = (String) myEncyptedMessage.getObject(dec);
				
		return message;
		  
	}	

}
