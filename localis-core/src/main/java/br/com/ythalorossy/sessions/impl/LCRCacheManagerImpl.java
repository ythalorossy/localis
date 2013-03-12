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
import br.com.ythalorossy.to.LCRTO;
import br.com.ythalorossy.utils.LCRUtils;

@ApplicationScoped
@Named(value="ejb/LCRCacheManager")
public class LCRCacheManagerImpl implements LCRCacheManager {

	@Singleton
	private static Map<String, LCRTO> cache = new HashMap<String, LCRTO>();

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

					LCRTO lcrto = LCRUtils.convert(lcr);
					
					cache.put(lcr.getUrl(), lcrto);
				}
			}
		}
	}
	
	public void put(LCRTO lcrto) {

		synchronized (cache) {

			cache.put(lcrto.getUrl(), lcrto);
		}
	}

	public LCRTO get(String url) {
		
		LCRTO lcr = cache.get(url);
		
		return lcr;
	}

	public Set<LCRTO> getAll() {
		
		Set<LCRTO> result = new HashSet<LCRTO>();
		
		if (cache.size() > 0) {
			
			for ( LCRTO lcrto : cache.values() ) {
				
				result.add(lcrto);
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
