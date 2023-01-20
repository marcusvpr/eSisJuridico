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
import com.mpxds.mpbasic.model.engreq.MpRequisitoNaoFuncional;
import com.mpxds.mpbasic.model.enums.engreq.MpCategoriaRNF;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpRequisitoNaoFuncionalFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpRequisitoNaoFuncionals implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpRequisitoNaoFuncionalFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpRequisitoNaoFuncional.class);
		
		if (StringUtils.isNotBlank(filtro.getIdRNF()))
			criteria.add(Restrictions.ilike("idRNF", filtro.getIdRNF(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getAssociacao()))
			criteria.add(Restrictions.ilike("associacao", filtro.getAssociacao(),
																				MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpRequisitoNaoFuncional> filtrados(MpRequisitoNaoFuncionalFilter filtro) {
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
	
	public int quantidadeFiltrados(MpRequisitoNaoFuncionalFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpRequisitoNaoFuncional guardar(MpRequisitoNaoFuncional mpRequisitoNaoFuncional) {
		//
		try {
			return manager.merge(mpRequisitoNaoFuncional);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Esse REQUISITO NÃO FUNCIONAL... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpRequisitoNaoFuncional mpRequisitoNaoFuncional) throws MpNegocioException {
		//
		try {
			mpRequisitoNaoFuncional = porId(mpRequisitoNaoFuncional.getId());
			
			manager.remove(mpRequisitoNaoFuncional);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("REQUISITO NÃO FUNCIONAL... não pode ser excluído(a).");
		}
	}

	public MpRequisitoNaoFuncional porIdRNF(String idRNF) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpRequisitoNaoFuncional where upper(idRNF) = :idRNF";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpRequisitoNaoFuncional.class)
									.setParameter("idRNF", idRNF.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpRequisitoNaoFuncional porId(Long id) {
		//
		return manager.find(MpRequisitoNaoFuncional.class, id);
	}
	
	public List<MpRequisitoNaoFuncional> mpRequisitoNaoFuncionalList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpRequisitoNaoFuncional";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY idRNF ASC";
		
		return manager.createQuery(hql,	MpRequisitoNaoFuncional.class).getResultList();
	}
	
	public Long porStatus(MpStatusRequisito mpStatusRequisito) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoNaoFuncional c where "
													+ "c.mpStatusRequisito = :mpStatusRequisito";
			
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
			
		return manager.createQuery(sql, Long.class)
						.setParameter("mpStatusRequisito", mpStatusRequisito).getSingleResult();
	}
	
	public Long porCategoria(MpCategoriaRNF mpCategoriaRNF) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoNaoFuncional c where "
															+ "c.mpCategoriaRNF = :mpCategoriaRNF";
			
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
			
		return manager.createQuery(sql, Long.class)
						.setParameter("mpCategoriaRNF", mpCategoriaRNF).getSingleResult();
	}
	public Long porCategoriaProjeto(MpCategoriaRNF mpCategoriaRNF, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRequisitoNaoFuncional c"
				+ " where c.mpCategoriaRNF = :mpCategoriaRNF AND c.mpProjeto = :mpProjeto";

		if (!tenantId.equals("0"))
			sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class)
				.setParameter("mpCategoriaRNF", mpCategoriaRNF)
				.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public long numeroRegistros() {
		//
    	System.out.println(
    		"MpRequisitoNaoFuncionals.encontrarQuantidadeRegistrosMpRequisitoNaoFuncional()");
//    			manager.createQuery("select count(c) from MpRequisitoNaoFuncional c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpRequisitoNaoFuncional c",
//																Long.class).getSingleResult();
		return 0L;
	}

}