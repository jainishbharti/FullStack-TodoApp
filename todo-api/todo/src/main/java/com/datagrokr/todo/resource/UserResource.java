package com.datagrokr.todo.resource;



import com.datagrokr.todo.annotation.Secured;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
 private final UserService userService;

    public UserResource() {
		this.userService = new UserService();
	}

    @Secured
	@GET
    public Response getAll() {
    	return Response.status(Response.Status.OK)
    			.entity(userService.findAll())
    			.build();
    }
	
    @Secured
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") Integer id) {
		return Response.status(Response.Status.OK)
    			.entity(userService.getById(id))
    			.build();
	}
	
    
	@POST
	@Path("/register")
    public Response postIt(User user) {
    	try {
    		User addedUser = userService.save(user);
			return Response.status(Response.Status.CREATED)
					.entity(addedUser)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getMessage())
					.build();
		}
    }
	
	@Secured
	@Path("/{id}")
	@PUT
	public Response updateById(User user, @PathParam("id") Integer id) {
		return Response.status(Response.Status.OK)
    			.entity(userService.updateById(user, id))
				.build();
	}
	
	@Secured
	@Path("/{id}")
	@DELETE
	public Response delete(@PathParam("id") Integer id) {
		userService.deleteById(id);
		return Response.status(Response.Status.OK)
		.entity("Entity deleted successfully!")
		.build();
	}
}
