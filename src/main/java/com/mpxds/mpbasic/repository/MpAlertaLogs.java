package com.mpxds.mpbasic.repository;

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

import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpAlertaLogFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAlertaLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpAlertaLogFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpAlertaLog.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dataMovimento", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dataMovimento", filtro.getDataAte()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpAlertaLog> filtrados(MpAlertaLogFilter filtro) {
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
	
	public int quantidadeFiltrados(MpAlertaLogFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpAlertaLog guardar(MpAlertaLog mpAlertaLog) {
		try {
			return manager.merge(mpAlertaLog);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ALARME... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpAlertaLog mpAlertaLog) throws MpNegocioException {
		try {
			mpAlertaLog = porId(mpAlertaLog.getId());
			manager.remove(mpAlertaLog);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("AlertaLog... não pode ser excluído.");
		}
	}

	public MpAlertaLog porDataMovimento(Date dataMovimento) {
		//
//		Calendar calendar = Calendar.getInstance();
//		//
//		calendar.setTime(horaMovimento);
//		//
//		calendar.set(Calendar.HOUR_OF_DAY,0);
//		calendar.set(Calendar.MINUTE,0);
//		calendar.set(Calendar.SECOND,0);
//		calendar.set(Calendar.MILLISECOND,0);
//		//
//		horaMovimento = calendar.getTime();
		//
		try {
			return manager.createQuery("from MpAlertaLog where dataMovimento = :dataMovimento",
																				MpAlertaLog.class)
				.setParameter("dataMovimento", dataMovimento)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpAlertaLog porDataMovTipoId(Date dataMovimento, String tipoAlerta, Long alertaId) {
		//
		try {
			return manager.createQuery("from MpAlertaLog where dataMovimento = :dataMovimento " +
						"and tipoAlerta = :tipoAlerta and alertaId = :alertaId", MpAlertaLog.class)
				.setParameter("dataMovimento", dataMovimento)
				.setParameter("tipoAlerta", tipoAlerta)
				.setParameter("alertaId", alertaId)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpAlertaLog porId(Long id) {
		return manager.find(MpAlertaLog.class, id);
	}
		
	public MpAlertaLog porNavegacao(String acao, Date horaMovimento) {
		//
//		System.out.println("MpAlertaLogs.MpAlertaLog ( " + acao + " / " + descricao);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpAlertaLog where horaMovimento < :horaMovimento ORDER BY horaMovimento DESC",
					MpAlertaLog.class)
					.setParameter("horaMovimento", horaMovimento)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpAlertaLog where horaMovimento > :horaMovimento ORDER BY horaMovimento ASC",
					MpAlertaLog.class)
					.setParameter("horaMovimento", horaMovimento)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}