package it.unipv.ingsw.c20.scores;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Prova {
	// Classe prova per la criptazione dei dati, se qualcuno riesce ad implementarla che io � un giorno che vedo solo errori hahahaha
	
	public static void main(String[] args){
		try 
	    {
	        String text = "Hello World";
	        String key = "Bar12345Bar12345"; // 128 bit key
	        // Create key and cipher
	        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        // encrypt the text
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	        byte[] encrypted = cipher.doFinal(text.getBytes());
	        System.err.println(new String(encrypted));
	        // decrypt the text
	        cipher.init(Cipher.DECRYPT_MODE, aesKey);
	        String decrypted = new String(cipher.doFinal(encrypted));
	        System.err.println(decrypted);
	    }
	    catch(Exception e) 
	    {
	        e.printStackTrace();
	    }
	}
    
}
