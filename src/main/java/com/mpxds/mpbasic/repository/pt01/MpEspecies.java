package com.mpxds.mpbasic.repository.pt01;

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

import com.mpxds.mpbasic.model.pt01.MpEspecie;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt01.MpEspecieFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpEspecies implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpEspecieFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpEspecie.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("especieCodigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpEspecie> filtrados(MpEspecieFilter filtro) {
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
	
	public int quantidadeFiltrados(MpEspecieFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpEspecie guardar(MpEspecie mpEspecies) {
		try {
			return manager.merge(mpEspecies);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa ESPÉCIE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpEspecie mpEspecies) throws MpNegocioException {
		try {
			mpEspecies = porId(mpEspecies.getId());
			manager.remove(mpEspecies);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ATO... não pode ser excluído.");
		}
	}

	public MpEspecie porCodigo(String especieCodigo) {
		try {
			return manager.createQuery(
				"from MpEspecie where upper(especieCodigo) = :especieCodigo",	MpEspecie.class)
				.setParameter("especieCodigo", especieCodigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpEspecie> byCodigoList() {
		return this.manager.createQuery("from MpEspecie ORDER BY especieCodigo", MpEspecie.class)
					.getResultList();
	}

	public int countByCodigo(String especieCodigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpEspecie.class);
		
		criteria.add(Restrictions.eq("especieCodigo", especieCodigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpEspecie porId(Long id) {
		return manager.find(MpEspecie.class, id);
	}

	public MpEspecie porNavegacao(String acao, String especieCodigo) {
		//
//		System.out.println("MpEspeciess.MpEspecies ( " + acao + " / " + especieCodigo);
		//		
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpEspecie where upper(especieCodigo) < :especieCodigo" +
					" ORDER BY (especieCodigo) DESC", MpEspecie.class)
					.setParameter("especieCodigo", especieCodigo)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpEspecie where upper(especieCodigo) > :especieCodigo" +
					" ORDER BY (especieCodigo) ASC", MpEspecie.class)
					.setParameter("especieCodigo", especieCodigo)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpEspecie.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpEspecies c",
//										Long.class).getSingleResult();
	}

}