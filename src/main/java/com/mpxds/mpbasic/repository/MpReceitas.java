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

import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.repository.filter.MpReceitaFilter;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpReceitas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public MpReceita guardar(MpReceita mpReceita) {
		try {
			return manager.merge(mpReceita);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa RECEITA... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpReceita mpReceita) throws MpNegocioException {
		try {
			mpReceita = porId(mpReceita.getId());
			manager.remove(mpReceita);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("RECEITA... não pode ser excluído.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MpReceita> filtrados(MpReceitaFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(MpReceita.class);
		
		if (filtro.getDataCriacaoDe() != null)
			criteria.add(Restrictions.ge("dataReceita", filtro.getDataCriacaoDe()));
		if (filtro.getDataCriacaoAte() != null)
			criteria.add(Restrictions.le("dataReceita", filtro.getDataCriacaoAte()));
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), 
																		MatchMode.ANYWHERE));
		//
		return criteria.addOrder(Order.desc("dataReceita")).list();
	}

	public MpReceita porId(Long id) {
		return manager.find(MpReceita.class, id);
	}

	public List<MpReceita> porDescricao(String descricao) {
		return this.manager.createQuery(
			"from MpReceita where upper(descricao) like :descricao ORDER BY descricao",
			MpReceita.class).setParameter("descricao", descricao.toUpperCase() + "%").
																			getResultList();
	}

	public MpReceita porNavegacao(String acao, String descricao) {
		//
//		System.out.println("MpReceitas.MpReceita ( " + acao + " / " + descricao);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpReceita where upper(descricao) < :descricao ORDER BY descricao DESC",
					MpReceita.class)
					.setParameter("descricao", descricao.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpReceita where upper(descricao) > :descricao ORDER BY descricao ASC",
					MpReceita.class)
					.setParameter("descricao", descricao.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpReceita.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpReceita c",
//										Long.class).getSingleResult();
	}
		
}
