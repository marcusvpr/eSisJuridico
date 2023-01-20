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

import com.mpxds.mpbasic.model.log.MpSistemaLog;
//import com.mpxds.mpbasic.model.enums.MpSistemaLogStatus;
//import com.mpxds.mpbasic.model.enums.MpSistemaLogTipo;
//import com.mpxds.mpbasic.model.enums.MpSistemaLogAreaTipo;
//import com.mpxds.mpbasic.model.enums.MpSistemaLogSeveridade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpSistemaLogFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpSistemaLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpSistemaLogFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpSistemaLog.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dtHrSistemaLog", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dtHrSistemaLog", filtro.getDataAte()));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpSistemaLog> filtrados(MpSistemaLogFilter filtro) {
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
	
	public int quantidadeFiltrados(MpSistemaLogFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpSistemaLog guardar(MpSistemaLog mpSistemaLog) {
		try {
			return manager.merge(mpSistemaLog);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse SISTEMA LOG... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpSistemaLog mpSistemaLog) throws MpNegocioException {
		try {
			mpSistemaLog = porId(mpSistemaLog.getId());
			manager.remove(mpSistemaLog);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("SISTEMA LOG... não pode ser excluído.");
		}
	}

	public MpSistemaLog porMensagem(String mensagem) {
		try {
			return manager.createQuery("from MpSistemaLog where upper(mensagem) = :mensagem",
																			MpSistemaLog.class)
				.setParameter("mensagem", mensagem.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpSistemaLog porId(Long id) {
		return manager.find(MpSistemaLog.class, id);
	}
	
	public List<MpSistemaLog> mpSistemaLogList() {
		return manager.createQuery("from MpSistemaLog ORDER BY dtHrSistemaLog", 
													MpSistemaLog.class).getResultList();
	}
		
	public MpSistemaLog porNavegacao(String acao, Date dtHrSistemaLog) {
		//
//		System.out.println("MpSistemaLogs.MpSistemaLog ( " + acao + " / " + mensagem);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
		"from MpSistemaLog where dtHrSistemaLog < :dtHrSistemaLog ORDER BY dtHrSistemaLog DESC",
					MpSistemaLog.class)
					.setParameter("dtHrSistemaLog", dtHrSistemaLog)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
		"from MpSistemaLog where dtHrSistemaLog > :dtHrSistemaLog ORDER BY dtHrSistemaLog ASC",
					MpSistemaLog.class)
					.setParameter("dtHrSistemaLog", dtHrSistemaLog)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}