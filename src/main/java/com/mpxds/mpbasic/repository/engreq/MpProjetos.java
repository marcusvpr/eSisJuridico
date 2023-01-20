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
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpProjetoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProjetos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpProjetoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpProjeto.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpProjeto> filtrados(MpProjetoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpProjetoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpProjeto guardar(MpProjeto mpProjeto) {
		//
		try {
			return manager.merge(mpProjeto);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse PROJETO... já foi alterado(a) anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpProjeto mpProjeto) throws MpNegocioException {
		//
		try {
			mpProjeto = porId(mpProjeto.getId());
			
			manager.remove(mpProjeto);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("PROJETO... não pode ser excluído(a).");
		}
	}

	public MpProjeto porNome(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpProjeto where upper(nome) = :nome";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpProjeto.class)
							.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpProjeto porId(Long id) {
		//
		return manager.find(MpProjeto.class, id);
	}
	
	public List<MpProjeto> mpProjetoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpProjeto";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql = hql + " ORDER BY nome ASC";
		//
		return manager.createQuery(hql,	MpProjeto.class).getResultList();
	}
	
	public MpProjeto porNavegacao(String acao, String nome) {
		//
			//
			String tenantId = mpSeguranca.capturaTenantId().trim();
			
			try {
				//
				String hql = "";
				String order = "";

				if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
					hql = "from MpProjeto where upper(nome) < :nome";
					order = " ORDER BY nome DESC";
				} else
				if (acao.equals("mpFirst") || acao.equals("mpNext")) {
					hql = "from MpProjeto where upper(nome) > :nome";
					order = " ORDER BY nome ASC";
				}
				//
				if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

				return manager.createQuery(hql + order,	MpProjeto.class)
						.setParameter("nome", nome.toUpperCase())
						.setMaxResults(1).getSingleResult();
				//						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long numeroRegistros() {

    	System.out.println("MpProjetos.encontrarQuantidadeRegistrosMpProjeto()");
//    			manager.createQuery("select count(c) from MpProjeto c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpProjeto c",
//																Long.class).getSingleResult();
		return 0L;
	}

}