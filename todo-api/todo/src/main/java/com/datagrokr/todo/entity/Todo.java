package com.datagrokr.todo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer todoId;
	private String title;
	private boolean done;
	
	@OneToOne
	@JoinColumn(name="userid")
	private User user;
	
	
	
	public Todo(String title, boolean done) {
		super();
		this.title = title;
		this.done = done;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Todo(String title, Boolean done) {
		super();
		this.title = title;
		this.done = done;
	}

	public Todo() {
		super();
	}

	public Integer getTodoId() {
		return todoId;
	}

	public void setTodoId(Integer todoId) {
		this.todoId = todoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", title=" + title + ", done=" + done + ", user=" + user + "]";
	}

	
	
}
