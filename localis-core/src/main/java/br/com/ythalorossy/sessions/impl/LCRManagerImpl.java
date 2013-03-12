package br.com.ythalorossy.sessions.impl;

import java.io.InputStream;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRCacheManager;
import br.com.ythalorossy.sessions.LCRDBManager;
import br.com.ythalorossy.sessions.LCRManager;
import br.com.ythalorossy.sessions.jms.producer.LCRRequestProducer;
import br.com.ythalorossy.streams.LCRStreamRecover;
import br.com.ythalorossy.to.LCRTO;
import br.com.ythalorossy.utils.LCRUtils;

// Session Facade ou application service

@Stateless
public class LCRManagerImpl implements LCRManager {

	@Inject
	private LCRRequestProducer lcrProducerJMS;

	@Inject
	private LCRDBManager lcrdbManager;

	@Inject
	private LCRCacheManager lcrCacheManager;
	
	@Inject
	private LCRStreamRecover lcrStream;
	
	public LCRManagerImpl() {
	}

	public LCRTO getLCR(String url, boolean cache) {
		
		LCRTO lcrTO = null;
		
		if (cache) {
			
			lcrTO = findCache(url);
		
		} else {
		
			try {
				InputStream inputStream = lcrStream.execute(url);
				
				lcrTO = LCRUtils.createLCR(url,inputStream);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return lcrTO;
	}

	/**
	 * Consulta o CACHE em memória e em base de dados.
	 * @param url URL utilizada como chave de acesso no CACHE de LCR.
	 * @return
	 */
	private LCRTO findCache(String url) {
		
		LCRTO lcrTO = lcrCacheManager.get(url);
		
		if (lcrTO == null) {
			
			lcrTO = findCacheDataBase(url);
			
			if (lcrTO.getLcrStatus().equals(LCRStatus.STATUS_NAO_LOCALIZADA)) {
				
				lcrProducerJMS.execute(url);
			}
		}
		
		return lcrTO;
	}

	/**
	 * Consulta o CACHE na base de dados
	 * @param url
	 * @return
	 */
	private LCRTO findCacheDataBase(String url) {
		
		LCRTO lcrTO = null;
		
		LCR lcr = lcrdbManager.findByURL(url);
		
		if (lcr == null) {
			
			lcrTO = new LCRTO(url);
			lcrTO.setLcrStatus(LCRStatus.STATUS_NAO_LOCALIZADA);
		
		} else {
			
			lcrTO = new LCRTO(url, lcr.getLcr());
			lcrTO.setLcrStatus(lcr.getStatus());
			lcrTO.setNextUpdate(lcr.getNextUpdate());
		}
		
		return lcrTO;
	}

	public LCRTO getLCR(String url) {
		return getLCR(url, true);
	}

	public Set<LCRTO> getAll() {
		
		Set<LCRTO> result = lcrCacheManager.getAll();
		
		return result;
	}

	public void add(String url) {

		lcrProducerJMS.execute(url);
		
	}

	public boolean remove(String url) {

		boolean result = false;
		
		if(lcrdbManager.delete(url)){
		
			lcrCacheManager.remove(url);
			
			result = true;
		}
		
		return result;
	}

}
