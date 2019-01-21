package com.examples.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.examples.Server;
import com.examples.request.RestFulReq;
import com.examples.request.RestFulReqPost;
import com.examples.request.RestFulRes;
import com.sun.research.ws.wadl.Request;

import ib.pdu.mfep.exception.PduException;


@Service
@Path("/restApi")
@Component
public class ResourceXML {
	
	@GET
	@Path("/tt")
	@Produces(MediaType.APPLICATION_XML)
	public RestFulRes testRes(@Context Request request, @BeanParam RestFulRes resVO) {
		return resVO;
	}
	
	@POST
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public RestFulRes requestPost(@Context Request request, @Context UriInfo uriInfo, @Context HttpHeaders header,@BeanParam RestFulReqPost requestVO, MultivaluedMap<String, String> formParams) {
		RestFulReq postVO = new RestFulReq();
		postVO.setMessage(requestVO.getMessage());
		return requestGet(request, uriInfo, header, postVO);
	}
	
	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public RestFulRes requestGet(@Context Request request, @Context UriInfo uriInfo, @Context HttpHeaders header, @BeanParam RestFulReq requestVO){
		RestFulRes restFulRes = null;
		System.out.println("Received Message : " + requestVO.getMessage());
		restFulRes = makeRestFulResponse(requestVO, "Success");
		return restFulRes;
	}
	
	protected RestFulRes makeRestFulResponse(RestFulReq reqVO, String resCode){
		RestFulRes smsRes = new RestFulRes();
		smsRes.setRet(resCode);
		return smsRes;
	}
}
