package com.mpxds.mpbasic.repository.sisJuri;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.sisJuri.MpProcessoAndamento;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProcessoAndamentos implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
		
	public MpProcessoAndamento guardar(MpProcessoAndamento mpProcessoAndamento) {
		try {
			return manager.merge(mpProcessoAndamento);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse Processo Andamento... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpProcessoAndamento mpProcessoAndamento) throws MpNegocioException {
		//
		try {
			mpProcessoAndamento = porId(mpProcessoAndamento.getId());
			manager.remove(mpProcessoAndamento);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Processo Andamento... não pode ser excluído.");
		}
	}
	
	public MpProcessoAndamento porId(Long id) {
		//
		return manager.find(MpProcessoAndamento.class, id);
	}
	public MpProcessoAndamento porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpProcessoAndamento where idCarga = :idCarga", MpProcessoAndamento.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpProcessoAndamento> findAllByDtHr(Date dataIni, Date dataFim) {
		//
		String hql = "FROM MpProcessoAndamento pa" + 
					 " WHERE pa.dataCadastro >= :dataIni AND pa.dataCadastro <= :dataFim" +  
				 	 " ORDER BY pa.dataCadastro";
	
		return manager.createQuery(hql,	MpProcessoAndamento.class)
			.setParameter("dataIni", dataIni)
			.setParameter("dataFim", dataFim)
			.getResultList();
	}
	public List<MpProcessoAndamento> findAllByDtHrX(Date dataIni, Date dataFim) {
		//
		String hql = "FROM MpProcessoAndamento pa INNER JOIN FETCH pa.mpProcesso p WHERE pa.mpProcesso.id = p.id" + 
					 " AND p.dataCadastro >= :dataIni AND p.dataCadastro <= :dataFim" +  
				 	 " ORDER BY p.dataCadastro";
	
		return manager.createQuery(hql,	MpProcessoAndamento.class)
			.setParameter("dataIni", dataIni)
			.setParameter("dataFim", dataFim)
			.getResultList();
	}

	public List<MpProcessoAndamento> findAllFilhas(Long paiId) {
		//
		String hql = "FROM MpProcessoAndamento pa WHERE pa.mpProcesso.id = :paiId"; 
	
		return manager.createQuery(hql,	MpProcessoAndamento.class)
			.setParameter("paiId", paiId)
			.getResultList();
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpProcessoAndamento.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpProcessoAndamento c",
//										Long.class).getSingleResult();
	}

}