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
import com.mpxds.mpbasic.model.engreq.MpSequencia;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpSequenciaFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpSequencias implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpSequenciaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpSequencia.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpSequencia> filtrados(MpSequenciaFilter filtro) {
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
	
	public int quantidadeFiltrados(MpSequenciaFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpSequencia guardar(MpSequencia mpSequencia) {
		//
		try {
			return manager.merge(mpSequencia);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa SEQUENCIA... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpSequencia mpSequencia) throws MpNegocioException {
		//
		try {
			mpSequencia = porId(mpSequencia.getId());
			
			manager.remove(mpSequencia);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("SEQUENCIA... não pode ser excluído(a).");
		}
	}

	public MpSequencia porCodigo(String codigo, MpProjeto mpProjeto) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = 
				"from MpSequencia where upper(codigo) = :codigo AND mpProjeto = :mpProjeto";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpSequencia.class)
									.setParameter("codigo", codigo.toUpperCase())
									.setParameter("mpProjeto", mpProjeto).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpSequencia porId(Long id) {
		//
		return manager.find(MpSequencia.class, id);
	}
	
	public List<MpSequencia> mpSequenciaList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpSequencia";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY codigo ASC";
		
		return manager.createQuery(hql,	MpSequencia.class).getResultList();
	}
		
	public long numeroRegistros() {
		//
    	System.out.println("MpSequencias.encontrarQuantidadeRegistrosMpSequencia()");
//    			manager.createQuery("select count(c) from MpSequencia c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpSequencia c",
//																Long.class).getSingleResult();
		return 0L;
	}

}