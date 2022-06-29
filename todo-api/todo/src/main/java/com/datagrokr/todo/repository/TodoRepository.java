package com.datagrokr.todo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.datagrokr.todo.entity.Todo;
public class TodoRepository {
	
	EntityManager entityManager;


	public TodoRepository() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("todo_pu");
		this.entityManager = emf.createEntityManager();
	}
	
	public List<Todo> addTodo(Todo todo) {
		entityManager.getTransaction().begin();
		entityManager.persist(todo);
		entityManager.getTransaction().commit();
		return getTodoByUserId(todo.getUser().getUserId());
	}
	
	public List<Todo> getTodoByUserId(Integer userId){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Todo> query = cb.createQuery(Todo.class);
		Root<Todo> root = query.from(Todo.class);
		query.where(
				cb.equal(root.get("user"), userId)
				);
		TypedQuery<Todo> tq = entityManager.createQuery(query);
		return tq.getResultList();
		
	}
	
	public List<Todo> getTodosList() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> cq = cb.createQuery(Todo.class);
        Root<Todo> rootEntry = cq.from(Todo.class);
        CriteriaQuery<Todo> all = cq.select(rootEntry);
        TypedQuery<Todo> allQuery = entityManager.createQuery(all);
        List<Todo> result = allQuery.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result; 
	}
	
	public Todo getById(Integer id) {
		return entityManager.find(Todo.class, id);
	}
	
	public List<Todo> updateTodo(Todo todo, Integer id) {
		Todo todoToUpdate = entityManager.find(Todo.class, id);
		entityManager.getTransaction().begin();
		if(todoToUpdate != null) {
			if(todo.getTitle() != null) todoToUpdate.setTitle(todo.getTitle());
			if(todo.isDone()) todoToUpdate.setDone(todo.isDone());
		}
		entityManager.persist(todoToUpdate);
		entityManager.getTransaction().commit();
		return getTodoByUserId(todoToUpdate.getUser().getUserId());
		
	}
	
	public List<Todo> deleteById(Integer id) {
		entityManager.getTransaction().begin();
		Todo todo = entityManager.find(Todo.class, id);
		Query query = entityManager.createQuery("DELETE FROM Todo WHERE id="+ id);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		return getTodoByUserId(todo.getUser().getUserId());
	}
	
	public void close() {
		entityManager.close();
	}
	
}
