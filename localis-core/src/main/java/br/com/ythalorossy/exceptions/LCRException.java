package br.com.ythalorossy.exceptions;

import br.com.ythalorossy.constants.LCRStatus;

public class LCRException extends Exception {

	private static final long serialVersionUID = 1L;

	private LCRStatus lcrStatus;

	public LCRException(LCRStatus lcrStatus) {
		this.lcrStatus = lcrStatus;
	}

	public LCRStatus getLcrStatus() {
		return lcrStatus;
	}

	public String toString() {
		return lcrStatus.toString();
	}
}
