package br.com.ythalorossy.constants;

public enum LCRStatus {

	STATUS_ATUALIZADA			(100, "ATUALIZADA NO CACHE"),
	STATUS_EXPIRADA				(101, "LCR EXPIRADA"),
	STATUS_BAIXANDO				(102, "EFETUANDO ATUALIZACAO DA LCR NO CACHE"), 
	STATUS_NAO_LOCALIZADA		(103, "LCR NAO LOCALIZADA NO CACHE"),
	SOLICITAR_ADICAO			(104, "SOLICITAR A INSERCAO DA LCR NO CACHE"),
	SOLICITAR_EXCLUSAO			(105, "SOLICITAR A EXCLUSAO DA LCR NO CACHE"),
	SOLICITAR_ATUALIZACAO		(106, "SOLICITAR ATUALIZACAO DA LCR NO CACHE"),
	ERRO_INTERNO				(900, "ERRO NO INTERNO NO SERVIDOR.");

	private Integer codigo;
	private String descricao;

	private LCRStatus(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public LCRStatus findByCodigo(Integer codigo) {
		
		for (LCRStatus lcrStatus : values()) {
			if(lcrStatus.codigo.equals(codigo)){
				return lcrStatus;
			}
		}
		
		return null;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String toString() {
		return String.format("%s - %s", codigo, descricao);
	}
}
