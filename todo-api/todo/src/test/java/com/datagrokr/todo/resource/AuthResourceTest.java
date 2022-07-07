package com.datagrokr.todo.resource;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.datagrokr.todo.entity.Credential;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@TestInstance(Lifecycle.PER_CLASS)
class AuthResourceTest extends JerseyTest{
	
	AuthResource underTest;
	UserService userService;
	
	 @Override
	 protected Application configure() {
	      return new ResourceConfig(AuthResource.class);
	 }

	@BeforeEach
	void init() throws Exception {
		underTest = new AuthResource();
		userService = new UserService();
	}

	@AfterEach
	public void tearDown() throws Exception {
		userService.closeConn();
	}

	@Test
	void testLogin() throws Exception {
		Credential credential = new Credential("johnny@gmail.com", "hexagon");
		
		Response response = target("/auth/login").request().post(Entity.entity(credential, MediaType.APPLICATION_JSON));
		assertEquals("should return an unauthorized response", 401, response.getStatus());
		
	}
	

	@Test
	void testCheckLogin() {
		Response response = target("/auth/checkLogin").request().header("Authorization", "Basic am9obm55QGdtYWlsLmNvbTpoZXhhZ29u").get();
		assertEquals("should return a token a response", 401, response.getStatus());
		
	}

}
