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

import com.mpxds.mpbasic.model.pt01.MpPessoaTitulo;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpPessoaTituloFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaTitulos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpPessoaTituloFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaTitulo.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoaTitulo> filtrados(MpPessoaTituloFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPessoaTituloFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpPessoaTitulo guardar(MpPessoaTitulo mpPessoaTitulos) {
		try {
			return manager.merge(mpPessoaTitulos);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoaTitulo mpPessoaTitulos) throws MpNegocioException {
		try {
			mpPessoaTitulos = porId(mpPessoaTitulos.getId());
			manager.remove(mpPessoaTitulos);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Pessoa... não pode ser excluída.");
		}
	}

	public MpPessoaTitulo porNome(String nome) {
		try {
			return manager.createQuery("from MpPessoaTitulo where upper(nome) = :nome",
																				MpPessoaTitulo.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoaTitulo> byNomeList() {
		return this.manager.createQuery("from MpPessoaTitulo ORDER BY nome", MpPessoaTitulo.class)
																			.getResultList();
	}

	public int countByNome(String nome) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaTitulo.class);
		
		criteria.add(Restrictions.eq("nome", nome));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoaTitulo porId(Long id) {
		return manager.find(MpPessoaTitulo.class, id);
	}

	public MpPessoaTitulo porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpPessoaTituloss.MpPessoaTitulos ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpPessoaTitulo where nome < :nome" +
					" ORDER BY nome DESC", MpPessoaTitulo.class)
					.setParameter("nome", nome)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpPessoaTitulo where nome > :nome" +
					" ORDER BY nome ASC", MpPessoaTitulo.class)
					.setParameter("nome", nome)
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
		
		Criteria criteria = session.createCriteria(MpPessoaTitulo.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpPessoaTitulos c",
//										Long.class).getSingleResult();
	}

}