package message;

import java.io.Serializable;

import javax.crypto.SealedObject;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String toWhom;
	public String fromWho;
	public SealedObject sealed;

	
	public void setToWhom(String username) {
		this.toWhom = username;
	}
	
	public String getToWhom() {
		return this.toWhom;
	}
	
	public void setFromWho(String username) {
		this.fromWho = username;
	}
	
	public String getFromWho() {
		return this.fromWho;
	}	
	
	public void setSealedData(SealedObject sealed) {
		this.sealed = sealed;
	}
	
	public SealedObject getSealedData() {
		return this.sealed;
	}
}
