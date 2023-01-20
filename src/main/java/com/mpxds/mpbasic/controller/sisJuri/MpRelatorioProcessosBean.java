package com.mpxds.mpbasic.controller.sisJuri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.mpxds.mpbasic.model.dto.sisJuri.MpObjetoProcessoDTO;
import com.mpxds.mpbasic.model.sisJuri.MpProcessoAndamento;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessoAndamentos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.report.MpExecutorRelatorioSJ;

@Named
@RequestScoped
public class MpRelatorioProcessosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpProcessoAndamentos mpProcessoAndamentos;

	private Date dataInicio;
	private Date dataFim;
	private String responsavel;
	private String tipoArquivo = "XLS"; // XLS ou PDF ...
	private Boolean indAndamento = true;

	private List<MpObjetoProcessoDTO> mpObjetoProcessoDTOList = new ArrayList<MpObjetoProcessoDTO>();
		
	// ---
	
	public void emitir() {
		//
		String msg = "";
		if (null == this.dataInicio) msg = msg + "(Data Inicial)";
		if (null == this.dataFim) msg = msg + "(Data Final)";

		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválida(s)");
			return;
		}
		
		if (this.dataInicio.after(dataFim)) {
			MpFacesUtil.addInfoMessage("Data Inicial maior Data Final!");
			return;
		}
		// Reset das horas !!!
		Calendar calendarX = Calendar.getInstance();
		
		calendarX.setTime(this.dataInicio);
		calendarX.set(Calendar.HOUR_OF_DAY, 0);
		calendarX.set(Calendar.MINUTE, 0);
		calendarX.set(Calendar.MILLISECOND, 0);
		
		this.dataInicio = calendarX.getTime();
		//
		calendarX.setTime(this.dataFim);
		calendarX.set(Calendar.HOUR_OF_DAY, 23);
		calendarX.set(Calendar.MINUTE, 59);
		calendarX.set(Calendar.MILLISECOND, 00);
		
		this.dataFim = calendarX.getTime();		
		//
		String usuario = "";
		
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLogin();
		else
			usuario = mpSeguranca.getMpUsuarioLogado().getMpUsuario().getLogin();
		//
		this.geraDTO();
		//
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		parametros.put("responsavel", this.responsavel);
		
		String nomeArquivo = "Processos.xlsx";
		if (this.tipoArquivo.equals("PDF"))
			nomeArquivo = "Processos.pdf";
		//
		MpExecutorRelatorioSJ executor = new MpExecutorRelatorioSJ(usuario,
														"/relatorios/mpRelatorio_processos.jasper", 
														this.response, this.mpObjetoProcessoDTOList,
														parametros, nomeArquivo);
		//
		Session session = manager.unwrap(Session.class);
		
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
	
    public void geraDTO() { 
		//
		List<MpProcessoAndamento> mpProcessoAndamentoList = new ArrayList<MpProcessoAndamento>();

		if (this.indAndamento)
			mpProcessoAndamentoList = this.mpProcessoAndamentos.findAllByDtHr(this.dataInicio, this.dataFim);
		else
			mpProcessoAndamentoList = this.mpProcessoAndamentos.findAllByDtHrX(this.dataInicio, this.dataFim);
		//
		for (MpProcessoAndamento mpProcessoAndamento : mpProcessoAndamentoList) {
    		//
//	    	System.out.println("MpRelatorioProcessosBean.geraDTO() - 001 ( " + this.dataInicio + " / " + this.dataFim + 
//	    															mpProcessoAndamento.getDataCadastro());
				//
				String responsavelX = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel()
				||  null == mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel().getNome())
					responsavelX = "";
				else
					responsavelX = mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel().getNome();
				// Filtra por responsavel !!!
				if (!this.responsavel.isEmpty())
					if (!responsavelX.toLowerCase().contains(this.responsavel.toLowerCase()))
						continue;
				//
				String atoPraticado = "";
				if (null == mpProcessoAndamento.getMpAtoPraticado()
				||  null == mpProcessoAndamento.getMpAtoPraticado().getDescricao())
					atoPraticado = "";
				else
					atoPraticado = mpProcessoAndamento.getMpAtoPraticado().getDescricao();
				//
				String andamentoTipo = "";
				if (null == mpProcessoAndamento.getMpAndamentoTipo()
				||  null == mpProcessoAndamento.getMpAndamentoTipo().getDescricao())
					andamentoTipo = "";
				else
					andamentoTipo = mpProcessoAndamento.getMpAndamentoTipo().getDescricao();
				//
				String localTramiteDescricao = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpComarcaCartorio()
				||  null == mpProcessoAndamento.getMpProcesso().getMpComarcaCartorio().getDescricao())
					localTramiteDescricao = "";
				else
					localTramiteDescricao = mpProcessoAndamento.getMpProcesso().getMpComarcaCartorio().
																								getDescricao();
				//
				String comarcaDescricao = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpComarca()
				||  null == mpProcessoAndamento.getMpProcesso().getMpComarca().getDescricao())
					comarcaDescricao = "";
				else
					comarcaDescricao = mpProcessoAndamento.getMpProcesso().getMpComarca().getDescricao();
				//
				String parteContrariaNome = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpParteContraria()
				||  null == mpProcessoAndamento.getMpProcesso().getMpParteContraria().getNome())
					parteContrariaNome = "";
				else
					parteContrariaNome = mpProcessoAndamento.getMpProcesso().getMpParteContraria().getNome();
				//
				String clienteNome = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpClienteSJ()
				||  null == mpProcessoAndamento.getMpProcesso().getMpClienteSJ().getNome())
					clienteNome = "";
				else
					clienteNome = mpProcessoAndamento.getMpProcesso().getMpClienteSJ().getNome();
				//
				String resultadoAudienciaDescricao = "";
