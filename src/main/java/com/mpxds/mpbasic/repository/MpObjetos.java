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

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpObjetoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpObjetos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpObjetoFilter filtro, String tenantId) {
		//
		Session session = this.manager.unwrap(Session.class);
				
		session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpObjeto.class);
		
		if (StringUtils.isNotBlank(filtro.getTransacao()))
			criteria.add(Restrictions.ilike("transacao", filtro.getTransacao(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getCodigoId()))
			criteria.add(Restrictions.ilike("codigoId", filtro.getCodigoId(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.ilike("mpStatus.descricao", filtro.getStatus(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getClasseAssociada()))
			criteria.add(Restrictions.ilike("classeAssociada", filtro.getStatus(), 
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpObjeto> filtrados(MpObjetoFilter filtro, String tenantId) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro, tenantId);
		//
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
	
	public int quantidadeFiltrados(MpObjetoFilter filtro, String tenantId) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro, tenantId);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpObjeto guardar(MpObjeto mpObjeto) {
		//
		try {
			return manager.merge(mpObjeto);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
							"Erro de concorrência. Esse OBJETO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpObjeto mpObjeto) throws MpNegocioException {
		//
		try {
			mpObjeto = porId(mpObjeto.getId());
			
			manager.remove(mpObjeto);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("Objeto... não pode ser excluído.");
		}
	}

	public MpObjeto porCodigo(String codigo, String tenantId) {
		//
		try {
			String hql = "from MpObjeto where upper(codigo) = :codigo";
			
			hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql, MpObjeto.class)
						.setParameter("codigo", codigo.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpObjeto porClasseAssociada(String classeAssociada, String tenantId) {
		//
		try {
			String hql = "from MpObjeto where upper(classeAssociada) = :classeAssociada";
			
			hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql, MpObjeto.class)
						.setParameter("classeAssociada", classeAssociada.toUpperCase())
						.getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpObjeto porCodigoId(String codigoId, String tenantId) {
		//
		try {
			String hql = "from MpObjeto where upper(codigoId) = :codigoId";
			
			hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql, MpObjeto.class)
						.setParameter("codigoId", codigoId.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpObjeto porTransacao(String transacao, String tenantId) {
		//
		try {
			String hql = "from MpObjeto where upper(transacao) = :transacao";
			
			hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql, MpObjeto.class)
						.setParameter("transacao", transacao.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpObjeto> porNome(String nome, String tenantId) {
		//
		String hql = "from MpObjeto where upper(nome) like :nome";
		
		hql = hql + " WHERE tenantId = '" + tenantId + "'";
		
		hql = hql + " ORDER BY nome";
		//
		return this.manager.createQuery(hql, MpObjeto.class).
							setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
	
	public MpObjeto porId(Long id) {
		//
		return manager.find(MpObjeto.class, id);
	}
	
	public List<MpObjeto> mpObjetoList(String tenantId) {
		//
		String hql = "from MpObjeto";
		
		hql = hql + " WHERE tenantId = '" + tenantId + "'";
		
		hql = hql + "  ORDER BY transacao";
		//
		return this.manager.createQuery(hql, MpObjeto.class).getResultList();
	}
	
	public MpObjeto porNavegacao(String acao, String transacao, String tenantId) {
		//
		try {
			//
			String hql = "";
			String order = "";
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpObjeto where upper(transacao) < :transacao";
				order = " ORDER BY transacao DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpObjeto where upper(transacao) > :transacao";
				order = " ORDER BY transacao ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql + order,	MpObjeto.class)
					.setParameter("transacao", transacao.toUpperCase())
					.setMaxResults(1).getSingleResult();
			//		
		} catch (NoResultException e) {
			return null;
		}
	}

}