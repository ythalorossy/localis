package br.com.ythalorossy.sessions;

import java.util.Collection;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;

public interface LCRDBManager {

	public Collection<LCR> findAllExpired();
	
	public void persist(LCR lcr);
	
	public LCR findByURL(String url);
	
	public void changeStatus(String url, LCRStatus status);

	public Collection<LCR> findAll();
	
	public boolean delete(String url);
}