package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.model.vo.MpDataValor;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.repository.filter.MpCalendarioFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCalendarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---

	private Criteria criarCriteriaParaFiltro(MpCalendarioFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		if (!tenantId.equals("0"))
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);	
		
		Criteria criteria = session.createCriteria(MpCalendario.class);
		
		if (filtro.getDataCriacaoDe() != null)
			criteria.add(Restrictions.ge("dataMovimento", filtro.getDataCriacaoDe()));
		if (filtro.getDataCriacaoAte() != null)
			criteria.add(Restrictions.le("dataMovimento", filtro.getDataCriacaoAte()));
		if (StringUtils.isNotBlank(filtro.getDescricao()))
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(),	MatchMode.ANYWHERE));
		if (null != filtro.getIndAtivo())
			criteria.add(Restrictions.eq("indAtivo", filtro.getIndAtivo()));
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpCalendario> filtrados(MpCalendarioFilter filtro) {
		//
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
	
	public int quantidadeFiltrados(MpCalendarioFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
	public MpCalendario guardar(MpCalendario mpCalendario) {
		//
		try {
			return manager.merge(mpCalendario);
			
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse CALENDÁRIO... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpCalendario mpCalendario) throws MpNegocioException {
		//
		try {
			mpCalendario = porId(mpCalendario.getId());
			manager.remove(mpCalendario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("Calendário... não pode ser excluído.");
		}
	}

	public MpCalendario porDtHrMovimento(Date dataMovimento) {
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		//
		Calendar calendar = Calendar.getInstance();
		//
		calendar.setTime(dataMovimento);
		//
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		//
		dataMovimento = calendar.getTime();
		//
		try {
			String hql = "from MpCalendario where dataMovimento = :dataMovimento";
			
			if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = '" + tenantId + "'";
			
			return manager.createQuery(hql,	MpCalendario.class)
									.setParameter("dataMovimento", dataMovimento).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MpCalendario porId(Long id) {
		//
		return manager.find(MpCalendario.class, id);
	}
		
	public MpCalendario porNavegacao(String acao, Date dataMovimento) {
		//
//		System.out.println("MpCalendarios.MpCalendario ( " + acao + " / " + descricao);
		//
		String tenantId = mpSeguranca.capturaTenantId().trim();
		
		try {
			//
			String hql = "";
			String order = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpCalendario where dataMovimento < :dataMovimento";
				order = " ORDER BY dataMovimento DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpCalendario where dataMovimento > :dataMovimento";
				order = " ORDER BY dataMovimento ASC";
			}
			//
			if (!tenantId.equals("0")) hql = hql + " AND tenantId = '" + tenantId + "'";

			return manager.createQuery(hql + order,	MpCalendario.class)
									.setParameter("dataMovimento", dataMovimento)
									.setMaxResults(1).getSingleResult();
			//						
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias) {
		//
		Session session = manager.unwrap(Session.class);
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(MpCalendario.class);
				
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection(
						"trunc({alias}.data_Movimento) as data" ,
						"trunc({alias}.data_Movimento)" ,
						new String[] { "data" } , 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("valor").as("valor"))
		)
		.add(Restrictions.ge("dataMovimento", dataInicial.getTime()));

		List<MpDataValor> mpValoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(MpDataValor.class)).list();
		
		for (MpDataValor mpDataValor : mpValoresPorData) {
			resultado.put(mpDataValor.getData(), mpDataValor.getValor());
		}
		//
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		//
		dataInicial = (Calendar) dataInicial.clone();
		
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();
		//
		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		//
		return mapaInicial;
	}
	
	public Map<Date, BigDecimal> valoresTotaisPorIntervalo(Date dataInicial, Date dataFinal) {
		//
		Session session = manager.unwrap(Session.class);
		//
		Calendar dataIni = Calendar.getInstance();
		Calendar dataFim = Calendar.getInstance();
		
		dataIni.setTime(dataInicial);
		dataFim.setTime(dataInicial);
		
		Map<Date, BigDecimal> resultado = criarMapaVazioX(dataIni, dataFim);
		
		Criteria criteria = session.createCriteria(MpCalendario.class);
				
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("data_Movimento as data" ,
						"data_Movimento", new String[] { "data" } , 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("valor").as("valor"))
		)
		.add(Restrictions.ge("dataMovimento", dataInicial))
		.add(Restrictions.le("dataMovimento", dataFinal));

		@SuppressWarnings("unchecked")
		List<MpDataValor> mpValoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(MpDataValor.class)).list();
		
		for (MpDataValor mpDataValor : mpValoresPorData) {
			resultado.put(mpDataValor.getData(), mpDataValor.getValor());
			//
//			System.out.println("MpCalendarios.valoresTotaisPorIntervalo() ( " + mpDataValor.getData() +
//																		"/" + mpDataValor.getValor());
		}
		//		
		return resultado;
	}
	
	private Map<Date, BigDecimal> criarMapaVazioX(Calendar dataIni, Calendar dataFim) {
		//
		dataIni = (Calendar) dataIni.clone();
		dataFim = (Calendar) dataFim.clone();
		
		Date dataI = dataIni.getTime();
		Date dataF = dataFim.getTime();
		// Calcula a diferença entre as datas !
		double result = 0;
		long diferenca = dataF.getTime() - dataI.getTime();
		double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias
		long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes
		
		result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias
		
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();
		//
		for (int i = 0; i <= result; i++) {
			mapaInicial.put(dataIni.getTime(), BigDecimal.ZERO);
			dataIni.add(Calendar.DAY_OF_MONTH, 1);
		}
		//
		return mapaInicial;
	}

	public List<MpCalendario> mpCalendarioList() {
		//
		return manager.createQuery("from MpCalendario ORDER BY dataMovimento", 
													MpCalendario.class).getResultList();
	}
			
}