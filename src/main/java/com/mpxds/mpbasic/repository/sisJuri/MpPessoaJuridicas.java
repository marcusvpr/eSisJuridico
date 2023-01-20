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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpxds.mpbasic.model.sisJuri.MpPessoaJuridica;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaSJ;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpPessoaJuridicaFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaJuridicas implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(MpPessoaJuridicaFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaJuridica.class);
		
		if (StringUtils.isNotBlank(filtro.getNome()))
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getEmail()))
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPessoaJuridica> filtrados(MpPessoaJuridicaFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPessoaJuridicaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoaJuridica guardar(MpPessoaJuridica mpPessoaJuridica) {
		try {
			return manager.merge(mpPessoaJuridica);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Essa PESSOA JURÍDICA... já foi alterada anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpPessoaJuridica mpPessoaJuridica) throws MpNegocioException {
		try {
			mpPessoaJuridica = porId(mpPessoaJuridica.getId());
			manager.remove(mpPessoaJuridica);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("PESSOA JURÍDICA... não pode ser excluída.");
		}
	}

	public MpPessoaJuridica porCodigo(String codigo) {
		try {
			return manager.createQuery("from MpPessoaJuridica where upper(codigo) = :codigo",
																			MpPessoaJuridica.class)
				.setParameter("codigo", codigo.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoaJuridica porNome(String nome) {
		try {
			return manager.createQuery("from MpPessoaJuridica where upper(nome) = :nome",
																			MpPessoaJuridica.class)
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MpPessoaJuridica> byNomeList() {
		return this.manager.createQuery("from MpPessoaJuridica ORDER BY nome", MpPessoaJuridica.class)
					.getResultList();
	}
	public List<MpPessoaJuridica> byNomeResponsavelList() {
		return this.manager.createQuery("from MpPessoaJuridica where indResponsavel = :indResp ORDER BY nome",
				MpPessoaJuridica.class).setParameter("indResp", true).getResultList();
	}

	public List<MpPessoaJuridica> porNomeList(String nome) {
		return this.manager.createQuery("from MpPessoaJuridica " +
					"where upper(nome) like :nome ORDER BY nome", MpPessoaJuridica.class)
					.setParameter("nome", nome.toUpperCase() + "%")
					.getResultList();
	}

	public int countByCodigo(String codigo) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPessoaJuridica.class);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public MpPessoaJuridica porId(Long id) {
		//
		return manager.find(MpPessoaJuridica.class, id);
	}
	public MpPessoaJuridica porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpPessoaJuridica where idCarga = :idCarga", MpPessoaJuridica.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MpPessoaJuridica porNavegacao(String acao, String nome) {
		//
//		System.out.println("MpPessoaJuridicas.MpPessoaJuridica ( " + acao + " / " + nome);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
					"from MpPessoaJuridica where upper(nome) < :nome ORDER BY nome DESC",
					MpPessoaJuridica.class)
					.setParameter("nome", nome.toUpperCase())
					.setMaxResults(1)
					.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
					"from MpPessoaJuridica where upper(nome) > :nome ORDER BY nome ASC",
					MpPessoaJuridica.class)
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
		
		Criteria criteria = session.createCriteria(MpPessoaJuridica.class);
		
		criteria.add(Restrictions.gt("id", 0L));
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).longValue();
		
//		return manager.createQuery("select count(c) from MpPessoaJuridica c",
//										Long.class).getSingleResult();
	}

}