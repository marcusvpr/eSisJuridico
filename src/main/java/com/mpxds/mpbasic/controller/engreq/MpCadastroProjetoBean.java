package com.mpxds.mpbasic.controller.engreq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.dto.engreq.MpRfRnDTO;
import com.mpxds.mpbasic.model.dto.engreq.MpMatrizRfRnDTO;
import com.mpxds.mpbasic.model.dto.engreq.MpProjetoFaseEtapaDTO;
import com.mpxds.mpbasic.model.dto.engreq.MpStatusDTO;
import com.mpxds.mpbasic.model.engreq.MpFuncionalidade;
import com.mpxds.mpbasic.model.engreq.MpMacroRequisito;
import com.mpxds.mpbasic.model.engreq.MpModulo;
import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.model.engreq.MpRegraNegocio;
import com.mpxds.mpbasic.model.engreq.MpRequisitoFuncional;
import com.mpxds.mpbasic.model.engreq.MpRequisitoNaoFuncional;
import com.mpxds.mpbasic.model.engreq.MpProjetoPessoaER;
import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.model.engreq.MpSequencia;
import com.mpxds.mpbasic.model.enums.engreq.MpCategoriaRNF;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpPrioridade;
import com.mpxds.mpbasic.model.enums.engreq.MpProjetoFaseEtapa;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;
import com.mpxds.mpbasic.model.enums.engreq.MpPessoaFuncao;
import com.mpxds.mpbasic.model.enums.engreq.MpRelatorioER;
import com.mpxds.mpbasic.repository.engreq.MpFuncionalidades;
import com.mpxds.mpbasic.repository.engreq.MpMacroRequisitos;
import com.mpxds.mpbasic.repository.engreq.MpModulos;
import com.mpxds.mpbasic.repository.engreq.MpProjetos;
import com.mpxds.mpbasic.repository.engreq.MpRegraNegocios;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoFuncionals;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoNaoFuncionals;
import com.mpxds.mpbasic.repository.engreq.MpProjetoPessoaERs;
import com.mpxds.mpbasic.repository.engreq.MpPessoaERs;
import com.mpxds.mpbasic.repository.engreq.MpSequencias;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.engreq.MpProjetoService;
import com.mpxds.mpbasic.service.engreq.MpSequenciaService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroProjetoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpProjetos mpProjetos;
	
	@Inject
	private MpMacroRequisitos mpMacroRequisitos;
	
	@Inject
	private MpModulos mpModulos;
	
	@Inject
	private MpFuncionalidades mpFuncionalidades;
	
	@Inject
	private MpRequisitoFuncionals mpRequisitoFuncionals;
	
	@Inject
	private MpRequisitoNaoFuncionals mpRequisitoNaoFuncionals;
	
	@Inject
	private MpProjetoPessoaERs mpProjetoPessoaERs;
	
	@Inject
	private MpPessoaERs mpPessoaERs;
	
	@Inject
	private MpRegraNegocios mpRegraNegocios;
	
	@Inject
	private MpSequencias mpSequencias;

	@Inject
	private MpProjetoService mpProjetoService;

	@Inject
	private MpSequenciaService mpSequenciaService;
	
	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private MpProjeto mpProjeto = new MpProjeto();
	private MpProjeto mpProjetoAnt;

	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;

	private Boolean indEditavelMReq = false;
	private Boolean indEditavelMod = false;
	private Boolean indEditavelFun = false;
	private Boolean indEditavelReqF = false;
	private Boolean indEditavelReqNF = false;
	private Boolean indEditavelRegN = false;
	private Boolean indEditavelPPesER = false;
	
	private String txtModoTela = "";
	private String txtModoMacroRequisitoDialog = "";
	private String txtModoModuloDialog = "";
	private String txtModoFuncionalidadeDialog = "";
	private String txtModoRequisitoFuncionalDialog = "";
	private String txtModoRequisitoNaoFuncionalDialog = "";
	private String txtModoRegraNegocioDialog = "";
	private String txtModoProjetoPessoaERDialog = "";

	private MpPrioridade mpPrioridade;
	private List<MpPrioridade> mpPrioridadeList = new ArrayList<>();
	private MpComplexibilidade mpComplexibilidade;
	private List<MpComplexibilidade> mpComplexibilidadeList = new ArrayList<>();
	private MpStatusRequisito mpStatusRequisito;
	private List<MpStatusRequisito> mpStatusRequisitoList = new ArrayList<>();
	private MpCategoriaRNF mpCategoriaRNF;
	private List<MpCategoriaRNF> mpCategoriaRNFList = new ArrayList<>();
	private MpProjetoFaseEtapa mpProjetoFaseEtapa;
	private List<MpProjetoFaseEtapa> mpProjetoFaseEtapaList = new ArrayList<>();
	private MpPessoaER mpPessoaER = new MpPessoaER();
	private List<MpPessoaER> mpPessoaERList = new ArrayList<>();
	private MpPessoaFuncao mpPessoaFuncao;
	private List<MpPessoaFuncao> mpPessoaFuncaoList = new ArrayList<>();
	private MpRelatorioER mpRelatorioER;
	private List<MpRelatorioER> mpRelatorioERList = new ArrayList<>();
	
	private MpMacroRequisito mpMacroRequisito = new MpMacroRequisito();
	private List<MpMacroRequisito> mpMacroRequisitoExcluidoList = new ArrayList<>();	
	
	private MpModulo mpModulo = new MpModulo();
	private List<MpModulo> mpModuloList = new ArrayList<>();	
	private List<MpModulo> mpModuloExcluidoList = new ArrayList<>();	
	
	private MpFuncionalidade mpFuncionalidade = new MpFuncionalidade();
	private List<MpFuncionalidade> mpFuncionalidadeList = new ArrayList<>();	
	private List<MpFuncionalidade> mpFuncionalidadeExcluidoList = new ArrayList<>();	
	
	private MpStatusDTO mpStatusDTO = new MpStatusDTO();
	private List<MpStatusDTO> mpStatusDTOList = new ArrayList<>();	
	
	private MpProjetoFaseEtapaDTO mpProjetoFaseEtapaDTO = new MpProjetoFaseEtapaDTO();
	private List<MpProjetoFaseEtapaDTO> mpProjetoFaseEtapaDTOList = new ArrayList<>();	
	private List<MpProjetoFaseEtapaDTO> mpProjetoFaseEtapaDetDTOList = new ArrayList<>();	
	
	private MpRequisitoFuncional mpRequisitoFuncional = new MpRequisitoFuncional();
	private List<MpRequisitoFuncional> mpRequisitoFuncionalExcluidoList = new ArrayList<>();	
	
	private MpRequisitoNaoFuncional mpRequisitoNaoFuncional = new MpRequisitoNaoFuncional();
	private List<MpRequisitoNaoFuncional> mpRequisitoNaoFuncionalExcluidoList = new ArrayList<>();	
	
	private MpProjetoPessoaER mpProjetoPessoaER = new MpProjetoPessoaER();
	private List<MpProjetoPessoaER> mpProjetoPessoaERExcluidoList = new ArrayList<>();	

	private List<MpMatrizRfRnDTO> mpMatrizRfRnDTOList = new ArrayList<>();	
	
	private MpRegraNegocio mpRegraNegocio = new MpRegraNegocio();
	private List<MpRegraNegocio> mpRegraNegocioExcluidoList = new ArrayList<>();	

	private MpSequencia mpSequencia = new MpSequencia();
	
	// Trata gráficos ...
	
    private PieChartModel pieModelRfStatus = new PieChartModel();
    private PieChartModel pieModelRfPrioridade = new PieChartModel();
    private PieChartModel pieModelRfComplexibilidade = new PieChartModel();

    private PieChartModel pieModelRnStatus = new PieChartModel();
    private PieChartModel pieModelRnComplexibilidade = new PieChartModel();

    private BarChartModel barModelRnfCategoria = new BarChartModel();

    // Trata Totais ...
	
	private BigDecimal esforcoRequisitoFuncional = BigDecimal.ZERO;
	private BigDecimal esforcoRequisitoNaoFuncional = BigDecimal.ZERO;
	private BigDecimal esforcoTotal = BigDecimal.ZERO;
	private BigDecimal prazoProjeto = BigDecimal.ZERO;

	private BigDecimal esforcoTotalPre = BigDecimal.ZERO;

	// -----------------------
	
	public MpCadastroProjetoBean() {
		//
		if (null == this.mpProjeto)
			this.limpar();
	}
	
	public void inicializar() {
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();		
		// ---
		this.mpPrioridadeList = Arrays.asList(MpPrioridade.values());
		this.mpComplexibilidadeList = Arrays.asList(MpComplexibilidade.values());
		this.mpStatusRequisitoList = Arrays.asList(MpStatusRequisito.values());
		this.mpCategoriaRNFList = Arrays.asList(MpCategoriaRNF.values());
		this.mpPessoaERList = this.mpPessoaERs.porPessoaERList();
		this.mpPessoaFuncaoList = Arrays.asList(MpPessoaFuncao.values());
		this.mpRelatorioERList = Arrays.asList(MpRelatorioER.values());
		//
		if (null == this.mpProjeto) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!

			this.txtModoTela = " .";
		} else {
			//
			this.mpNavegaComplementa();
		}	
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpProjeto.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		//
		// Trata posição anterior !
		// ========================
		this.setMpProjetoAnt(this.mpProjeto);
		//
		this.trataMpStatusDTO();
	}
	
	public void carregarMpFuncionalidadesRF() {
		//
		this.mpFuncionalidadeList = this.mpFuncionalidades.porModuloList(
														this.mpRequisitoFuncional.getMpModulo());
		//
	}
	public void carregarMpFuncionalidadesRNF() {
		//
		this.mpFuncionalidadeList = this.mpFuncionalidades.porModuloList(
														this.mpRequisitoNaoFuncional.getMpModulo());
		//
	}
	
	public void trataMpStatusDTO() {
		//
		MpStatusDTO mpStatusDTO = new MpStatusDTO();

		this.mpStatusDTOList.clear();
		
		for (MpPrioridade mpPrioridade : this.mpPrioridadeList) {
			//
			mpStatusDTO = new MpStatusDTO();
			
			mpStatusDTO.setMpPrioridade(mpPrioridade);
			
			this.mpStatusDTOList.add(mpStatusDTO);
		}
		//
		for (MpComplexibilidade mpComplexibilidade : mpComplexibilidadeList) {
			//
			if (mpComplexibilidadeList.indexOf(mpComplexibilidade) < this.mpStatusDTOList.size()) {
				mpStatusDTO = this.mpStatusDTOList.get(mpComplexibilidadeList.indexOf(
																				mpComplexibilidade));
				mpStatusDTO.setMpComplexibilidade(mpComplexibilidade);
				//
				this.mpStatusDTOList.set(mpComplexibilidadeList.indexOf(mpComplexibilidade), mpStatusDTO);
			} else {
				mpStatusDTO = new MpStatusDTO();
				
				mpStatusDTO.setMpComplexibilidade(mpComplexibilidade);
				//
				this.mpStatusDTOList.add(mpStatusDTO);
			}			
		}
		//
		for (MpStatusRequisito mpStatusRequisito : mpStatusRequisitoList) {
			//
			if (mpStatusRequisitoList.indexOf(mpStatusRequisito) < this.mpStatusDTOList.size()) {
				mpStatusDTO = this.mpStatusDTOList.get(mpStatusRequisitoList.indexOf(mpStatusRequisito));
				mpStatusDTO.setMpStatusRequisito(mpStatusRequisito);
				//
				this.mpStatusDTOList.set(mpStatusRequisitoList.indexOf(mpStatusRequisito), mpStatusDTO);
			} else {
				mpStatusDTO = new MpStatusDTO();
				mpStatusDTO.setMpStatusRequisito(mpStatusRequisito);
				//
				this.mpStatusDTOList.add(mpStatusDTO);
			}			
		}
		//
		for (MpCategoriaRNF mpCategoriaRNF : mpCategoriaRNFList) {
			//
			if (mpCategoriaRNFList.indexOf(mpCategoriaRNF) < this.mpStatusDTOList.size()) {
				mpStatusDTO = this.mpStatusDTOList.get(mpCategoriaRNFList.indexOf(mpCategoriaRNF));
				mpStatusDTO.setMpCategoriaRNF(mpCategoriaRNF);
				//
				this.mpStatusDTOList.set(mpCategoriaRNFList.indexOf(mpCategoriaRNF), mpStatusDTO);
			} else {
				mpStatusDTO = new MpStatusDTO();
				mpStatusDTO.setMpCategoriaRNF(mpCategoriaRNF);
				//
				this.mpStatusDTOList.add(mpStatusDTO);
			}			
		}
	}
	
	public void trataMpRfRnDTO() {
		//
		List<MpRfRnDTO> mpRfRnDTOListX = new ArrayList<>();;
		
		for (MpRegraNegocio mpRegraNegocioX : this.mpProjeto.getMpRegraNegocioList()) {
			//
			if (mpRegraNegocioX.getDependencia().isEmpty()) continue;
			//
			String[] requisitoFuncionalListX = mpRegraNegocioX.getDependencia().split(" ");

			for (String requisitoFuncionalX : requisitoFuncionalListX) {
				//
				MpRfRnDTO mpRfRnDTOX = new MpRfRnDTO();
				
				mpRfRnDTOX.setRegraNegocio(mpRegraNegocioX.getIdRN());
				mpRfRnDTOX.setRequisitoFuncional(requisitoFuncionalX);
				
				mpRfRnDTOListX.add(mpRfRnDTOX);
				//
//				System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (mpRfRnDTOX.RN = " + 
//						mpRfRnDTOX.getRegraNegocio() + 	" / RF = " + 
//						mpRfRnDTOX.getRequisitoFuncional());
			}
		}
		//
//		System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (Size.mpRfRnDTOListX = " + 
//																			mpRfRnDTOListX.size());
		//
		Collections.sort(mpRfRnDTOListX, new Comparator<MpRfRnDTO>(){
		    public int compare(MpRfRnDTO s1, MpRfRnDTO s2) {
		    	Integer s1Num = Integer.parseInt(s1.getRequisitoFuncional().substring(2));
		    	Integer s2Num = Integer.parseInt(s2.getRequisitoFuncional().substring(2));
		        if(s1Num < s2Num) return -1;
		        if(s1Num == s2Num) return 0;
		        return 1;
		    }
		});
		//
//		for (MpRfRnDTO mpRfRnDTOYY : mpRfRnDTOListX) {
//			System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (mpRfRnDTOYY.RF = " + 
//					mpRfRnDTOYY.getRequisitoFuncional() + " / RN = "+
//					mpRfRnDTOYY.getRegraNegocio());			
//		}
		//
		this.mpMatrizRfRnDTOList.clear();
		
		MpMatrizRfRnDTO mpMatrizRfRnDTOX = new MpMatrizRfRnDTO();
		for(int i=1; i<51; i++){
			mpMatrizRfRnDTOX.getRegraNegocioList().add("");				
		}
		//
		String requisitoFuncionalAtu = "";
		String requisitoFuncionalAnt = "*";
		Integer indexRN = 0;
		//
		for (MpRfRnDTO mpRfRnDTOY : mpRfRnDTOListX) {
			//
			requisitoFuncionalAtu = mpRfRnDTOY.getRequisitoFuncional();
			
			if (requisitoFuncionalAnt.equals(requisitoFuncionalAtu)) {
				// Trata posição RegraNegocio no ARRAY : RN0005 -> Index = 5 (X) ...
				indexRN = Integer.parseInt(mpRfRnDTOY.getRegraNegocio().substring(2));
				//
				if (indexRN == 0 || indexRN > 50) {
					MpFacesUtil.addInfoMessage("Erro Regra Negócio INDEX=0 ou INDEX>50");
					continue;
				}
				//
				mpMatrizRfRnDTOX.getRegraNegocioList().add(indexRN-1, "X"); // mpRfRnDTOY.getRegraNegocio());				
			} else {
				if (!requisitoFuncionalAnt.equals("*"));
					if (null == mpMatrizRfRnDTOX.getRequisitoFuncional())
						assert(true); // nop
					else
						this.mpMatrizRfRnDTOList.add(mpMatrizRfRnDTOX);
				
				requisitoFuncionalAnt = mpRfRnDTOY.getRequisitoFuncional();
				//
				mpMatrizRfRnDTOX = new MpMatrizRfRnDTO();
				for(int i=1; i<51; i++){
					mpMatrizRfRnDTOX.getRegraNegocioList().add("");				
				}
				//
				mpMatrizRfRnDTOX.setRequisitoFuncional(mpRfRnDTOY.getRequisitoFuncional());
				// Trata posição RegraNegocio no ARRAY : RN0005 -> Index = 5 (X) ...
				indexRN = Integer.parseInt(mpRfRnDTOY.getRegraNegocio().substring(2));
				if (indexRN == 0 || indexRN > 50) {
					MpFacesUtil.addInfoMessage("Erro Regra Negócio INDEX=0 ou INDEX>50");
					continue;
				}
				//
//				System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (indexRN = " +
//						indexRN + " / DTOY.RN = " + mpRfRnDTOY.getRegraNegocio() +
//						" / RnList.size = " + mpMatrizRfRnDTOX.getRegraNegocioList().size());
				//
				mpMatrizRfRnDTOX.getRegraNegocioList().add(indexRN-1, "X"); // mpRfRnDTOY.getRegraNegocio());				
			}
			//
//			System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (RF.Atu = " + 
//					requisitoFuncionalAtu + " / RN = "+
//					mpRfRnDTOY.getRegraNegocio() + " / RF.Ant = " + requisitoFuncionalAnt +
//					" / RNList.size = " + mpMatrizRfRnDTOX.getRegraNegocioList().size());
		}
		//
		if (requisitoFuncionalAnt.equals(requisitoFuncionalAtu))
			if (null == mpMatrizRfRnDTOX.getRequisitoFuncional())
				assert(true); // nop
			else
				this.mpMatrizRfRnDTOList.add(mpMatrizRfRnDTOX);
		//
//		for (MpMatrizRfRnDTO mpMatrizRfRnDTOxx : this.mpMatrizRfRnDTOList) {
//			//
//			System.out.println("MpCadastroProjetoBean.trataMpRfRnDTO() (mpMatrizRfRnDTOxx.RF = " + 
//														mpMatrizRfRnDTOxx.getRequisitoFuncional());
//			//
//			for (String regraNegocioXX : mpMatrizRfRnDTOxx.getRegraNegocioList()) {
//				//
//				System.out.println(" ===> RegraNegocioX : " + regraNegocioXX);
//			}
//		}
	}
	
	public void trataMpProjetoFaseEtapaDTO() {
		//
		MpProjetoFaseEtapaDTO mpProjetoFaseEtapaDTO = new MpProjetoFaseEtapaDTO();

		this.mpProjetoFaseEtapaDTOList.clear();
		//
		for (MpProjetoFaseEtapa mpProjetoFaseEtapa : this.mpProjetoFaseEtapaList) {
			//
			if (mpProjetoFaseEtapa.getIsDisciplina()) continue;
			//
			mpProjetoFaseEtapaDTO = new MpProjetoFaseEtapaDTO();
			
			mpProjetoFaseEtapaDTO.setMpProjetoFaseEtapa(mpProjetoFaseEtapa);
			
			mpProjetoFaseEtapaDTO.setEsforco(this.esforcoTotal
					.multiply(BigDecimal.valueOf(mpProjetoFaseEtapa.getPercentualConsumo()).
					divide(BigDecimal.valueOf(100))).intValue());
			mpProjetoFaseEtapaDTO.setDuracao(mpProjetoFaseEtapaDTO.getEsforco() / 8);
			mpProjetoFaseEtapaDTO.setCusto(BigDecimal.valueOf(mpProjetoFaseEtapaDTO.getEsforco())
										   		.multiply(this.mpProjeto.getValorHoraEquipe()));
			//
			this.mpProjetoFaseEtapaDTOList.add(mpProjetoFaseEtapaDTO);
		}
		//
	}
	
	public void trataMpProjetoFaseEtapaDetDTO() {
		//
		MpProjetoFaseEtapaDTO mpProjetoFaseEtapaDTO = new MpProjetoFaseEtapaDTO();

		this.mpProjetoFaseEtapaDetDTOList.clear();
		//
		Integer esforcoTotalEtapa = 0;
		
		for (MpProjetoFaseEtapa mpProjetoFaseEtapa : this.mpProjetoFaseEtapaList) {
			//
			mpProjetoFaseEtapaDTO = new MpProjetoFaseEtapaDTO();
			
			mpProjetoFaseEtapaDTO.setMpProjetoFaseEtapa(mpProjetoFaseEtapa);

			if (mpProjetoFaseEtapa.getIsDisciplina()) {
				//
				mpProjetoFaseEtapaDTO.setEsforco(BigDecimal.valueOf(esforcoTotalEtapa)
						.multiply(BigDecimal.valueOf(mpProjetoFaseEtapa.getPercentualConsumo()).
						divide(BigDecimal.valueOf(100))).intValue());
				mpProjetoFaseEtapaDTO.setDuracao(mpProjetoFaseEtapaDTO.getEsforco() / 8);
				mpProjetoFaseEtapaDTO.setCusto(BigDecimal.valueOf(mpProjetoFaseEtapaDTO.getEsforco())
											   		.multiply(this.mpProjeto.getValorHoraEquipe()));
			} else {
				esforcoTotalEtapa = this.esforcoTotal
						.multiply(BigDecimal.valueOf(mpProjetoFaseEtapa.getPercentualConsumo()).
								divide(BigDecimal.valueOf(100))).intValue();
				//
				mpProjetoFaseEtapaDTO.setEsforco(esforcoTotalEtapa);
				mpProjetoFaseEtapaDTO.setDuracao(mpProjetoFaseEtapaDTO.getEsforco() / 8);
				mpProjetoFaseEtapaDTO.setCusto(BigDecimal.valueOf(mpProjetoFaseEtapaDTO.getEsforco())
										   		.multiply(this.mpProjeto.getValorHoraEquipe()));
			}
			//
			this.mpProjetoFaseEtapaDetDTOList.add(mpProjetoFaseEtapaDTO);
		}
		//
	}
	
	public void salvar() {
		//
		this.mpProjeto = this.mpProjetoService.salvar(this.mpProjeto);
		//
		if (this.mpMacroRequisitoExcluidoList.size() > 0) {
			for (MpMacroRequisito mpMacroRequisitoX : this.mpMacroRequisitoExcluidoList) {
				//
				if (null == mpMacroRequisitoX.getId()) continue;

				this.mpMacroRequisitos.remover(mpMacroRequisitoX);
			}
		}
		//
		if (this.mpModuloExcluidoList.size() > 0) {
			for (MpModulo mpModuloX : this.mpModuloExcluidoList) {
				//
				if (null == mpModuloX.getId()) continue;

				this.mpModulos.remover(mpModuloX);
			}
		}
		//
		if (this.mpFuncionalidadeExcluidoList.size() > 0) {
			for (MpFuncionalidade mpFuncionalidadeX : this.mpFuncionalidadeExcluidoList) {
				//
				if (null == mpFuncionalidadeX.getId()) continue;

				this.mpFuncionalidades.remover(mpFuncionalidadeX);
			}
		}
		//
		if (this.mpRequisitoFuncionalExcluidoList.size() > 0) {
			for (MpRequisitoFuncional mpRequisitoFuncionalX : 
														this.mpRequisitoFuncionalExcluidoList) {
				//
				if (null == mpRequisitoFuncionalX.getId()) continue;

				this.mpRequisitoFuncionals.remover(mpRequisitoFuncionalX);
			}
		}
		//
		if (this.mpRequisitoNaoFuncionalExcluidoList.size() > 0) {
			for (MpRequisitoNaoFuncional mpRequisitoNaoFuncionalX : 
														this.mpRequisitoNaoFuncionalExcluidoList) {
				//
				if (null == mpRequisitoNaoFuncionalX.getId()) continue;

				this.mpRequisitoNaoFuncionals.remover(mpRequisitoNaoFuncionalX);
			}
		}
		//
		if (this.mpProjetoPessoaERExcluidoList.size() > 0) {
			for (MpProjetoPessoaER mpProjetoPessoaERX : this.mpProjetoPessoaERExcluidoList) {
				//
				if (null == mpProjetoPessoaERX.getId()) continue;

				this.mpProjetoPessoaERs.remover(mpProjetoPessoaERX);
			}
		}
		//
		if (this.mpRegraNegocioExcluidoList.size() > 0) {
			for (MpRegraNegocio mpRegraNegocioX : this.mpRegraNegocioExcluidoList) {
				//
				if (null == mpRegraNegocioX.getId()) continue;

				this.mpRegraNegocios.remover(mpRegraNegocioX);
			}
		}
		//	
		MpFacesUtil.addInfoMessage("Projeto... salvo com sucesso!");
	}

	// --- Trata Macro Requisito ...
	
	public void alterarMpMacroRequisito() {
		//
		this.txtModoMacroRequisitoDialog = "Edição";
		
		this.indEditavelMReq = true;
	}			
	
