package com.datagrokr.todo.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.TodoService;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class TodoResourceTest extends JerseyTest{
	
	TodoService todoService;
	UserService userService;
	
	 @Override
	 protected Application configure() {
		  enable(TestProperties.LOG_TRAFFIC);
		  enable(TestProperties.DUMP_ENTITY);
	      return new ResourceConfig(TodoResource.class);
	 }

	@BeforeEach
	public
	void init() throws Exception {
		todoService = new TodoService();
		userService = new UserService();
	}



	@Test
	void testTodoResource() {
	}

	@Test
	void testGetAll() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todo.setUser(addedUser);
		todoService.save(todo, addedUser);
		Response response = target("/todos").request().get();
		assertEquals("should return an OK response", 200, response.getStatus());
		assertNotNull(response.getEntity());
	}

	@Test
	void testGetById() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todo.setUser(addedUser);
		todoService.save(todo, addedUser);
		
		Response response = target("/todos/"+ 1).request().get();
		assertEquals("should return an OK Response", 200, response.getStatus());
		
	}

	@Test
	void testGetTodoByUserId() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todo.setUser(addedUser);
		todoService.save(todo, addedUser);
		
		Response response = target("/todos/user/"+ addedUser.getUserId()).request().get();
		assertEquals("should return an OK response", 200, response.getStatus());
	}

	@Test
	void testPostIt() throws Exception {
		User user = new User("John Doe", "johnny@gmail.com", "hexagon");
		userService.save(user);
		Todo todo = new Todo("Testing todo", false);		
		Response response = target("/todos").request()
				.header("Authorization", "Basic am9obm55QGdtYWlsLmNvbTpoZXhhZ29u")
				.post(Entity.entity(todo, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertEquals("should return CREATED response", 201, response.getStatus());
	}

	@Test
	void testUpdateById() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todoService.save(todo, addedUser);
		Todo todoToUpdate = new Todo("Testing todo", false);
		todoToUpdate.setUser(addedUser);
		Response response = target("/todos/1").request().put(Entity.entity(todoToUpdate, MediaType.APPLICATION_JSON));
		assertEquals("should return OK response", 200, response.getStatus());
	}

	@Test
	void testDelete() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todoService.save(todo, addedUser);
		Response response = target("/todos/1").request().delete();
		assertNotEquals("Should return an No content response", 204, response.getStatus());
	}

}
