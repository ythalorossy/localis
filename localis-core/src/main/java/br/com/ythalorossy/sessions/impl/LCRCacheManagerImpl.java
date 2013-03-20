package br.com.ythalorossy.sessions.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRCacheManager;
import br.com.ythalorossy.sessions.LCRDBManager;

@ApplicationScoped
@Named(value="ejb/LCRCacheManager")
public class LCRCacheManagerImpl implements LCRCacheManager {

	@Singleton
	private static Map<String, LCR> cache = new HashMap<String, LCR>();

	@Inject
	private LCRDBManager lcrdbManager;
	
	public LCRCacheManagerImpl() {
	}

	@PostConstruct
	public void initializeCache() {
		
		synchronized (cache) {

			Collection<LCR> allLCR = lcrdbManager.findAll();
			
			for (LCR lcr : allLCR) {

				if (lcr.getStatus().equals(LCRStatus.STATUS_ATUALIZADA)) {

					cache.put(lcr.getUrl(), lcr);
				}
			}
		}
	}
	
	public void put(LCR lcrto) {

		synchronized (cache) {

			cache.put(lcrto.getUrl(), lcrto);
		}
	}

	public LCR get(String url) {
		
		LCR lcr = cache.get(url);
		
		return lcr;
	}

	public Set<LCR> getAll() {
		
		Set<LCR> result = new HashSet<LCR>();
		
		if (cache.size() > 0) {
			
			for ( LCR lcr : cache.values() ) {
				
				result.add(lcr);
			}
		}
		
		return result;
	}

	public void remove(String url) {

		if(cache.containsKey(url)){
			
			synchronized (cache) {
				
				cache.remove(url);
			}
		}
	}
}
