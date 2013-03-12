package br.com.ythalorossy.managed;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import br.com.ythalorossy.sessions.LCRManager;
import br.com.ythalorossy.to.LCRTO;

@RequestScoped
@ManagedBean(name = "LCRManaged")
public class LCRManaged {

	@Inject
	@Any
	private LCRTO lcrto;
	
	private String lcrDados;

	@EJB
	private LCRManager lcrManager;
	
	public String getLCR() {

		this.lcrto = lcrManager.getLCR(this.lcrto.getUrl());
		
		if (lcrto != null) {
			this.lcrDados = lcrto.getLcrStatus().toString();	
		}
		
		return "";
	}

	public LCRTO getLcrto() {
		return lcrto;
	}

	public void setLcrto(LCRTO lcrto) {
		this.lcrto = lcrto;
	}

	public String getLcrDados() {
		return lcrDados;
	}
}
