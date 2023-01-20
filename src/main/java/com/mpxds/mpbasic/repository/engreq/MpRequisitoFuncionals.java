package com.mpxds.mpbasic.repository.engreq;

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

import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.model.engreq.MpRequisitoFuncional;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpPrioridade;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpRequisitoFuncionalFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpRequisitoFuncionals implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpRequisitoFuncionalFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpRequisitoFuncional.class);
		
		if (StringUtils.isNotBlank(filtro.getIdRF()))
			criteria.add(Restrictions.ilike("idRF", filtro.getIdRF(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpRequisitoFuncional> filtrados(MpRequisitoFuncionalFilter filtro) {
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
	
	public int quantidadeFiltrados(MpRequisitoFuncionalFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpRequisitoFuncional guardar(MpRequisitoFuncional mpRequisitoFuncional) {
		try {
			return manager.merge(mpRequisitoFuncional);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Esse REQUISITO FUNCIONAL... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpRequisitoFuncional mpRequisitoFuncional) throws MpNegocioException {
		//
		try {
			mpRequisitoFuncional = porId(mpRequisitoFuncional.getId());
			
			manager.remove(mpRequisitoFuncional);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("REQUISITO FUNCIONAL... não pode ser excluído(a).");
		}
	}

	public MpRequisitoFuncional porIdRF(String idRF, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpRequisitoFuncional where upper(idRF) = :idRF" +
							" AND mpProjeto = :mpProjeto";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpRequisitoFuncional.class)
							.setParameter("idRF", idRF.toUpperCase())
							.setParameter("mpProjeto", mpProjeto).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpRequisitoFuncional porId(Long id) {
		//
		return manager.find(MpRequisitoFuncional.class, id);
	}
	
	public List<MpRequisitoFuncional> mpRequisitoFuncionalList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpRequisitoFuncional";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY idRF ASC";
		
		return manager.createQuery(hql,	MpRequisitoFuncional.class).getResultList();
	}
	
	public Long porStatus(MpStatusRequisito mpStatusRequisito) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c where "
													+ "c.mpStatusRequisito = :mpStatusRequisito";
			
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
			
		return manager.createQuery(sql, Long.class)
						.setParameter("mpStatusRequisito", mpStatusRequisito).getSingleResult();
	}
	public Long porStatusProjeto(MpStatusRequisito mpStatusRequisito, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c"
				+ " where c.mpStatusRequisito = :mpStatusRequisito AND c.mpProjeto = :mpProjeto";

		if (!tenantId.equals("0"))
			sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class)
				.setParameter("mpStatusRequisito", mpStatusRequisito)
				.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public Long porPrioridade(MpPrioridade mpPrioridade) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c" + 
														" where c.mpPrioridade = :mpPrioridade";
			
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class)
									.setParameter("mpPrioridade", mpPrioridade).getSingleResult();
	}
	public Long porPrioridadeProjeto(MpPrioridade mpPrioridade, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c"
				+ " where c.mpPrioridade = :mpPrioridade AND c.mpProjeto = :mpProjeto";

		if (!tenantId.equals("0"))
			sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class).setParameter("mpPrioridade", mpPrioridade)
				.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public Long porComplexibilidade(MpComplexibilidade mpComplexibilidade) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c" + 
											" where c.mpComplexibilidade = :mpComplexibilidade";
		
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
		//	
		return manager.createQuery(sql, Long.class)
						.setParameter("mpComplexibilidade", mpComplexibilidade).getSingleResult();
	}	
	public Long porComplexibilidadeProjeto(MpComplexibilidade mpComplexibilidade,
											MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoFuncional c" + 
					" where c.mpComplexibilidade = :mpComplexibilidade AND c.mpProjeto = :mpProjeto";
		
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
		//	
		return manager.createQuery(sql, Long.class)
						.setParameter("mpComplexibilidade", mpComplexibilidade)
						.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public long numeroRegistros() {
		//
    	System.out.println(
    				"MpRequisitoFuncionals.encontrarQuantidadeRegistrosMpRequisitoFuncional()");
//    			manager.createQuery("select count(c) from MpRequisitoFuncional c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpRequisitoFuncional c",
//																Long.class).getSingleResult();
		return 0L;
	}

}