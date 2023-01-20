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

import com.mpxds.mpbasic.model.MpDependente;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpDependenteFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpDependentes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpDependenteFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpDependente.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpDependente> filtrados(MpDependenteFilter filtro) {
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
	
	public int quantidadeFiltrados(MpDependenteFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpDependente guardar(MpDependente mpDependente) {
		try {
			return manager.merge(mpDependente);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse DEPENDENTE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpDependente mpDependente) throws MpNegocioException {
		try {
			mpDependente = porId(mpDependente.getId());
			manager.remove(mpDependente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("DEPENDENTE... não pode ser excluído.");
		}
	}

	public MpDependente porNome(String nome) {
		try {
			return manager.createQuery("from MpDependente where upper(nome) = :nome",
																			MpDependente.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpDependente> porNomeList(String nome) {
		return this.manager.createQuery("from MpDependente " +
					"where upper(nome) like :nome ORDER BY nome", MpDependente.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}
	
	public MpDependente porId(Long id) {
		return manager.find(MpDependente.class, id);
	}
		
}