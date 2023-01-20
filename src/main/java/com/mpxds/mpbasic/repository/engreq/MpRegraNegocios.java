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

import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.model.engreq.MpRegraNegocio;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpRegraNegocioFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpRegraNegocios implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpRegraNegocioFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpRegraNegocio.class);
		
		if (StringUtils.isNotBlank(filtro.getIdRN()))
			criteria.add(Restrictions.ilike("idRN", filtro.getIdRN(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDependencia()))
			criteria.add(Restrictions.ilike("dependencia", filtro.getDependencia(),
																				MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpRegraNegocio> filtrados(MpRegraNegocioFilter filtro) {
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
	
	public int quantidadeFiltrados(MpRegraNegocioFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpRegraNegocio guardar(MpRegraNegocio mpRegraNegocio) {
		try {
			return manager.merge(mpRegraNegocio);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Essa REGRA NEGOCIO... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpRegraNegocio mpRegraNegocio) throws MpNegocioException {
		//
		try {
			mpRegraNegocio = porId(mpRegraNegocio.getId());
			
			manager.remove(mpRegraNegocio);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("REGRA NEGOCIO... não pode ser excluído(a).");
		}
	}

	public MpRegraNegocio porIdRN(String idRN) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpRegraNegocio where upper(idRN) = :idRN";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpRegraNegocio.class)
							.setParameter("idRN", idRN.toUpperCase()).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpRegraNegocio porId(Long id) {
		//
		return manager.find(MpRegraNegocio.class, id);
	}
	
	public List<MpRegraNegocio> mpRegraNegocioList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpRegraNegocio";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY idRN ASC";
		//
		return manager.createQuery(hql,	MpRegraNegocio.class).getResultList();
	}

	public Long porStatus(MpStatusRequisito mpStatusRequisito) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRegraNegocio c where "
											+ "c.mpStatusRequisito = :mpStatusRequisito";
			
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
			
		return manager.createQuery(sql, Long.class)
						.setParameter("mpStatusRequisito", mpStatusRequisito).getSingleResult();
	}
	public Long porStatusProjeto(MpStatusRequisito mpStatusRequisito, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRegraNegocio c"
				+ " where c.mpStatusRequisito = :mpStatusRequisito AND c.mpProjeto = :mpProjeto";

		if (!tenantId.equals("0"))
			sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class)
				.setParameter("mpStatusRequisito", mpStatusRequisito)
				.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public Long porComplexibilidade(MpComplexibilidade mpComplexibilidade) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRegraNegocio c" + 
											" where c.mpComplexibilidade = :mpComplexibilidade";
		
		if (!tenantId.equals("0")) sql = sql + " AND tenantId = '" + tenantId + "'";
		//	
		return manager.createQuery(sql, Long.class)
						.setParameter("mpComplexibilidade", mpComplexibilidade).getSingleResult();
	}
	public Long porComplexibilidadeProjeto(MpComplexibilidade mpComplexibilidade,
																			MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String sql = "select count(c) from MpRegraNegocio c"
				+ " where c.mpComplexibilidade = :mpComplexibilidade AND c.mpProjeto = :mpProjeto";

		if (!tenantId.equals("0"))
			sql = sql + " AND tenantId = '" + tenantId + "'";
		//
		return manager.createQuery(sql, Long.class)
				.setParameter("mpComplexibilidade", mpComplexibilidade)
				.setParameter("mpProjeto", mpProjeto).getSingleResult();
	}
	
	public long numeroRegistros() {
		//
    	System.out.println("MpRegraNegocios.encontrarQuantidadeRegistrosMpRegraNegocio()");
//    			manager.createQuery("select count(c) from MpRegraNegocio c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpRegraNegocio c",
//																Long.class).getSingleResult();
		return 0L;
	}

}