//	public void adicionarMpMacroRequisito() {
//		//
//		if (this.mpMacroRequisito != null) {
//			this.mpProjeto.getMpMacroRequisitoList().add(this.mpMacroRequisito);
//			
//			this.mpMacroRequisito.setMpProjeto(this.mpProjeto);
//		}
//	}
//
//	public void removerMpMacroRequisito() {
//		//
//		if (this.mpMacroRequisito != null)
//			this.mpProjeto.getMpMacroRequisitoList().remove(this.mpMacroRequisito);
//	}			
//	
	public void adicionarMpMacroRequisitoX() {
		//
		this.txtModoMacroRequisitoDialog = "Novo";
		
		this.mpMacroRequisito = new MpMacroRequisito();
		
		this.mpMacroRequisito.setMpProjeto(this.mpProjeto);
		this.mpMacroRequisito.setTenantId(mpSeguranca.capturaTenantId());

		this.mpProjeto.getMpMacroRequisitoList().add(this.mpMacroRequisito);
		//
		this.indEditavelMReq = true;
	}

	public void removerMpMacroRequisitoX() {
		//
		try {
			this.mpProjeto.getMpMacroRequisitoList().remove(this.mpMacroRequisito);
			
			this.mpMacroRequisitoExcluidoList.add(this.mpMacroRequisito);
			
			MpFacesUtil.addInfoMessage("MacroRequisito... " + this.mpMacroRequisito.getDescricao()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpMacroRequisito() {
		//
		this.indEditavelMReq = false;
		//
//		System.out.println("MpCadastroProjetoBean.salvarMpMacroRequisito() - Size = " + 
//		this.mpProjeto.getMpMacroRequisitoList().size() + " / " + this.mpMacroRequisito.getDescricao());

		this.mpMacroRequisito = new MpMacroRequisito();
	}			

	public void fecharMpMacroRequisito() {
		//
		if (this.txtModoMacroRequisitoDialog.equals("Novo"))
			this.mpProjeto.getMpMacroRequisitoList().remove(this.mpMacroRequisito);
	}			

	// --- Trata Modulo ...
	
	public void alterarMpModulo() {
		//
		this.txtModoModuloDialog = "Edição";
		
		this.indEditavelMReq = true;
	}			
	
	public void adicionarMpModuloX() {
		//
		this.txtModoModuloDialog = "Novo";
		
		this.mpModulo = new MpModulo();

		this.mpModulo.setMpProjeto(this.mpProjeto);
		this.mpModulo.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpProjeto.getMpModuloList().add(this.mpModulo);		
		//
		this.indEditavelMReq = true;
	}

	public void removerMpModuloX() {
		//
		try {
			this.mpProjeto.getMpModuloList().remove(this.mpModulo);
			
			this.mpModuloExcluidoList.add(this.mpModulo);
			
			MpFacesUtil.addInfoMessage("Modulo... " + this.mpModulo.getDescricao()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpModulo() {
		//
		this.indEditavelMReq = false;
		
		this.mpModulo = new MpModulo();
	}			

	public void fecharMpModulo() {
		//
		if (this.txtModoModuloDialog.equals("Novo"))
			this.mpProjeto.getMpModuloList().remove(this.mpModulo);
	}			

	// --- Trata Funcionalidade ...
	
	public void alterarMpFuncionalidade() {
		//
		this.txtModoFuncionalidadeDialog = "Edição";
		
		this.indEditavelFun = true;
	}			
		
	public void adicionarMpFuncionalidadeX() {
		//
		this.txtModoFuncionalidadeDialog = "Novo";
		
		this.mpFuncionalidade = new MpFuncionalidade();

		this.mpFuncionalidade.setMpProjeto(this.mpProjeto);
		this.mpFuncionalidade.setTenantId(mpSeguranca.capturaTenantId());
			
		this.mpProjeto.getMpFuncionalidadeList().add(this.mpFuncionalidade);
		//
		this.indEditavelFun = true;
	}

	public void removerMpFuncionalidadeX() {
		//
		try {
			this.mpProjeto.getMpFuncionalidadeList().remove(this.mpFuncionalidade);
			
			this.mpFuncionalidadeExcluidoList.add(this.mpFuncionalidade);
			
			MpFacesUtil.addInfoMessage("Funcionalidade... " + this.mpFuncionalidade.getDescricao()
																	+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpFuncionalidade() {
		//
		this.indEditavelFun = false;
		
		this.mpFuncionalidade = new MpFuncionalidade();
	}			

	public void fecharMpFuncionalidade() {
		//
		if (this.txtModoFuncionalidadeDialog.equals("Novo"))
			this.mpProjeto.getMpFuncionalidadeList().remove(this.mpFuncionalidade);
	}			
	
	// --- Trata Requisito Funcional ...
		
	public void alterarMpRequisitoFuncional() {
		//
		this.txtModoRequisitoFuncionalDialog = "Edição";
		
		this.indEditavelReqF = true;
	}			
	
	public void adicionarMpRequisitoFuncionalX() {
		//
		this.txtModoRequisitoFuncionalDialog = "Novo";
 
		this.mpRequisitoFuncional = new MpRequisitoFuncional();
		
		this.mpRequisitoFuncional.setMpProjeto(this.mpProjeto);
		this.mpRequisitoFuncional.setTenantId(mpSeguranca.capturaTenantId());

		this.mpProjeto.getMpRequisitoFuncionalList().add(this.mpRequisitoFuncional);
		//
		this.indEditavelReqF = true;
		
		// Trata Sequencia (IdRF) ...
		this.mpSequencia = this.mpSequencias.porCodigo("IdRF", this.mpProjeto);
		if (null == this.mpSequencia) {
			this.mpSequencia = new MpSequencia();	
			this.mpSequencia.setCodigo("IdRF");	
			this.mpSequencia.setSequencia(0);	
			this.mpSequencia.setMpProjeto(this.mpProjeto);	
			this.mpSequencia.setTenantId(mpSeguranca.capturaTenantId());
		}
		//
		this.mpSequencia.setSequencia(this.mpSequencia.getSequencia() + 1);	
			
		try {
			this.mpRequisitoFuncional.setIdRF("RF" + String.format("%04d",
																this.mpSequencia.getSequencia()));
			
			this.mpSequencia = mpSequenciaService.salvar(this.mpSequencia);
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void removerMpRequisitoFuncionalX() {
		//
		try {
			this.mpProjeto.getMpRequisitoFuncionalList().remove(this.mpRequisitoFuncional);
			
			this.mpRequisitoFuncionalExcluidoList.add(this.mpRequisitoFuncional);
			
			MpFacesUtil.addInfoMessage("Requisito Funcional... " + 
								this.mpRequisitoFuncional.getDescricao() + " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpRequisitoFuncional() {
		//
		this.indEditavelReqF = false;
		
		this.mpRequisitoFuncional = new MpRequisitoFuncional();
	}			

	public void fecharMpRequisitoFuncional() {
		//
		if (this.txtModoRequisitoFuncionalDialog.equals("Novo"))
			this.mpProjeto.getMpRequisitoFuncionalList().remove(this.mpRequisitoFuncional);
	}			
	
	// --- Trata Regra Negocio ...
	
	public void alterarMpRegraNegocio() {
		//
		this.txtModoRegraNegocioDialog = "Edição";
		
		this.indEditavelRegN = true;
	}			
	
	public void adicionarMpRegraNegocioX() {
		//
		this.txtModoRegraNegocioDialog = "Novo";

		this.mpRegraNegocio = new MpRegraNegocio();

		this.mpRegraNegocio.setMpProjeto(this.mpProjeto);
		this.mpRegraNegocio.setTenantId(mpSeguranca.capturaTenantId());

		this.mpProjeto.getMpRegraNegocioList().add(this.mpRegraNegocio);
		//
		this.indEditavelRegN = true;
		
		// Trata Sequencia (IdRGN) ...
		this.mpSequencia = this.mpSequencias.porCodigo("IdRGN", this.mpProjeto);
		if (null == this.mpSequencia) {
			this.mpSequencia = new MpSequencia();	
			this.mpSequencia.setCodigo("IdRGN");	
			this.mpSequencia.setSequencia(0);	
			this.mpSequencia.setMpProjeto(this.mpProjeto);	
			this.mpSequencia.setTenantId(mpSeguranca.capturaTenantId());
		}
		//
		this.mpSequencia.setSequencia(this.mpSequencia.getSequencia() + 1);	
			
		try {
			this.mpRegraNegocio.setIdRN("RN" + String.format("%04d", 
																this.mpSequencia.getSequencia()));
			
			this.mpSequencia = mpSequenciaService.salvar(this.mpSequencia);
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}		
	}

	public void removerMpRegraNegocioX() {
		//
		try {
			this.mpProjeto.getMpRegraNegocioList().remove(this.mpRegraNegocio);
			
			this.mpRegraNegocioExcluidoList.add(this.mpRegraNegocio);
			
			MpFacesUtil.addInfoMessage("Regra Negocio... " + this.mpRegraNegocio.getDescricao()
																		+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpRegraNegocio() {
		//
		this.indEditavelRegN = false;
		
		this.mpRegraNegocio = new MpRegraNegocio();
	}			

	public void fecharMpRegraNegocio() {
		//
		if (this.txtModoRegraNegocioDialog.equals("Novo"))
			this.mpProjeto.getMpRegraNegocioList().remove(this.mpRegraNegocio);
	}
	
	public void validateRnDependencia(FacesContext context, UIComponent comp, Object value) {
		//
		String dependencias = (String) value;
		
		if (dependencias.isEmpty()) return;
		//
		String msgErro = "";
		
		String[] dependenciasListX = dependencias.split(" ");
		//
		for (String dependenciaX : dependenciasListX) {
			//
			MpRequisitoFuncional mpRequisitoFuncional = this.mpRequisitoFuncionals.
																porIdRF(dependenciaX, this.mpProjeto);
			
			if (null == mpRequisitoFuncional) {
				msgErro = msgErro + "(" + dependenciaX + ")";
			}
		}
		//
		if (!msgErro.isEmpty()) {
			((UIInput) comp).setValid(false);
			//
			FacesMessage message = new FacesMessage(
										"Requisito(s) Funcional(s) inválidos ! = " + msgErro);
			//
			context.addMessage(comp.getClientId(context), message);
		}
		//
	}	
		
	// --- Trata Requisito Nao Funcional ...
	
	public void alterarMpRequisitoNaoFuncional() {
		//
		this.txtModoRequisitoNaoFuncionalDialog = "Edição";
		
		this.indEditavelReqNF = true;
	}			
	
	public void adicionarMpRequisitoNaoFuncionalX() {
		//
		this.txtModoRequisitoNaoFuncionalDialog = "Novo";

		this.mpRequisitoNaoFuncional = new MpRequisitoNaoFuncional();
		
		this.mpRequisitoNaoFuncional.setMpProjeto(this.mpProjeto);
		this.mpRequisitoNaoFuncional.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpProjeto.getMpRequisitoNaoFuncionalList().add(this.mpRequisitoNaoFuncional);
		//
		this.indEditavelReqNF = true;
		
		// Trata Sequencia (IdRNF) ...
		this.mpSequencia = this.mpSequencias.porCodigo("IdRNF", this.mpProjeto);
		if (null == this.mpSequencia) {
			this.mpSequencia = new MpSequencia();	
			this.mpSequencia.setCodigo("IdRNF");	
			this.mpSequencia.setSequencia(0);	
			this.mpSequencia.setMpProjeto(this.mpProjeto);
			this.mpSequencia.setTenantId(mpSeguranca.capturaTenantId());
		}
		//
		this.mpSequencia.setSequencia(this.mpSequencia.getSequencia() + 1);	
			
		try {
			this.mpRequisitoNaoFuncional.setIdRNF("RNF" + String.format("%04d", 
																this.mpSequencia.getSequencia()));
			
			this.mpSequencia = mpSequenciaService.salvar(this.mpSequencia);
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void removerMpRequisitoNaoFuncionalX() {
		//
		try {
			this.mpProjeto.getMpRequisitoNaoFuncionalList().remove(this.mpRequisitoNaoFuncional);
			
			this.mpRequisitoNaoFuncionalExcluidoList.add(this.mpRequisitoNaoFuncional);
			
			MpFacesUtil.addInfoMessage("Requisito Nao Funcional... " + 
							this.mpRequisitoNaoFuncional.getDescricao()	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpRequisitoNaoFuncional() {
		//
		this.indEditavelReqF = false;
		
		this.mpRequisitoNaoFuncional = new MpRequisitoNaoFuncional();
	}			

	public void fecharMpRequisitoNaoFuncional() {
		//
		if (this.txtModoRequisitoNaoFuncionalDialog.equals("Novo"))
			this.mpProjeto.getMpRequisitoNaoFuncionalList().remove(this.mpRequisitoNaoFuncional);
	}			
	
	// --- Trata Projeto Pessoa ...

	public void alterarMpProjetoPessoaER() {
		//
		this.txtModoProjetoPessoaERDialog = "Edição";

		this.indEditavelPPesER = true;
	}

	public void adicionarMpProjetoPessoaERX() {
		//
		this.txtModoProjetoPessoaERDialog = "Nova";

		this.mpProjetoPessoaER = new MpProjetoPessoaER();

		this.mpProjetoPessoaER.setMpProjeto(this.mpProjeto);
		this.mpProjetoPessoaER.setMpPessoaER(this.mpPessoaER);
		this.mpProjetoPessoaER.setMpPessoaFuncao(this.mpPessoaFuncao);
		this.mpProjetoPessoaER.setTenantId(mpSeguranca.capturaTenantId());

		this.mpProjeto.getMpProjetoPessoaERList().add(this.mpProjetoPessoaER); ;
		//
		this.indEditavelPPesER = true;
	}

	public void removerMpProjetoPessoaERX() {
		//
		try {
			this.mpProjeto.getMpProjetoPessoaERList().remove(this.mpProjetoPessoaER);

			this.mpProjetoPessoaERExcluidoList.add(this.mpProjetoPessoaER);

			MpFacesUtil.addInfoMessage("Pessoa ER... " + this.mpProjetoPessoaER.getMpPessoaER().getNome()
					+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void salvarMpProjetoPessoaER() {
		//
		this.indEditavelPPesER = false;

		this.mpProjetoPessoaER = new MpProjetoPessoaER();
	}

	public void fecharMpProjetoPessoaER() {
		//
		if (this.txtModoProjetoPessoaERDialog.equals("Novo"))
			this.mpProjeto.getMpProjetoPessoaERList().remove(this.mpProjetoPessoaER);
	}
	
	// ---
	
	public void validateRnfAssociacao(FacesContext context, UIComponent comp, Object value) {
		//
		String associacaos = (String) value;
		
		if (associacaos.isEmpty()) return;
		//
		String msgErro = "";
		//
		System.out.println("MpCadastroProjetoBean.validateRnfAssociacao (associacao = " +
																		 associacaos);
		//
		String[] associacaosListX = associacaos.split(" ");
		//
		for (String associacaoX : associacaosListX) {
			//
			MpRequisitoFuncional mpRequisitoFuncional = this.mpRequisitoFuncionals.porIdRF(
																		associacaoX, this.mpProjeto);
			if (null == mpRequisitoFuncional) {
				msgErro = msgErro + "(" + associacaoX + ")";
			}
		}
		//
		if (!msgErro.isEmpty()) {
			((UIInput) comp).setValid(false);
			//
			FacesMessage message = new FacesMessage("Requisito(s) Funcional(s) inválidos ! = " + msgErro);
			//
			context.addMessage(comp.getClientId(context), message);
		}
		//
	}	
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpProjeto = this.mpProjetos.porNavegacao("mpFirst", " "); 
		if (null == this.mpProjeto) {
//			System.out.println("MpCadastroProjetoBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
		//
		this.txtModoTela = "( Início )";
		//
		this.mpNavegaComplementa();
	}
	
	public void mpPrev() {
		//
		if (null == this.mpProjeto.getNome()) return;
		//
		this.setMpProjetoAnt(this.mpProjeto);
		//
		this.mpProjeto = this.mpProjetos.porNavegacao("mpPrev", mpProjeto.getNome());
		if (null == this.mpProjeto) {
			this.mpProjeto = this.mpProjetoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.mpNavegaComplementa();
	}

	public void mpNew() {
		//
		this.setMpProjetoAnt(this.mpProjeto);
		
		this.mpProjeto = new MpProjeto();
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpProjeto.getId()) return;
		//
		this.setMpProjetoAnt(this.mpProjeto);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpProjeto.getId()) return;
		//
		try {
			this.mpProjetos.remover(mpProjeto);
			
			MpFacesUtil.addInfoMessage("Projeto... " + this.mpProjeto.getNome() + " excluído(a) com sucesso.");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}

		this.mpProjetoAnt = this.mpProjeto;
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario(). getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpProjeto = this.mpProjetoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpProjeto.getNome()) return;
		//
		this.setMpProjetoAnt(this.mpProjeto);
		//
		this.mpProjeto = this.mpProjetos.porNavegacao("mpNext", mpProjeto.getNome());
		if (null == this.mpProjeto) {
			this.mpProjeto = this.mpProjetoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.mpNavegaComplementa();
	}
	
	public void mpEnd() {
		//
		this.mpProjeto = this.mpProjetos.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpProjeto)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
		//
		this.mpNavegaComplementa();
	}

	public void mpNavegaComplementa() {
		//
		if (null == this.mpProjeto.getId()) return;
		
		// Trata Matriz Rastreabilidade Requisitos Funcionais X Regras Negócios !
		// ======================================================================
		this.trataMpRfRnDTO();

		this.mpModuloList = this.mpProjeto.getMpModuloList();
		//
		// Trata gráficos ...
		// ==================
		this.createPieModelRfStatus();
		this.createPieModelRfPrioridade();
		this.createPieModelRfComplexibilidade();

		this.createPieModelRnStatus();
		this.createPieModelRnComplexibilidade();

		this.createBarModelRnfCategoria();
		
		// Trata calculo esforço Projeto...
		this.calculaEsforcoProjeto();
		this.recalcularValorFinalProjeto();
		this.calculaPrazoProjeto();
		//
		this.mpProjetoFaseEtapaList = Arrays.asList(MpProjetoFaseEtapa.values());
		//
		this.trataMpProjetoFaseEtapaDTO();
		this.trataMpProjetoFaseEtapaDetDTO();
		//
	}
	
	public void mpClone() {
		//
		if (null == this.mpProjeto.getId()) return;

		try {
			this.setMpProjetoAnt(this.mpProjeto);

			this.mpProjeto = (MpProjeto) this.mpProjeto.clone();
			//
			this.mpProjeto.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}
	
	// ---
	
	private void limpar() {
		//
		this.mpProjeto = new MpProjeto();
		this.mpProjeto.setTenantId(mpSeguranca.capturaTenantId());
		this.mpProjeto.setNome("");
		//
		this.mpProjeto.setMpMacroRequisitoList(new ArrayList<MpMacroRequisito>());
		this.mpMacroRequisito = new MpMacroRequisito();		
		this.mpMacroRequisito.setTenantId(mpSeguranca.capturaTenantId());
		this.mpMacroRequisito.setDescricao("");
		//
		this.mpProjeto.setMpModuloList(new ArrayList<MpModulo>());
		this.mpModulo = new MpModulo();
		this.mpModulo.setTenantId(mpSeguranca.capturaTenantId());
		this.mpModulo.setDescricao("");
		//
		this.mpProjeto.setMpFuncionalidadeList(new ArrayList<MpFuncionalidade>());
		this.mpFuncionalidade = new MpFuncionalidade();
		this.mpFuncionalidade.setTenantId(mpSeguranca.capturaTenantId());
		this.mpFuncionalidade.setDescricao("");
		//
		this.mpProjeto.setMpRequisitoFuncionalList(new ArrayList<MpRequisitoFuncional>());
		this.mpRequisitoFuncional = new MpRequisitoFuncional();
		this.mpRequisitoFuncional.setTenantId(mpSeguranca.capturaTenantId());
		this.mpRequisitoFuncional.setDescricao("");
		//
		this.mpProjeto.setMpRegraNegocioList(new ArrayList<MpRegraNegocio>());
		this.mpRegraNegocio = new MpRegraNegocio();
		this.mpRegraNegocio.setTenantId(mpSeguranca.capturaTenantId());
		this.mpRegraNegocio.setDescricao("");
		//
		this.mpProjeto.setMpRequisitoNaoFuncionalList(new ArrayList<MpRequisitoNaoFuncional>());
		this.mpRequisitoNaoFuncional = new MpRequisitoNaoFuncional();
		this.mpRequisitoNaoFuncional.setTenantId(mpSeguranca.capturaTenantId());
		this.mpRequisitoNaoFuncional.setDescricao("");
		//
		this.mpProjeto.setMpProjetoPessoaERList(new ArrayList<MpProjetoPessoaER>());
		this.mpProjetoPessoaER = new MpProjetoPessoaER();
		this.mpPessoaER = new MpPessoaER();
		this.mpProjetoPessoaER.setMpPessoaER(this.mpPessoaER);
		this.mpProjetoPessoaER.setTenantId(mpSeguranca.capturaTenantId());
		this.mpProjetoPessoaER.setObservacao("");
	}
	
	// ---
	
    private void calculaEsforcoProjeto() {
    	//
    	this.esforcoTotal = BigDecimal.ZERO;
    	this.esforcoRequisitoFuncional = BigDecimal.ZERO;
    	this.esforcoRequisitoNaoFuncional = BigDecimal.ZERO;
    	//
    	for (MpRequisitoFuncional mpRequisitoFuncional : this.mpProjeto.getMpRequisitoFuncionalList()) {
    		//
    		if (null == mpRequisitoFuncional.getNumHoraEsforco())
    			assert(true); // nop
    		else
    			this.esforcoRequisitoFuncional = this.esforcoRequisitoFuncional.
													add(mpRequisitoFuncional.getNumHoraEsforco());
    	}
    	//
    	for (MpRequisitoNaoFuncional mpRequisitoNaoFuncional : 
    											this.mpProjeto.getMpRequisitoNaoFuncionalList()) {
    		//
    		if (null == mpRequisitoNaoFuncional.getNumHoraEsforco())
    			assert(true); // nop
    		else
    			this.esforcoRequisitoNaoFuncional = this.esforcoRequisitoNaoFuncional.
    												add(mpRequisitoNaoFuncional.getNumHoraEsforco());
    	}
    	//
		this.esforcoTotal = this.esforcoTotal.add(this.esforcoRequisitoFuncional);
		this.esforcoTotal = this.esforcoTotal.add(this.esforcoRequisitoNaoFuncional);
		//
		// Calcula Esforco Total Preliminar ...
		//
		if (null == this.mpProjeto.getValorFinalProjetoPre()
		||	null == this.mpProjeto.getValorHoraEquipePre()
		||	this.mpProjeto.getValorHoraEquipePre().equals(BigDecimal.ZERO))
			this.esforcoTotalPre = BigDecimal.ZERO;
		else {
			try{
				this.esforcoTotalPre = this.mpProjeto.getValorFinalProjetoPre().divide(
									this.mpProjeto.getValorHoraEquipePre(), 2, RoundingMode.HALF_UP);
			} catch (ArithmeticException e) {
				this.esforcoTotalPre = BigDecimal.ZERO;
				//
				System.out.println("MpCadastroReceitaBeanX.calculaEsforcoProjeto() ( E = " + e 
						+ " / ValHorEqPre = " + this.mpProjeto.getValorHoraEquipePre());
			}
		}
    }
	
	public void recalcularValorFinalProjeto() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
		total = total.add(this.esforcoTotal).multiply(this.mpProjeto.getValorHoraEquipe());
		//
		this.mpProjeto.setValorFinalProjeto(total);
	}
	
	public void calculaPrazoProjeto() {
		//
		if (null == this.mpProjeto.getQtdProfissional() 
		|| this.mpProjeto.getQtdProfissional().equals(BigDecimal.ZERO))
			this.mpProjeto.setQtdProfissional(BigDecimal.ONE);
		//
		BigDecimal horasMes = BigDecimal.valueOf(160L);
		
		this.prazoProjeto = BigDecimal.ZERO;
		
		this.prazoProjeto = this.prazoProjeto.add(this.esforcoTotal).divide(
															this.mpProjeto.getQtdProfissional());
		
		this.prazoProjeto = this.prazoProjeto.divide(horasMes);
		//
	}
    
	// ---

    private void createPieModelRfStatus() {
    	//
        pieModelRfStatus = new PieChartModel();
         
        for (MpStatusRequisito mpStatusRequisitoX : this.mpStatusRequisitoList) {
        	//
        	Long contX = mpRequisitoFuncionals.porStatusProjeto(mpStatusRequisitoX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
        	pieModelRfStatus.set(mpStatusRequisitoX.getDescricao(), contX);
        	//
        	System.out.println("MpCadastroProjetoBean.createPieModelRfStatus() / Proj = " + 
				this.mpProjeto.getId() + " / " + mpStatusRequisitoX.getDescricao() + " / " + contX);
        }
        //
        pieModelRfStatus.setTitle("Requisitos Funcionais por Status");
        pieModelRfStatus.setLegendPosition("w");
    }	

    private void createPieModelRfPrioridade() {
    	//
        pieModelRfPrioridade = new PieChartModel();
         
        for (MpPrioridade mpPrioridadeX : this.mpPrioridadeList) {
        	//
        	Long contX = mpRequisitoFuncionals.porPrioridadeProjeto(mpPrioridadeX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
        	pieModelRfPrioridade.set(mpPrioridadeX.getDescricao(), contX);
        	//
        	System.out.println("MpCadastroProjetoBean.createPieModelRfPrioridade() / Proj = " + 
				this.mpProjeto.getId() + " / " + mpPrioridadeX.getDescricao() + " / " + contX);
        }
        //
        pieModelRfPrioridade.setTitle("Requisitos Funcionais por Prioridade");
        pieModelRfPrioridade.setLegendPosition("w");
    }	

    private void createPieModelRfComplexibilidade() {
    	//
        pieModelRfComplexibilidade = new PieChartModel();
         
        for (MpComplexibilidade mpComplexibilidadeX : this.mpComplexibilidadeList) {
        	//
        	Long contX = mpRequisitoFuncionals.porComplexibilidadeProjeto(mpComplexibilidadeX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
        	pieModelRfComplexibilidade.set(mpComplexibilidadeX.getDescricao(), contX);
        	//
        	System.out.println("MpCadastroProjetoBean.createPieModelRfComplexibilidade() / Proj = " + 
				this.mpProjeto.getId() + " / " + mpComplexibilidadeX.getDescricao() + " / " + contX);
        }
        //
        pieModelRfComplexibilidade.setTitle("Requisitos Funcionais por Complexibilidade");
        pieModelRfComplexibilidade.setLegendPosition("w");
    }	

    private void createPieModelRnStatus() {
    	//
        pieModelRnStatus = new PieChartModel();
         
        for (MpStatusRequisito mpStatusRequisitoX : this.mpStatusRequisitoList) {
        	//
        	Long contX = mpRegraNegocios.porStatusProjeto(mpStatusRequisitoX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
        	pieModelRnStatus.set(mpStatusRequisitoX.getDescricao(), contX);
        	//
        	System.out.println("MpCadastroProjetoBean.createPieModelRnStatus() / Proj = " + 
				this.mpProjeto.getId() + " / " + mpStatusRequisitoX.getDescricao() + " / " + contX);
        }
        //
        pieModelRnStatus.setTitle("Regras Negócios por Status");
        pieModelRnStatus.setLegendPosition("w");
    }	

    private void createPieModelRnComplexibilidade() {
    	//
        pieModelRnComplexibilidade = new PieChartModel();
         
        for (MpComplexibilidade mpComplexibilidadeX : this.mpComplexibilidadeList) {
        	//
        	Long contX = mpRegraNegocios.porComplexibilidadeProjeto(mpComplexibilidadeX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
        	pieModelRnComplexibilidade.set(mpComplexibilidadeX.getDescricao(), contX);
        	//
        	System.out.println("MpCadastroProjetoBean.createPieModelRnComplexibilidade() / Proj = " + 
				this.mpProjeto.getId() + " / " + mpComplexibilidadeX.getDescricao() + " / " + contX);
        }
        //
        pieModelRnComplexibilidade.setTitle("Regras Negócios por Complexibilidade");
        pieModelRnComplexibilidade.setLegendPosition("w");
    }	

    private void createBarModelRnfCategoria() {
    	//
		barModelRnfCategoria = new BarChartModel();
         
        for (MpCategoriaRNF mpCategoriaRNFX : this.mpCategoriaRNFList) {
        	//
            ChartSeries categorias = new ChartSeries();
            
            categorias.setLabel(mpCategoriaRNFX.getDescricao());
        	//
        	Long contX = mpRequisitoNaoFuncionals.porCategoriaProjeto(mpCategoriaRNFX, this.mpProjeto);
        	if (null == contX) contX = 0L;
        	//
            categorias.set(mpCategoriaRNFX.getDescricao(), contX);
        	//
        	barModelRnfCategoria.addSeries(categorias);
        	//
        	System.out.println("MpCadastroProjetoBean.createBarModelRnfCategoria() / Proj = " + 
					this.mpProjeto.getId() + " / " + mpCategoriaRNFX.getDescricao() + " / " + contX);
        }
        //
        barModelRnfCategoria.setTitle("Requisitos Não Funcionais por Categoria");
        barModelRnfCategoria.setLegendPosition("w");
    }	
    
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { 
														this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public MpProjeto getMpProjeto() { return mpProjeto; }
	public void setMpProjeto(MpProjeto mpProjeto) { this.mpProjeto = mpProjeto; }

	public MpProjeto getMpProjetoAnt() { return mpProjetoAnt; }
	public void setMpProjetoAnt(MpProjeto mpProjetoAnt) {
		try {
			this.mpProjetoAnt = (MpProjeto) this.mpProjeto.clone();
			this.mpProjetoAnt.setId(this.mpProjeto.getId());
			//
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpPrioridade getMpPrioridade() { return mpPrioridade; }
	public void setMpPrioridade(MpPrioridade mpPrioridade) { this.mpPrioridade = mpPrioridade; }
	public List<MpPrioridade> getMpPrioridadeList() { return mpPrioridadeList; }

	public MpComplexibilidade getMpComplexibilidade() { return mpComplexibilidade; }
	public void setMpComplexibilidade(MpComplexibilidade mpComplexibilidade) {
													this.mpComplexibilidade = mpComplexibilidade; }
	public List<MpComplexibilidade> getMpComplexibilidadeList() { return mpComplexibilidadeList; }

	public MpStatusRequisito getMpStatusRequisito() { return mpStatusRequisito; }
	public void setMpStatusRequisito(MpStatusRequisito mpStatusRequisito) {
																this.mpStatusRequisito = mpStatusRequisito; }
	public List<MpStatusRequisito> getMpStatusRequisitoList() { return mpStatusRequisitoList; }

	public MpCategoriaRNF getMpCategoriaRNF() { return mpCategoriaRNF; }
	public void setMpCategoriaRNF(MpCategoriaRNF mpCategoriaRNF) { 	this.mpCategoriaRNF = mpCategoriaRNF; }
	public List<MpCategoriaRNF> getMpCategoriaRNFList() { return mpCategoriaRNFList; }

	public MpPessoaER getMpPessoaER() { return mpPessoaER; }
	public void setMpPessoaER(MpPessoaER mpPessoaER) { 	this.mpPessoaER = mpPessoaER; }
	public List<MpPessoaER> getMpPessoaERList() { return mpPessoaERList; }

	public MpPessoaFuncao getMpPessoaFuncao() { return mpPessoaFuncao; }
	public void setMpPessoaFuncao(MpPessoaFuncao mpPessoaFuncao) { 	this.mpPessoaFuncao = mpPessoaFuncao; }
	public List<MpPessoaFuncao> getMpPessoaFuncaoList() { return mpPessoaFuncaoList; }

	public MpRelatorioER getMpRelatorioER() { return mpRelatorioER; }
	public void setMpRelatorioER(MpRelatorioER mpRelatorioER) { this.mpRelatorioER = mpRelatorioER; }
	public List<MpRelatorioER> getMpRelatorioERList() { return mpRelatorioERList; }
	
	// ---
	
	public MpStatusDTO getMpStatusDTO() { return mpStatusDTO; }
	public void setMpStatusDTO(MpStatusDTO mpStatusDTO) { this.mpStatusDTO = mpStatusDTO; }
	public List<MpStatusDTO> getMpStatusDTOList() { return mpStatusDTOList; }
	
	public MpProjetoFaseEtapa getMpProjetoFaseEtapa() { return mpProjetoFaseEtapa; }
	public void setMpProjetoFaseEtapa(MpProjetoFaseEtapa mpProjetoFaseEtapa) {
												this.mpProjetoFaseEtapa = mpProjetoFaseEtapa; }
	public List<MpProjetoFaseEtapa> getMpProjetoFaseEtapaList() { return mpProjetoFaseEtapaList; }
	
	public MpProjetoFaseEtapaDTO getMpProjetoFaseEtapaDTO() { return mpProjetoFaseEtapaDTO; }
	public void setMpProjetoFaseEtapaDTO(MpProjetoFaseEtapaDTO mpProjetoFaseEtapaDTO) {
											this.mpProjetoFaseEtapaDTO = mpProjetoFaseEtapaDTO; }
	public List<MpProjetoFaseEtapaDTO> getMpProjetoFaseEtapaDTOList() { return mpProjetoFaseEtapaDTOList; }
	public List<MpProjetoFaseEtapaDTO> getMpProjetoFaseEtapaDetDTOList() { return mpProjetoFaseEtapaDetDTOList; }
	
	public List<MpMatrizRfRnDTO> getMpMatrizRfRnDTOList() { return mpMatrizRfRnDTOList; }
	
	// ---
	
	public boolean isEditando() { return this.mpProjeto.getId() != null; }
	
	public MpMacroRequisito getMpMacroRequisito() { return mpMacroRequisito; }
	public void setMpMacroRequisito(MpMacroRequisito mpMacroRequisito) { this.mpMacroRequisito = mpMacroRequisito; }

	public MpModulo getMpModulo() { return mpModulo; }
	public void setMpModulo(MpModulo mpModulo) { this.mpModulo = mpModulo; }
	public List<MpModulo> getMpModuloList() { return mpModuloList; }

	public MpFuncionalidade getMpFuncionalidade() { return mpFuncionalidade; }
	public void setMpFuncionalidade(MpFuncionalidade mpFuncionalidade) { this.mpFuncionalidade = mpFuncionalidade; }
	public List<MpFuncionalidade> getMpFuncionalidadeList() { return mpFuncionalidadeList; }
	
	public MpRequisitoFuncional getMpRequisitoFuncional() { return mpRequisitoFuncional; }
	public void setMpRequisitoFuncional(MpRequisitoFuncional mpRequisitoFuncional) { 
																this.mpRequisitoFuncional = mpRequisitoFuncional; }
	
	public MpRegraNegocio getMpRegraNegocio() { return mpRegraNegocio; }
	public void setMpRegraNegocio(MpRegraNegocio mpRegraNegocio) { this.mpRegraNegocio = mpRegraNegocio; }
	
	public MpRequisitoNaoFuncional getMpRequisitoNaoFuncional() { return mpRequisitoNaoFuncional; }
	public void setMpRequisitoNaoFuncional(MpRequisitoNaoFuncional mpRequisitoNaoFuncional) { 
														this.mpRequisitoNaoFuncional = mpRequisitoNaoFuncional; }
	
	public MpProjetoPessoaER getMpProjetoPessoaER() { return mpProjetoPessoaER; }
	public void setMpProjetoPessoaER(MpProjetoPessoaER mpProjetoPessoaER) { 
																	this.mpProjetoPessoaER = mpProjetoPessoaER; }
	
	public boolean getIndEditavelMReq() { return indEditavelMReq; }
	public void setIndEditavelMReq(Boolean indEditavelMReq) { this.indEditavelMReq = indEditavelMReq; }
	
	public boolean getIndEditavelMod() { return indEditavelMod; }
	public void setIndEditavelMod(Boolean indEditavelMod) { this.indEditavelMod = indEditavelMod; }
	
	public boolean getIndEditavelFun() { return indEditavelFun; }
	public void setIndEditavelFun(Boolean indEditavelFun) { this.indEditavelFun = indEditavelFun; }
	
	public boolean getIndEditavelReqF() { return indEditavelReqF; }
	public void setIndEditavelReqF(Boolean indEditavelReqF) { this.indEditavelReqF = indEditavelReqF; }
	
	public boolean getIndEditavelRegN() { return indEditavelRegN; }
	public void setIndEditavelRegN(Boolean indEditavelRegN) { this.indEditavelRegN = indEditavelRegN; }
	
	public boolean getIndEditavelReqNF() { return indEditavelReqNF; }
	public void setIndEditavelReqNF(Boolean indEditavelReqNF) { this.indEditavelReqNF = indEditavelReqNF; }
	
	public boolean getIndEditavelPPesER() { return indEditavelPPesER; }
	public void setIndEditavelPPesER(Boolean indEditavelPPesER) { this.indEditavelPPesER = indEditavelPPesER; }

	public String getTxtModoMacroRequisitoDialog() { return txtModoMacroRequisitoDialog; }
	public void setTxtModoMacroRequisitoDialog(String txtModoMacroRequisitoDialog) {
												this.txtModoMacroRequisitoDialog = txtModoMacroRequisitoDialog; }

	public String getTxtModoModuloDialog() { return txtModoModuloDialog; }
	public void setTxtModoModuloDialog(String txtModoModuloDialog) { 
																this.txtModoModuloDialog = txtModoModuloDialog; }

	public String getTxtModoFuncionalidadeDialog() { return txtModoFuncionalidadeDialog; }
	public void setTxtModoFuncionalidadeDialog(String txtModoFuncionalidadeDialog) { 
												this.txtModoFuncionalidadeDialog = txtModoFuncionalidadeDialog; }

	public String getTxtModoRequisitoFuncionalDialog() { return txtModoRequisitoFuncionalDialog; }
	public void setTxtModoRequisitoFuncionalDialog(String txtModoRequisitoFuncionalDialog) {
										this.txtModoRequisitoFuncionalDialog = txtModoRequisitoFuncionalDialog; }

	public String getTxtModoRegraNegocioDialog() { return txtModoRegraNegocioDialog; }
	public void setTxtModoRegraNegocioDialog(String txtModoRegraNegocioDialog) {
													this.txtModoRegraNegocioDialog = txtModoRegraNegocioDialog; }

	public String getTxtModoRequisitoNaoFuncionalDialog() { return txtModoRequisitoNaoFuncionalDialog; }
	public void setTxtModoRequisitoNaoFuncionalDialog(String txtModoRequisitoNaoFuncionalDialog) {
									this.txtModoRequisitoNaoFuncionalDialog = txtModoRequisitoNaoFuncionalDialog; }

	public String getTxtModoProjetoPessoaERDialog() { return txtModoProjetoPessoaERDialog; }
	public void setTxtModoProjetoPessoaERDialog(String txtModoProjetoPessoaERDialog) {
												this.txtModoProjetoPessoaERDialog = txtModoProjetoPessoaERDialog; }
	
	// ---
	
    public PieChartModel getPieModelRfStatus() { return pieModelRfStatus; }
    public PieChartModel getPieModelRfPrioridade() { return pieModelRfPrioridade; }
    public PieChartModel getPieModelRfComplexibilidade() { return pieModelRfComplexibilidade; }

    public PieChartModel getPieModelRnStatus() { return pieModelRnStatus; }
    public PieChartModel getPieModelRnComplexibilidade() { return pieModelRnComplexibilidade; }
	
    public BarChartModel getBarModelRnfCategoria() { return barModelRnfCategoria; }

    // ---
    
	public BigDecimal getEsforcoRequisitoFuncional() { return esforcoRequisitoFuncional; }
	public void setEsforcoRequisitoFuncional(BigDecimal esforcoRequisitoFuncional) { 
											this.esforcoRequisitoFuncional = esforcoRequisitoFuncional; }
    
	public BigDecimal getEsforcoRequisitoNaoFuncional() { return esforcoRequisitoNaoFuncional; }
	public void setEsforcoRequisitoNaoFuncional(BigDecimal esforcoRequisitoNaoFuncional) { 
											this.esforcoRequisitoNaoFuncional = esforcoRequisitoNaoFuncional; }
    
	public BigDecimal getEsforcoTotal() { return esforcoTotal; }
	public void setEsforcoTotal(BigDecimal esforcoTotal) { this.esforcoTotal = esforcoTotal; }
    
	public BigDecimal getPrazoProjeto() { return prazoProjeto; }
	public void setPrazoProjeto(BigDecimal prazoProjeto) { this.prazoProjeto = prazoProjeto; }
    
	public BigDecimal getEsforcoTotalPre() { return esforcoTotalPre; }
	public void setEsforcoTotalPre(BigDecimal esforcoTotalPre) { this.esforcoTotalPre = esforcoTotalPre; }
	
}