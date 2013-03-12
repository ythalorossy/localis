package br.com.ythalorossy.to;

import java.io.Serializable;
import java.util.Calendar;

import br.com.ythalorossy.constants.LCRStatus;

public class LCRTO implements Serializable {

	private static final long serialVersionUID = 4714542760925142993L;

	private String url;

	private byte[] bytes;

	private Calendar thisUpdate;

	private Calendar nextUpdate;

	private LCRStatus lcrStatus;

	public LCRTO(String url, LCRStatus lcrStatus) {
		this.url = url;
		this.lcrStatus = lcrStatus;
	}

	public LCRTO(String url) {

		this.url = url;
	}

	public LCRTO(String url, byte[] bytes) {
		this.url = url;
		this.bytes = bytes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public Calendar getThisUpdate() {
		return thisUpdate;
	}

	public void setThisUpdate(Calendar thisUpdate) {
		this.thisUpdate = thisUpdate;
	}

	public Calendar getNextUpdate() {
		return nextUpdate;
	}

	public void setNextUpdate(Calendar nextUpdate) {
		this.nextUpdate = nextUpdate;
	}

	public LCRStatus getLcrStatus() {
		return lcrStatus;
	}

	public void setLcrStatus(LCRStatus lcrStatus) {
		this.lcrStatus = lcrStatus;
	}

}
