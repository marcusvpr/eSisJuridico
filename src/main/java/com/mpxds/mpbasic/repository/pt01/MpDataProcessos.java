package com.mpxds.mpbasic.repository.pt01;

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

import com.mpxds.mpbasic.model.pt01.MpDataProcesso;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpDataProcessoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpDataProcessos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpDataProcessoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpDataProcesso.class);
		
		if (filtro.getDataProtocolo() != null)
			criteria.add(Restrictions.ge("dataProtocolo", filtro.getDataProtocolo()));
		if (filtro.getDataProtocolo() != null)
			criteria.add(Restrictions.le("dataProtocolo", filtro.getDataProtocolo()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpDataProcesso> filtrados(MpDataProcessoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpDataProcessoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpDataProcesso guardar(MpDataProcesso mpDataProcessos) {
		try {
			return manager.merge(mpDataProcessos);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Essa DATA PROCESSO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpDataProcesso mpDataProcessos) throws MpNegocioException {
		try {
			mpDataProcessos = porId(mpDataProcessos.getId());
			manager.remove(mpDataProcessos);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("DATA PROCESSO... não pode ser excluído.");
		}
	}

	public MpDataProcesso porDataProtocolo(Date dataProtocolo) {
		try {
			return manager.createQuery(
				"from MpDataProcesso where dataProtocolo = :dataProtocolo",
				MpDataProcesso.class).setParameter("dataProtocolo", dataProtocolo)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpDataProcesso> byCodigoList() {
		return this.manager.createQuery("from MpDataProcesso ORDER BY dataProtocolo",
														MpDataProcesso.class).getResultList();
	}

	public int countByDataProtocolo(Date dataProtocolo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpDataProcesso.class);
		
		criteria.add(Restrictions.eq("dataProtocolo", dataProtocolo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpDataProcesso porId(Long id) {
		return manager.find(MpDataProcesso.class, id);
	}

	public MpDataProcesso porNavegacao(String acao, Date dataProtocolo) {
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpDataProcesso where dataProtocolo < :dataProtocolo" +
					" ORDER BY dataProtocolo DESC", MpDataProcesso.class)
					.setParameter("dataProtocolo", dataProtocolo).setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpDataProcesso where dataProtocolo > :dataProtocolo" +
					" ORDER BY dataProtocolo ASC", MpDataProcesso.class)
					.setParameter("dataProtocolo", dataProtocolo).setMaxResults(1)
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
		
		Criteria criteria = session.createCriteria(MpDataProcesso.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpDataProcessos c",
//										Long.class).getSingleResult();
	}

}