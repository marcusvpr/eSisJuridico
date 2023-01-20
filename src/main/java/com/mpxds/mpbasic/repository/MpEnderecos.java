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

import com.mpxds.mpbasic.model.MpEndereco;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpEnderecoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpEnderecos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpEnderecoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpEndereco.class);
		
		if (StringUtils.isNotBlank(filtro.getLogradouro()))
			criteria.add(Restrictions.ilike("logradouro", filtro.getLogradouro(),
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpEndereco> filtrados(MpEnderecoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpEnderecoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpEndereco guardar(MpEndereco mpEndereco) {
		try {
			return manager.merge(mpEndereco);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ENDEREÇO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpEndereco mpEndereco) throws MpNegocioException {
		try {
			mpEndereco = porId(mpEndereco.getId());
			manager.remove(mpEndereco);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ENDEREÇO... não pode ser excluído.");
		}
	}

	public MpEndereco porNome(String logradouro) {
		try {
			return manager.createQuery("from MpEndereco where upper(logradouro) = :logradouro",
																				MpEndereco.class)
				.setParameter("logradouro", logradouro.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpEndereco> porNomeList(String logradouro) {
		return this.manager.createQuery("from MpEndereco " +
				"where upper(logradouro) like :logradouro ORDER BY logradouro", MpEndereco.class)
				.setParameter("logradouro", logradouro.toUpperCase() + "%")
				.getResultList();
	}
	
	public MpEndereco porId(Long id) {
		return manager.find(MpEndereco.class, id);
	}
		
}