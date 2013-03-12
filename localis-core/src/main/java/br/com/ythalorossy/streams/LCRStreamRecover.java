package br.com.ythalorossy.streams;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public interface LCRStreamRecover {

	/**
	 * Efetua o download da LCR
	 * 
	 * @param url
	 *            URL de um ponto de distribuição.
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public InputStream execute(String url) throws Exception;
	
}
