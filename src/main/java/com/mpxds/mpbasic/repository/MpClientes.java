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

import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpClienteFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpClientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpClienteFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpCliente.class);
		
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
	public List<MpCliente> filtrados(MpClienteFilter filtro) {
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
	
	public int quantidadeFiltrados(MpClienteFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpCliente guardar(MpCliente mpCliente) {
		try {
			return manager.merge(mpCliente);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse CLIENTE... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpCliente mpCliente) throws MpNegocioException {
		try {
			mpCliente = porId(mpCliente.getId());
			manager.remove(mpCliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("CLIENTE... não pode ser excluído.");
		}
	}

	public MpCliente porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpCliente where upper(codigo) = :codigo",
																			MpCliente.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpCliente porNome(String nome) {
		try {
			return manager.createQuery("from MpCliente where upper(nome) = :nome",
																			MpCliente.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpCliente> byNomeList() {
		return this.manager.createQuery("from MpCliente ORDER BY nome", MpCliente.class)
					.getResultList();
	}

	public List<MpCliente> porNomeList(String nome) {
		return this.manager.createQuery("from MpCliente " +
					"where upper(nome) like :nome ORDER BY nome", MpCliente.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpCliente.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public int countByIdadeDeAte(Integer idadeDe, Integer idadeAte) {
		Session session = this.manager.unwrap(Session.class);
		
		SQLQuery query =
				session.createSQLQuery("SELECT COUNT(data_Nascimento) FROM MP_CLIENTE " + 
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
	
	public MpCliente porId(Long id) {
		//
		return manager.find(MpCliente.class, id);
	}

	public MpCliente porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpClientes.MpCliente ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpCliente where upper(nome) < :nome ORDER BY nome DESC",
					MpCliente.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpCliente where upper(nome) > :nome ORDER BY nome ASC",
					MpCliente.class)
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
		
		Criteria criteria = session.createCriteria(MpCliente.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpCliente c",
//										Long.class).getSingleResult();
	}

}