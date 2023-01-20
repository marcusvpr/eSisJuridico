package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.model.vo.MpDataValor;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpDolarFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;
import com.mpxds.mpbasic.security.MpSeguranca;

public class MpDolars implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpDolarFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpDolar.class);
		
		if (filtro.getFiltroxxx() != null)
			criteria.add(Restrictions.ge("filtroxxx", filtro.getFiltroxxx()));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpDolar> filtrados(MpDolarFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpDolarFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpDolar guardar(MpDolar mpDolar) {
		//
		try {
			return manager.merge(mpDolar);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException("Erro de concorrência. Esse DOLAR... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpDolar mpDolar) throws MpNegocioException {
		//
		try {
			mpDolar = porId(mpDolar.getId());
			manager.remove(mpDolar);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Dolar... não pode ser excluído.");
		}
	}

	public MpDolar porId(Long id) {
		//
		return manager.find(MpDolar.class, id);
	}
		
	public MpDolar porNavegacao(String acao, Date dataMovimento) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			//
			String hql = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpDolar where dataMovimento < :dataMovimento";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataMovimento DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpDolar where dataMovimento > :dataMovimento";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataMovimento ASC";
			}
			//
			if (hql.isEmpty())
				return null;
			else
				return manager.createQuery(hql,	MpDolar.class)
					.setParameter("dataMovimento", dataMovimento).setMaxResults(1).
																			getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}