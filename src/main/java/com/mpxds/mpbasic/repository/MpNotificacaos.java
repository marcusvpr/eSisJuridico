package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
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

import com.mpxds.mpbasic.model.MpNotificacao;
import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpNotificacaoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpNotificacaos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpNotificacaoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpNotificacao.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dataDe", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dataDe", filtro.getDataAte()));
		
		if (StringUtils.isNotBlank(filtro.getMensagem()))
			criteria.add(Restrictions.ilike("mensagem", filtro.getMensagem(), 
																			MatchMode.ANYWHERE));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpNotificacao> filtrados(MpNotificacaoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpNotificacaoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpNotificacao guardar(MpNotificacao mpNotificacao) {
		//
		try {
			return manager.merge(mpNotificacao);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa NOTIFICAÇÂO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpNotificacao mpNotificacao) throws MpNegocioException {
		//
		try {
			mpNotificacao = porId(mpNotificacao.getId());
			manager.remove(mpNotificacao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Notificação... não pode ser excluída.");
		}
	}

	public MpNotificacao porMensagem(String mensagem) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			String hql = "from MpNotificacao where upper(mensagem) = :mensagem";
			
			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
			
			return manager.createQuery(hql, MpNotificacao.class)
							.setParameter("mensagem", mensagem.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpNotificacao porId(Long id) {
		//
		return manager.find(MpNotificacao.class, id);
	}
	
	public List<MpNotificacao> mpNotificacaoList() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpNotificacao";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		
		hql =  hql + " ORDER BY dataDe";
		
		return manager.createQuery(hql, MpNotificacao.class).getResultList();
	}
	
	public String trataMensagemSistema() {
		//
		String tenantId = mpSeguranca.capturaTenantId();

		String hql = "from MpNotificacao where dataDe <= :dataDe " + 
										"and dataAte >= :dataAte " +
										"and mpStatusNotificacao = :mpStatusNotificacao ";
		
		if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
		//
		String mensagemSistema = "";
		//
//		Session session = this.manager.unwrap(Session.class);
//		
//		Criteria criteria = session.createCriteria(MpPedido.class);

		Date dataAtual = new Date();
		
//		criteria.add(Restrictions.ge("dataDe", dataAtual));
//		criteria.add(Restrictions.le("dataAte", dataAtual));
//		criteria.add(Restrictions.eq("mpTipoNotificacao", MpTipoNotificacao.SISTEMA));
//		criteria.add(Restrictions.eq("mpStatusNotificacao", MpStatusNotificacao.NOVA));
		//
		List<MpNotificacao> mpNotificacaoList =
				manager.createQuery(hql, MpNotificacao.class).
							setParameter("dataDe", dataAtual).
							setParameter("dataAte", dataAtual).
							setParameter("mpStatusNotificacao", MpStatusNotificacao.NOVA).
							getResultList();
		//	
//		System.out.println("MpNotificacaos.trataMensagemSistema() - sizeList ( " +
//																	mpNotificacaoList.size());
    	//		
    	Iterator<MpNotificacao> itr = mpNotificacaoList.iterator(); 
	    	
    	int cont = 0;
	    while(itr.hasNext()) {
	    	//
	    	MpNotificacao mpNotificacao = (MpNotificacao) itr.next();
	    	//
	    	cont++;
	    	if (cont ==1)
	    		mensagemSistema = mensagemSistema + "\n" + mpNotificacao.getMensagem();
	    	else
	    		mensagemSistema = mensagemSistema + "\n(" + cont + ") " + 
	    															mpNotificacao.getMensagem();	    	
	    }
		//
		return mensagemSistema;	
	}
	
	public MpNotificacao porNavegacao(String acao, Date dataDe) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			//
			String hql = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpNotificacao where dataDe < :dataDe";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + "  ORDER BY dataDe DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpNotificacao where dataDe > :dataDe";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataDe ASC";
			}
			//
			if (hql.isEmpty())
				return null;
			else
				return manager.createQuery(hql,	MpNotificacao.class)
							.setParameter("dataDe", dataDe).setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}