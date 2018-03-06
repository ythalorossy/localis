package br.com.ythalorossy.sessions.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.ythalorossy.constants.LCRStatus;
import br.com.ythalorossy.model.LCR;
import br.com.ythalorossy.sessions.LCRDBManager;

@Named(value="ejb/LCRDBManager")
@Stateless
public class LCRDBManagerImpl implements LCRDBManager {

	@PersistenceContext(unitName = "lcr-pu")
	private EntityManager entityManager;

	public LCRDBManagerImpl() {

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persist(LCR lcr) {

		if (lcr.getId() == null) {

			entityManager.persist(lcr);

		} else {

			entityManager.merge(lcr);
		}
	}

	public LCR findByURL(String url) {

		LCR result = null;

		try {

			TypedQuery<LCR> query = entityManager.createNamedQuery("LCR.findByUrl", LCR.class);
			query.setParameter("url", url);

			result = query.getSingleResult();

		} catch (NoResultException e) {
			
			result = null;
		}

		return result;
	}

	public Collection<LCR> findAllExpired() {

		Collection<LCR> lcrs = new ArrayList<LCR>();

		TypedQuery<LCR> query = entityManager.createNamedQuery("LCR.findAllExpired", LCR.class);
		query.setParameter("nextUpdate", Calendar.getInstance());
		query.setParameter("status", LCRStatus.STATUS_EXPIRADA);

		List<LCR> result = query.getResultList();

		for (LCR lcr : result) {

			lcrs.add(lcr);
		}

		return lcrs;
	}

	public void changeStatus(String url, LCRStatus status) {

		LCR lcr = findByURL(url);
		
		
		lcr.setStatus(status);
		
		persist(lcr);
	}

	public Collection<LCR> findAll() {

		TypedQuery<LCR> query = entityManager.createNamedQuery("LCR.findAll", LCR.class);
		
		List<LCR> result = query.getResultList();
		
		return result;
	}

	public boolean delete(String url) {

		TypedQuery<LCR> query = entityManager.createNamedQuery("LCR.deleteByURL", LCR.class);

		query.setParameter("url", url);
		
		query.executeUpdate();
		
		return false;
	}

}
