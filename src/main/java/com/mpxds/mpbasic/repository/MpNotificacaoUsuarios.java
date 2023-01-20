package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpNotificacaoUsuarioFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpNotificacaoUsuarios implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpNotificacaoUsuarioFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
//		
//		if (!tenantId.equals("0"))
//			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
				
		Criteria criteria = session.createCriteria(MpNotificacaoUsuario.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dataEnvio", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dataEnvio", filtro.getDataAte()));
		//
		if (StringUtils.isNotBlank(filtro.getUsuario()))
			criteria.add(Restrictions.ilike("nomeUsuario", filtro.getUsuario(),
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getAssunto()))
			criteria.add(Restrictions.ilike("assunto", filtro.getAssunto(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getMensagem()))
			criteria.add(Restrictions.ilike("mensagem", filtro.getMensagem(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.ilike("mpStatusNotificacao.descricao", filtro.getStatus(),
																			MatchMode.ANYWHERE));
		//
		if (!tenantId.equals("0"))
			if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario()) 
				criteria.add(Restrictions.eq("mpUsuarioTenant.id",
															mpSeguranca.getMpUsuarioLogado().
																getMpUsuarioTenant().getId()));
			else
				criteria.add(Restrictions.eq("mpUsuario.id", mpSeguranca.getMpUsuarioLogado().
																getMpUsuario().getId()));		
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpNotificacaoUsuario> filtrados(MpNotificacaoUsuarioFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpNotificacaoUsuarioFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpNotificacaoUsuario guardar(MpNotificacaoUsuario mpNotificacaoUsuario) {
		//
		try {
			return manager.merge(mpNotificacaoUsuario);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
			"Erro de concorrência. Essa NOTIFICAÇÂO USUÁRIO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpNotificacaoUsuario mpNotificacaoUsuario) throws MpNegocioException {
		//
		try {
			mpNotificacaoUsuario = porId(mpNotificacaoUsuario.getId());
			manager.remove(mpNotificacaoUsuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Notificação Usuário... não pode ser excluída.");
		}
	}
		
	public MpNotificacaoUsuario porId(Long id) {
		//
		return manager.find(MpNotificacaoUsuario.class, id);
	}
	
	public List<MpNotificacaoUsuario> mpNotificacaoUsuarioList(String selecao) {
		//
//		String tenantId = mpSeguranca.capturaTenantId();
		//
		List<MpNotificacaoUsuario>  mpNotificacaoUsuarioList;
		//
		String hql = "FROM MpNotificacaoUsuario WHERE";

		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario()) 
			hql = hql + " mpUsuarioTenant.id = :id" ;
		else
			hql = hql + " mpUsuario.id = :id" ;
		//
		if (selecao.equals("LEITURA"))
			hql = hql + " AND indLeitura = FALSE" ;
		//
//		if (!tenantId.equals("0")) hql = hql + " AND tenantId = " + tenantId;
				
		hql = hql + " ORDER BY dataEnvio DESC";
		//
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario()) 
			mpNotificacaoUsuarioList = manager.createQuery(hql,	MpNotificacaoUsuario.class).
								setParameter("id", mpSeguranca.getMpUsuarioLogado().
											getMpUsuarioTenant().getId()).getResultList();	
		else
			mpNotificacaoUsuarioList = manager.createQuery(hql,	MpNotificacaoUsuario.class).
								setParameter("id", mpSeguranca.getMpUsuarioLogado().
												getMpUsuario().getId()).getResultList();
		//
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario()) 
			System.out.println("MpNotificacaoUsuarios.mpNotificacaoUsuarioList() - 000 ( hql = " + 
				hql + " / Size = " + mpNotificacaoUsuarioList.size() + " / Id = " + 
				mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getMpPessoa().getId());
		else
			System.out.println("MpNotificacaoUsuarios.mpNotificacaoUsuarioList() - 000 ( hql = " + 
				hql + " / Size = " + mpNotificacaoUsuarioList.size() + " / Id = " + 
				mpSeguranca.getMpUsuarioLogado().getMpUsuario().getId());
		//
		return mpNotificacaoUsuarioList;
	}
	
	public List<MpNotificacaoUsuario> mpNotificacaoUsuarioNovaList(MpUsuario mpUsuario) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpNotificacaoUsuario" +
						 		" WHERE mpUsuario = :mpUsuario AND ind_leitura = FALSE";
		
		if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";
		
		hql = hql + " ORDER BY dataEnvio DESC";
		
		return manager.createQuery(hql,	MpNotificacaoUsuario.class).setParameter("mpUsuario",
																	mpUsuario).getResultList();		
	}
	
	public MpNotificacaoUsuario porNavegacao(String acao, Date dataEnvio) {
		//
//		System.out.println("MpNotificacaoUsuarios.MpNotificacaoUsuario ( " + acao + " / " + 
//																						mensagem);
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			//
			String hql = "";
			String order = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpNotificacaoUsuario where dataEnvio < :dataEnvio";
				order = " ORDER BY dataEnvio DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpNotificacaoUsuario where dataEnvio > :dataEnvio";
				order = " ORDER BY dataEnvio ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpNotificacaoUsuario.class)
										.setParameter("dataEnvio", dataEnvio)
										.setMaxResults(1).getSingleResult();
			//						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}