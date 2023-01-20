package com.mpxds.mpbasic.repository;

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

import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpContatoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpContatos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpContatoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);

		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpContato.class);
		
		if (StringUtils.isNotBlank(filtro.getNomeRazaoSocial()))
			criteria.add(Restrictions.ilike("nomeRazaoSocial", filtro.getNomeRazaoSocial(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpContato> filtrados(MpContatoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpContatoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpContato guardar(MpContato mpContato) {
		try {
			return manager.merge(mpContato);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse CONTATO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpContato mpContato) throws MpNegocioException {
		try {
			mpContato = porId(mpContato.getId());
			manager.remove(mpContato);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CONTATO... não pode ser excluído.");
		}
	}

	public MpContato porCodigo(String codigo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			String hql = "from MpContato where upper(codigo) = :codigo";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpContato.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpContato porNomeRazaoSocial(String nomeRazaoSocial) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			String hql = "from MpContato where upper(nomeRazaoSocial) = :nomeRazaoSocial";
			
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpContato.class)
				.setParameter("nomeRazaoSocial", nomeRazaoSocial.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpContato> byNomeRazaoSocialList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpContato ORDER BY nomeRazaoSocial";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		//
		return this.manager.createQuery(hql, MpContato.class).getResultList();
	}

	public List<MpContato> porNomeRazaoSocialList(String nomeRazaoSocial) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpContato WHERE upper(nomeRazaoSocial) like :nomeRazaoSocial";
		String order = " ORDER BY nomeRazaoSocial";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		//
		return this.manager.createQuery(hql + order, MpContato.class)
					.setParameter("nomeRazaoSocial", nomeRazaoSocial.toUpperCase() + "%")
					.getResultList();
	}

	public int countByIdadeDeAte(Integer idadeDe, Integer idadeAte) {
		Session session = this.manager.unwrap(Session.class);
		
		SQLQuery query =
				session.createSQLQuery("SELECT COUNT(data_Nascimento) FROM MP_CONTATO " + 
				"WHERE YEAR(CURRENT_DATE) - YEAR(data_Nascimento) - " +
				"CASEWHEN(MONTH(CURRENT_DATE)*100 + DAYOFMONTH(CURRENT_DATE) >= " + 
				"MONTH(data_Nascimento)*100 + DAYOFMONTH(data_Nascimento),0,1) >= :idadeDe AND " +
				"YEAR(CURRENT_DATE) - YEAR(data_Nascimento) - " +
				"CASEWHEN(MONTH(CURRENT_DATE)*100 + DAYOFMONTH(CURRENT_DATE) >= " + 
				"MONTH(data_Nascimento)*100 + DAYOFMONTH(data_Nascimento),0,1) <= :idadeAte");		
		//
		query.setParameter("idadeDe", idadeDe);
		query.setParameter("idadeAte", idadeAte);
		//
		return ((Number) query.uniqueResult()).intValue();
	}
	
	public MpContato porId(Long id) {
		return manager.find(MpContato.class, id);
	}

	public MpContato porNavegacao(String acao, String nomeRazaoSocial) {
		//
//		System.out.println("MpContatos.MpContato ( " + acao + " / " + nomeRazaoSocial);
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			//
			String hql = "FROM MpContato WHERE upper(nomeRazaoSocial)";
			String order = " ORDER BY nomeRazaoSocial";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = hql + " < :nomeRazaoSocial";
				order = order + " DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = hql + " > :nomeRazaoSocial";
				order = order + " ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpContato.class)
					.setParameter("nomeRazaoSocial", nomeRazaoSocial.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			//
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpContato.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpContato c",
//										Long.class).getSingleResult();
	}

}