package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.ArrayList;
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

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.repository.filter.MpProdutoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
		
	public List<MpProduto> filtrados(MpProdutoFilter filtro) {
		//
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<MpProduto> criteriaQuery = builder.createQuery(MpProduto.class);
		
		Root<MpProduto> mpProdutoRoot = criteriaQuery.from(MpProduto.class);
		
		Fetch<MpProduto, MpCategoria> categoriaJoin = mpProdutoRoot.fetch("mpCategoria", JoinType.INNER);
		categoriaJoin.fetch("mpCategoriaPai", JoinType.INNER);
		//
		List<Predicate> predicates = new ArrayList<>();
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			predicates.add(builder.equal(mpProdutoRoot.get("tenantId"), tenantId));
	
		if (StringUtils.isNotBlank(filtro.getSku()))
			predicates.add(builder.equal(mpProdutoRoot.get("sku"), filtro.getSku()));
		if (StringUtils.isNotBlank(filtro.getNome()))
			predicates.add(builder.like(builder.lower(mpProdutoRoot.get("nome")), 
															"%" + filtro.getNome().toLowerCase() + "%"));
		if (StringUtils.isNotBlank(filtro.getLocalizacao()))
			predicates.add(builder.like(builder.lower(mpProdutoRoot.get("mpLocalizacao.descricao")), 
															"%" + filtro.getLocalizacao().toLowerCase() + "%"));
		//
		criteriaQuery.select(mpProdutoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(mpProdutoRoot.get("nome")));
		
		TypedQuery<MpProduto> query = manager.createQuery(criteriaQuery);
		//
		return query.getResultList();
	}	
	
	private Criteria criarCriteriaParaFiltro(MpProdutoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
				
		Criteria criteria = session.createCriteria(MpProduto.class);
		
		if (StringUtils.isNotBlank(filtro.getSku()))
			criteria.add(Restrictions.ilike("sku", filtro.getSku(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getLocalizacao()))
			criteria.add(Restrictions.ilike("mpLocalizacao.descricao", filtro.getLocalizacao(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getTipoProduto()))
			criteria.add(Restrictions.ilike("tipoProduto", filtro.getTipoProduto(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpProduto> filtradosX(MpProdutoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpProdutoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpProduto guardar(MpProduto mpProduto) {
		//
		try {
			return this.manager.merge(mpProduto);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse PRODUTO... já foi alterada anteriormente!");
		}
	}

	public MpProduto porId(Long id) {
		return this.manager.find(MpProduto.class, id);
	}
	
	@MpTransactional
	public void remover(MpProduto mpProduto) throws MpNegocioException {
		try {
			mpProduto = porId(mpProduto.getId());
			manager.remove(mpProduto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PRODUTO... não pode ser excluído.");
		}
	}

	public MpProduto porSku(String sku) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "FROM MpProduto WHERE UPPER(sku) = :sku";
			
			if (!tenantId.equals("0")) hql = hql +	" AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql , MpProduto.class).
						setParameter("sku", sku.toUpperCase()).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpProduto porNavegacao(String acao, String sku) {
		//
//		System.out.println("MpProdutos.MpProduto ( " + acao + " / " + parametro);
		//		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			//
			String hql = "";
			String order = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "FROM MpProduto WHERE UPPER(sku) < :sku";
				order = " ORDER BY sku DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "FROM MpProduto WHERE UPPER(sku) > :sku";
				order = " ORDER BY sku ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order, MpProduto.class)
							.setParameter("sku", sku.toUpperCase())
							.setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<MpProduto> porNome(String nome) {
		//		
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "FROM MpProduto WHERE upper(nome) LIKE :nome"; 
		//
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		String order = " ORDER BY nome";

		return this.manager.createQuery(hql + order, MpProduto.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public List<MpProduto> porProdutoList() {
		//		
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "FROM MpProduto"; 
		//
		if (!tenantId.equals("0")) hql = hql + " WHERE tenantId = '" + tenantId + "'";
		
		String order = " ORDER BY nome";

		return this.manager.createQuery(hql + order, MpProduto.class).getResultList();
	}	

	public List<MpProduto> porProdutoTenantList(String tenantId, String nomeSel) {
		//		
		String hql = "FROM MpProduto"; 
		//
		hql = hql + " WHERE UPPER(nome) LIKE :nome AND tenantId = '" + tenantId + "'";
		//
		String order = " ORDER BY nome";
		//
		return this.manager.createQuery(hql + order, MpProduto.class)
				.setParameter("nome", "%" + nomeSel.toUpperCase() + "%")
				.getResultList();
	}	
	
}
