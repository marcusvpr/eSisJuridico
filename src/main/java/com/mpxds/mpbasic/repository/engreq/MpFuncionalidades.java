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

import com.mpxds.mpbasic.model.engreq.MpFuncionalidade;
import com.mpxds.mpbasic.model.engreq.MpModulo;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpFuncionalidadeFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpFuncionalidades implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpFuncionalidadeFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpFuncionalidade.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpFuncionalidade> filtrados(MpFuncionalidadeFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getMpFilterOrdenacao().getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getMpFilterOrdenacao().getQuantidadeRegistros());
		
		if (filtro.getMpFilterOrdenacao().isAscendente() && filtro.getMpFilterOrdenacao().
															getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.asc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		else if (filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.desc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		//
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MpFuncionalidadeFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpFuncionalidade guardar(MpFuncionalidade mpFuncionalidade) {
		//
		try {
			return manager.merge(mpFuncionalidade);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Essa FUNCIONALIDADE... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpFuncionalidade mpFuncionalidade) throws MpNegocioException {
		//
		try {
			mpFuncionalidade = porId(mpFuncionalidade.getId());
			
			manager.remove(mpFuncionalidade);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("FUNCIONALIDADE... não pode ser excluído(a).");
		}
	}

	public MpFuncionalidade porTitulo(String descricao) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpFuncionalidade where upper(descricao) = :descricao";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpFuncionalidade.class)
				.setParameter("descricao", descricao.toUpperCase()).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpFuncionalidade> porModuloList(MpModulo mpModulo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpFuncionalidade where mpModulo = :mpModulo" ;
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpFuncionalidade.class)
											.setParameter("mpModulo", mpModulo).getResultList();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpFuncionalidade porId(Long id) {
		//
		return manager.find(MpFuncionalidade.class, id);
	}
	
	public List<MpFuncionalidade> mpFuncionalidadeList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpFuncionalidade";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;		
		//
		return manager.createQuery(hql,	MpFuncionalidade.class).getResultList();
	}
	
	public long numeroRegistros() {
		//
    	System.out.println("MpFuncionalidades.encontrarQuantidadeRegistrosMpFuncionalidade()");
//    			manager.createQuery("select count(c) from MpFuncionalidade c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpFuncionalidade c",
//																Long.class).getSingleResult();
		return 0L;
	}

}