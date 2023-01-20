package com.mpxds.mpbasic.repository.pt08;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.pt08.MpFeriado;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt08.MpFeriadoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpFeriados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpFeriadoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpFeriado.class);
		
		if (filtro.getDataCriacaoDe() != null)
			criteria.add(Restrictions.ge("dataMovimento", filtro.getDataCriacaoDe()));
		if (filtro.getDataCriacaoAte() != null)
			criteria.add(Restrictions.le("dataMovimento", filtro.getDataCriacaoAte()));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpFeriado> filtrados(MpFeriadoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpFeriadoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpFeriado guardar(MpFeriado mpFeriado) {
		try {
			return manager.merge(mpFeriado);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Esse FERIADO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpFeriado mpFeriado) throws MpNegocioException {
		try {
			mpFeriado = porId(mpFeriado.getId());
			manager.remove(mpFeriado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Feriado... não pode ser excluído.");
		}
	}

	public MpFeriado porDataFeriado(Date dataFeriado) {
		//
		try {
			return manager.createQuery("from MpFeriado where dataFeriado = :dataFeriado " , 
										MpFeriado.class)
				.setParameter("dataFeriado", dataFeriado)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpFeriado porId(Long id) {
		return manager.find(MpFeriado.class, id);
	}
		
	public MpFeriado porNavegacao(String acao, Date dataFeriado) {
		//
//		System.out.println("MpFeriados.MpFeriado ( " + acao + " / " + descricao);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery("from MpFeriado where dataFeriado < :dataFeriado " +
													"ORDER BY dataFeriado DESC",
					MpFeriado.class)
					.setParameter("dataFeriado", dataFeriado)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery("from MpFeriado where dataFeriado > :dataFeriado " +
													"ORDER BY dataFeriado ASC",
					MpFeriado.class)
					.setParameter("dataFeriado", dataFeriado)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpFeriado> mpFeriadoList() {
		//
		return manager.createQuery("from MpFeriado ORDER BY dataFeriado", 
													MpFeriado.class).getResultList();
	}
	
}