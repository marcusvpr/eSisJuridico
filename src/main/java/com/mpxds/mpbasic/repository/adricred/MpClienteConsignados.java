package com.mpxds.mpbasic.repository.adricred;

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

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.adricred.MpClienteConsignado;
import com.mpxds.mpbasic.repository.filter.adricred.MpClienteConsignadoFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;	

public class MpClienteConsignados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpClienteConsignadoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpClienteConsignado.class);
		
		if (StringUtils.isNotBlank(filtro.getCodigo()))
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal()))
			criteria.add(Restrictions.ilike("documentoReceitaFederal", 
									filtro.getDocumentoReceitaFederal(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getTipoPessoa()))
			criteria.add(Restrictions.ilike("mpTipo.descricao", filtro.getTipoPessoa(),
																		 MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getStatus()))
			criteria.add(Restrictions.ilike("mpStatus.descricao", filtro.getStatus(),
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getSexo()))
			criteria.add(Restrictions.ilike("mpSexo.descricao", filtro.getSexo(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEstadoCivil()))
			criteria.add(Restrictions.ilike("mpEstadoCivil.descricao", filtro.getEstadoCivil(),
																			MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpClienteConsignado> filtrados(MpClienteConsignadoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpClienteConsignadoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpClienteConsignado guardar(MpClienteConsignado mpClienteConsignado) {
		try {
			return manager.merge(mpClienteConsignado);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse ClienteConsignado... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpClienteConsignado mpClienteConsignado) throws MpNegocioException {
		try {
			mpClienteConsignado = porId(mpClienteConsignado.getId());
			manager.remove(mpClienteConsignado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ClienteConsignado... não pode ser excluído.");
		}
	}

	public MpClienteConsignado porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpClienteConsignado where upper(codigo) = :codigo",
																			MpClienteConsignado.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpClienteConsignado porNome(String nome) {
		try {
			return manager.createQuery("from MpClienteConsignado where upper(nome) = :nome",
																			MpClienteConsignado.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpClienteConsignado> byNomeList() {
		return this.manager.createQuery("from MpClienteConsignado ORDER BY nome", MpClienteConsignado.class)
					.getResultList();
	}

	public List<MpClienteConsignado> porNomeList(String nome) {
		return this.manager.createQuery("from MpClienteConsignado " +
					"where upper(nome) like :nome ORDER BY nome", MpClienteConsignado.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpClienteConsignado.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public int countByIdadeDeAte(Integer idadeDe, Integer idadeAte) {
		Session session = this.manager.unwrap(Session.class);
		
		SQLQuery query =
				session.createSQLQuery("SELECT COUNT(data_Nascimento) FROM MP_ClienteConsignado " + 
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
	
	public MpClienteConsignado porId(Long id) {
		//
		return manager.find(MpClienteConsignado.class, id);
	}

	public MpClienteConsignado porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpClienteConsignados.MpClienteConsignado ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpClienteConsignado where upper(nome) < :nome ORDER BY nome DESC",
					MpClienteConsignado.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpClienteConsignado where upper(nome) > :nome ORDER BY nome ASC",
					MpClienteConsignado.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public long quantidadeRegistros() {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpClienteConsignado.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpClienteConsignado c",
//										Long.class).getSingleResult();
	}

}