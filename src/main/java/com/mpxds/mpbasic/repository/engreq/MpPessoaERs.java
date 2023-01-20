package com.mpxds.mpbasic.repository.engreq;

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

import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.engreq.MpPessoaERFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaERs implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpPessoaERFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpPessoaER.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoaER> filtrados(MpPessoaERFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPessoaERFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoaER guardar(MpPessoaER mpPessoaER) {
		try {
			return manager.merge(mpPessoaER);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoaER mpPessoaER) throws MpNegocioException {
		try {
			mpPessoaER = porId(mpPessoaER.getId());
			manager.remove(mpPessoaER);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PESSOA... não pode ser excluída.");
		}
	}

	public MpPessoaER porCodigo(String codigo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "FROM MpPessoaER WHERE upper(codigo) = :codigo";
			
			if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpPessoaER.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoaER porNome(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "FROM MpPessoaER WHERE upper(nome) = :nome";
			
			if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpPessoaER.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoaER> porNomeList(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoaER WHERE upper(nome) LIKE :nome";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoaER.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public List<MpPessoaER> porPessoaERList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoaER";

		if (!tenantId.equals("0")) hql = hql + " WHERE tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoaER.class).getResultList();
	}

	public List<MpPessoaER> porPessoaERAtivoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoaER WHERE mpStatus = 'ATIVO'";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoaER.class).getResultList();
	}

	public List<MpPessoaER> porPessoaERMedicoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpPessoaER where mpProfissaoPessoaER = 'MEDICO'";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoaER.class)
					.getResultList();
	}

	public int countByIdadeDeAte(Integer idadeDe, Integer idadeAte) {
		Session session = this.manager.unwrap(Session.class);
		
		SQLQuery query =
			session.createSQLQuery("SELECT COUNT(data_Nascimento) FROM MP_PESSOA " + 
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
	
	public MpPessoaER porId(Long id) {
		return manager.find(MpPessoaER.class, id);
	}

	public MpPessoaER porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpPessoaERs.MpPessoaER ( " + acao + " / " + nome);
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			//
			String hql = "";
			String order = "";
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) { 
				hql = "from MpPessoaER where upper(nome) < :nome";
				order = " ORDER BY nome DESC";
			} else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) { 
				hql = "from MpPessoaER where upper(nome) > :nome";
				order = " ORDER BY nome ASC";
			}

			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql + order,	MpPessoaER.class)
				.setParameter("nome", nome.toUpperCase()).setMaxResults(1).getSingleResult();
			//	
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaER.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		//
	}

}