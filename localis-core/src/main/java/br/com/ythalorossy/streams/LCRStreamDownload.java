package br.com.ythalorossy.streams;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LCRStreamDownload implements LCRStreamRecover {

	public LCRStreamDownload() {
	}

	public InputStream execute(String url) throws Exception {

		URL urlCRL = new URL(url);

		URLConnection connection = urlCRL.openConnection();
		
		InputStream inputStrem = connection.getInputStream();;
		
		return inputStrem;
	}
}
