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

import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.repository.filter.MpSistemaConfigFilter;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpSistemaConfigs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	private Criteria criarCriteriaParaFiltro(MpSistemaConfigFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpSistemaConfig.class);
		
		if (StringUtils.isNotBlank(filtro.getParametro()))
			criteria.add(Restrictions.ilike("parametro", filtro.getParametro(),
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(),
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getValor()))
			criteria.add(Restrictions.ilike("valor", filtro.getValor(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpSistemaConfig> filtrados(MpSistemaConfigFilter filtro) {
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
	
	public int quantidadeFiltrados(MpSistemaConfigFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpSistemaConfig guardar(MpSistemaConfig mpSistemaConfig) {
		try {
			return this.manager.merge(mpSistemaConfig);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Esse SISTEMA CONFIGURAÇÃO... já foi alterada anteriormente!");
		}
	}

	public MpSistemaConfig porId(Long id) {
		return this.manager.find(MpSistemaConfig.class, id);
	}
	
	@MpTransactional
	public void remover(MpSistemaConfig mpSistemaConfig) throws MpNegocioException {
		try {
			mpSistemaConfig = porId(mpSistemaConfig.getId());
			manager.remove(mpSistemaConfig);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("SISTEMA CONFIGURAÇÃO... não pode ser excluído.");
		}
	}

	public MpSistemaConfig porParametro(String parametro) {
		//
//		System.out.println("MpSistemaConfigs.porParametro ( " + parametro);
		//
		try {
			return manager.createQuery(
									"from MpSistemaConfig where upper(parametro) = :parametro",
									MpSistemaConfig.class)
									.setParameter("parametro", parametro.toUpperCase())
									.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpSistemaConfig porNavegacao(String acao, String parametro) {
		//
//		System.out.println("MpSistemaConfigs.porNavegacao ( " + acao + " / " + parametro);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
			"from MpSistemaConfig where upper(parametro) < :parametro ORDER BY parametro DESC",
					MpSistemaConfig.class)
					.setParameter("parametro", parametro.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
			"from MpSistemaConfig where upper(parametro) > :parametro ORDER BY parametro ASC",
					MpSistemaConfig.class)
					.setParameter("parametro", parametro.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
