package com.examples.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.examples.request.RestFulReq;
import com.examples.request.RestFulReqPost;
import com.examples.request.RestFulRes;
import com.sun.research.ws.wadl.Request;

@Service
@Path("/json")
@Component
public class ResourceJson {
	@GET
	@Path("/tt")
	@Produces(MediaType.APPLICATION_JSON)
	public String testRes(@Context Request request, @BeanParam RestFulReq requestVO) {
		String pattern = "{ \"test\":\"0\"}";
		return String.format(pattern);  
	}
	
	@POST
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String requestPost(@Context Request request, @PathParam("username") String userName, @Context UriInfo uriInfo, @Context HttpHeaders header, @BeanParam RestFulReqPost requestVO, MultivaluedMap<String, String> formParams) {
		RestFulReq req = new RestFulReq();
		req.setMessage(requestVO.getMessage());
		return requestGet(request, userName, uriInfo, header, req);  
	}
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String requestGet(@Context Request request, @PathParam("username") String userName, @Context UriInfo uriInfo, @Context HttpHeaders header, @BeanParam RestFulReq requestVO) {
		String pattern = "{ \"%s\":\"%s\", \"result\" : \"Success\"}";
		return String.format(pattern,  userName, requestVO.getMessage());  
	}
}
