package br.com.ythalorossy.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lcr")
public class LCRDTO {

	private LCRStatusDTO lcrStatusDTO;

	private String base64;

	public LCRStatusDTO getLcrStatusDTO() {
		return lcrStatusDTO;
	}

	public void setLcrStatusDTO(LCRStatusDTO lcrStatusDTO) {
		this.lcrStatusDTO = lcrStatusDTO;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

}
