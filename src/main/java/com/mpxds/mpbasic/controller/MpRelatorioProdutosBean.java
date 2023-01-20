package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.report.MpExecutorRelatorio;

@Named
@RequestScoped
public class MpRelatorioProdutosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpProdutos mpProdutos;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;
	
	private String nomeSelecao;

	// ---
	
	public void emitir() {
		//
		if (mpProdutos.porProdutoTenantList(mpSeguranca.capturaTenantId().trim(),
												this.nomeSelecao.toUpperCase()).size() == 0) {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
			return;
		}
		//
		String usuario = "";
		
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLogin();
		else
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuario().getLogin();
		//
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("nome", this.nomeSelecao.toUpperCase());
		parametros.put("tenantID", mpSeguranca.capturaTenantId());
		
//		System.out.println("MpRelatorioProdutosBean.emitir() - ( nome = " +
//						"%" + this.nomeSelecao + "% / Tenant = " + mpSeguranca.capturaTenantId()); 
		//
		MpExecutorRelatorio executor = new MpExecutorRelatorio(usuario,
														"/relatorios/mpRelatorio_produtos.jasper", 
														this.response, parametros, "Produtos.pdf");
		//
		Session session = this.manager.unwrap(Session.class);
		
		session.doWork(executor);
		//
		if (executor.isRelatorioGerado()) {
			this.facesContext.responseComplete();
		} else {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	public String getNomeSelecao() { return nomeSelecao; }
	public void setNomeSelecao(String nomeSelecao) { this.nomeSelecao = nomeSelecao; }
	
}