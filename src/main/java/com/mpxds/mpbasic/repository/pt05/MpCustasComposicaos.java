package com.mpxds.mpbasic.repository.pt05;

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

import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt05.MpCustasComposicaoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCustasComposicaos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpCustasComposicaoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpCustasComposicao.class);
		
		if (StringUtils.isNotBlank(filtro.getTabela()))
			criteria.add(Restrictions.ilike("tabela", filtro.getTabela(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getItem()))
			criteria.add(Restrictions.ilike("item", filtro.getItem(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getSubItem()))
			criteria.add(Restrictions.ilike("subItem", filtro.getSubItem(), MatchMode.ANYWHERE));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpCustasComposicao> filtrados(MpCustasComposicaoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpCustasComposicaoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public List<MpCustasComposicao> byTabItemSubList() {
		return this.manager.createQuery("from MpCustasComposicao ORDER BY tabela, item, subItem",
														MpCustasComposicao.class).getResultList();
	}
	
	public MpCustasComposicao guardar(MpCustasComposicao mpCustasComposicao) {
		try {
			return manager.merge(mpCustasComposicao);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Essa CUSTAS COMPOSIÇÃO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpCustasComposicao mpCustasComposicao) throws MpNegocioException {
		try {
			mpCustasComposicao = porId(mpCustasComposicao.getId());
			manager.remove(mpCustasComposicao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Custas Composição... não pode ser excluída.");
		}
	}

	public MpCustasComposicao porTabelaItemSubItem(String tabela, String item, String subItem) {
		//
		try {
			return manager.createQuery("from MpCustasComposicao where tabela = :tabela and " +
								"item = :item and subItem = :subItem", MpCustasComposicao.class)
				.setParameter("tabela", tabela)
				.setParameter("item", item)
				.setParameter("subItem", subItem)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpCustasComposicao porId(Long id) {
		return manager.find(MpCustasComposicao.class, id);
	}
		
	public MpCustasComposicao porNavegacao(String acao, String tabela, String item, String subItem) {
		//
//		System.out.println("MpCustasComposicaos.MpCustasComposicao ( " + acao + " / " + descricao);
		//
		String tabelaItemSubItem = tabela + item + subItem ;
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
									"from MpCustasComposicao where (tabela+item+subItem) " +
									"< :tabelaItemSubItem ORDER BY (tabela+item+subItem) DESC",
										MpCustasComposicao.class)
										.setParameter("tabelaItemSubItem", tabelaItemSubItem)
										.setMaxResults(1)
										.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
						"from MpCustasComposicao where (tabela+item+subItem) " +
						"> :tabelaItemSubItem ORDER BY (tabela+item+subItem) ASC",
										MpCustasComposicao.class)
										.setParameter("tabelaItemSubItem", tabelaItemSubItem)
										.setMaxResults(1)
										.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
		
}