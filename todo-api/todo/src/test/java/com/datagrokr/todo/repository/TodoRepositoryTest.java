package com.datagrokr.todo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;

@TestInstance(Lifecycle.PER_CLASS)
class TodoRepositoryTest {
	
	TodoRepository underTest;
	UserRepository userUnderTest;
	
	@Test
	void testTodoRepository() {}

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		underTest = new TodoRepository();
		userUnderTest = new UserRepository();
	}

	@AfterEach
	void tearDown() throws Exception {
		underTest.close();
		userUnderTest.close();
	}

	

	@Test
	void testAddTodo() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = userUnderTest.addUser(user);
		Todo todo = new Todo("Testing todo task", false);
		todo.setUser(addedUser);
		Todo addedTodo = underTest.addTodo(todo);
		assertEquals(todo, addedTodo);
	}

	@Test
	void testGetTodoByUserId() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = userUnderTest.addUser(user);
		Todo todo = new Todo("Testing todo task", false);
		todo.setUser(addedUser);
		underTest.addTodo(todo);
		List<Todo> foundTodos = underTest.getTodoByUserId(addedUser.getUserId());
		assertNotNull(foundTodos);
	}

	@Test
	void testGetTodosList() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User user2 = new User("Test User2", "test2@gmail.com", "Testagon5");

		User addedUser = userUnderTest.addUser(user);
		User addedUser2 = userUnderTest.addUser(user2);

		Todo todo = new Todo("Testing todo task", false);
		Todo todo2 = new Todo("Testing todo task2", true);
		todo.setUser(addedUser);
		todo2.setUser(addedUser2);		
		underTest.addTodo(todo);
		underTest.addTodo(todo2);
		assertNotNull(underTest.getTodosList());

	}

	@Test
	void testGetById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = userUnderTest.addUser(user);
		Todo todo = new Todo("Testing todo task", false);
		todo.setUser(addedUser);
		Todo addedTodo = underTest.addTodo(todo);
		Todo foundTodo = underTest.getById(addedTodo.getTodoId());
		assertNotNull(foundTodo);
	}

	@Test
	void testUpdateTodo() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = userUnderTest.addUser(user);
		Todo todo = new Todo("Testing todo task", false);
		todo.setUser(addedUser);
		Todo addedTodo = underTest.addTodo(todo);
		
		User updateUser = new User("Updated User", "testupdate@gmail.com", "Hexagon");
		Todo updateTodo = new Todo("Updating todo", true);
		updateTodo.setUser(updateUser);
		Todo updatedTodo = underTest.updateTodo(updateTodo, addedTodo.getTodoId());
		assertEquals(updateTodo.getTitle(), updatedTodo.getTitle());
		
	}

	@Test
	@Disabled
	void testDeleteById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = userUnderTest.addUser(user);
		Todo todo = new Todo("Testing todo task", false);
		todo.setUser(addedUser);
		Todo addedTodo = underTest.addTodo(todo);
		underTest.deleteById(addedTodo.getTodoId());
		assertNull(underTest.getById(1));
	}

}
