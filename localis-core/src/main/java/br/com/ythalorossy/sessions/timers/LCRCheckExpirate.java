package br.com.ythalorossy.sessions.timers;

import java.util.Collection;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;

import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRDBManager;
import br.com.ythalorossy.sessions.jms.producer.LCRRequestProducer;

@Stateless
public class LCRCheckExpirate {
	
	@Inject
	private LCRDBManager lcrdbManager;
	
	@Inject
	private LCRRequestProducer lcrProducer;

	public LCRCheckExpirate() {
	}

	@Schedule(second = "*/30", minute = "*", hour = "*", dayOfWeek = "*", dayOfMonth = "*", month = "*", year = "*", info = "LCRCheckExpirate")
	private void scheduledTimeout(final Timer t) {

		try {

			System.out.println("Verificando se existe LCR expirada...");
			
			Collection<LCR> lcrsExpired = lcrdbManager.findAllExpired();
			
			for (LCR lcr : lcrsExpired) {

//				lcr.setStatus(LCRStatus.STATUS_BAIXANDO);
				
				System.out.println("Enviando para fila: " + lcr.getUrl());
				
				lcrProducer.execute(lcr.getUrl());
			}

		} catch (Exception e) {

			System.err.println(e.getMessage());

		}

	}
}