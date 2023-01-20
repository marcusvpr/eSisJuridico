package com.mpxds.mpbasic.repository.sisJuri;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpPessoaFisicaFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaFisicas implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpPessoaFisicaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaFisica.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoaFisica> filtrados(MpPessoaFisicaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		//
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
	
	public int quantidadeFiltrados(MpPessoaFisicaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpPessoaFisica guardar(MpPessoaFisica mpPessoaFisica) {
		try {
			return manager.merge(mpPessoaFisica);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA FÍSICA... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoaFisica mpPessoaFisica) throws MpNegocioException {
		try {
			mpPessoaFisica = porId(mpPessoaFisica.getId());
			manager.remove(mpPessoaFisica);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PESSOA FÍSICA... não pode ser excluída.");
		}
	}

	public MpPessoaFisica porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpPessoaFisica where upper(codigo) = :codigo",
																			MpPessoaFisica.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoaFisica porNome(String nome) {
		try {
			return manager.createQuery("from MpPessoaFisica where upper(nome) = :nome",
																			MpPessoaFisica.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoaFisica> byNomeList() {
		return this.manager.createQuery("from MpPessoaFisica ORDER BY nome", MpPessoaFisica.class)
					.getResultList();
	}

	public List<MpPessoaFisica> porNomeList(String nome) {
		return this.manager.createQuery("from MpPessoaFisica " +
					"where upper(nome) like :nome ORDER BY nome", MpPessoaFisica.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaFisica.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoaFisica porId(Long id) {
		//
		return manager.find(MpPessoaFisica.class, id);
	}
	public MpPessoaFisica porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpPessoaFisica where idCarga = :idCarga", MpPessoaFisica.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoaFisica porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpPessoaFisicas.MpPessoaFisica ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpPessoaFisica where upper(nome) < :nome ORDER BY nome DESC",
					MpPessoaFisica.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpPessoaFisica where upper(nome) > :nome ORDER BY nome ASC",
					MpPessoaFisica.class)
					.setParameter("nome", nome.toUpperCase())
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
		
		Criteria criteria = session.createCriteria(MpPessoaFisica.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpPessoaFisica c",
//										Long.class).getSingleResult();
	}

}