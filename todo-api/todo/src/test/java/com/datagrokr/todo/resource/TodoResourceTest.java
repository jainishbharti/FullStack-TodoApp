package com.datagrokr.todo.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.TodoService;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@TestInstance(Lifecycle.PER_CLASS)
class TodoResourceTest extends JerseyTest{
	
	TodoService todoService;
	UserService userService;
	
	 @Override
	 protected Application configure() {
	      return new ResourceConfig(TodoResource.class);
	 }

	@BeforeEach
	public
	void init() throws Exception {
		todoService = new TodoService();
		userService = new UserService();
	}

	@AfterEach
	public void tearDown() throws Exception {}

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
		Todo addedTodo = todoService.save(todo, addedUser);
		
		Response response = target("/todos/"+ addedTodo.getTodoId()).request().get();
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
	@Disabled
	void testPostIt() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todo.setUser(addedUser);
		Response response = target("todos").request().post(Entity.entity(todo, MediaType.APPLICATION_JSON));
		assertEquals("should return CREATED response", 201, response.getStatus());
	}

	@Test
	@Disabled
	void testUpdateById() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		todo.setUser(addedUser);
		
		Todo todoToUpdate = new Todo("Testing todo", false);
		todoToUpdate.setUser(addedUser);
		Response response = target("todos").request().put(Entity.entity(todoToUpdate, MediaType.APPLICATION_JSON));
		assertEquals("should return OK response", 200, response.getStatus());
	}

	@Test
	void testDelete() throws Exception {
		User user = new User("TestUser", "test@gmail.com", "Testagon");
		User addedUser = userService.save(user);
		Todo todo = new Todo("Testing todo", false);
		Todo addedTodo = todoService.save(todo, addedUser);
		Response response = target("/todos/"+ addedTodo.getTodoId()).request().delete();
		assertEquals("Should return an OK response", 200, response.getStatus());
	}

}
