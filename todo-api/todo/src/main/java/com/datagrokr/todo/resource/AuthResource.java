package com.datagrokr.todo.resource;

import java.util.Base64;

import com.datagrokr.todo.entity.Credential;
import com.datagrokr.todo.entity.User;
import com.datagrokr.todo.service.TokenUtilService;
import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
	
	UserService userService = new UserService();
	TokenUtilService tokenUtilService = new TokenUtilService();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Credential credential) {
		User user = userService.isUserAuthenticated(credential.getEmail(), credential.getPassword());
		String joinedString = credential.getEmail().concat(":").concat(credential.getPassword());
		String encodedString = Base64.getEncoder().encodeToString(joinedString.getBytes());

		if(user != null ) {
			System.out.println(user);
			return Response.status(Response.Status.OK)
					.entity(encodedString)
					.build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User unauthorized").build();
		}
		
	}
	
	@GET
	@Path("/checkLogin")
	public Response checkLogin(@HeaderParam("Authorization") String auth) {
		String token = auth.substring(6);
		User user = tokenUtilService.getUserFromToken(token);
		if(auth!= null && isAuthenticated(auth)) {
			return Response.status(Response.Status.ACCEPTED)
					.entity(user)
					.build();
		} else return Response
				.status(Response.Status.UNAUTHORIZED)
				.entity("Unauthorized Access: User cannot access the resource.")
				.build();
	}

	private boolean isAuthenticated(String auth) {
		String[] authParts = auth.split("\\s+");
		String authInfo = authParts[1];
		byte[] bytes = Base64.getDecoder().decode(authInfo);

		String decodedString = new String(bytes);
		String[] details = decodedString.split(":");
		String email = details[0];
		String password = details[1];
		User user = userService.isUserAuthenticated(email, password);
		if(user != null) {
			return true;
		}
		else return false;
		
	}

}
