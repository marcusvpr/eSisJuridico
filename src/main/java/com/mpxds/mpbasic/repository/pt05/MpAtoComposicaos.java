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

import com.mpxds.mpbasic.model.pt05.MpAtoComposicao;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt05.MpAtoComposicaoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAtoComposicaos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpAtoComposicaoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpAtoComposicao.class);
		
		if (StringUtils.isNotBlank(filtro.getTabela()))
			criteria.add(Restrictions.ilike("tabela", filtro.getTabela(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAtoComposicao> filtrados(MpAtoComposicaoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpAtoComposicaoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpAtoComposicao guardar(MpAtoComposicao mpAtoComposicao) {
		try {
			return manager.merge(mpAtoComposicao);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse ATO COMPOSIÇÃO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAtoComposicao mpAtoComposicao) throws MpNegocioException {
		try {
			mpAtoComposicao = porId(mpAtoComposicao.getId());
			manager.remove(mpAtoComposicao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ATO COMPOSIÇÃO... não pode ser excluído.");
		}
	}

	public MpAtoComposicao porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpAtoComposicao where upper(codigo) = :codigo",
																			MpAtoComposicao.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpAtoComposicao porId(Long id) {
		return manager.find(MpAtoComposicao.class, id);
	}

	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpAtoComposicao.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpAtosComposicao c",
//										Long.class).getSingleResult();
	}

}