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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.engreq.MpProjetoPessoaER;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpProjetoPessoaERFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProjetoPessoaERs implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpProjetoPessoaERFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpProjetoPessoaER.class);
		
		if (StringUtils.isNotBlank(filtro.getObservacao()))
			criteria.add(Restrictions.ilike("observacao", filtro.getObservacao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpProjetoPessoaER> filtrados(MpProjetoPessoaERFilter filtro) {
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
	
	public int quantidadeFiltrados(MpProjetoPessoaERFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpProjetoPessoaER guardar(MpProjetoPessoaER mpProjetoPessoaER) {
		try {
			return manager.merge(mpProjetoPessoaER);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse PROJETO PESSOA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpProjetoPessoaER mpProjetoPessoaER) throws MpNegocioException {
		try {
			mpProjetoPessoaER = porId(mpProjetoPessoaER.getId());
			manager.remove(mpProjetoPessoaER);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PROJETO PESSOA... não pode ser excluído.");
		}
	}

	public MpProjetoPessoaER porId(Long id) {
		return manager.find(MpProjetoPessoaER.class, id);
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpProjetoPessoaER.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		//
	}

}