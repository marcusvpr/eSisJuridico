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

import com.mpxds.mpbasic.model.pt01.MpTitulo;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpTituloFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTitulos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpTituloFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpTitulo.class);
		
		if (filtro.getDataProtocolo() != null)
			criteria.add(Restrictions.ge("dataProtocolo", filtro.getDataProtocolo()));
		if (filtro.getDataProtocolo() != null)
			criteria.add(Restrictions.le("dataProtocolo", filtro.getDataProtocolo()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTitulo> filtrados(MpTituloFilter filtro) {
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
	
	public int quantidadeFiltrados(MpTituloFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpTitulo guardar(MpTitulo mpTitulos) {
		try {
			return manager.merge(mpTitulos);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse TITULO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpTitulo mpTitulos) throws MpNegocioException {
		try {
			mpTitulos = porId(mpTitulos.getId());
			manager.remove(mpTitulos);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("TITULO... não pode ser excluído.");
		}
	}

	public MpTitulo porDataProtocolo(Date dataProtocolo) {
		try {
			return manager.createQuery("from MpTitulo where dataProtocolo = :dataProtocolo",
																				MpTitulo.class)
				.setParameter("dataProtocolo", dataProtocolo)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpTitulo> byDataProtocoloList() {
		return this.manager.createQuery("from MpTitulo ORDER BY dataProtocolo", MpTitulo.class)
																				.getResultList();
	}

	public int countByDataProtocolo(Date dataProtocolo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpTitulo.class);
		
		criteria.add(Restrictions.eq("dataProtocolo", dataProtocolo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpTitulo porId(Long id) {
		return manager.find(MpTitulo.class, id);
	}

	public MpTitulo porNavegacao(String acao, Date dataProtocolo) {
		//
//		System.out.println("MpTituloss.MpTitulos ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpTitulo where dataProtocolo < :dataProtocolo" +
					" ORDER BY dataProtocolo DESC", MpTitulo.class)
					.setParameter("dataProtocolo", dataProtocolo)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpTitulo where dataProtocolo > :dataProtocolo" +
					" ORDER BY dataProtocolo ASC", MpTitulo.class)
					.setParameter("dataProtocolo", dataProtocolo)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
			//			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpTitulo.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpTitulos c",
//										Long.class).getSingleResult();
	}

}