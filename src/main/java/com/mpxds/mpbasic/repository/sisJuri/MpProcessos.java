package com.mpxds.mpbasic.repository.sisJuri;

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

import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpProcessoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;
import com.mpxds.mpbasic.security.MpSeguranca;

public class MpProcessos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private Criteria criarCriteriaParaFiltro(MpProcessoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpProcesso.class);
		
		if (StringUtils.isNotBlank(filtro.getProcessoCodigo()))
			criteria.add(Restrictions.ilike("processoCodigo", filtro.getProcessoCodigo(), MatchMode.ANYWHERE));
		if (filtro.getDataCadastroDe() != null)
			criteria.add(Restrictions.ge("dataCadastro", filtro.getDataCadastroDe()));
		if (filtro.getDataCadastroAte() != null)
			criteria.add(Restrictions.le("dataCadastro", filtro.getDataCadastroAte()));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpProcesso> filtrados(MpProcessoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpProcessoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpProcesso guardar(MpProcesso mpProcesso) {
		//
		try {
			return manager.merge(mpProcesso);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException("Erro de concorrência. Esse PROCESSO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpProcesso mpProcesso) throws MpNegocioException {
		//
		try {
			mpProcesso = porId(mpProcesso.getId());
			manager.remove(mpProcesso);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PROCESSO... não pode ser excluído.");
		}
	}

	public MpProcesso porDataCadastro(Date dataCadastro) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
//		Calendar calendar = Calendar.getInstance();
//		//
//		calendar.setTime(dataCadastro);
//		//
//		calendar.set(Calendar.HOUR_OF_DAY,0);
//		calendar.set(Calendar.MINUTE,0);
//		calendar.set(Calendar.SECOND,0);
//		calendar.set(Calendar.MILLISECOND,0);
//		//
//		dataCadastro = calendar.getTime();
		//
		try {
			String hql = "from MpProcesso where dataCadastro = :dataCadastro";
			
			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;
			
			return manager.createQuery(hql,	MpProcesso.class)
								.setParameter("dataCadastro", dataCadastro).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpProcesso porId(Long id) {
		//
		return manager.find(MpProcesso.class, id);
	}
	public MpProcesso porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpProcesso where idCarga = :idCarga", MpProcesso.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
		
	public List<MpProcesso> findAllByDtHr(Date dataIni, Date dataFim) {
		//
		String hql = "FROM MpProcesso WHERE dataCadastro >= :dataIni AND dataCadastro <= :dataFim" +  
					 " ORDER BY dataCadastro";
		
		return manager.createQuery(hql,	MpProcesso.class)
				.setParameter("dataIni", dataIni)
				.setParameter("dataFim", dataFim)
				.getResultList();
	}

	public List<MpProcesso> byProcessoCodigoList() {
		return this.manager.createQuery("from MpProcesso ORDER BY processoCodigo", MpProcesso.class)
					.getResultList();
	}

	public List<MpProcesso> findAllByProcessoCodigo(String processoCodigoX) {
		//
//		MpAppUtil.PrintarLn("MpProcessos.findAllByProcessoCodigo() - 000 ( " + processoCodigoX);
		
		String hql = "FROM MpProcesso p WHERE TRIM(p.processoCodigo) = :processoCodigoX";  
		
		return manager.createQuery(hql,	MpProcesso.class)
				.setParameter("processoCodigoX", processoCodigoX)
				.getResultList();
	}
		
	public MpProcesso porNavegacao(String acao, Date dataCadastro) {
		//
		String tenantId = mpSeguranca.capturaTenantId();
		//
		try {
			//
			String hql = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "FROM MpProcesso WHERE dataCadastro < :dataCadastro";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataCadastro DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "FROM MpProcesso WHERE dataCadastro > :dataCadastro";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataCadastro ASC";
			}
			//
			if (hql.isEmpty())
				return null;
			else
				return manager.createQuery(hql,	MpProcesso.class)
					.setParameter("dataCadastro", dataCadastro).setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}
	
}