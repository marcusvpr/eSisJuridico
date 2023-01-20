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

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpCategoriaFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCategorias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpCategoriaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpCategoria.class);
		
//		if (StringUtils.isNotBlank(filtro.getTipo()))
//			criteria.add(Restrictions.ilike("mpTipoProduto.descricao", filtro.getTipo(),
//																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
//		if (StringUtils.isNotBlank(filtro.getPai()))
//			criteria.add(Restrictions.ilike("mpCategoriaPai.descricao", filtro.getPai(),
//																			MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpCategoria> filtrados(MpCategoriaFilter filtro) {
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
	
	public int quantidadeFiltrados(MpCategoriaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpCategoria guardar(MpCategoria mpCategoria) {
		try {
			return manager.merge(mpCategoria);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa CATEGORIA... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpCategoria mpCategoria) throws MpNegocioException {
		try {
			mpCategoria = porId(mpCategoria.getId());
			
			manager.remove(mpCategoria);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CATEGORIA... não pode ser excluída.");
		}
	}

	public MpCategoria porTipoDescricao(MpTipoProduto mpTipoProduto, String descricao) {
		try {
			return manager.createQuery(
								"from MpCategoria where upper(descricao) = :descricao and" +
										" mpTipoProduto = :mpTipoProduto", MpCategoria.class)
								.setParameter("descricao", descricao.toUpperCase())
								.setParameter("mpTipoProduto", mpTipoProduto).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpCategoria porId(Long id) {
		return manager.find(MpCategoria.class, id);
	}
	
	public List<MpCategoria> mpCategoriaList(String tenantId) {
		//
		String hql = "from MpCategoria AND tenantId = :tenantId";
		
		hql = hql + " ORDER BY mpTipoProduto.descricao ASC, descricao ASC";
		//
		return manager.createQuery(hql,	MpCategoria.class)
						.setParameter("tenantId", tenantId).getResultList();
	}
	
	public List<MpCategoria> raizes(MpTipoProduto mpTipoProduto) {
		//
		return manager.createQuery("from MpCategoria where mpCategoriaPai is null AND" +
								" mpTipoProduto = :tipo" +
								" ORDER BY mpTipoProduto.descricao, descricao", MpCategoria.class)
							.setParameter("tipo", mpTipoProduto).getResultList();
	}
	
	public List<MpCategoria> raizesTenantId(MpTipoProduto mpTipoProduto, String tenantId) {
		//
		return manager.createQuery("from MpCategoria where mpCategoriaPai is null AND" +
								" mpTipoProduto = :tipo" +
								" ORDER BY mpTipoProduto.descricao, descricao", MpCategoria.class)
							.setParameter("tipo", mpTipoProduto).getResultList();
	}
	
	public List<MpCategoria> mpSubCategoriasDe(MpCategoria mpCategoriaPai) {
		//
		return manager.createQuery("from MpCategoria where mpCategoriaPai = :raiz" +
								" ORDER BY mpTipoProduto.descricao, descricao", MpCategoria.class)
							.setParameter("raiz", mpCategoriaPai).getResultList();
	}

	public MpCategoria porNavegacao(String acao, String tipoProduto, String descricao) {
		//
//		System.out.println("MpCategorias.MpCategoria ( " + acao + " / " + descricao);
		//
		try {
			//
			String tenantId = mpSeguranca.capturaTenantId().trim();
			
			String hql = "FROM MpCategoria where upper(mpTipoProduto.descricao)";
			String order = " ORDER BY mpTipoProduto.descricao ";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = hql + " < :tipoProduto AND upper(descricao) < :descricao";
				order = order + " DESC, descricao DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = hql + " > :tipoProduto AND upper(descricao) > :descricao";
				order = order + " ASC, descricao ASC";
			}
			hql = hql + " AND mpCategoriaPai is null";
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpCategoria.class)
					.setParameter("tipoProduto", tipoProduto.toUpperCase())
					.setParameter("descricao", descricao.toUpperCase())
					.setMaxResults(1).getSingleResult();
			//						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long numeroRegistros() {

    	System.out.println("MpCategorias.encontrarQuantidadeRegistrosMpCategoria()");
//    			manager.createQuery("select count(c) from MpCategoria c",
//						Long.class).getSingleResult());
//	
//		return manager.createQuery("select count(c) from MpCategoria c",
//																Long.class).getSingleResult();
		return 0L;
	}

}