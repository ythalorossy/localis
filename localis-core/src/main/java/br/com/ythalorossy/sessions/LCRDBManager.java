package br.com.ythalorossy.sessions;

import java.util.Collection;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;

public interface LCRDBManager {

	Collection<LCR> findAllExpired();
	
	void persist(LCR lcr);
	
	LCR findByURL(String url);
	
	void changeStatus(String url, LCRStatus status);

	Collection<LCR> findAll();
	
	boolean delete(String url);
}