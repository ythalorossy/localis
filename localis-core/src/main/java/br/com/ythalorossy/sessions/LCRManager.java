package br.com.ythalorossy.sessions;

import java.util.Set;

import javax.ejb.Remote;

import br.com.ythalorossy.model.LCR;

@Remote
public interface LCRManager {
	
	public LCR getLCR(String url);
	
	public LCR getLCR(String url, boolean cache);
	
	public Set<LCR> getAll();
	
	public void add(String url);
	
	public boolean remove(String url);
	
}
