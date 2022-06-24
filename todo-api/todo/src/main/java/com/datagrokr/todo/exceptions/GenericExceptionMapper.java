package com.datagrokr.todo.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
	public Response toResponse(Throwable occuredException)
	{
		if(occuredException instanceof EntityNotFoundException)
		{
		
			return Response.status(Response.Status.NOT_FOUND)
				       .entity(new ErrorProps("404", occuredException.getMessage()))
				       .build();
		}
		if(occuredException instanceof NoResultException)
		{
			return Response.status(Response.Status.NOT_FOUND)
				       .entity(new ErrorProps("404", occuredException.getMessage()))
				       .build();
		}
		else
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorProps("500", occuredException.getMessage()))
					.build();
		}
	}
}


