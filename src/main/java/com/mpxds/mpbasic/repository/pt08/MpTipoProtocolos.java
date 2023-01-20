package com.mpxds.mpbasic.repository.pt08;

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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.pt08.MpTipoProtocolo;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.pt08.MpTipoProtocoloFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTipoProtocolos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpTipoProtocoloFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpTipoProtocolo.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), 
																		MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), 
																		MatchMode.ANYWHERE));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpTipoProtocolo> filtrados(MpTipoProtocoloFilter filtro) {
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
	
	public int quantidadeFiltrados(MpTipoProtocoloFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpTipoProtocolo guardar(MpTipoProtocolo mpTipoProtocolo) {
		try {
			return manager.merge(mpTipoProtocolo);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Essa TIPO PROTOCOLO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpTipoProtocolo mpTipoProtocolo) throws MpNegocioException {
		try {
			mpTipoProtocolo = porId(mpTipoProtocolo.getId());
			manager.remove(mpTipoProtocolo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Tipo Protocolo... não pode ser excluído.");
		}
	}

	public MpTipoProtocolo porCodigo(String codigo) {
		//
		try {
			return manager.createQuery("from MpTipoProtocolo where codigo = :codigo " , 
										MpTipoProtocolo.class)
				.setParameter("codigo", codigo)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpTipoProtocolo porId(Long id) {
		return manager.find(MpTipoProtocolo.class, id);
	}
		
	public MpTipoProtocolo porNavegacao(String acao, String codigo) {
		//
//		System.out.println("MpTipoProtocolos.MpTipoProtocolo ( " + acao + " / " + descricao);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery("from MpTipoProtocolo where codigo < :codigo " +
													"ORDER BY codigo DESC",
					MpTipoProtocolo.class)
					.setParameter("codigo", codigo)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery("from MpTipoProtocolo where codigo > :codigo " +
													"ORDER BY codigo ASC",
					MpTipoProtocolo.class)
					.setParameter("codigo", codigo)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
		
}