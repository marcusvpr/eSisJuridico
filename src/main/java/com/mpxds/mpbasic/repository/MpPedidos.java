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

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.vo.MpDataValor;
import com.mpxds.mpbasic.repository.filter.MpPedidoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPedidos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	// ---

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias,	MpUsuario criadoPor) {
		//
		// Session session = manager.unwrap(Session.class); Aula.21 Hibernate 5.2 !
		Session session = (Session) manager;
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(MpPedido.class);
		
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

		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("data_criacao as data", 
						"data_criacao", new String[] { "data" }, 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("valorTotal").as("valor"))
			)
			.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
		
		if (criadoPor != null)
			criteria.add(Restrictions.eq("mpVendedor", criadoPor));
		//
		List<MpDataValor> mpValoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(MpDataValor.class)).list();
		
		for (MpDataValor mpDataValor : mpValoresPorData) {
			resultado.put(mpDataValor.getData(), mpDataValor.getValor());
		}
		//
//		System.out.println("MpPedidos.valoresTotaisPorData - ( resultado = " + resultado.size());
		//
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		//
		dataInicial = (Calendar) dataInicial.clone();
		
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		//
		return mapaInicial;
	}
	
	private Criteria criarCriteriaParaFiltro(MpPedidoFilter filtro) {
		//
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(MpPedido.class)
				.createAlias("mpCliente", "c")
				.createAlias("mpVendedor", "v");
		
		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null)
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		if (filtro.getDataCriacaoAte() != null)
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// acessamos o nome do cliente associado ao mpPedido pelo alias "c", 
			// criado anteriormente
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// acessamos o nome do vendedor associado ao mpPedido pelo alias "v",
			// criado anteriormente
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getMpStatuses() != null && filtro.getMpStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum 
			// StatusMpPedido
			criteria.add(Restrictions.in("mpStatusPedido", filtro.getMpStatuses()));
		}
		//
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<MpPedido> filtrados(MpPedidoFilter filtro) {
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
	
	public int quantidadeFiltrados(MpPedidoFilter filtro) {
		//
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}

	public MpPedido guardar(MpPedido mpPedido) {
		//
		try {
			return this.manager.merge(mpPedido);
			//
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
						"Erro de concorrência. Esse PEDIDO... já foi alterado anteriormente!");
		}
	}

	public MpPedido porId(Long id) {
		//
		return this.manager.find(MpPedido.class, id);
	}

	public boolean isDolarLogin() {
		//
		MpSistemaConfig mpSistemaConfig = mpSistemaConfigs.porParametro("indDolarLogin");
		if (null == mpSistemaConfig) {
			//
			mpSistemaConfig = new MpSistemaConfig();
			mpSistemaConfig.setIndValor(false);
		}
//		System.out.println("MpGraficoPedidosCriadosBean - ( " +
//															mpSistemaConfig.getValor());
		//
		return mpSistemaConfig.getIndValor();
	}

	@MpTransactional
	public void remover(MpPedido mpPedido) throws MpNegocioException {
		//
		try {
			mpPedido = porId(mpPedido.getId());
			
			manager.remove(mpPedido);
			manager.flush();
			//
		} catch (PersistenceException e) {
			throw new MpNegocioException("Pedido... não pode ser excluído.");
		}
	}

	public MpPedido porNavegacao(String acao, Date dataCriacao) {
		//
//		System.out.println("MpPedidos.MpPedido ( " + acao + " / " + descricao);
		//		
		String tenantId = mpSeguranca.capturaTenantId();
		
		try {
			//
			String hql = "";

			if (acao.equals("mpEnd") || acao.equals("mpPrev")) {
				hql = "from MpPedido where dataCriacao < :dataCriacao";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataCriacao DESC";
			} else
			if (acao.equals("mpFirst") || acao.equals("mpNext")) {
				hql = "from MpPedido where dataCriacao > :dataCriacao";

				if (!tenantId.equals("0")) hql = hql +	" AND  tenantId = " + tenantId;

				hql = hql + " ORDER BY dataCriacao ASC";
			}
			//
			if (hql.isEmpty())
				return null;
			else
				return manager.createQuery(hql,	MpPedido.class)
							.setParameter("dataCriacao", dataCriacao)
							.setMaxResults(1).getSingleResult();
			//
		} catch (NoResultException e) {
			return null;
		}
	}	
	
}