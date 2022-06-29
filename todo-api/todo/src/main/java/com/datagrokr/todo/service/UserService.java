package com.datagrokr.todo.service;

import java.util.List;

import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.repository.UserRepository;

public class UserService {
	UserRepository userRepo;

	public UserService() {
		this.userRepo = new UserRepository();
	}
		
	public List<User> findAll(){
		return userRepo.getUsersList();
	}
	
	public User save(User user) throws Exception {
		return userRepo.addUser(user);
	}
	
	
	public User getById(Integer id) {
		return userRepo.getById(id);
	}
	
	public User updateById(User user, Integer id) {
		return userRepo.updateUser(user, id);	
	}
	
	public void deleteById(Integer id) {
		userRepo.deleteById(id);
	}
	
	public User isUserAuthenticated(String email, String password) {
		return userRepo.authenticate(email, password);
	}
	
	public void closeConn() {
		userRepo.close();
	}
	
}
