package com.datagrokr.todo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;

@TestInstance(Lifecycle.PER_CLASS)
class TodoServiceTest {
	
	TodoService underTest;
	UserService underTestUser;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new TodoService();
		underTestUser = new UserService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTodoService() {
		
	}

	@Test
	void testGetTodoByUserId() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		assertNotNull(underTest.getTodoByUserId(addedUser.getUserId()));
	}

	@Test
	void testFindAll() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		assertNotNull(underTest.findAll());
	}

	@Test
	void testSave() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		assertNotNull(underTest.getById(1));
	}

	@Test
	void testGetById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		assertNotNull(underTest.getById(1));
	}

	@Test
	void testUpdate() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		Todo initialTodo = underTest.getById(1);
		User user2 = new User("Test User2", "test2@gmail.com", "Testagon8");
		User addedUser2 = underTestUser.save(user2);
		Todo todo2 = new Todo("Testing todo task comp", true);
		todo2.setUser(addedUser2);
		
		underTest.update(todo2, 1);
		assertEquals(todo2.getTitle(), initialTodo.getTitle());
	}

	@Test
	void testDeleteById() throws Exception {
		User user = new User("Test User", "test@gmail.com", "Testagon");
		User addedUser = underTestUser.save(user);
		Todo todo = new Todo("Testing todo task", false);
		underTest.save(todo, addedUser);
		underTest.deleteById(1);
		assertNotNull(underTest.getById(1));
	}

}
