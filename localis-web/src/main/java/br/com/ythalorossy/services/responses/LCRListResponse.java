package br.com.ythalorossy.services.responses;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.ythalorossy.dto.LCRDTO;

@XmlRootElement
public class LCRListResponse implements Serializable {

	@XmlElement
	private Set<LCRDTO> lcrs = new HashSet<LCRDTO>();

	public LCRListResponse () {}

	public Set<LCRDTO> getLcrs() {
		return lcrs;
	}

	public void setLcrs(Set<LCRDTO> lcrs) {
		this.lcrs = lcrs;
	}
}
