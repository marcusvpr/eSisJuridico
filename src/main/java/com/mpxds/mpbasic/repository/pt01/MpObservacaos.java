package com.mpxds.mpbasic.repository.pt01;

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

import com.mpxds.mpbasic.model.pt01.MpObservacao;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpObservacaoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpObservacaos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpObservacaoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpObservacao.class);
		
		if (StringUtils.isNotBlank(filtro.getTipoObservacao()))
			criteria.add(Restrictions.ilike("tipoObservacao", filtro.getTipoObservacao(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricaoObservacao()))
			criteria.add(Restrictions.ilike("descricaoObservacao", filtro.getDescricaoObservacao(), 
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpObservacao> filtrados(MpObservacaoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpObservacaoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpObservacao guardar(MpObservacao mpObservacao) {
		try {
			return manager.merge(mpObservacao);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa OBSERVACAO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpObservacao mpObservacao) throws MpNegocioException {
		try {
			mpObservacao = porId(mpObservacao.getId());
			manager.remove(mpObservacao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("OBSERVAÇÃO... não pode ser excluído.");
		}
	}

	public MpObservacao porTipoObservacao(String tipoObservacao) {
		try {
			return manager.createQuery(
				"from MpObservacao where upper(tipoObservacao) = :tipoObservacao",
				MpObservacao.class)
				.setParameter("tipoObservacao", tipoObservacao.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpObservacao> byCodigoList() {
		return this.manager.createQuery("from MpObservacao ORDER BY tipoObservacao", 
															MpObservacao.class).getResultList();
	}

	public int countByTipoObservacao(String tipoObservacao) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpObservacao.class);
		
		criteria.add(Restrictions.eq("tipoObservacao", tipoObservacao));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpObservacao porId(Long id) {
		return manager.find(MpObservacao.class, id);
	}

	public MpObservacao porNavegacao(String acao, String tipoObservacao) {
		//
//		System.out.println("MpObservacaoss.MpObservacao ( " + acao + " / " + tipoObservacao);
		//		
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpObservacao where upper(tipoObservacao) < :tipoObservacao" +
					" ORDER BY (tipoObservacao) DESC", MpObservacao.class)
					.setParameter("tipoObservacao", tipoObservacao.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpObservacao where upper(tipoObservacao) > :tipoObservacao" +
					" ORDER BY (tipoObservacao) ASC", MpObservacao.class)
					.setParameter("tipoObservacao", tipoObservacao.toUpperCase())
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
		
		Criteria criteria = session.createCriteria(MpObservacao.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpEspecies c",
//										Long.class).getSingleResult();
	}

}