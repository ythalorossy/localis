package br.com.ythalorossy.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="lcrStatus")
public class LCRStatusDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String descricao;

	public LCRStatusDTO() {
	}
	
	public LCRStatusDTO(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
