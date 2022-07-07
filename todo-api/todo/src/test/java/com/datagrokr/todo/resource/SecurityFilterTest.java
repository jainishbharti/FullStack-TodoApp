package com.datagrokr.todo.resource;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


@TestInstance(Lifecycle.PER_METHOD)
class SecurityFilterTest extends JerseyTest{
	
	 @Override
	 protected Application configure() {
	      return new ResourceConfig(
	    		  AuthResource.class,
	    		  Provider.class
	    		  );
	 }


	@BeforeEach
	void init() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	void testFilter()  {
		Response response = target("todo/users").request().get();
		assertEquals("should return a not found response", 404, response.getStatus());
	}

}
