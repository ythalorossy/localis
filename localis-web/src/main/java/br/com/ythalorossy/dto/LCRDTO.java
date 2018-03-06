package br.com.ythalorossy.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "lcr")
public class LCRDTO implements Serializable {

	private LCRStatusDTO lcrStatusDTO;

	private String base64;

	public LCRDTO () {

	}

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
