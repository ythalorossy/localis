package br.com.ythalorossy.sessions;

import java.util.Set;

import javax.ejb.Remote;

import br.com.ythalorossy.to.LCRTO;

@Remote
public interface LCRManager {
	
	public LCRTO getLCR(String url);
	
	public LCRTO getLCR(String url, boolean cache);
	
	public Set<LCRTO> getAll();
	
	public void add(String url);
	
	public boolean remove(String url);
	
}
