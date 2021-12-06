package org.jmmj.blockchain.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

// https://www.baeldung.com/java-blockchain
// https://betterprogramming.pub/the-blockchain-explained-with-a-java-implementation-27b74d478223

public class Block {
	private Logger logger = Logger.getLogger(Block.class.getName());
	
	private String hash;
	private String previousHash;
	private String data;
	private long timeStamp;
	private int nonce;

	public Block(String data, String previousHash, long timeStamp) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = timeStamp;
		this.hash = calculateBlockHash();
	}

	public String calculateBlockHash() {
		String dataToHash = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data;
		MessageDigest digest = null;
		byte[] bytes = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			bytes = digest.digest(dataToHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		StringBuffer buffer = new StringBuffer();
		for(byte b : bytes) {
			buffer.append(String.format("%02x", b));
		}
		return buffer.toString();
	}
	
	public String mineBlock(int prefix) {
		String prefixString = new String(new char[prefix]).replace('\0', '0');
		while (!hash.substring(0, prefix).equals(prefixString)) {
			nonce++;
			hash = calculateBlockHash();
		}
		return hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

}