//				if (null == mpProcessoAndamento.getMpResultadoAudiencia())
//					resultadoAudienciaDescricao = "";
//				else
//					if (null == mpProcessoAndamento.getMpResultadoAudiencia().getDescricao())
//						resultadoAudienciaDescricao = "";
//					else
//						resultadoAudienciaDescricao = mpProcessoAndamento.getMpResultadoAudiencia().getDescricao();
				//
				String autorNome = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getAutor())
					autorNome = "";
				else
					autorNome = mpProcessoAndamento.getMpProcesso().getAutor();
				//
				String processoCodigo = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getProcessoCodigo())
					processoCodigo = "";
				else
					processoCodigo = mpProcessoAndamento.getMpProcesso().getProcessoCodigo();
				//
				if (null == mpProcessoAndamento.getDetalhamento()) 
					mpProcessoAndamento.setDetalhamento("");
				//
				String telefone = "";
				if (null == mpProcessoAndamento.getMpProcesso()
				||  null == mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel()
				||  null == mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel().getTelefone())
					telefone = "";
				else
					telefone = mpProcessoAndamento.getMpProcesso().getMpAdvogadoResponsavel().getTelefone();
				//
				MpObjetoProcessoDTO objetoProcessoDTO = 
						new MpObjetoProcessoDTO(mpProcessoAndamento.getId(),
											atoPraticado,
											andamentoTipo,
											mpProcessoAndamento.getDataCadastro(),
											localTramiteDescricao,
											comarcaDescricao,
											parteContrariaNome,
											clienteNome,
											processoCodigo,
											autorNome,
											mpProcessoAndamento.getDetalhamento(),
											responsavelX,
											resultadoAudienciaDescricao,
											telefone);
				//
				this.mpObjetoProcessoDTOList.add(objetoProcessoDTO);
		}
    	//
		if (this.mpObjetoProcessoDTOList.size()==0) {
			//
			MpFacesUtil.addInfoMessage(". Nenhum Objeto Processo selecionado ! Favor verificar !");
			//
			return;
		}
    }

	// ---
	
	@NotNull
	public Date getDataInicio() { return dataInicio; }
	public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

	@NotNull
	public Date getDataFim() { return dataFim; }
	public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

	public String getResponsavel() { return responsavel; }
	public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

	public String getTipoArquivo() { return tipoArquivo; }
	public void setTipoArquivo(String tipoArquivo) { this.tipoArquivo = tipoArquivo; }

	public Boolean getIndAndamento() { return indAndamento; }
	public void setIndAndamento(Boolean indAndamento) { this.indAndamento = indAndamento; }

}