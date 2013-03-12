package br.com.ythalorossy.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ythalorossy.constants.LCRStatus;

@Entity
@Table(name = "lcr")
@NamedQueries({
		@NamedQuery(name = "LCR.findByUrl", query = "select l from LCR l where l.url = :url"),
		@NamedQuery(name = "LCR.findAllExpired", query = "SELECT l FROM LCR l WHERE (l.nextUpdate <= :nextUpdate)  OR (l.status = :status)"),
		@NamedQuery(name = "LCR.findAll", query = "SELECT l FROM LCR l"),
		@NamedQuery(name = "LCR.deleteByURL", query = "DELETE FROM LCR l WHERE l.url = :url")
})
public class LCR implements Serializable {

	private static final long serialVersionUID = 2859324824520393326L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "url")
	private String url;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "thisUpdate")
	private Calendar thisUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "nextUpdate")
	private Calendar nextUpdate;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private LCRStatus status;

	@Column(name = "lcr")
	@Basic
	private byte[] lcr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public LCRStatus getStatus() {
		return status;
	}

	public void setStatus(LCRStatus status) {
		this.status = status;
	}

	public byte[] getLcr() {
		return lcr;
	}

	public void setLcr(byte[] lcr) {
		this.lcr = lcr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LCR other = (LCR) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	
	
}
