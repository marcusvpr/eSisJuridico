package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAlarmes implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// -----
	
	private Criteria criarCriteriaParaFiltro(MpAlarmeFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpAlarme.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (null != filtro.getIndDomingo())
			criteria.add(Restrictions.eq("indDomingo", filtro.getIndDomingo()));
		if (null != filtro.getIndSegunda())
			criteria.add(Restrictions.eq("indSegunda", filtro.getIndSegunda()));
		if (null != filtro.getIndTerca())
			criteria.add(Restrictions.eq("indTerca", filtro.getIndTerca()));
		if (null != filtro.getIndQuarta())
			criteria.add(Restrictions.eq("indQuarta", filtro.getIndQuarta()));
		if (null != filtro.getIndQuinta())
			criteria.add(Restrictions.eq("indQuinta", filtro.getIndQuinta()));
		if (null != filtro.getIndSexta())
			criteria.add(Restrictions.eq("indSexta", filtro.getIndSexta()));
		if (null != filtro.getIndSabado())
			criteria.add(Restrictions.eq("indSabado", filtro.getIndSabado()));
		if (null != filtro.getIndAtivo())
			criteria.add(Restrictions.eq("indAtivo", filtro.getIndAtivo()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAlarme> filtrados(MpAlarmeFilter filtro) {
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
	
	public int quantidadeFiltrados(MpAlarmeFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpAlarme guardar(MpAlarme mpAlarme) {
		//
		try {
			return manager.merge(mpAlarme);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ALARME... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAlarme mpAlarme) throws MpNegocioException {
		//
		try {
			mpAlarme = porId(mpAlarme.getId());
			
			manager.remove(mpAlarme);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Alarme... não pode ser excluído.");
		}
	}

	public MpAlarme porHoraMovimento(Date horaMovimento) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "from MpAlarme where horaMovimento = :horaMovimento";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpAlarme.class)
							.setParameter("horaMovimento", horaMovimento).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpAlarme porId(Long id) {
		//
		return manager.find(MpAlarme.class, id);
	}
		
	public MpAlarme porNavegacao(String acao, Date horaMovimento) {
		//
//		System.out.println("MpAlarmes.MpAlarme ( " + acao + " / " + descricao);
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			//
			String hql = "";
			String order = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpAlarme where horaMovimento < :horaMovimento";
				order = " ORDER BY horaMovimento DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpAlarme where horaMovimento > :horaMovimento";
				order = " ORDER BY horaMovimento ASC";
			}

			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql + order,	MpAlarme.class)
							.setParameter("horaMovimento", horaMovimento)
							.setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}