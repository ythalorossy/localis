package br.com.ythalorossy.services.responses;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;

import javax.xml.bind.annotation.XmlRootElement;


import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.dto.LCRDTO;
import br.com.ythalorossy.dto.LCRStatusDTO;
import br.com.ythalorossy.to.LCRTO;
import com.sun.org.apache.xml.internal.security.utils.Base64;


@XmlRootElement(name="lcrresponse")
public class LCRResponse {

	private LCRDTO lcr;

	public LCRResponse() {
	}
	
	public LCRResponse(LCRTO lcrto) {
	
		prepare(lcrto);
	}
	
	public LCRDTO getLcr() {
		return lcr;
	}

	public void setLcr(LCRDTO lcr) {
		this.lcr = lcr;
	}
	
	private void prepare(LCRTO lcr) {
		
		LCRDTO lcrdto = new LCRDTO();
    	
    	if (lcr.getLcrStatus().equals(LCRStatus.STATUS_ATUALIZADA)) {
    		
        	try {
    			CertificateFactory cf = CertificateFactory.getInstance("X.509");
    		
    			X509CRL crl = (X509CRL) cf.generateCRL(new ByteArrayInputStream(lcr.getBytes()));
    			
    			String crlBase64 = Base64.encode(crl.getEncoded());
    			
    			lcrdto.setBase64(crlBase64);
    			
    			LCRStatusDTO lcrStatusDTO = new LCRStatusDTO(lcr.getLcrStatus().getCodigo().toString(), lcr.getLcrStatus().getDescricao());
    			
    			lcrdto.setLcrStatusDTO(lcrStatusDTO);
     			
    		} catch (Exception e) {
    			
    			LCRStatusDTO lcrStatusDTO = new LCRStatusDTO(LCRStatus.ERRO_INTERNO.getCodigo().toString(), LCRStatus.ERRO_INTERNO.getDescricao());
    			
    			lcrdto.setLcrStatusDTO(lcrStatusDTO);
    		}    		
    	
    	} else {
    		
    		LCRStatusDTO lcrStatusDTO = new LCRStatusDTO(lcr.getLcrStatus().getCodigo().toString(), lcr.getLcrStatus().getDescricao());
    		
    		lcrdto.setLcrStatusDTO(lcrStatusDTO);
    	}
	    
    	setLcr( lcrdto );
	}
}
