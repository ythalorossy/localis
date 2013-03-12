package br.com.ythalorossy.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.to.LCRTO;


public class LCRUtils {
	
	public static X509CRL createLCR(byte[] bytes) {
		
		return createLCR(new ByteArrayInputStream(bytes));
	}
	
	
	public static X509CRL createLCR(InputStream inputStream) {
		
		X509CRL crl = null;
		
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			crl = (X509CRL) cf.generateCRL(inputStream);
		
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CRLException e) {
			e.printStackTrace();
		}
		
		return crl;
	}
	
	public static LCRTO createLCR(String url, InputStream inputStream) {
		
		X509CRL crl = createLCR(inputStream);

		LCRTO lcrto = null;
		
		try {
			
			lcrto = parse(crl);
		
		} catch (CRLException e) {

			e.printStackTrace();
		}

		if (lcrto != null) {
			lcrto.setUrl(url);
		}
		
		return lcrto;
	}

	public static LCRTO parse(X509CRL crl) throws CRLException {
		
		LCRTO lcrto = null;
		
		if (crl != null) {
			
			lcrto = new LCRTO("");
			lcrto.setBytes(crl.getEncoded());
			lcrto.setNextUpdate(getNextUpdate(crl));
			lcrto.setThisUpdate(getThisUpdate(crl));
			lcrto.setLcrStatus(LCRStatus.STATUS_ATUALIZADA);
		}
		
		return lcrto;
	}
	
	public static LCR convert(LCRTO lcrto) {

		LCR lcr = new LCR();

		lcr.setUrl(lcrto.getUrl());

		if (lcrto.getLcrStatus() != null) {
			lcr.setStatus(lcrto.getLcrStatus());
		}

		if ((lcrto.getBytes() != null) && (lcrto.getBytes().length > 0)) {
			lcr.setLcr(lcrto.getBytes());
		}

		if (lcrto.getNextUpdate() != null) {
			lcr.setNextUpdate(lcrto.getNextUpdate());
		}

		if (lcrto.getThisUpdate() != null) {
			lcr.setThisUpdate(lcrto.getThisUpdate());
		}

		return lcr;
	}

	public static LCRTO convert(LCR lcr) {

		LCRTO lcrto = new LCRTO(lcr.getUrl());

		if ((lcr.getLcr() != null) && (lcr.getLcr().length > 0)) {
			lcrto.setBytes(lcr.getLcr());
		}

		if (lcr.getNextUpdate() != null) {
			lcrto.setNextUpdate(lcr.getNextUpdate());
		}

		if (lcr.getStatus() != null) {
			lcrto.setLcrStatus(lcr.getStatus());
		}

		if (lcr.getThisUpdate() != null) {
			lcrto.setThisUpdate(lcr.getThisUpdate());
		}

		return lcrto;
	}

	public static Calendar getThisUpdate(X509CRL crl) {
		
		return CalendarUtil.convert(crl.getThisUpdate());
	}
	
	public static Calendar getNextUpdate(X509CRL crl) {
		
		return CalendarUtil.convert(crl.getNextUpdate());
	}


	/**
	 * Recupera lista de URLs.
	 * 
	 * @param cert
	 *            {@link X509Certificate}
	 * @return
	 */
	public static List<String> extractURL(X509Certificate cert) {

		byte[] urlLcr = cert.getExtensionValue("2.5.29.31");
		
		List<String> result = new ArrayList<String>();

		String lcrs = new String(urlLcr);

		String[] splits = lcrs.split("http://");

		for (int i = 1; i < splits.length; i++) {

			int indxFinal = splits[i].indexOf(".crl");

			String url = "http://" + splits[i].substring(0, indxFinal) + ".crl";

			result.add(url);
		}

		return result;
	}
	
}


