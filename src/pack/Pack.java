package pack;

import java.io.Serializable;
import java.security.Key;

public class Pack implements Serializable {
	private static final long serialVersionUID = 5850955200059031071L;

	public String username;
	public Key publicKey;
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return this.username;
	}
	public Key getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(Key publicKey) {
		this.publicKey = publicKey;
	}
}
