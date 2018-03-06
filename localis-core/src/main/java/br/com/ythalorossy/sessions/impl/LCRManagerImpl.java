package br.com.ythalorossy.sessions.impl;

import java.io.InputStream;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRCacheManager;
import br.com.ythalorossy.sessions.LCRDBManager;
import br.com.ythalorossy.sessions.LCRManager;
import br.com.ythalorossy.sessions.jms.producer.LCRRequestProducer;
import br.com.ythalorossy.streams.LCRStreamRecover;
import br.com.ythalorossy.utils.LCRUtils;

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

	public LCR getLCR(String url, boolean cache) {
		
		LCR lcr = null;
		
		if (cache) {
			
			lcr = findCache(url);
		
		} else {
		
			try {
				
				InputStream inputStream = lcrStream.execute(url);
				
				lcr = LCRUtils.createLCR(url,inputStream);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return lcr;
	}

	/**
	 * Consulta o CACHE em mem�ria e em base de dados.
	 * @param url URL utilizada como chave de acesso no CACHE de LCR.
	 * @return
	 */
	private LCR findCache(String url) {
		
		LCR lcr = lcrCacheManager.get(url);
		
		if (lcr == null) {
			
			lcr = findCacheDataBase(url);

			if (lcr == null) {
			
				lcrProducerJMS.execute(url);

			}
		
		}
		
		return lcr;
	}

	/**
	 * Consulta o CACHE na base de dados
	 * @param url
	 * @return
	 */
	private LCR findCacheDataBase(String url) {
		
		LCR lcr = lcrdbManager.findByURL(url);
		
		return lcr;
	}

	public LCR getLCR(String url) {
		return getLCR(url, true);
	}

	public Set<LCR> getAll() {
		
		Set<LCR> result = lcrCacheManager.getAll();
		
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
