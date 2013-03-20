package br.com.ythalorossy.sessions;

import java.util.Set;

import javax.ejb.Remote;

import br.com.ythalorossy.model.LCR;

@Remote
public interface LCRCacheManager {

	public abstract LCR get(String url);

	public abstract void put(LCR lcr);
	
	public abstract Set<LCR> getAll();
	
	public abstract void remove(String url);
	
}
