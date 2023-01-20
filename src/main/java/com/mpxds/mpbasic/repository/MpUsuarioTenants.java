package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpUsuarioTenant;

import com.mpxds.mpbasic.model.enums.MpGrupoMenu;
import com.mpxds.mpbasic.model.enums.MpGrupoTenant;
import com.mpxds.mpbasic.model.enums.MpStatus;

import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.repository.filter.MpUsuarioTenantFilter;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpUsuarioTenants implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpGrupos mpGrupos;

	// ---

	private Criteria criarCriteriaParaFiltro(MpUsuarioTenantFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpUsuarioTenant.class);
		
		if (StringUtils.isNotBlank(filtro.getLogin()))
			criteria.add(Restrictions.ilike("login", filtro.getLogin(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("mpPessoa.nome", filtro.getNome(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("mpPessoa.email", filtro.getEmail(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.eq("mpStatus", MpStatus.valueOf(filtro.getStatus())));
		//		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpUsuarioTenant> filtrados(MpUsuarioTenantFilter filtro) {
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
	
	public int quantidadeFiltrados(MpUsuarioTenantFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
		
	public MpUsuarioTenant guardar(MpUsuarioTenant mpUsuarioTenant) {
		//
		try {
			return manager.merge(mpUsuarioTenant);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse USUÁRIO AMBIENTE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpUsuarioTenant mpUsuarioTenant) throws MpNegocioException {
		//
		try {
			mpUsuarioTenant = porId(mpUsuarioTenant.getId());
			manager.remove(mpUsuarioTenant);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("USUÁRIO AMBIENTE... não pode ser excluído.");
		}
	}

	public MpUsuarioTenant porNome(String nome) {
		try {
			return manager.createQuery("from MpUsuarioTenant where upper(mpPessoa.nome) = :nome",
				MpUsuarioTenant.class).setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpUsuarioTenant porId(Long id) {
		//
		return manager.find(MpUsuarioTenant.class, id);
	}
				
	
	public List<MpUsuarioTenant> mpVendedores() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where mpStatus = 'ATIVO' AND mpGrupoTenant = 'VENDEDOR'";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

		hql = hql +	" ORDER BY mpPessoa.nome";
		//
		return this.manager.createQuery(hql, MpUsuarioTenant.class).getResultList();
	}
	
	public List<MpObjeto> porMpGrupoMenu(MpGrupoMenu mpGrupoMenu,
															MpUsuarioTenant mpUsuarioTenant) {
		//
        SortedSet<MpObjeto> mpObjetoSortedSet = new TreeSet<>(Comparator.comparing(MpObjeto::
        																	getMpGrupamentoNome));
        //
        MpGrupo mpGrupo = new MpGrupo();
        
        if (mpUsuarioTenant.getMpGrupoTenant().equals(MpGrupoTenant.CUIDADOR))
        	mpGrupo = mpGrupos.porNome("PROTESTOS");
        else
            if (mpUsuarioTenant.getMpGrupoTenant().equals(MpGrupoTenant.EMPREGADO))
            	mpGrupo = mpGrupos.porNome("PROTESTOS");
            else
            	mpGrupo = mpGrupos.porNome("PROTESTOS");
        	
		mpObjetoSortedSet.addAll(mpGrupo.getMpObjetos());
		//
		List<MpObjeto> mpObjetoList = new ArrayList<MpObjeto>();

        for(MpObjeto mpObjeto : mpObjetoSortedSet) {
    		//
    		if (mpGrupoMenu.equals(mpObjeto.getMpGrupoMenu()))
    			mpObjetoList.add(mpObjeto);
    	}
        //
		return mpObjetoList;
	}

	public List<MpUsuarioTenant> mpUsuarioTenantAtivos() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where mpStatus = 'ATIVO'";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

		hql = hql +	" ORDER BY mpPessoa.nome";
		
		return this.manager.createQuery(hql, MpUsuarioTenant.class).getResultList();
	}
		
	public List<MpUsuarioTenant> mpUsuarioTenantBloqueados() {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where mpStatus = 'BLOQUEADO'";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

		hql = hql +	" ORDER BY mpPessoa.nome";

		return this.manager.createQuery(hql, MpUsuarioTenant.class).getResultList();
	}

	public MpUsuarioTenant porLoginEmail(String loginEmail) {
		//
		if (null == loginEmail || loginEmail.isEmpty()) return null;
		//	
		MpUsuarioTenant mpUsuarioTenant = null;
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where (lower(login) = :loginEmail" + 
				  							" or lower( mpPessoa.email) = :loginEmail)";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		try {
			mpUsuarioTenant = this.manager.createQuery(hql,	MpUsuarioTenant.class)
					.setParameter("loginEmail", loginEmail.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o login OU e-mail informado
		}
		//
		return mpUsuarioTenant;
	}

	public MpUsuarioTenant porLoginEmailAmbiente(String loginEmail, String ambiente) {
		//
		if (null == loginEmail || loginEmail.isEmpty())return null;
		//	
		MpUsuarioTenant mpUsuarioTenant = null;
		//
		try {
			if (ambiente.isEmpty()) {
				String tenantId = mpSeguranca.capturaTenantId().trim();
				//
				String hql = "from MpUsuarioTenant where (lower(login) = :loginEmail" + 
												" or lower( mpPessoa.email) = :loginEmail)";
			
				if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
					
				mpUsuarioTenant = this.manager.createQuery(hql, MpUsuarioTenant.class)
						.setParameter("loginEmail", loginEmail.toLowerCase())
						.getSingleResult();
			} else
				mpUsuarioTenant = this.manager.createQuery(
						"from MpUsuarioTenant where (lower(login) = :loginEmail" + 
						" or lower( mpPessoa.email) = :loginEmail) and tenantId = :ambiente",
						MpUsuarioTenant.class)
						.setParameter("loginEmail", loginEmail.toLowerCase())
						.setParameter("ambiente", ambiente.trim())
						.getSingleResult();
			//
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o login OU e-mail informado
		}
		//
		return mpUsuarioTenant;
	}

	public MpUsuarioTenant porLogin(String login) {
		//
		if (null == login || login.isEmpty()) return null;
		//	
		MpUsuarioTenant mpUsuarioTenant = null;
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where lower(login) = :login";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = '" + tenantId + "'";
		
		try {
			mpUsuarioTenant = this.manager.createQuery(hql, MpUsuarioTenant.class)
						.setParameter("login", login.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o login informado
		}
		//
		return mpUsuarioTenant;
	}

	public MpUsuarioTenant porEmail(String email) {
		//
		if (null == email || email.isEmpty()) return null;
		//	
		MpUsuarioTenant mpUsuarioTenant = null;
		//		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql = "from MpUsuarioTenant where lower(mpPessoa.email) = :email";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		try {
			mpUsuarioTenant = this.manager.createQuery(hql,	MpUsuarioTenant.class)
								.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		//
		return mpUsuarioTenant;
	}
	
	public MpUsuarioTenant porNavegacao(String acao, String login) {
		//
//		System.out.println("MpUsuarioTenants.MpUsuarioTenant ( " + acao + " / " + nome);
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		String hql1 = "from MpUsuarioTenant where upper(login) < :login";
		String hql2 = "from MpUsuarioTenant where upper(login) > :login";
		
		if (!tenantId.equals("0")) {
			hql1 = hql1 + " AND  tenantId = '" + tenantId + "'";
			hql2 = hql2 + " AND  tenantId = '" + tenantId + "'";
		}
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(hql1 + " ORDER BY login DESC",
					MpUsuarioTenant.class)
					.setParameter("login", login.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(hql2 + " ORDER BY login ASC",
					MpUsuarioTenant.class)
					.setParameter("login", login.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}