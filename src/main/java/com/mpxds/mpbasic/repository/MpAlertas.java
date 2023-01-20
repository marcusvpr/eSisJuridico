package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpAlertaFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAlertas implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// -----
	
	private Criteria criarCriteriaParaFiltro(MpAlertaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpAlerta.class);
		
		if (null != filtro.getIndEmail())
			criteria.add(Restrictions.eq("indEmail", filtro.getIndEmail()));
		if (null != filtro.getIndSMS())
			criteria.add(Restrictions.eq("indSMS", filtro.getIndSMS()));
		if (null != filtro.getIndPush())
			criteria.add(Restrictions.eq("indPush", filtro.getIndPush()));
		if (null != filtro.getIndTelegram())
			criteria.add(Restrictions.eq("indTelegram", filtro.getIndTelegram()));
		if (null != filtro.getIndWhatsapp())
			criteria.add(Restrictions.eq("indWhatsapp", filtro.getIndWhatsapp()));
		if (null != filtro.getIndMpComunicator())
			criteria.add(Restrictions.eq("indMpComunicator", filtro.getIndMpComunicator()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAlerta> filtrados(MpAlertaFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getMpFilterOrdenacao().getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getMpFilterOrdenacao().getQuantidadeRegistros());
		
		if (filtro.getMpFilterOrdenacao().isAscendente() && filtro.getMpFilterOrdenacao().
															getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.asc(filtro.getMpFilterOrdenacao().
																getPropriedadeOrdenacao()));
		else if (filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.desc(filtro.getMpFilterOrdenacao().
																	getPropriedadeOrdenacao()));
		//		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MpAlertaFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpAlerta guardar(MpAlerta mpAlerta) {
		//
		try {
			return manager.merge(mpAlerta);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ALERTA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAlerta mpAlerta) throws MpNegocioException {
		//
		try {
			mpAlerta = porId(mpAlerta.getId());
			
			manager.remove(mpAlerta);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Alerta... não pode ser excluído.");
		}
	}
	
	public MpAlerta porId(Long id) {
		//
		return manager.find(MpAlerta.class, id);
	}
			
}