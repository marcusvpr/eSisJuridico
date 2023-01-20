package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.report.MpExecutorRelatorio;

@Named
@RequestScoped
public class MpRelatorioPedidosEmitidosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	public void emitir() {
		//
		String usuario = "";
		
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLogin();
		else
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuario().getLogin();
		//
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		
		MpExecutorRelatorio executor = new MpExecutorRelatorio(usuario,
												"/relatorios/mpRelatorio_pedidos_emitidos.jasper", 
												this.response, parametros, "Pedidos_Emitidos.pdf");
		//
		Session session = manager.unwrap(Session.class);
		
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() { return dataInicio; }
	public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

	@NotNull
	public Date getDataFim() { return dataFim; }
	public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

}