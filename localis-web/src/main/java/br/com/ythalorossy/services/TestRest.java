package br.com.ythalorossy.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path(value = "/test")
public class TestRest {

	@GET
	@Path(value = "/{url}")
	public String testeGet(@PathParam("url") String url) {

		System.out.println("get: " + url);

		return url;
	}

	@POST
	@Path(value = "/{url}")
	public String testePost(@PathParam("url") String url) {

		System.out.println("post: " + url);

		return url;
	}	
	
}
