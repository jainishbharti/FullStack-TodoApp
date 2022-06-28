package com.datagrokr.todo.resource;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.datagrokr.todo.service.UserService;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


@Provider
public class SecurityFilter implements ContainerRequestFilter{
	
	private final UserService userService = new UserService();

	
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic";
//	private static final String SECURED_URL_PREFIX = "secured";



	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
//		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			
			
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				authToken = authToken.replaceAll(" ", "");
				byte[] decodedBytes = Base64.getDecoder().decode(authToken);
				String decodedString = new String(decodedBytes);
				String[] details = decodedString.split(":");
				String email = details[0];
				String password = details[1];
				userService.isUserAuthenticated(email, password);
				return;
			}
			Response unauthorizedStatus = Response
					.status(Response.Status.UNAUTHORIZED)
					.entity("User cannot access the resource.")
					.build();
			requestContext.abortWith(unauthorizedStatus);
			
//		}	
	}	
}
