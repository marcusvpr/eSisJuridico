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

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpArquivoBDFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpArquivoBDs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpArquivoBDFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpArquivoBD.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(),
																			MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpArquivoBD> filtrados(MpArquivoBDFilter filtro) {
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
	
	public int quantidadeFiltrados(MpArquivoBDFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpArquivoBD guardar(MpArquivoBD mpArquivoBD) {
		try {
			return manager.merge(mpArquivoBD);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa ARQUIVO... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpArquivoBD mpArquivoBD) throws MpNegocioException {
		try {
			mpArquivoBD = porId(mpArquivoBD.getId());
			manager.remove(mpArquivoBD);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("IMAGEM... não pode ser excluída.");
		}
	}

	public List<MpArquivoBD> porDescricaoList(String descricao) {
		return this.manager.createQuery("from MpArquivoBD " +
					"where upper(descricao) like :descricao ORDER BY descricao", MpArquivoBD.class)
					.setParameter("descricao", descricao.toUpperCase() + "%")
					.getResultList();
	}

	public MpArquivoBD porId(Long id) {
		return manager.find(MpArquivoBD.class, id);
	}

	public List<MpArquivoBD> porMpArquivoBDList() {
		return manager.createQuery("from MpArquivoBD ORDER BY descricao", 
																MpArquivoBD.class).getResultList();
	}
		
	public MpArquivoBD porDescricao(String descricao) {
		try {
			return manager.createQuery("from MpArquivoBD where upper(descricao) = :descricao",
																				MpArquivoBD.class)
				.setParameter("descricao", descricao.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpArquivoBD porNavegacao(String acao, String descricao) {
		//
//		System.out.println("MpArquivoBDs.MpArquivoBD ( " + acao + " / " + descricao);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpArquivoBD where upper(descricao) < :descricao ORDER BY descricao DESC",
					MpArquivoBD.class)
					.setParameter("descricao", descricao.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpArquivoBD where upper(descricao) > :descricao ORDER BY descricao ASC",
					MpArquivoBD.class)
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
		
		Criteria criteria = session.createCriteria(MpArquivoBD.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		//
	}
	
	@MpTransactional
	public String executaSQL(String sql) {
		//
		try {
			int result = manager.createNativeQuery(sql).executeUpdate();
			//
			return "" + result;
			//
		} catch (NoResultException e) {
			return null;
		}
	}

}