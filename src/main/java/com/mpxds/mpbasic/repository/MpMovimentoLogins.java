package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.model.vo.MpDataQuantidade;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpMovimentoLoginFilter;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpMovimentoLogins implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	// ---

	private Criteria criarCriteriaParaFiltro(MpMovimentoLoginFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpMovimentoLogin.class);

		if (filtro.getDataCriacaoDe() != null)
			criteria.add(Restrictions.ge("dtHrMovimento", filtro.getDataCriacaoDe()));
		if (filtro.getDataCriacaoAte() != null)
			criteria.add(Restrictions.le("dtHrMovimento", filtro.getDataCriacaoAte()));
				
		if (StringUtils.isNotBlank(filtro.getAtividade()))
			criteria.add(Restrictions.ilike("atividade", filtro.getAtividade(),
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getNumeroIP()))
			criteria.add(Restrictions.ilike("numeroIP", filtro.getNumeroIP(), 
																			MatchMode.ANYWHERE));
		if (StringUtils.isNotBlank(filtro.getUsuario()))
			criteria.add(Restrictions.ilike("usuarioLogin", filtro.getUsuario(), 
																			MatchMode.ANYWHERE));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpMovimentoLogin> filtrados(MpMovimentoLoginFilter filtro) {
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
	
	public int quantidadeFiltrados(MpMovimentoLoginFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Date, Long> valoresTotaisPorData(Integer numeroDeDias) {
		//
		Session session = manager.unwrap(Session.class);
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, Long> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(MpMovimentoLogin.class);
		
		// select date(data_criacao) as data, sum(valor_total) as valor 
		// from mpPedido where data_criacao >= :dataInicial and vendedor_id = :criadoPor 
		// group by date(data_criacao)
		
//		criteria.setProjection(Projections.projectionList()
//				.add(Projections.sqlGroupProjection("date(data_criacao) as data", 
//						"date(data_criacao)", new String[] { "data" }, 
//						new Type[] { StandardBasicTypes.DATE } ))
//				.add(Projections.sum("valorTotal").as("valor"))
//			)
//			.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));

//		criteria.setProjection(Projections.projectionList()
//				.add(Projections.sqlGroupProjection("dtHrMovimento as data", 
//						"dtHrMovimento", new String[] { "data" }, 
//						new Type[] { StandardBasicTypes.DATE } ))
//				.add(Projections.count("atividade").as("valor"))
//			)
//			.add(Restrictions.ge("dtHrMovimento", dataInicial.getTime()));

//		"year({alias}.dtHrMovimento), " +
//		"month({alias}.dtHrMovimento), " +
//		"day({alias}.dtHrMovimento)" ,
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection(
						"trunc({alias}.dtHr_Movimento) as data" ,
						"trunc({alias}.dtHr_Movimento)" ,
						new String[] { "data" } , 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.count("usuarioLogin").as("valor"))
		)
		.add(Restrictions.ge("dtHrMovimento", dataInicial.getTime()));

		List<MpDataQuantidade> mpValoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(MpDataQuantidade.class)).list();
		
		for (MpDataQuantidade mpDataQuantidade : mpValoresPorData) {
			resultado.put(mpDataQuantidade.getData(), mpDataQuantidade.getValor());
		}
		//
		return resultado;
	}

	private Map<Date, Long> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, Long> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), 0L);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		//
		return mapaInicial;
	}
	
	public MpMovimentoLogin guardar(MpMovimentoLogin mpMovimentoLogin) {
		try {
			return manager.merge(mpMovimentoLogin);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
				"Erro de concorrência. Esse Movimento Login... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpMovimentoLogin mpMovimentoLogin) throws MpNegocioException {
		try {
			mpMovimentoLogin = porId(mpMovimentoLogin.getId());
			manager.remove(mpMovimentoLogin);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Movimento Login... não pode ser excluído.");
		}
	}

	public List<MpMovimentoLogin> porAtividadeList(String atividade) {
		return this.manager.createQuery("from MpMovimentoLogin " +
			"where upper(atividade) like :atividade ORDER BY atividade", MpMovimentoLogin.class)
			.setParameter("atividade", atividade.toUpperCase() + "%")
			.getResultList();
	}
	
	public MpMovimentoLogin porId(Long id) {
		return manager.find(MpMovimentoLogin.class, id);
	}
	
	public Long totalMovimento(String param) {
    	String query = "SELECT COUNT(ml) FROM MpMovimentoLogin ml WHERE ";
    	//
    	if (param.equals("total")) 
    		query = query + "ml.atividade = 'Login'";
    	else {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        	//
        	if (param.equals("dia"))
        		query = query + "ml.atividade = 'Login' AND " + 
        										"TO_CHAR(ml.dtHrMovimento, 'YYYY/MM/DD') = '" +
        										sdf.format(new Date()) + "'";
        	else
            	if (param.equals("semanaPercentual")) {
            		//
            		query = query + "ml.atividade = 'Login'";
            		//
            		Calendar calSemAtualI = Calendar.getInstance();
            		Calendar calSemAtualF = Calendar.getInstance();

            		Calendar calSemPassadaI = Calendar.getInstance();
            		Calendar calSemPassadaF = Calendar.getInstance();
            		
            		calSemAtualF.setTime(new Date());
            		calSemAtualI.add(Calendar.DAY_OF_MONTH, -6);
            		
            		calSemPassadaF.add(Calendar.DAY_OF_MONTH, -7);
            		calSemPassadaI.add(Calendar.DAY_OF_MONTH, -13);
            		//
            		String queryI = query + " AND TO_CHAR(ml.dtHrMovimento, 'YYYY/MM/DD') >= '" +
            									sdf.format(calSemAtualI.getTime()) + "'";
            		queryI = queryI + " AND TO_CHAR(ml.dtHrMovimento, 'YYYY/MM/DD') <= '" +
												sdf.format(calSemAtualF.getTime()) + "'";
            	
            		Long totSemAtual = (long) manager.createQuery(queryI).getSingleResult();
            		//
            		String queryF = query + " AND TO_CHAR(ml.dtHrMovimento, 'YYYY/MM/DD') >= '" +
            									sdf.format(calSemPassadaI.getTime()) + "'";
            		queryF = queryF + " AND TO_CHAR(ml.dtHrMovimento, 'YYYY/MM/DD') <= '" +
												sdf.format(calSemPassadaF.getTime()) + "'";
            	
            		Long totSemPassada = (long) manager.createQuery(queryF).getSingleResult();

//            		System.out.println("MpMovimentoLogins.totalMovimento (TotSemAtu = " + 
//            								totSemAtual + " /TotSemPass = " + totSemPassada);
            		//
            		if (totSemPassada < 1) return 0L; 
            		
            		Double percentual = (double) totSemAtual / (double) totSemPassada;
            		percentual = (percentual - 1) * 100;
            		
            		return Double.valueOf(percentual).longValue();
            	}
    	}
    	//
//    	System.out.println("MpPesquisaTabelaBDsBean() - 001 = " + query);
//    	
    	return (long) manager.createQuery(query).getSingleResult();
	}	

	public MpMovimentoLogin porNavegacao(String acao, String atividade) {
		//
//		System.out.println("MpMovimentoLogins.MpMovimentoLogin ( " + acao + " / " + atividade);
		//
		try {
			//
			if (acao.equals("mpEnd") || acao.equals("mpPrev")) 
				return manager.createQuery(
			"from MpMovimentoLogin where upper(atividade) < :atividade ORDER BY atividade DESC",
				MpMovimentoLogin.class)
				.setParameter("atividade", atividade.toUpperCase())
				.setMaxResults(1)
				.getSingleResult();
			else 
			if (acao.equals("mpFirst") || acao.equals("mpNext")) 
				return manager.createQuery(
			"from MpMovimentoLogin where upper(atividade) > :atividade ORDER BY atividade ASC",
				MpMovimentoLogin.class)
				.setParameter("atividade", atividade.toUpperCase())
				.setMaxResults(1)
				.getSingleResult();
			else
				return null;
						
		} catch (NoResultException e) {
			return null;
		}
	}
	
}