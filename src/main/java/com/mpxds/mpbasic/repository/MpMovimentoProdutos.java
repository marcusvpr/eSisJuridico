package com.mpxds.mpbasic.repository;

import java.io.Serializable;
//import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.criterion.MatchMode;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.MpMovimentoProduto;
import com.mpxds.mpbasic.exception.MpNegocioException;
//import com.mpxds.mpbasic.repository.filter.MpMovimentoProdutoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpMovimentoProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

//	private Criteria criarCriteriaParaFiltro(MpMovimentoProdutoFilter filtro) {
//		Session session = this.manager.unwrap(Session.class);
//		
//		Criteria criteria = session.createCriteria(MpMovimentoProduto.class);
//		
//		if (StringUtils.isNotBlank(filtro.getLogradouro()))
//			criteria.add(Restrictions.ilike("logradouro", filtro.getLogradouro(), MatchMode.ANYWHERE));
//		//
//		return criteria;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<MpMovimentoProduto> filtrados(MpMovimentoProdutoFilter filtro) {
//		Criteria criteria = criarCriteriaParaFiltro(filtro);
//		
//		criteria.setFirstResult(filtro.getPrimeiroRegistro());
//		criteria.setMaxResults(filtro.getQuantidadeRegistros());
//		
//		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null)
//			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
//		else if (filtro.getPropriedadeOrdenacao() != null)
//			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
//		//		
//		return criteria.list();
//	}
	
//	public int quantidadeFiltrados(MpMovimentoProdutoFilter filtro) {
//		Criteria criteria = criarCriteriaParaFiltro(filtro);
//		
//		criteria.setProjection(Projections.rowCount());
//		
//		return ((Number) criteria.uniqueResult()).intValue();
//	}
	
	
	public MpMovimentoProduto guardar(MpMovimentoProduto mpMovimentoProduto) {
		try {
			return manager.merge(mpMovimentoProduto);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse Movimento Produto... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpMovimentoProduto mpMovimentoProduto) throws MpNegocioException {
		try {
			mpMovimentoProduto = porId(mpMovimentoProduto.getId());
			manager.remove(mpMovimentoProduto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Movimento Produto... não pode ser excluído.");
		}
	}
	
	public MpMovimentoProduto porId(Long id) {
		return manager.find(MpMovimentoProduto.class, id);
	}
		
}