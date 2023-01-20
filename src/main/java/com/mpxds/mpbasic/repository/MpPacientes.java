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

import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpPacienteFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPacientes implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpPacienteFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
				
		Criteria criteria = session.createCriteria(MpPaciente.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.eq("mpStatus", MpStatus.valueOf(filtro.getStatus())));
		//		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPaciente> filtrados(MpPacienteFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpPacienteFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpPaciente guardar(MpPaciente mpPaciente) {
		//
		try {
			return manager.merge(mpPaciente);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Esse PACIENTE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPaciente mpPaciente) throws MpNegocioException {
		//
		try {
			mpPaciente = porId(mpPaciente.getId());
			manager.remove(mpPaciente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PACIENTE... não pode ser excluída.");
		}
	}

	public MpPaciente porNome(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "from MpPaciente where upper(nome) = :nome";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpPaciente.class)
								.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpPaciente porId(Long id) {
		//
		return manager.find(MpPaciente.class, id);
	}
	
	public List<MpPaciente> mpPacienteAtivos() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpPaciente where mpStatus = 'ATIVO'";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPaciente.class).getResultList();
	}

	public MpPaciente porNavegacao(String acao, String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			//
			String hql = "";
			String order = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpPaciente where upper(nome) < :nome";
				order = " ORDER BY nome DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpPaciente where upper(nome) > :nome";
				order = " ORDER BY nome ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpPaciente.class)
					.setParameter("nome", nome.toUpperCase()).setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}