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

import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpPessoaFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoas implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpPessoaFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpPessoa.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getTipo()))
			criteria.add(Restrictions.ilike("mpTipo.descricao", filtro.getTipo(),	
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getProfissao()))
			criteria.add(Restrictions.ilike("mpProfissao.descricao", filtro.getProfissao(),
																		MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoa> filtrados(MpPessoaFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPessoaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoa guardar(MpPessoa mpPessoa) {
		try {
			return manager.merge(mpPessoa);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoa mpPessoa) throws MpNegocioException {
		try {
			mpPessoa = porId(mpPessoa.getId());
			manager.remove(mpPessoa);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PESSOA... não pode ser excluída.");
		}
	}

	public MpPessoa porCodigo(String codigo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "FROM MpPessoa WHERE upper(codigo) = :codigo";
			
			if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpPessoa.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoa porNome(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		try {
			String hql = "FROM MpPessoa WHERE upper(nome) = :nome";
			
			if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql, MpPessoa.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoa> porNomeList(String nome) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoa WHERE upper(nome) LIKE :nome";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoa.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public List<MpPessoa> porPessoaList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoa";

		if (!tenantId.equals("0")) hql = hql + " WHERE tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoa.class).getResultList();
	}

	public List<MpPessoa> porPessoaAtivoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "FROM MpPessoa WHERE mpStatus = 'ATIVO'";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoa.class).getResultList();
	}

	public List<MpPessoa> porPessoaMedicoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpPessoa where mpProfissaoPessoa = 'MEDICO'";

		if (!tenantId.equals("0")) hql = hql + " AND  tenantId = '" + tenantId + "'";

		hql = hql + " ORDER BY nome";

		return this.manager.createQuery(hql, MpPessoa.class)
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
	
	public MpPessoa porId(Long id) {
		return manager.find(MpPessoa.class, id);
	}

	public MpPessoa porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpPessoas.MpPessoa ( " + acao + " / " + nome);
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			//
			String hql = "";
			String order = "";
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) { 
				hql = "from MpPessoa where upper(nome) < :nome";
				order = " ORDER BY nome DESC";
			} else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) { 
				hql = "from MpPessoa where upper(nome) > :nome";
				order = " ORDER BY nome ASC";
			}

			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = '" + tenantId + "'";
			//
			return manager.createQuery(hql + order,	MpPessoa.class)
				.setParameter("nome", nome.toUpperCase()).setMaxResults(1).getSingleResult();
			//	
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoa.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		//
	}

}