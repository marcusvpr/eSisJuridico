package com.mpxds.mpbasic.repository;

import java.io.Serializable;
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

import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpTurnoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTurnos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpTurnoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpTurno.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), 
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTurno> filtrados(MpTurnoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		//
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
	
	public int quantidadeFiltrados(MpTurnoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpTurno guardar(MpTurno mpTurno) {
		//
		try {
			return manager.merge(mpTurno);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse TURNO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpTurno mpTurno) throws MpNegocioException {
		//
		try {
			mpTurno = porId(mpTurno.getId());
			
			manager.remove(mpTurno);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Turno... não pode ser excluído.");
		}
	}

	public MpTurno porDescricao(String descricao) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			String hql = "from MpTurno where descricao = :descricao";
			
			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
			
			return manager.createQuery(hql,	MpTurno.class)
								.setParameter("descricao", descricao).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpTurno porId(Long id) {
		//
		return manager.find(MpTurno.class, id);
	}
	
	public List<MpTurno> mpTurnoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpTurno";
		
		if (!tenantId.equals("0")) hql = hql +	" WHERE tenantId = " + tenantId;
		
		hql = hql + " ORDER BY mpTipoJornada ASC, descricao ASC";
		
		return manager.createQuery(hql,	MpTurno.class).getResultList();
	}
	
	public MpTurno porNavegacao(String acao, String descricao) {
		//
//		System.out.println("MpTurnos.MpTurno ( " + acao + " / " + descricao);
		//
		String tenantId = mpSeguranca.capturaTenantId();

		try {
			//
			String hql = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpTurno where descricao < :descricao";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY descricao DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpTurno where descricao > :descricao";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY descricao ASC";
			}
			//
			if (hql.isEmpty())
				return null;
			else
				return manager.createQuery(hql,	MpTurno.class)
							.setParameter("descricao", descricao)
							.setMaxResults(1).getSingleResult();

			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}