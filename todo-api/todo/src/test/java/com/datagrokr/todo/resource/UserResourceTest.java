package com.datagrokr.todo.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

@TestInstance(Lifecycle.PER_CLASS)
class UserResourceTest extends JerseyTest{
	UserService userService;
	
  
    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
    }
    

	@BeforeEach
	public void init() throws Exception {
		userService = new UserService();
	}
	
	@AfterEach
	public void tearDown() {
		userService.closeConn();
	}

	@Test
	void testUserResource() {
		
	}

	@Test
	void testGetAll() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		userService.save(user);
		Response response = target("/users").request().get();
		assertEquals("should return an OK response", 200, response.getStatus());

	}

	@Test
	void testGetById() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		String url = "/users/" + addedUser.getUserId();
		Response response = target(url).request().get();
		assertEquals("should return an OK response", 200, response.getStatus());
	}

	@Test
	void testPostIt() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		Response response = target("/users/register").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return an CREATED response", 201, response.getStatus());
		assertNotNull(response.getEntity());
	}
	
	@Test
	void testPostIt2() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		userService.save(user);
		Response response = target("/users/register").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return an CONFLICT response", 409, response.getStatus());
	}

	@Test
	void testUpdateById() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		System.out.println(addedUser);
		User newUser = new User("TestUser2", "test2@gmail.com", "Testag4on");
		String url = "/users/" + addedUser.getUserId();
		System.out.println(url);
		Response response = target(url).request().put(Entity.entity(newUser, MediaType.APPLICATION_JSON));
		assertEquals("Should return an OK response", 200, response.getStatus());	
	}

	@Test
	void testDelete() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Response response = target("/users/"+ addedUser.getUserId()).request().delete();
		assertEquals("Should return an OK response", 200, response.getStatus());
	}

}
