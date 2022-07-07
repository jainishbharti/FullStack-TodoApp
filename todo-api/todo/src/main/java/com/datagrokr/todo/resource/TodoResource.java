package com.datagrokr.todo.resource;



import java.util.List;

import com.datagrokr.todo.annotation.Secured;
import com.datagrokr.todo.entity.Todo;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.TodoService;
import com.datagrokr.todo.service.TokenUtilService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
	TodoService todoService;
	TokenUtilService tokenUtilService;
	

    public TodoResource() {
		this.todoService = new TodoService();
		this.tokenUtilService = new TokenUtilService();
	}


	@GET
	@Secured
    public Response getAll() {
    	return Response.status(Response.Status.OK)
    			.entity(todoService
    			.findAll())
    			.build();
    }
	
	@GET
	@Path("/{id}")
	@Secured
	public Response getById(@PathParam("id") Integer id) {
		return Response.status(Response.Status.OK).entity(todoService.getById(id)).build();
	}
	
	@GET
	@Path("/user/{id}")
	public Response getTodoByUserId(@PathParam("id") Integer id) {
		return Response.status(Response.Status.OK).entity(todoService.getTodoByUserId(id)).build();
	}
	
	
	@POST
	@Secured
    public Response postIt(@HeaderParam("Authorization") String auth, Todo todo) {
		System.out.println("Initial line");
		String token = auth.substring(6);
		System.out.println("After slicing token");
		User user = tokenUtilService.getUserFromToken(token);
		System.out.println("After finding user from token: "+  user);
		List<Todo> todos = todoService.save(todo, user);
		System.out.println("In post todo");
    	return Response.status(Response.Status.CREATED).entity(todos).build();
    }
	
	@Path("/{id}")
	@PUT
	@Secured
	public Response updateById(Todo todo, @PathParam("id") Integer id) {
		return Response.status(Response.Status.OK).entity(todoService.update(todo, id)).build();
	}
	
	@Path("/{id}")
	@DELETE
	@Secured
	public Response delete(@PathParam("id") Integer id) {
		todoService.deleteById(id);
		return Response.status(Response.Status.NO_CONTENT).entity(todoService.deleteById(id)).build();
	}
}
