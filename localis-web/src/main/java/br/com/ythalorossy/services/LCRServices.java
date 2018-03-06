package br.com.ythalorossy.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.dto.LCRStatusDTO;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.services.responses.LCRListResponse;
import br.com.ythalorossy.services.responses.LCRResponse;
import br.com.ythalorossy.services.responses.StatusCodeResponse;
import br.com.ythalorossy.sessions.LCRManager;
import br.com.ythalorossy.utils.LCRUtils;


@Path("lcr")
public class LCRServices {

	@EJB
	private LCRManager lcrManager;
	
	@Context
    private UriInfo context;
	
    public LCRServices() {
    }

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

		LCRListResponse lcrListResponse = new LCRListResponse();

		Set<LCR> lcrs = lcrManager.getAll();

		if (lcrs.isEmpty()) {

			throw new WebApplicationException(Status.NOT_FOUND);
		}

		for (LCR lcr : lcrs) {
			lcrListResponse
					.getLcrs()
					.add(new LCRResponse(LCRUtils.convert(lcr)).getLcr());
		}

		return Response.status(Status.OK).entity(lcrListResponse).build();
    }

	@GET
	@Path(value = "/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByURL(@QueryParam("url") String url, @QueryParam("cache") String cache) {

		LCR lcr = lcrManager.getLCR(url, new Boolean(cache));

		if (lcr == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return Response.status(Status.OK).entity(new LCRResponse(LCRUtils.convert(lcr))).build();
    }

	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putURL(@QueryParam("url") String url) {
		
		lcrManager.add(url);
		
		return Response.status(Status.ACCEPTED).build();
	}
	
	@DELETE
	@Path("/")
	public Response deleteURL(@QueryParam("url") String url){
		
		Response response = Response.status(Status.ACCEPTED).build();
		
		LCR lcr = lcrManager.getLCR(url, new Boolean(true));
    	
    	if (lcr == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
    	} 
		
		if(lcrManager.remove(url)){
			
			response = Response.status(Status.ACCEPTED).build();
		}
		
		return response;
	}
	
	@GET
	@Path("/check")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkGet(@QueryParam("url") String url) {
		
		LCR lcr = lcrManager.getLCR(url);

		if (lcr == null) {
			throw new WebApplicationException(Status.NOT_FOUND);			
		
		} 
		
		return Response.ok(lcr).build();
	}

	@GET
	@Path("/codes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCodes() {

		StatusCodeResponse result = new StatusCodeResponse();

		for (LCRStatus lcrStatus : LCRStatus.values()) {
			result.getStatus()
					.add( new LCRStatusDTO( lcrStatus.getCodigo().toString(), lcrStatus.getDescricao()) );
		}

		return Response.status(Status.OK).entity(result).build();
	}

//    @POST
//    @Path("/certificate/{base64}/{cache}")
//    public LCRDTO byCertificateBase64(@QueryParam("certificateBase64") String certificateBase64, @QueryParam("cache") String cache) {
//
//    	LCRDTO lcrdto = null;
//
//		try {
//
//			byte[] bytes = Base64.decode(certificateBase64);
//
//			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//
//			InputStream in = new ByteArrayInputStream(bytes);
//
//			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
//
//			List<String> urls = LCRUtils.extractURL(cert);
//
//			for (String url : urls) {
//
//				LCR lcr = lcrManager.getLCR(url, new Boolean(cache));
//
//				if (lcr != null) {
//
//					lcrdto = new LCRResponse(LCRUtils.convert(lcr)).getLcr();
//
//					break;
//				}
//			}
//
//		} catch (CertificateException e) {
//
//			e.printStackTrace();
//		}
//
//    	return lcrdto;
//    }


    /*
     * DAQUI PARA BAIXO REFATORAR
     */
    
    @POST
    @Path("/validar")
    public String validar(@FormParam("certificado") String certificado) {
    	
    	try {

        	byte[] cert = certificado.getBytes();
        	
        	InputStream inputStream = new ByteArrayInputStream(cert);
    		
    		java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance("X.509");
			
    		Certificate certificate = cf.generateCertificate(inputStream);
    		
    		System.out.println(certificate.toString());
    		
		} catch (CertificateException e1) {
			
			e1.printStackTrace();
		}
    	return "OK";
    }
}