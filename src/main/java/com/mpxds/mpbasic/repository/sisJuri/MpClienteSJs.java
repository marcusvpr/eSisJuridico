package com.mpxds.mpbasic.repository.sisJuri;

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

import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpClienteSJFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpClienteSJs implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpClienteSJFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpClienteSJ.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpClienteSJ> filtrados(MpClienteSJFilter filtro) {
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
	
	public int quantidadeFiltrados(MpClienteSJFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpClienteSJ guardar(MpClienteSJ mpClienteSJ) {
		try {
			return manager.merge(mpClienteSJ);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse CLIENTE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpClienteSJ mpClienteSJ) throws MpNegocioException {
		try {
			mpClienteSJ = porId(mpClienteSJ.getId());
			manager.remove(mpClienteSJ);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CLIENTE... não pode ser excluído.");
		}
	}

	public MpClienteSJ porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpClienteSJ where upper(codigo) = :codigo",
																			MpClienteSJ.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpClienteSJ porNome(String nome) {
		try {
			return manager.createQuery("from MpClienteSJ where upper(nome) = :nome",
																			MpClienteSJ.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpClienteSJ> byNomeList() {
		return this.manager.createQuery("from MpClienteSJ ORDER BY nome", MpClienteSJ.class)
					.getResultList();
	}

	public List<MpClienteSJ> porNomeList(String nome) {
		return this.manager.createQuery("from MpClienteSJ " +
					"where upper(nome) like :nome ORDER BY nome", MpClienteSJ.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpClienteSJ.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpClienteSJ porId(Long id) {
		//
		return manager.find(MpClienteSJ.class, id);
	}
	public MpClienteSJ porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpClienteSJ where idCarga = :idCarga", MpClienteSJ.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpClienteSJ porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpClienteSJs.MpClienteSJ ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpClienteSJ where upper(nome) < :nome ORDER BY nome DESC",
					MpClienteSJ.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpClienteSJ where upper(nome) > :nome ORDER BY nome ASC",
					MpClienteSJ.class)
					.setParameter("nome", nome.toUpperCase())
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
		
		Criteria criteria = session.createCriteria(MpClienteSJ.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpClienteSJ c",
//										Long.class).getSingleResult();
	}

}