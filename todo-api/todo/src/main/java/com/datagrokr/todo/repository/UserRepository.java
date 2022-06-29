package com.datagrokr.todo.repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.datagrokr.todo.entity.User;

public class UserRepository {
	EntityManager entityManager;

	public UserRepository() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("todo_pu");
		this.entityManager = emf.createEntityManager();
	}
	
	public User addUser(User user) throws Exception {
		if(userFromEmailExists(user.getEmail())) {
			throw new Exception("User exists with email: "+ user.getEmail());
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			return user;
		}
	}
	
	
	
	public List<User> getUsersList() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = entityManager.createQuery(all);
        List<User> result = allQuery.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result; 
	}
	
	public boolean userFromEmailExists(String email) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(
				cb.equal(root.get("email"), email)
				);
		TypedQuery<User> tq = entityManager.createQuery(query);
		try {
			tq.getSingleResult();
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}
	
	public User getById(Integer id) {
		User user = entityManager.find(User.class, id);
		if(user == null) return null;
		else return user;
	}
	
	public User updateUser(User user, Integer id) {
		User userToUpdate = entityManager.find(User.class, id);
		if(user != null) {
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setName(user.getName());
			userToUpdate.setPassword(user.getPassword());
		}
		entityManager.getTransaction().begin();
		entityManager.persist(userToUpdate);
		entityManager.getTransaction().commit();
		return userToUpdate;	
	}
	
	public void deleteById(Integer id) {
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("DELETE FROM User WHERE userId="+ id);
		query.executeUpdate();
		entityManager.getTransaction().commit();
	}
	
	public User authenticate(String email, String password) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> root = query.from(User.class);
		
		Predicate predicate1 = cb.equal(root.get("email"), email);
		Predicate predicate2 = cb.equal(root.get("password"), password);
		Predicate andPredicate = cb.and(predicate1, predicate2);
		query.where(andPredicate);
		TypedQuery<User> tq = entityManager.createQuery(query);
		try {
			User user = tq.getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
		
	}
	
	public void close() {
		entityManager.close();
	}
	
	

}
