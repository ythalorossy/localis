package br.com.ythalorossy.services.responses;

import br.com.ythalorossy.dto.LCRStatusDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class StatusCodeResponse implements Serializable{

    @XmlElement
    private
    List<LCRStatusDTO> status = new ArrayList<LCRStatusDTO>();

    public StatusCodeResponse () {}

    public List<LCRStatusDTO> getStatus() {
        return status;
    }

    public void setStatus(List<LCRStatusDTO> status) {
        this.status = status;
    }
}
