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

import com.mpxds.mpbasic.model.log.MpErrorLog;
//import com.mpxds.mpbasic.model.enums.MpErrorLogStatus;
//import com.mpxds.mpbasic.model.enums.MpErrorLogTipo;
//import com.mpxds.mpbasic.model.enums.MpErrorLogAreaTipo;
//import com.mpxds.mpbasic.model.enums.MpErrorLogSeveridade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpErrorLogFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpErrorLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpErrorLogFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpErrorLog.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dtHrErrorLog", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dtHrErrorLog", filtro.getDataAte()));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpErrorLog> filtrados(MpErrorLogFilter filtro) {
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
	
	public int quantidadeFiltrados(MpErrorLogFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpErrorLog guardar(MpErrorLog mpErrorLog) {
		try {
			return manager.merge(mpErrorLog);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse CHAMADO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpErrorLog mpErrorLog) throws MpNegocioException {
		try {
			mpErrorLog = porId(mpErrorLog.getId());
			manager.remove(mpErrorLog);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CHAMADO... não pode ser excluído.");
		}
	}

	public MpErrorLog porMensagem(String mensagem) {
		try {
			return manager.createQuery("from MpErrorLog where upper(mensagem) = :mensagem",
																			MpErrorLog.class)
				.setParameter("mensagem", mensagem.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpErrorLog porId(Long id) {
		return manager.find(MpErrorLog.class, id);
	}
	
	public List<MpErrorLog> mpErrorLogList() {
		return manager.createQuery("from MpErrorLog ORDER BY dtHrErrorLog", 
													MpErrorLog.class).getResultList();
	}
		
	public MpErrorLog porNavegacao(String acao, Date dtHrErrorLog) {
		//
//		System.out.println("MpErrorLogs.MpErrorLog ( " + acao + " / " + mensagem);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpErrorLog where dtHrErrorLog < :dtHrErrorLog ORDER BY dtHrErrorLog DESC",
					MpErrorLog.class)
					.setParameter("dtHrErrorLog", dtHrErrorLog)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
						"from MpErrorLog where dtHrErrorLog > :dtHrErrorLog ORDER BY dtHrErrorLog ASC",
					MpErrorLog.class)
					.setParameter("dtHrErrorLog", dtHrErrorLog)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}