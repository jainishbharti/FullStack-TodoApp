package com.datagrokr.todo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.User;

@TestInstance(Lifecycle.PER_CLASS)
class TokenUtilServiceTest {
	
	TokenUtilService underTest;
	UserService userService;

	@BeforeEach
	void setUp() throws Exception {
		userService = new UserService();
		underTest = new TokenUtilService();
	}

	@AfterEach
	void tearDown() throws Exception {
		userService.closeConn();
	}

	@Test
	void testGetUserFromtoken() {
		String token = "am9obm55QGdtYWlsLmNvbTpoZXhhZ29u";
		User user = underTest.getUserFromToken(token);
		if(user != null) {
			assertNotNull(user);
			assertEquals(user, userService.getById(user.getUserId()));
		}
		
		else assertNull(user);
	}

}
