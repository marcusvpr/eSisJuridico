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

import com.mpxds.mpbasic.model.MpTenant;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpTenantFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTenants implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpTenantFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpTenant.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.ilike("mpStatus.descricao", filtro.getStatus(),
																			MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTenant> filtrados(MpTenantFilter filtro) {
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
	
	public int quantidadeFiltrados(MpTenantFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpTenant guardar(MpTenant mpTenant) {
		try {
			return manager.merge(mpTenant);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse TENANT... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpTenant mpTenant) throws MpNegocioException {
		try {
			mpTenant = porId(mpTenant.getId());
			manager.remove(mpTenant);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Tenant... não pode ser excluído.");
		}
	}

	public MpTenant porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpTenant where upper(codigo) = :codigo", MpTenant.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpTenant porId(Long id) {
		return manager.find(MpTenant.class, id);
	}
	
	public List<MpTenant> mpTenantList() {
		return manager.createQuery("from MpTenant ORDER By codigo", 
														MpTenant.class).getResultList();
	}
	
	public MpTenant porNavegacao(String acao, String codigo) {
		//
//		System.out.println("MpTenants.MpTenant ( " + acao + " / " + codigo);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpTenant where upper(codigo) < :codigo ORDER BY codigo DESC",
					MpTenant.class)
					.setParameter("codigo", codigo.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpTenant where upper(codigo) > :codigo ORDER BY codigo ASC",
					MpTenant.class)
					.setParameter("codigo", codigo.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}