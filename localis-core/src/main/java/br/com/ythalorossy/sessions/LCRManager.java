package br.com.ythalorossy.sessions;

import java.util.Set;

import javax.ejb.Remote;

import br.com.ythalorossy.model.LCR;

@Remote
public interface LCRManager {
	
	LCR getLCR(String url);
	
	LCR getLCR(String url, boolean cache);
	
	Set<LCR> getAll();
	
	void add(String url);
	
	boolean remove(String url);
}
