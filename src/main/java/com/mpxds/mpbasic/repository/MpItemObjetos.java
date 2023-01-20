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

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpItemObjeto;
import com.mpxds.mpbasic.repository.filter.MpItemObjetoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpItemObjetos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpItemObjetoFilter filtro, String tenantId) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
				
		Criteria criteria = session.createCriteria(MpItemObjeto.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpItemObjeto> filtrados(MpItemObjetoFilter filtro, String tenantId) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro, tenantId);
		//
		criteria.setFirstResult(filtro.getMpFilterOrdenacao().getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getMpFilterOrdenacao().getQuantidadeRegistros());
		
		if (filtro.getMpFilterOrdenacao().isAscendente() && filtro.getMpFilterOrdenacao().
															getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.asc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		else if (filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.desc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		//
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MpItemObjetoFilter filtro, String tenantId) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro, tenantId);
		
		criteria.setProjection(Projections.rowCount());
		//
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpItemObjeto guardar(MpItemObjeto mpItemObjeto) {
		//
		try {
			return manager.merge(mpItemObjeto);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ITEM OBJETO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpItemObjeto mpItemObjeto) throws MpNegocioException {
		//
		try {
			mpItemObjeto = porId(mpItemObjeto.getId());
			
			manager.remove(mpItemObjeto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ITEM OBJETO... não pode ser excluído.");
		}
	}

	public MpItemObjeto porNome(String nome, String tenantId) {
		//
		try {
			//
			String hql = "from MpItemObjeto where upper(nome) = :nome";
			
			hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql, MpItemObjeto.class)
						.setParameter("nome", nome.toUpperCase()).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpItemObjeto> porNomeList(String nome, String tenantId) {
		//
		String hql = "from MpItemObjeto where upper(nome) = :nome";
		
		hql = hql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(hql, MpItemObjeto.class)
					.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
	
	public MpItemObjeto porId(Long id) {
		//
		return manager.find(MpItemObjeto.class, id);
	}
		
}