package com.mpxds.mpbasic.repository.pt05;

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

import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt05.MpAtoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAtos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpAtoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpAto.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(),
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAto> filtrados(MpAtoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpAtoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpAto guardar(MpAto mpAto) {
		try {
			return manager.merge(mpAto);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException("Erro de concorrência. Esse ATO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAto mpAtos) throws MpNegocioException {
		try {
			mpAtos = porId(mpAtos.getId());
			manager.remove(mpAtos);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ATO... não pode ser excluído.");
		}
	}

	public MpAto porCodigoSequencia(String codigo, String sequencia) {
		try {
			return manager.createQuery(
				"from MpAto where upper(codigo) = :codigo AND upper(sequencia) = :sequencia",
																					MpAto.class)
				.setParameter("codigo", codigo.toUpperCase())
				.setParameter("sequencia", sequencia.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpAto> byCodigoList() {
		return this.manager.createQuery("from MpAto ORDER BY codigo, sequencia", MpAto.class)
																			.getResultList();
	}

	public int countByCodigoSequencia(String codigo, String sequencia) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpAto.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.add(Restrictions.eq("sequencia", sequencia));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpAto porId(Long id) {
		return manager.find(MpAto.class, id);
	}

	public MpAto porNavegacao(String acao, String codigo, String sequencia) {
		//
//		System.out.println("MpAtoss.MpAtos ( " + acao + " / " + nome);
		//
		String codigoSequencia = codigo + sequencia;
		
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpAto where upper(codigo+sequencia) < :codigoSequencia" +
					" ORDER BY (codigo+sequencia) DESC", MpAto.class)
					.setParameter("codigoSequencia", codigoSequencia)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpAto where upper(codigo+sequencia) > :codigoSequencia" +
					" ORDER BY (codigo+sequencia) ASC", MpAto.class)
					.setParameter("codigoSequencia", codigoSequencia)
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
		
		Criteria criteria = session.createCriteria(MpAto.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpAtos c",
//										Long.class).getSingleResult();
	}

}