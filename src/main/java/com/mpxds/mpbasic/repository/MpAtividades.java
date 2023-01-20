package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.Date;
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

import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpAtividadeFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAtividades implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpAtividadeFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpAtividade.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dtHrAtividade", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dtHrAtividade", filtro.getDataAte()));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getProduto()))
			criteria.add(Restrictions.ilike("mpProduto.nome", filtro.getProduto(), 
																		MatchMode.ANYWHERE));
		if (null != filtro.getIndAtivo())
			criteria.add(Restrictions.eq("indAtivo", filtro.getIndAtivo()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAtividade> filtrados(MpAtividadeFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getMpFilterOrdenacao().getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getMpFilterOrdenacao().getQuantidadeRegistros());
		
		if (filtro.getMpFilterOrdenacao().isAscendente() && filtro.getMpFilterOrdenacao().
															getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.asc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		else if (filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.desc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MpAtividadeFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		//
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpAtividade guardar(MpAtividade mpAtividade) {
		try {
			return manager.merge(mpAtividade);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa ATIVIDADE... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAtividade mpAtividade) throws MpNegocioException {
		//
		try {
			mpAtividade = porId(mpAtividade.getId());
			
			manager.remove(mpAtividade);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("ATIVIDADE... não pode ser excluída.");
		}
	}

	public MpAtividade porMensagem(String mensagem) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpAtividade where upper(mensagem) = :mensagem";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpAtividade.class)
						.setParameter("mensagem", mensagem.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpAtividade porId(Long id) {
		//
		return manager.find(MpAtividade.class, id);
	}
	
	public List<MpAtividade> mpAtividadeList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpAtividade";
		
		if (!tenantId.equals("0")) hql = hql + " WHERE tenantId = '" + tenantId + "'";
		
		hql = hql + " ORDER BY dtHrAtividade";
		
		return manager.createQuery(hql,	MpAtividade.class).getResultList();
	}
	
	public List<MpAtividade> mpAtividadeAllList() {
		//
		String hql = "FROM MpAtividade ORDER BY dtHrAtividade";
		
		return manager.createQuery(hql,	MpAtividade.class).getResultList();
	}
		
	public MpAtividade porNavegacao(String acao, Date dtHrAtividade) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			//
			String hql = "FROM MpAtividade WHERE dtHrAtividade";
			String order = " ORDER BY dtHrAtividade";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = hql + " < :dtHrAtividade";
				order = order + " DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = hql + " > :dtHrAtividade";
				order = order + " ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpAtividade.class)
										.setParameter("dtHrAtividade", dtHrAtividade)
										.setMaxResults(1).getSingleResult();
			//						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}