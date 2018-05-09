package com.gkadmin.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordService {
	
	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String plaintext , String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}

}
