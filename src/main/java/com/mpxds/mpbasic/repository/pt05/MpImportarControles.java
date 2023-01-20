package com.mpxds.mpbasic.repository.pt05;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.pt05.MpImportarControle;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt05.MpImportarControleFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpImportarControles implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpImportarControleFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpImportarControle.class);
		
		if (filtro.getDataImportar() != null)
			criteria.add(Restrictions.ge("dataImportar", filtro.getDataImportar()));
		if (filtro.getDataImportar() != null)
			criteria.add(Restrictions.le("dataImportar", filtro.getDataImportar()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpImportarControle> filtrados(MpImportarControleFilter filtro) {
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
	
	public int quantidadeFiltrados(MpImportarControleFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpImportarControle guardar(MpImportarControle mpImportarControle) {
		try {
			return manager.merge(mpImportarControle);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Esse IMPORTAR CONTROLE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpImportarControle mpImportarControle) throws MpNegocioException {
		try {
			mpImportarControle = porId(mpImportarControle.getId());
			manager.remove(mpImportarControle);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("IMPORTAR CONTROLE... não pode ser excluído.");
		}
	}

	public List<MpImportarControle> porDataImportar(Date dataImportar) {
		try {
			return manager.createQuery(
							"from MpImportarControle where dataImportar = :dataImportar",
																		MpImportarControle.class)
				.setParameter("dataImportar", dataImportar)
				.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpImportarControle porId(Long id) {
		return manager.find(MpImportarControle.class, id);
	}

	public List<MpImportarControle> mpImportarControleList() {
		return manager.createQuery("from MpImportarControle ORDER BY dataImportar",
													MpImportarControle.class).getResultList();
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpImportarControle.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpAtosComposicao c",
//										Long.class).getSingleResult();
	}

}