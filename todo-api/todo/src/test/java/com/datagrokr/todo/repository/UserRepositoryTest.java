package com.datagrokr.todo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.User;

@TestInstance(Lifecycle.PER_CLASS)
class UserRepositoryTest {

	  UserRepository underTest;

	    public UserRepositoryTest() {}
	    
	    @BeforeEach
	    public void setUp(){
	        underTest = new UserRepository();
	    }
	    
	    @AfterEach
	    public void tearDown(){
	        underTest.close();
	    }

	@Test
	void testAddUser() throws Exception {
		System.out.println("Testing addUser");
		User user = new User("Test User", "test@gmail.com", "Testagon");		
		User returnedUser = underTest.addUser(user);
		assertEquals(user, returnedUser);
	}
	
	@Test
	void testAddUser2() throws Exception {
		System.out.println("Testing addUser with same email");
		User user = new User("Test User", "test@gmail.com", "Testagon");
		underTest.addUser(user);
		assertThrows(Exception.class, () -> underTest.addUser(user), "If user found with same email, it should throw");
	}
	
	
	@Test
	void testGetUsersList() throws Exception{
		System.out.println("Testing getUsersList");
		User addedUser = underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		assert(underTest.getUsersList()).contains(addedUser);
	}
	
	@Test
	void testUserFromEmailExists() throws Exception {
		System.out.println("Testing testUserFromEmailExists");
		underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		String email = "test2@gmail.com";
		assertTrue(underTest.userFromEmailExists(email));
	}
	
	@Test
	void testGetById() throws Exception {
		System.out.println("Testing getById");
		User addedUser = underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		Integer id = 1;
		assertEquals(addedUser.getUserId(), id);
	}
	
	@Test
	void updateUser() throws Exception {
		System.out.println("Testing updateUser");
		User user = underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		User updatedUserDetails = new User("Tanay", "tanay@gmail.com", "Pentagon");
		User updatedUser = underTest.updateUser(updatedUserDetails, user.getUserId());
		assertEquals(updatedUserDetails.getEmail(), updatedUser.getEmail());
	}
	
	@Test
	@Disabled
	void testDeleteById() throws Exception {
		underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		underTest.deleteById(1);
		assertEquals(null, underTest.getById(1));
	}

	@Test
	void testAuthentication() throws Exception {
		underTest.addUser(new User("Test User 2", "test2@gmail.com", "Testagon5"));
		String email= "test2@gmail.com";
		String password = "Testagon5";
		User user = underTest.authenticate(email, password);
		assertEquals(email, user.getEmail());
	}
	
	@Test
	void testAuthenticationwithWrongCredential() {
		String email= "test2@gmail.com";
		String password = "Testagon5";
		User user = underTest.authenticate(email, password);
		assertNull(user);
	}
}
