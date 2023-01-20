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

import com.mpxds.mpbasic.model.pt01.MpBanco;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpBancoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpBancos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpBancoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpBanco.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpBanco> filtrados(MpBancoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpBancoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpBanco guardar(MpBanco mpBancos) {
		try {
			return manager.merge(mpBancos);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse BANCO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpBanco mpBancos) throws MpNegocioException {
		try {
			mpBancos = porId(mpBancos.getId());
			manager.remove(mpBancos);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("BANCO... não pode ser excluído.");
		}
	}

	public MpBanco porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpBanco where upper(codigo) = :codigo",
																				MpBanco.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpBanco> byCodigoList() {
		return this.manager.createQuery("from MpBanco ORDER BY codigo", MpBanco.class)
																			.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpBanco.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpBanco porId(Long id) {
		return manager.find(MpBanco.class, id);
	}

	public MpBanco porNavegacao(String acao, String codigo) {
		//
//		System.out.println("MpBancoss.MpBancos ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpBanco where codigo < :codigo" +
					" ORDER BY codigo DESC", MpBanco.class)
					.setParameter("codigo", codigo)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpBanco where codigo > :codigo" +
					" ORDER BY codigo ASC", MpBanco.class)
					.setParameter("codigo", codigo)
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
		
		Criteria criteria = session.createCriteria(MpBanco.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpBancos c",
//										Long.class).getSingleResult();
	}

}