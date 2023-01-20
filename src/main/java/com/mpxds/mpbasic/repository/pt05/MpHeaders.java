package com.mpxds.mpbasic.repository.pt05;

import java.io.Serializable;
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

import com.mpxds.mpbasic.model.pt05.MpHeader;
import com.mpxds.mpbasic.model.pt05.MpImportarControle;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt05.MpHeaderFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpHeaders implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpHeaderFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpHeader.class);
		
		if (filtro.getDataImportar() != null)
			criteria.add(Restrictions.ge("dataImportar", filtro.getDataImportar()));
		if (filtro.getDataImportar() != null)
			criteria.add(Restrictions.le("dataImportar", filtro.getDataImportar()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpHeader> filtrados(MpHeaderFilter filtro) {
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
	
	public int quantidadeFiltrados(MpHeaderFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpHeader guardar(MpHeader mpHeader) {
		try {
			return manager.merge(mpHeader);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse HEADER... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpHeader mpHeader) throws MpNegocioException {
		try {
			mpHeader = porId(mpHeader.getId());
			manager.remove(mpHeader);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("HEADER... não pode ser excluído.");
		}
	}

	public List<MpHeader> porMpImportarControle(MpImportarControle mpImportarControle) {
		try {
			return manager.createQuery(
						"from MpHeader where mpRemessa.mpImportarControle = :mpImportarControle",
																			MpHeader.class)
				.setParameter("mpImportarControle", mpImportarControle)
				.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpHeader porId(Long id) {
		return manager.find(MpHeader.class, id);
	}

	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpHeader.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpAtosComposicao c",
//										Long.class).getSingleResult();
	}

}