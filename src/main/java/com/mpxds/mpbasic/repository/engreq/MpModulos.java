package com.mpxds.mpbasic.repository.engreq;

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

import com.mpxds.mpbasic.model.engreq.MpModulo;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpModuloFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpModulos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpModuloFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpModulo.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpModulo> filtrados(MpModuloFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpModuloFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpModulo guardar(MpModulo mpModulo) {
		//
		try {
			return manager.merge(mpModulo);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Esse MODULO... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpModulo mpModulo) throws MpNegocioException {
		//
		try {
			mpModulo = porId(mpModulo.getId());
			
			manager.remove(mpModulo);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("MODULO... não pode ser excluído(a).");
		}
	}

	public MpModulo porTitulo(String descricao) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpModulo where upper(descricao) = :descricao";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpModulo.class)
						.setParameter("descricao", descricao.toUpperCase()).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpModulo porId(Long id) {
		//
		return manager.find(MpModulo.class, id);
	}
	
	public List<MpModulo> mpModuloList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpModulo";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY descricao ASC";
		
		return manager.createQuery(hql,	MpModulo.class).getResultList();
	}
	
	public long numeroRegistros() {
		//
    	System.out.println("MpModulos.encontrarQuantidadeRegistrosMpModulo()");
//    			manager.createQuery("select count(c) from MpModulo c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpModulo c",
//																Long.class).getSingleResult();
		return 0L;
	}

}