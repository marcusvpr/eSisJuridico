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

import com.mpxds.mpbasic.model.MpPessoaPaciente;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpPessoaPacienteFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaPacientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpPessoaPacienteFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaPaciente.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoaPaciente> filtrados(MpPessoaPacienteFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPessoaPacienteFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpPessoaPaciente guardar(MpPessoaPaciente mpPessoaPaciente) {
		try {
			return manager.merge(mpPessoaPaciente);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA PACIENTE... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoaPaciente mpPessoaPaciente) throws MpNegocioException {
		try {
			mpPessoaPaciente = porId(mpPessoaPaciente.getId());
			manager.remove(mpPessoaPaciente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PESSOA PACIENTE... não pode ser excluída.");
		}
	}

	public MpPessoaPaciente porNome(String nome) {
		try {
			return manager.createQuery("from MpPessoaPaciente where upper(nome) = :nome",
																			MpPessoaPaciente.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoaPaciente> porNomeList(String nome) {
		return this.manager.createQuery("from MpPessoaPaciente " +
					"where upper(nome) like :nome ORDER BY nome", MpPessoaPaciente.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}
	
	public MpPessoaPaciente porId(Long id) {
		return manager.find(MpPessoaPaciente.class, id);
	}
		
}