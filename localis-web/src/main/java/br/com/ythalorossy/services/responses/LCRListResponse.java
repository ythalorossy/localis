package br.com.ythalorossy.services.responses;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.ythalorossy.dto.LCRDTO;

@XmlRootElement(name = "lcrlistresponse")
public class LCRListResponse {

	private Set<LCRDTO> lcrs;

	public Set<LCRDTO> getLcrs() {
		return lcrs;
	}

	public void setLcrs(Set<LCRDTO> lcrs) {
		this.lcrs = lcrs;
	}
	
	public LCRListResponse add(LCRDTO lcrdto) {
		if (lcrs == null) {
			lcrs = new HashSet<LCRDTO>();
		} 
		
		lcrs.add(lcrdto);
		
		return this;
	}

}
