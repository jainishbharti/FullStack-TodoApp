package com.datagrokr.todo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.datagrokr.todo.entity.User;

class UserServiceTest {
	
	UserService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new UserService();
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testUserService() {
		
	}

	@Test
	void testFindAll() throws Exception {
		System.out.println("Testing testFindAll");
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		underTest.save(user);
		List<User> returnedUsers = underTest.findAll();
		assertNotNull(returnedUsers);
	}

	@Test
	void testSave() throws Exception {
		System.out.println("Testing testSave");
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		User savedUser = underTest.save(user);	
		assertEquals(savedUser, underTest.getById(savedUser.getUserId()));
	}

	@Test
	void testGetById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		User savedUser = underTest.save(user);	
		assertNotNull(underTest.getById(savedUser.getUserId()));
	}

	@Test
	void testUpdateById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		User savedUser = underTest.save(user);	
		User userUpdate = new User("Updated User", "test2@gmail.com", "Testagon3");	
		User updatedUser = underTest.updateById(userUpdate, savedUser.getUserId());
		assertEquals(userUpdate.getEmail(), updatedUser.getEmail());
	}

	@Test
	void testDeleteById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		User savedUser = underTest.save(user);	
		underTest.deleteById(savedUser.getUserId());
		assertNotNull(underTest.getById(1));
	}

	@Test
	void testIsUserAuthenticated() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");	
		User savedUser = underTest.save(user);
		User returnedUser = underTest.isUserAuthenticated(savedUser.getEmail(), savedUser.getPassword());
		assertEquals(savedUser.getEmail(), returnedUser.getEmail());
	}

}
