package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.log.MpLoginLog;
//import com.mpxds.mpbasic.model.enums.MpLoginLogStatus;
//import com.mpxds.mpbasic.model.enums.MpLoginLogTipo;
//import com.mpxds.mpbasic.model.enums.MpLoginLogAreaTipo;
//import com.mpxds.mpbasic.model.enums.MpLoginLogSeveridade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpLoginLogFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpLoginLogs implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpLoginLogFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpLoginLog.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("createdDate", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("createdDate", filtro.getDataAte()));
				
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpLoginLog> filtrados(MpLoginLogFilter filtro) {
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
	
	public int quantidadeFiltrados(MpLoginLogFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpLoginLog guardar(MpLoginLog mpLoginLog) {
		try {
			return manager.merge(mpLoginLog);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse LOGIN_LOG... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpLoginLog mpLoginLog) throws MpNegocioException {
		try {
			mpLoginLog = porId(mpLoginLog.getId());
			manager.remove(mpLoginLog);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("LOGIN_LOG... não pode ser excluído.");
		}
	}
	
	public MpLoginLog porId(Long id) {
		return manager.find(MpLoginLog.class, id);
	}
		
}