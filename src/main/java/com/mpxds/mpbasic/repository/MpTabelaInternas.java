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

import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.repository.filter.MpTabelaInternaFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTabelaInternas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpTabelaInternaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpTabelaInterna.class);		
		//
		if (StringUtils.isNotBlank(filtro.getTipoTabelaInterna()))
			criteria.add(Restrictions.ilike("tipoTabelaInterna", filtro.getTipoTabelaInterna(),
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTabelaInterna> filtrados(MpTabelaInternaFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpTabelaInternaFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpTabelaInterna guardar(MpTabelaInterna mpTabelaInterna) {
		//
		try {
			return this.manager.merge(mpTabelaInterna);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa TABELA INTERNA... já foi alterada anteriormente!");
		}
	}

	public MpTabelaInterna porId(Long id) {
		//
		return this.manager.find(MpTabelaInterna.class, id);
	}
	
	@MpTransactional
	public void remover(MpTabelaInterna mpTabelaInterna) throws MpNegocioException {
		//
		try {
			mpTabelaInterna = porId(mpTabelaInterna.getId());
			manager.remove(mpTabelaInterna);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("TABELA INTERNA... não pode ser excluída.");
		}
	}

	public MpTabelaInterna porMpNumeroCodigo(MpTipoTabelaInterna mpTipoTabelaInterna, String codigo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpTabelaInterna where mpTipoTabelaInterna = :mpTipoTabelaInterna AND " +
					  														"upper(codigo) = :codigo";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpTabelaInterna.class)
				.setParameter("mpTipoTabelaInterna", mpTipoTabelaInterna)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<MpTabelaInterna> mpPaiList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInterna where indPai = true";
		String order = " ORDER BY mpTipoTabelaInterna + codigo";
			
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		return manager.createQuery(hql + order, MpTabelaInterna.class).getResultList();
	}

	public List<MpTabelaInterna> mpFilhaList(MpTabelaInterna mpPai) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInterna where mpPai = :mpPai";
		String order = " ORDER BY descricao";
			
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		return manager.createQuery(hql + order, MpTabelaInterna.class)
															.setParameter("mpPai", mpPai).getResultList();
	}
	
	public List<MpTabelaInterna> mpNumeroList(MpTipoTabelaInterna mpTipoTabelaInterna) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInterna where mpTipoTabelaInterna = :mpTipoTabelaInterna";
		String order = " ORDER BY mpTipoTabelaInterna + codigo";
			
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

		return manager.createQuery(hql + order, MpTabelaInterna.class)
				.setParameter("mpTipoTabelaInterna", mpTipoTabelaInterna).getResultList();
	}

	public MpTabelaInterna porNavegacao(String acao, MpTipoTabelaInterna mpTipoTabelaInterna,
										String codigo) {
		//
//		System.out.println("MpTabelaInternas.MpTabelaInterna ( " + acao + " / " + numero +
//																		  " / " + codigo);
		//
		try {
			String tipoTabelaInternaCodigo = mpTipoTabelaInterna.getDescricao() + codigo.trim(); 
			//
			String tenantId = mpSeguranca.capturaTenantId().trim();
			//
			String hql = "FROM MpTabelaInterna WHERE UPPER(mpTipoTabelaInterna.descricao + codigo)";
			String order = " ORDER BY mpTipoTabelaInterna.descricao + codigo";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = hql + " < :tipoTabelaInternaCodigo";
				order = order + " DESC";
			} else if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = hql + " > :tipoTabelaInternaCodigo";
				order = order + " ASC";
			}
			//
			if (!tenantId.equals("0"))
				hql = hql + " AND tenantId = '" + tenantId + "'";
			//
//			System.out.println("MpTabelaIntenas.porNavegacao ( hql = " + hql + order + " / " + tipoTabelaInternaCodigo);
//
			return manager.createQuery(hql + order, MpTabelaInterna.class)
					.setParameter("tipoTabelaInternaCodigo", tipoTabelaInternaCodigo.toUpperCase()).setMaxResults(1)
					.getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}	
	
}
