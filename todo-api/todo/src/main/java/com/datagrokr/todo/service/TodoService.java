package com.datagrokr.todo.service;

import java.util.List;

import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.repository.TodoRepository;


public class TodoService {
	TodoRepository todoRepo;

	public TodoService() {
		this.todoRepo = new TodoRepository();
	}
	
	public List<Todo> getTodoByUserId(Integer id){
		return todoRepo.getTodoByUserId(id);
	}
	
	public List<Todo> findAll(){
		return todoRepo.getTodosList();
	}
	
	public Todo save(Todo todo, User user) {
		todo.setUser(user);
		return todoRepo.addTodo(todo);
	}
	
	public Todo getById(Integer id) {
		return todoRepo.getById(id);
	}
	
	public Todo update(Todo todo, Integer id) {
		return todoRepo.updateTodo(todo, id);
	}
	
	public void deleteById(Integer id) {
		todoRepo.deleteById(id);
	}
	
	

}
