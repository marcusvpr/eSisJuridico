package com.mpxds.mpbasic.repository.sisJuri;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpTabelaInternaSJFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTabelaInternaSJs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private Criteria criarCriteriaParaFiltro(MpTabelaInternaSJFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpTabelaInternaSJ.class);		
		//
		if (StringUtils.isNotBlank(filtro.getTipoTabelaInternaSJ())) {
			//
			Disjunction or = Restrictions.disjunction();
			
			for (MpTipoTabelaInternaSJ mpTipoTabelaInternaSJX : MpTipoTabelaInternaSJ.values()) {
				//				
			    if (mpTipoTabelaInternaSJX.getDescricao().toUpperCase().contains(filtro.getTipoTabelaInternaSJ().
			    																						toUpperCase())) {
			        or.add(Restrictions.eq("mpTipoTabelaInternaSJ", mpTipoTabelaInternaSJX));
			    }
			}
			criteria.add(or);			
			//
		}
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTabelaInternaSJ> filtrados(MpTabelaInternaSJFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		//
		criteria.setFirstResult(filtro.getMpFilterOrdenacao().getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getMpFilterOrdenacao().getQuantidadeRegistros());
		
		if (filtro.getMpFilterOrdenacao().isAscendente() && filtro.getMpFilterOrdenacao().
															getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.asc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		else if (filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao() != null)
			criteria.addOrder(Order.desc(filtro.getMpFilterOrdenacao().getPropriedadeOrdenacao()));
		//
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MpTabelaInternaSJFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpTabelaInternaSJ guardar(MpTabelaInternaSJ mpTabelaInternaSJ) {
		//
		try {
			return this.manager.merge(mpTabelaInternaSJ);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa TABELA INTERNA... já foi alterada anteriormente!");
		}
	}

	public MpTabelaInternaSJ porId(Long id) {
		//
		return this.manager.find(MpTabelaInternaSJ.class, id);
	}
	public MpTabelaInternaSJ porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpTabelaInternaSJ where idCarga = :idCarga", MpTabelaInternaSJ.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@MpTransactional
	public void remover(MpTabelaInternaSJ mpTabelaInternaSJ) throws MpNegocioException {
		//
		try {
			mpTabelaInternaSJ = porId(mpTabelaInternaSJ.getId());
			manager.remove(mpTabelaInternaSJ);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("TABELA INTERNA... não pode ser excluída.");
		}
	}

	public MpTabelaInternaSJ porMpNumeroCodigo(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ, String codigo) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		try {
			String hql = "from MpTabelaInternaSJ where mpTipoTabelaInternaSJ = :mpTipoTabelaInternaSJ AND " +
					  														"upper(codigo) = :codigo";
			
			if (!tenantId.equals("0")) hql += " AND tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpTabelaInternaSJ.class)
				.setParameter("mpTipoTabelaInternaSJ", mpTipoTabelaInternaSJ)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
//	public List<MpTabelaInternaSJ> mpPaiList() {
//		//
//		String tenantId = mpSeguranca.capturaTenantId().trim();
//
//		String hql = "from MpTabelaInternaSJ where mpTipoTabelaInternaSJ > '0000'";
//		String order = " ORDER BY mpTipoTabelaInternaSJ, codigo";
//			
//		if (!tenantId.equals("0")) hql += " AND tenantId = '" + tenantId + "'";
//		
//		return manager.createQuery(hql + order, MpTabelaInternaSJ.class).getResultList();
//	}

	public List<MpTabelaInternaSJ> mpFilhaList(MpTabelaInternaSJ mpPai) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInternaSJ where mpPai = :mpPai";
		String order = " ORDER BY descricao";
			
		if (!tenantId.equals("0")) hql += " AND tenantId = '" + tenantId + "'";
		
		return manager.createQuery(hql + order, MpTabelaInternaSJ.class)
															.setParameter("mpPai", mpPai).getResultList();
	}
	
	public List<MpTabelaInternaSJ> mpNumeroList(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInternaSJ where mpTipoTabelaInternaSJ = :mpTipoTabelaInternaSJ";
		String order = " ORDER BY mpTipoTabelaInternaSJ + codigo";
			
		if (!tenantId.equals("0")) hql += " AND tenantId = '" + tenantId + "'";

		return manager.createQuery(hql + order, MpTabelaInternaSJ.class)
				.setParameter("mpTipoTabelaInternaSJ", mpTipoTabelaInternaSJ).getResultList();
	}
	
	public List<MpTabelaInternaSJ> mpNumeroDescricaoList(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();

		String hql = "from MpTabelaInternaSJ where mpTipoTabelaInternaSJ = :mpTipoTabelaInternaSJ";
		String order = " ORDER BY mpTipoTabelaInternaSJ + descricao";
			
		if (!tenantId.equals("0")) hql += " AND tenantId = '" + tenantId + "'";

		return manager.createQuery(hql + order, MpTabelaInternaSJ.class)
				.setParameter("mpTipoTabelaInternaSJ", mpTipoTabelaInternaSJ).getResultList();
	}

	public MpTabelaInternaSJ porNavegacao(String acao, String tipoTabelaInternaSJ, String codigo) {
		//
//		System.out.println("MpTabelaInternaSJs.MpTabelaInternaSJ ( " + acao + " / " + numero +
//																		 	  " / " + codigo);
		//
		try {
			//
			String tenantId = mpSeguranca.capturaTenantId().trim();
			//
			String hql = "FROM MpTabelaInternaSJ ti";
			String order = " ORDER BY ti.mpTipoTabelaInternaSJ.descricao";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql += " WHERE ti.mpTipoTabelaInternaSJ.descricao < :tipoTabelaInternaSJ AND UPPER(codigo) < :codigo";
				order += " DESC, ti.codigo DESC";
			} else if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql += " WHERE ti.mpTipoTabelaInternaSJ.descricao > :tipoTabelaInternaSJ AND UPPER(codigo) > :codigo";
				order += " ASC, ti.codigo ASC";
			}
			//
			if (!tenantId.equals("0"))
				hql += " AND ti.tenantId = '" + tenantId + "'";
			//
//			System.out.println("MpTabelaIntenas.porNavegacao ( hql = " + hql + order + " / " + tipoTabelaInternaSJCodigo);
//
			return manager.createQuery(hql + order, MpTabelaInternaSJ.class)
				.setParameter("tipoTabelaInternaSJ", tipoTabelaInternaSJ)
				.setParameter("codigo", codigo.toUpperCase())
				.setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}	
	
}
