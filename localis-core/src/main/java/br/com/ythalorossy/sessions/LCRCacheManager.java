package br.com.ythalorossy.sessions;

import java.util.Set;

import javax.ejb.Remote;

import br.com.ythalorossy.to.LCRTO;

@Remote
public interface LCRCacheManager {

	public abstract LCRTO get(String url);

	public abstract void put(LCRTO lcr);
	
	public abstract Set<LCRTO> getAll();
	
	public abstract void remove(String url);
	
}
