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

import com.mpxds.mpbasic.model.engreq.MpMacroRequisito;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpMacroRequisitoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpMacroRequisitos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpMacroRequisitoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpMacroRequisito.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpMacroRequisito> filtrados(MpMacroRequisitoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpMacroRequisitoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpMacroRequisito guardar(MpMacroRequisito mpMacroRequisito) {
		//
		try {
			return manager.merge(mpMacroRequisito);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse MACRO REQUSITO... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpMacroRequisito mpMacroRequisito) throws MpNegocioException {
		//
		try {
			mpMacroRequisito = porId(mpMacroRequisito.getId());
			
			manager.remove(mpMacroRequisito);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("MACRO REQUSITO... não pode ser excluído(a).");
		}
	}

	public MpMacroRequisito porDescricao(String descricao) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpMacroRequisito where upper(descricao) = :descricao";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpMacroRequisito.class)
							.setParameter("descricao", descricao.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpMacroRequisito porId(Long id) {
		//
		return manager.find(MpMacroRequisito.class, id);
	}
	
	public List<MpMacroRequisito> mpMacroRequisitoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpMacroRequisito";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY descricao ASC";
		
		return manager.createQuery(hql,	MpMacroRequisito.class).getResultList();
	}
		
	public long numeroRegistros() {
		//
    	System.out.println("MpMacroRequisitos.encontrarQuantidadeRegistrosMpMacroRequisito()");
//    			manager.createQuery("select count(c) from MpMacroRequisito c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpMacroRequisito c",
//																Long.class).getSingleResult();
		return 0L;
	}

}