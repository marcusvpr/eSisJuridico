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

import com.mpxds.mpbasic.model.MpChamado;
//import com.mpxds.mpbasic.model.enums.MpChamadoStatus;
//import com.mpxds.mpbasic.model.enums.MpChamadoTipo;
//import com.mpxds.mpbasic.model.enums.MpChamadoAreaTipo;
//import com.mpxds.mpbasic.model.enums.MpChamadoSeveridade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpChamadoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpChamados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpChamadoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpChamado.class);
		
		if (filtro.getDataDe() != null)
			criteria.add(Restrictions.ge("dtHrChamado", filtro.getDataDe()));
		if (filtro.getDataAte() != null)
			criteria.add(Restrictions.le("dtHrChamado", filtro.getDataAte()));
		
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpChamado> filtrados(MpChamadoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpChamadoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpChamado guardar(MpChamado mpChamado) {
		try {
			return manager.merge(mpChamado);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Esse CHAMADO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpChamado mpChamado) throws MpNegocioException {
		try {
			mpChamado = porId(mpChamado.getId());
			manager.remove(mpChamado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CHAMADO... não pode ser excluído.");
		}
	}

	public MpChamado porMensagem(String mensagem) {
		try {
			return manager.createQuery("from MpChamado where upper(mensagem) = :mensagem",
																			MpChamado.class)
				.setParameter("mensagem", mensagem.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpChamado porId(Long id) {
		return manager.find(MpChamado.class, id);
	}
	
	public List<MpChamado> mpChamadoList() {
		return manager.createQuery("from MpChamado ORDER BY dtHrChamado", 
													MpChamado.class).getResultList();
	}
		
	public MpChamado porNavegacao(String acao, Date dtHrChamado) {
		//
//		System.out.println("MpChamados.MpChamado ( " + acao + " / " + mensagem);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpChamado where dtHrChamado < :dtHrChamado ORDER BY dtHrChamado DESC",
					MpChamado.class)
					.setParameter("dtHrChamado", dtHrChamado)
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpChamado where dtHrChamado > :dtHrChamado ORDER BY dtHrChamado ASC",
					MpChamado.class)
					.setParameter("dtHrChamado", dtHrChamado)
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}