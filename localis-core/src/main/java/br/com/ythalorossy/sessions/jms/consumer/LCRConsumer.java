package br.com.ythalorossy.sessions.jms.consumer;

import java.io.InputStream;
import java.security.cert.X509CRL;
import java.util.Calendar;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
import br.com.ythalorossy.to.LCRTO;
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
				
				criarLCR(url);
			}
			
			LCRTO lcrTO = baixaLCR(url);
			
			if (lcrTO == null) {
				
				lcrdbManager.changeStatus(url, LCRStatus.STATUS_EXPIRADA);
				
			} else {
				
				LCR lcr1 = lcrdbManager.findByURL(url);
				lcr1.setLcr(lcrTO.getBytes());
				
				X509CRL crl = LCRUtils.createLCR(lcrTO.getBytes());

				lcr1.setNextUpdate(CalendarUtil.convert(crl.getNextUpdate()));
				lcr1.setStatus(LCRStatus.STATUS_ATUALIZADA);
				
				lcrdbManager.persist(lcr1);
				
				lcrTO = LCRUtils.convert(lcr1);
				
				cacheManager.put(lcrTO);
			}
			
		} catch (JMSException e) {
			
			e.printStackTrace();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void criarLCR(String url) {
		LCR lcr = new LCR();
		lcr.setStatus(LCRStatus.STATUS_EXPIRADA);
		lcr.setUrl(url);
		lcr.setThisUpdate(Calendar.getInstance());
		
		lcrdbManager.persist(lcr);
	}

	private LCRTO baixaLCR(String url) {

		LCRTO lcrto = null;
		
		try {
			
			InputStream inputStream = new LCRStreamDownload().execute(url);
			
			if (inputStream != null) {
			
				lcrto = new LCRTO(url, FileUtil.getBytes(inputStream));
			}
			
		}  catch (Exception e) {
			
			lcrto = null;
		}

		return lcrto;
	}

}
