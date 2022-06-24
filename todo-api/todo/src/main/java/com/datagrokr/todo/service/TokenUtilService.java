package com.datagrokr.todo.service;

import java.util.Base64;

import com.datagrokr.todo.entity.User;

public class TokenUtilService {
	
	UserService userService = new UserService();
	
	public User getUserFromToken(String token) {
		
		byte[] decodedBytes = Base64.getDecoder().decode(token);
		String decodedString = new String(decodedBytes);
		String[] details = decodedString.split(":");
		String email = details[0];
		String password = details[1];
		User user= userService.isUserAuthenticated(email, password);
		if(user != null) {
			return user;
		} else return null;
		
	}

}
