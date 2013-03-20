package br.com.ythalorossy.sessions.jms.consumer;

import java.io.InputStream;
import java.security.cert.X509CRL;
import java.util.Calendar;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRCacheManager;
import br.com.ythalorossy.sessions.LCRDBManager;
import br.com.ythalorossy.streams.LCRStreamDownload;
import br.com.ythalorossy.utils.CalendarUtil;
import br.com.ythalorossy.utils.FileUtil;
import br.com.ythalorossy.utils.LCRUtils;

@MessageDriven(
		name = "mdb/LCRConsumer", 
		activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, 
		mappedName = "jms/lcr")
public class LCRConsumer implements MessageListener {

	@Inject
	private LCRDBManager lcrdbManager;

	@Inject
	private LCRCacheManager cacheManager;
	
	public LCRConsumer() {
	}

	public void onMessage(Message message) {

		try {

			String url = (String) ((ObjectMessage) message).getObject();

			LCR lcr = lcrdbManager.findByURL(url);
			
			if (lcr == null) {
				
				lcr = new LCR();
				lcr.setUrl(url);
				lcr.setThisUpdate(Calendar.getInstance());
				
				lcrdbManager.persist(lcr);
			} 
				
			lcr.setStatus(LCRStatus.STATUS_BAIXANDO);
			
			try {
				
				InputStream inputStream = new LCRStreamDownload().execute(lcr.getUrl());
				
				if (inputStream != null) {
					
					lcr.setLcr(FileUtil.getBytes(inputStream));
					lcr.setStatus(LCRStatus.STATUS_ATUALIZADA);
				}

				if (lcr.getStatus().equals(LCRStatus.STATUS_ATUALIZADA)) {

					X509CRL crl = LCRUtils.createLCR(lcr.getLcr());

					lcr.setNextUpdate(CalendarUtil.convert(crl.getNextUpdate()));
				}
				
				lcrdbManager.persist(lcr);
				
				cacheManager.put(lcr);
			
			} catch (Exception e) {

				lcrdbManager.changeStatus(url, LCRStatus.STATUS_EXPIRADA);

			}
			
		} catch (JMSException e) {
			
			e.printStackTrace();
		}
	}
}
