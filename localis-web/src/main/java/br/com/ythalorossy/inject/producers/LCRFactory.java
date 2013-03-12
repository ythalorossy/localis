package br.com.ythalorossy.inject.producers;

import javax.enterprise.inject.Produces;

import br.com.ythalorossy.to.LCRTO;


public class LCRFactory {

	@Produces
	public LCRTO create() {
		return new LCRTO("");
	}
	
}
