package com.mpxds.mpbasic.controller.pt05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import javax.faces.context.ExternalContext;

import org.apache.commons.lang.StringUtils;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt01.MpEspecie;
import com.mpxds.mpbasic.model.pt05.MpHeader;
import com.mpxds.mpbasic.model.pt05.MpImportarControle;
import com.mpxds.mpbasic.model.pt05.MpRemessa;
import com.mpxds.mpbasic.model.pt05.MpTrailler;
import com.mpxds.mpbasic.model.pt05.MpTransacao;

import com.mpxds.mpbasic.repository.pt01.MpEspecies;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt05.MpImportarControleService;
import com.mpxds.mpbasic.service.pt05.MpRemessaService;
import com.mpxds.mpbasic.service.pt05.MpHeaderService;
import com.mpxds.mpbasic.service.pt05.MpTransacaoService;
import com.mpxds.mpbasic.service.pt05.MpTraillerService;

import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpRecebimentoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpEspecies mpEspecies;

	@Inject
	private MpImportarControleService mpImportarControleService;

	@Inject
	private MpRemessaService mpRemessaService;

	@Inject
	private MpHeaderService mpHeaderService;

	@Inject
	private MpTransacaoService mpTransacaoService;

	@Inject
	private MpTraillerService mpTraillerService;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---

	private MpImportarControle mpImportarControle;

	private MpRemessa mpRemessa = new MpRemessa();

	private Integer progresso = 0;
	private Integer numSequencial = 0;

	private File file;

	private List<MpHeader> mpHeaderList = new ArrayList<MpHeader>();
	private List<MpTrailler> mpTraillerList = new ArrayList<MpTrailler>();
	private List<MpTransacao> mpTransacaoList = new ArrayList<MpTransacao>();

	private Boolean indReceber = false;
	private Boolean indComplemento = false;
	private Boolean indXML = false;
	private Boolean indCancela = false;
	private Boolean indGrava = false;
	private Boolean indGravaRecebimento = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyyMMdd");
	
	private MpObjeto mpObjetoHelp;
	
	// ---
	
	public void inicializar() {
		//
		HttpServletRequest request = (HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext().getRequest();

		String paramDtiIC = request.getParameter("dtiIC");
		if (null == paramDtiIC) {
	    	MpFacesUtil.addInfoMessage("Error.Inicializar() Cod.E001i... contactar o Suporte ! ( Parm.Null");
	    	return;
		}
		String paramDtcIC = request.getParameter("dtcIC");
		if (null == paramDtcIC) {
	    	MpFacesUtil.addInfoMessage("Error.Inicializar() Cod.E001c... contactar o Suporte ! ( Parm.Null");
	    	return;
		}
		String paramDtaIC = request.getParameter("dtaIC");
		if (null == paramDtaIC) {
	    	MpFacesUtil.addInfoMessage("Error.Inicializar() Cod.E001a... contactar o Suporte ! ( Parm.Null");
	    	return;
		}
		//
		this.mpImportarControle = new MpImportarControle();
		
		try {
			this.mpImportarControle.setDataImportar(sdf.parse(paramDtiIC));
			this.mpImportarControle.setDataControle(sdf.parse(paramDtcIC));
			this.mpImportarControle.setDataAte(sdf.parse(paramDtaIC));
			//
		} catch (ParseException e) {
	    	MpFacesUtil.addInfoMessage("Error.Inicializar().parse()... contactar o Suporte ! (e=" + e);
	    	return;
		}
		//
		this.mpRemessa = new MpRemessa();
		
		this.mpRemessa.setMpImportarControle(this.mpImportarControle);
	}

	public void trataArquivo() {
		//
		if (this.mpRemessa.getNomeArquivo().length() == 9
		||  this.mpRemessa.getNomeArquivo().length() == 10)
			assert(true); // nop
		else
			if  (this.mpRemessa.getNomeArquivo().length() == 11
			&&	 this.mpRemessa.getNomeArquivo().toUpperCase().indexOf("ATO") >= 0)
			assert(true); // nop
		else {
	    	MpFacesUtil.addInfoMessage("Nome Arquivo... inválido ! (Tam.= " +
	    													this.mpRemessa.getNomeArquivo().length());
	    	return;
		}
		// 20160718ATO Z0X1807161 Z11708162
		// 01234567890 0123456789 012345678
		String pathX = "";
		if (this.mpRemessa.getNomeArquivo().toUpperCase().indexOf(".ATO") >= 0) 
			pathX = this.mpRemessa.getNomeArquivo().substring(0, 4) +
					File.separator + this.mpRemessa.getNomeArquivo().substring(5, 7) +
					File.separator + this.mpRemessa.getNomeArquivo().substring(8, 10) +
					File.separator;
		else
			pathX = sdfYMD.format(this.mpImportarControle.getDataImportar()) + File.separator;
		//
		String arquivo = "";
		if (this.mpRemessa.getNomeArquivo().length() == 9)
			arquivo = this.mpRemessa.getNomeArquivo().substring(0, 6) + "." +
												this.mpRemessa.getNomeArquivo().substring(6);
		else
			arquivo = this.mpRemessa.getNomeArquivo().substring(0, 7) + "." +
												this.mpRemessa.getNomeArquivo().substring(7);
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		
//		ClassLoader classLoader = getClass().getClassLoader();
		//
		try {
//			this.file  = new File(classLoader.getResource("importacao" + File.separator + 
//																			arquivo).getFile());			

			this.file = new File(extContext.getRealPath(File.separator + "resources" +
							File.separator + "importacao" + File.separator + pathX + arquivo));
			//
			if (this.mpRemessa.getNomeArquivo().toUpperCase().indexOf("ATO") >= 0
			&&  this.mpRemessa.getNomeArquivo().length() == 11)
				this.indXML = true;
			else
				if (this.mpRemessa.getNomeArquivo().length() == 9)
					this.indComplemento = true;
				else
					this.indReceber = true;	// length() == 10			
			//
		}
		catch(Exception e) {
	    	MpFacesUtil.addInfoMessage("Arquivo... não existe ! ( " + arquivo + " / pathX = " + 
	    																				pathX);
	    	return;
		}
	}
	
	public void receber() {
		//
		if (this.indGravaRecebimento) {
	    	MpFacesUtil.addInfoMessage("Gravação do Recebimento ...já foi Efetuada !");
	    	return;
		}
		//
		if (this.mpRemessa.getNomeArquivo().length() == 10)
			assert(true); // nop
		else {
	    	MpFacesUtil.addInfoMessage("Nome Arquivo... inválido !");
	    	return;
		}
		//
		// dataImportarSDF = dd/mm/aaaa
		//                   0123456789
		String dataImportarDMY = this.mpImportarControle.getDataImportarSDF().substring(0, 2) +
						this.mpImportarControle.getDataImportarSDF().substring(3, 5) +
						this.mpImportarControle.getDataImportarSDF().substring(8);
		//
		this.progresso = 0;
		
		Integer contador = 0;

		Double contadorProgresso = (double) (100 / 10); // mpAtoList.size());
		Double totalProgresso = 0.0;
		
		this.mpHeaderList.clear();
		this.mpTraillerList.clear();
		this.mpTransacaoList.clear();
		//
		try (Scanner scanner = new Scanner(this.file)) {
			//
			while (scanner.hasNextLine()) {
				//
				String line = scanner.nextLine();
				
				if (line.length() == 500)
					assert(true); // nop
				else {
			    	MpFacesUtil.addInfoMessage("Tamanho Linha(500)... inválida ! ( Tam.= " +
			    											line.length() + " / Linha = " + line);
			    	return; 
				}
				
				// 01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
				// 0180716SDT3OPETP0000010021001900080011                                                                                                                                                                                                                                                                                                                                                                                                                                                                          0001
				// 1701               2016011643     ANATEL  AGENCIA NACIONAL DE TELECOMUNICACOES ANATEL  AGENCIA NACIONAL DE TELECOMUNICACOES CDA2016011643 0807169999990010000000015505700000000155057RIO DE JANEIRO       N1MINNERACAO JENIPAPO                          CGC3197501400015400000000000AVENIDA OLEGARIO MACIEL 260 COB 02           22621200RIO DE JANEIRO      RJ030052861PROCURADORIA-GERAL FEDERAL-PGF               0000010425211439                   BARRA DA TIJUCA          EBQJ74834JLS                F 0002
				// 1840               776940 B       CCN COMERCIAL CENTRO NORTE ALIMENTOS LTDA    CCN COMERCIAL CENTRO NORTE ALIMENTOS LTDA    DMI776940 B   2801161202160010000000005510900000000055109RIO DE JANEIRO      MN1KATIA REGINA DE LIMA 03738374701             CGC1509391800010800000000000ESTRADA DA SANTO ANTONIO, 944LOJA            23535650RIO DE JANEIRO      RJ030053034CCN                                          0000006341212137                   SEPETIBA                 EBQJ74901XSK                C 0019
				// 1D24               18953          H.G.F. EXTRADA HIPER SHOPPING LTDA - SHOPPINGH.G.F. EXTRADA HIPER SHOPPING LTDA - SHOPPINGDMI18953-3    3103162906160010000000019695000000000065650RIO DE JANEIRO       N1MODORA RIO LTDA ME                           CGC0845859000010200000000000ENES FILHO N 258 CASA 2                      21011290RIO DE JANEIRO      RJ030053035H.G.F. EXTRADA HIPER SHOPPING LTDA - SHO     0000006971212142                   PENHA CIRCULAR           EBQJ74906SEB                C 0020
				// 9180716000000000000000001626583                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 0021				
				// 01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789

				// ----------------
				// Trata HEADER ...
				// ----------------
				if (line.substring(0, 1).equals("0")) {
					//
					MpHeader mpHeader = new MpHeader();
					
					mpHeader.setMpRemessa(this.mpRemessa);
					//
					if (!line.substring(1, 7).equals(dataImportarDMY)) {
				    	MpFacesUtil.addInfoMessage("HEADER... Datas divergentes... (Dt.Importar = " +
				    					dataImportarDMY + " / Data Arquivo = " + line.substring(1, 7));
						return;
					}
					//
					mpHeader.setIdenTransRemetente(line.substring(7, 10));
					mpHeader.setIdenTransDestinatario(line.substring(10, 13));
					mpHeader.setIdenTransTipo(line.substring(13, 16));
					mpHeader.setNumeroSeqRemessa(line.substring(16, 22));
					mpHeader.setQtdRegRemessa(line.substring(22, 26));
					mpHeader.setQtdTitRemessa(line.substring(26, 30));
					mpHeader.setQtdIndRemessa(line.substring(30, 34));
					mpHeader.setQtdOrigRemessa(line.substring(34, 38));
					mpHeader.setComplementoRegistro(" ");
					mpHeader.setNumeroSeqRegistro(line.substring(496));
					//
					this.mpHeaderList.add(mpHeader);
				}
				// ------------------
				// Trata TRAILLER ...
				// ------------------
				if (line.substring(0, 1).equals("9")) {
					//
					MpTrailler mpTrailler = new MpTrailler();
					
					mpTrailler.setMpRemessa(this.mpRemessa);
					//
					mpTrailler.setDataDistribuicao(line.substring(1, 7));
					mpTrailler.setSomaSegQtdRemessa(line.substring(7, 22));
					mpTrailler.setSomaSegValRemessa(line.substring(22, 40));
					mpTrailler.setComplementoRegistro(" ");
					mpTrailler.setNumeroSeqRegistro(line.substring(496));
					//
					this.mpTraillerList.add(mpTrailler);
				}
				// -------------------
				// Trata TRANSACAO ...
				// -------------------
				if (line.substring(0, 1).equals("1")) {
					//
					MpTransacao mpTransacao = new MpTransacao();
					
					mpTransacao.setMpRemessa(this.mpRemessa);
					//
					mpTransacao.setNumeroCodigoPortador(line.substring(1, 4));
					mpTransacao.setAgenciaCodCedente(line.substring(4, 19));
					mpTransacao.setNossoNumero(line.substring(19, 34));
					mpTransacao.setNomeSacador(line.substring(34, 79));
					mpTransacao.setNomeCedFav(line.substring(79, 124));
					mpTransacao.setEspecieTitulo(line.substring(124, 127));
					mpTransacao.setNumeroTitulo(line.substring(127, 138));
					mpTransacao.setDataEmissaoTitulo(line.substring(138, 144) + "00");
					mpTransacao.setDataVencimentoTitulo(line.substring(144, 150) + "00");
					mpTransacao.setTipoMoeda(line.substring(150, 153));
					mpTransacao.setValorTitulo(line.substring(153, 167));
					mpTransacao.setSaldoTitulo(line.substring(167, 181));
					mpTransacao.setPracaPagamento(line.substring(181, 201));
					mpTransacao.setTipoEndosso(line.substring(201, 202));
					mpTransacao.setInformacaoAceite(line.substring(202, 203));
					mpTransacao.setNumeroControleDevedor(line.substring(203, 204));
					mpTransacao.setNomeDevedor(line.substring(204, 249));
					mpTransacao.setTipoIdentDevedor(line.substring(249, 252));
					mpTransacao.setNumeroIdentDevedor(line.substring(252, 266));
					mpTransacao.setDocumentoDevedor(line.substring(266, 277));
					mpTransacao.setEnderecoDevedor(line.substring(277, 322));
					mpTransacao.setCepDevedor(line.substring(322, 330));
					mpTransacao.setCidadeDevedor(line.substring(330, 350));
					mpTransacao.setUfDevedor(line.substring(350, 352));
					mpTransacao.setNumeroCartorio(line.substring(352, 354));
					mpTransacao.setNumeroProtocoloCartorio("000" + line.substring(354, 361));
					mpTransacao.setDataProtocoloCartorio(dataImportarDMY);
					mpTransacao.setNomePortador(line.substring(361, 406));
					mpTransacao.setValorCustasCartorioDist(line.substring(406, 416));
					mpTransacao.setNumeroDistribuicao(line.substring(416, 422));
					mpTransacao.setBairroDevedor(line.substring(441, 466));
					mpTransacao.setSeloDistribuidor(line.substring(466, 478));
					mpTransacao.setComplementoRegistro(" ");
					mpTransacao.setFinsFAlimentares(line.substring(493, 494));
					mpTransacao.setConvenio(line.substring(494, 495));
					mpTransacao.setEmpresa(line.substring(495, 496));
					mpTransacao.setNumeroSeqRegistro(line.substring(496));
					//
					this.mpTransacaoList.add(mpTransacao);
				}
				//
//				System.out.println("MpRecebimentoBean.receber().line =  ( " + line);

				contador++;
				//
				totalProgresso = totalProgresso + contadorProgresso;
				if (totalProgresso > 20.0) {
					this.progresso = this.progresso + 20;
					totalProgresso = 0.0;
				}
				else
				if (totalProgresso > 10.0) {
					this.progresso = this.progresso + 10;
					totalProgresso = 0.0;
				}
				//
				if (this.progresso > 100) this.progresso = 100;
				//
			}
			//
			scanner.close();
			//
			this.progresso = 100;
			//
			MpFacesUtil.addInfoMessage("RECEBE... completado com sucesso : (Arquivo : " +
							this.mpRemessa.getNomeArquivo() + " / Contador = " +contador + " )");
			//
			this.indGrava = true;
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage("Error : ( e = " + e.toString());
		}
	}

	public void complemento() {
		//
		if (this.indGravaRecebimento) {
	    	MpFacesUtil.addInfoMessage("Gravação do Recebimento ...já foi Efetuada !");
	    	return;
		}
		//
		if (this.mpRemessa.getNomeArquivo().length() == 9)
			assert(true); // nop
		else {
	    	MpFacesUtil.addInfoMessage("Nome Arquivo... inválido !");
	    	return;
		}
		//
		// dataImportarSDF = dd/mm/aaaa
		//                   0123456789
		String dataImportarDMY = this.mpImportarControle.getDataImportarSDF().substring(0, 2) +
						this.mpImportarControle.getDataImportarSDF().substring(3, 5) +
						this.mpImportarControle.getDataImportarSDF().substring(8);
		//
		this.progresso = 0;
		
		Integer contador = 0;

		Double contadorProgresso = (double) (100 / 10); // mpAtoList.size());
		Double totalProgresso = 0.0;
		
		this.mpHeaderList.clear();
		this.mpTraillerList.clear();
		this.mpTransacaoList.clear();
		//
		try (Scanner scanner = new Scanner(this.file)) {
			//
			while (scanner.hasNextLine()) {
				//
				String line = scanner.nextLine();
				
				if (line.length() == 440)
					assert(true); // nop
				else {
			    	MpFacesUtil.addInfoMessage("Tamanho Linha(440)... inválida ! ( Tam.= " +
			    										line.length() + " / Linha = " + line);
			    	return; 
				}
				
				// 01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
				// 0170816SDT1OPETP0000010005000300000003                                                                                                                                                                                                                                                                                                                                                                                                              0001
				// 1                                 PEPSICO DO BRASIL LTDA                       PEPSICO DO BRASIL LTDA                       CH 000202     290616290616   0000000009429200000000094292                      0MINI MERCADO AMERICO DA ROCHA 1497 L ME      CGC00550322000151           RUA AMERICO ROCHA 1497 COMPLE C              21555300RIO DE JANEIRO      RJ010060224PEPSICO DO BRASIL LTDA                       00000088570240899EBRY02689SQN 0002
				// 1                                 PEPSICO DO BRASIL LTDA                       PEPSICO DO BRASIL LTDA                       CH 000263     180616180616   0000000015054900000000150549                      0ANTONIO MARQUES DIAS                         CPF   10377450782           RUA ANDRADE ARAUJO 1150 OSWALDO CRUZ         21341312RIO DE JANEIRO      RJ010060225PEPSICO DO BRASIL LTDA                       00000104250240900EBRY02690BJU 0003
				// 1                                 BANCO BRADESCO S/A                           BANCO BRADESCO S/A                           CCB003559119  190214260716   0000000176193900000001761939                      0BRUNO DE ARAUJO SANTANA                      CPF   05455031759           RUA CANDIDO BENICIO 2610 SACA 03             22733001RIO DE JANEIRO      RJ010060226BANCO BRADESCO S/A                           00000173630240907EBRY02697JXW 0004
				// 917081600018000000000002006780                                                                                                                                                                                                                                                                                                                                                                                                                      0005
				// 01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789

				// ----------------
				// Trata HEADER ...
				// ----------------
				if (line.substring(0, 1).equals("0")) {
					//
					MpHeader mpHeader = new MpHeader();
					
					mpHeader.setMpRemessa(this.mpRemessa);
					//
					if (!line.substring(1, 7).equals(dataImportarDMY)) {
				    	MpFacesUtil.addInfoMessage("HEADER... Datas divergentes... (Dt.Importar = "
				    			+ dataImportarDMY + " / Data Arquivo = " + line.substring(1, 7));
						return;
					}
					//
					mpHeader.setIdenTransRemetente(line.substring(7, 10));
					mpHeader.setIdenTransDestinatario(line.substring(10, 13));
					mpHeader.setIdenTransTipo(line.substring(13, 16));
					mpHeader.setNumeroSeqRemessa(line.substring(16, 22));
					mpHeader.setQtdRegRemessa(line.substring(22, 26));
					mpHeader.setQtdTitRemessa(line.substring(26, 30));
					mpHeader.setQtdIndRemessa(line.substring(30, 34));
					mpHeader.setQtdOrigRemessa(line.substring(34, 38));
					mpHeader.setComplementoRegistro(" ");
					mpHeader.setNumeroSeqRegistro(line.substring(436));
					//
					this.mpHeaderList.add(mpHeader);
				}
				// ------------------
				// Trata TRAILLER ...
				// ------------------
				if (line.substring(0, 1).equals("9")) {
					//
					MpTrailler mpTrailler = new MpTrailler();
					
					mpTrailler.setMpRemessa(this.mpRemessa);
					//
					mpTrailler.setDataDistribuicao(line.substring(1, 7) + "00");
					mpTrailler.setSomaSegQtdRemessa(line.substring(7, 22));
					mpTrailler.setSomaSegValRemessa(line.substring(22, 40));
					mpTrailler.setComplementoRegistro(" ");
					mpTrailler.setNumeroSeqRegistro(line.substring(436));
					//
					this.mpTraillerList.add(mpTrailler);
				}
				// -------------------
				// Trata TRANSACAO ...
				// -------------------
				if (line.substring(0, 1).equals("1")) {
					//
					MpTransacao mpTransacao = new MpTransacao();
					
					mpTransacao.setMpRemessa(this.mpRemessa);
					//
					mpTransacao.setNumeroCodigoPortador(line.substring(1, 4));
					mpTransacao.setAgenciaCodCedente(line.substring(4, 19));
					mpTransacao.setNossoNumero(line.substring(19, 34));
					mpTransacao.setNomeSacador(line.substring(34, 79));
					mpTransacao.setNomeCedFav(line.substring(79, 124));
					mpTransacao.setEspecieTitulo(line.substring(124, 127));
					mpTransacao.setNumeroTitulo(line.substring(127, 138));
					mpTransacao.setDataEmissaoTitulo(line.substring(138, 144) + "00");
					mpTransacao.setDataVencimentoTitulo(line.substring(144, 150) + "00");
					mpTransacao.setTipoMoeda(line.substring(150, 153));
					mpTransacao.setValorTitulo(line.substring(153, 167));
					mpTransacao.setSaldoTitulo(line.substring(167, 181));
					mpTransacao.setPracaPagamento(line.substring(181, 201));
					mpTransacao.setTipoEndosso(line.substring(201, 202));
					mpTransacao.setInformacaoAceite(line.substring(202, 203));
					mpTransacao.setNumeroControleDevedor(line.substring(203, 204));
					mpTransacao.setNomeDevedor(line.substring(204, 249));
					mpTransacao.setTipoIdentDevedor(line.substring(249, 252));
					mpTransacao.setNumeroIdentDevedor(line.substring(252, 266));
					mpTransacao.setDocumentoDevedor(line.substring(266, 277));
					mpTransacao.setEnderecoDevedor(line.substring(277, 322));
					mpTransacao.setCepDevedor(line.substring(322, 330));
					mpTransacao.setCidadeDevedor(line.substring(330, 350));
					mpTransacao.setUfDevedor(line.substring(350, 352));
					mpTransacao.setNumeroCartorio(line.substring(352, 354));
					mpTransacao.setNumeroProtocoloCartorio("000" + line.substring(354, 361));
					mpTransacao.setDataProtocoloCartorio(dataImportarDMY);
					mpTransacao.setNomePortador(line.substring(361, 406));
					mpTransacao.setValorCustasCartorioDist(line.substring(406, 416));
					mpTransacao.setNumeroDistribuicao(line.substring(416, 423));
					mpTransacao.setSeloDistribuidor(line.substring(423, 435));
					mpTransacao.setComplementoRegistro(" ");
					mpTransacao.setConvenio(line.substring(435, 436));
					mpTransacao.setNumeroSeqRegistro(line.substring(436));
					//
					this.mpTransacaoList.add(mpTransacao);
				}
				//
//				System.out.println("MpRecebimentoBean.receber().line =  ( " + line);

				contador++;
				//
				totalProgresso = totalProgresso + contadorProgresso;
				if (totalProgresso > 20.0) {
					this.progresso = this.progresso + 20;
					totalProgresso = 0.0;
				}
				else
				if (totalProgresso > 10.0) {
					this.progresso = this.progresso + 10;
					totalProgresso = 0.0;
				}
				//
				if (this.progresso > 100) this.progresso = 100;
				//
			}
			//
			scanner.close();
			//
			this.progresso = 100;
			//
			MpFacesUtil.addInfoMessage("COMPLEMENTO... completado com sucesso : (Arquivo : " +
							this.mpRemessa.getNomeArquivo() + " / Contador = " +contador + " )");
			//
			this.indGrava = true;
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage("Error : ( e = " + e.toString());
		}
	}
	
	public void receberXML() {
		//
		if (this.indGravaRecebimento) {
	    	MpFacesUtil.addInfoMessage("Gravação do Recebimento ...já foi Efetuada !");
	    	return;
		}		
		// 20160718ATO
		// 012345678901
		if (this.mpRemessa.getNomeArquivo().length() == 11
		&& this.mpRemessa.getNomeArquivo().substring(8, 11).equals("ATO"))
			assert(true); // nop
		else {
	    	MpFacesUtil.addInfoMessage("Nome Arquivo... inválido !");
	    	return;
		}
		//
		this.mpHeaderList.clear();
		this.mpTraillerList.clear();
		this.mpTransacaoList.clear();
		//
		// Lista arquivos da pasta ...
		//
		String arquivoPath = this.mpRemessa.getNomeArquivo().substring(0,4) + File.separator + 
						 this.mpRemessa.getNomeArquivo().substring(4,6) + File.separator +
		 				 this.mpRemessa.getNomeArquivo().substring(6,8);
		
		ClassLoader classLoader = getClass().getClassLoader();
		//
		try {
			this.file  = new File(classLoader.getResource("importacao" + File.separator + 
																		arquivoPath).getFile());			
		}
		catch(Exception e) {
	    	MpFacesUtil.addInfoMessage("XML Pasta/Diretório... não existe ! ( " + arquivoPath);
	    	return;
		}		
		//		
		Integer contador = 0;
		
		this.numSequencial = 0;
		
		File[] arquivos = this.file.listFiles();

		for (File arquivoTmp : arquivos) {
			//
//			System.out.println("MpRecebimentoBean.receberXML() ( " + arquivoPath +
//															File.separator + arquivoTmp.getName());
			contador++;
			//
			this.receberFileXML(arquivoPath, arquivoTmp);	
		}
		//
		this.progresso = 100;
		//
		MpFacesUtil.addInfoMessage("Recebimento.XML completado com sucesso : (Arquivo : " +
						this.mpRemessa.getNomeArquivo() + " / Contador = " + contador + " )");
		//
		this.indGrava = true;
		//
	}
	
	public void receberFileXML(String arquivoPath, File fileX) {
		//
		this.progresso = 0;

		Double contadorProgresso = (double) (100 / 10); // mpAtoList.size());
		Double totalProgresso = 0.0;		
		//
		Boolean indXML = true;
		//
		MpHeader mpHeader = new MpHeader();
		
		MpRemessa mpRemessaX = new MpRemessa();
		mpRemessaX.setMpImportarControle(this.mpRemessa.getMpImportarControle());
		mpRemessaX.setNomeArquivo(fileX.getName());
		mpRemessaX.setProtocoloInicial(this.mpRemessa.getProtocoloInicial());
		mpRemessaX.setProtocoloFinal(this.mpRemessa.getProtocoloFinal());
		
		mpHeader.setMpRemessa(mpRemessaX);
		//
		mpHeader.setNumeroSeqRegistro("0001");
		//
		this.mpHeaderList.add(mpHeader);
		//
//		System.out.println("MpRecebimentoBean.receberFileXML().000 ( " + arquivoPath + " / " + 
//																					fileX.getName());
		//
		try (BufferedReader br = new BufferedReader(new FileReader(fileX.getAbsolutePath()))) {
			// -------------------
			// Trata TRANSACAO ...
			// -------------------
			this.numSequencial++;
			//
			MpTransacao mpTransacao = new MpTransacao();
				
			mpTransacao.setMpRemessa(mpRemessaX);
			//
			String line;
			//
			while ((line = br.readLine()) != null) {
				//
//				System.out.println("MpRecebimentoBean.receberFileXML().001 line = ( " + line +
//																			" / Ind.XML = " + indXML);
				//
				if (line.toUpperCase().indexOf("<XML>") >= 0) indXML = false;
				//
				if (indXML) continue;
				//
				String campo = "";
				//
				if (line.indexOf("<Documento") >= 0) {
					if (null == mpTransacao.getNumeroCodigoPortador()) {
						campo = this.capturaCampo("Numero=", line);
						if (campo.length() >= 0)
							mpTransacao.setNumeroCodigoPortador(campo);
					}
				}
				//
				if (line.indexOf("<Titulo") >= 0) {
					if (null == mpTransacao.getAgenciaCodCedente()) {
						campo = this.capturaCampo("AgenciasCedenteDepositaria=", line);
						if (campo.length() >= 0)
							mpTransacao.setAgenciaCodCedente(campo);
					}
					if (null == mpTransacao.getNossoNumero()) {
						campo = this.capturaCampo("NumeroControlePortador=", line);
						if (campo.length() >= 0)
							mpTransacao.setNossoNumero(campo);
					}
					if (line.indexOf("Especie=\"20\"") >= 0) {
						MpEspecie mpEspecie = mpEspecies.porCodigo("20"); // ????
						if (null == mpEspecie)
							mpTransacao.setEspecieTitulo(" ");
						else
							mpTransacao.setEspecieTitulo(mpEspecie.getDescricao());
					}
					if (null == mpTransacao.getNumeroTitulo()) {
						campo = this.capturaCampo("NumeroTitulo=", line);
						if (campo.length() >= 0)
							mpTransacao.setNumeroTitulo(campo);
					}
					if (null == mpTransacao.getDataEmissaoTitulo()) {
						campo = this.capturaCampo("DataEmissao=", line);
						if (campo.length() >= 0)
							mpTransacao.setDataEmissaoTitulo(campo);
					}
					if (null == mpTransacao.getDataVencimentoTitulo()) {
						campo = this.capturaCampo("DataVencimento=", line);
						if (campo.length() >= 0)
							if (line.indexOf("DataVencimento=\"99/99/9999\"") >= 0
							||  line.indexOf("DataVencimento=\"  /  /    \"") >= 0)
								mpTransacao.setDataVencimentoTitulo(mpTransacao.
																			getDataEmissaoTitulo());
							else
								mpTransacao.setDataVencimentoTitulo(campo);
					}
					if (null == mpTransacao.getTipoMoeda()) {
						campo = this.capturaCampo("Moeda=", line);
						if (campo.length() >= 0)
							mpTransacao.setTipoMoeda(campo);
					}
					if (null == mpTransacao.getValorTitulo()) {
						campo = this.capturaCampo("ValorTitulo=", line);
						if (campo.length() >= 0)
							mpTransacao.setValorTitulo(campo);
					}
					if (null == mpTransacao.getSaldoTitulo()) {
						campo = this.capturaCampo("ValorProtestar=", line);
						if (campo.length() >= 0)
							mpTransacao.setSaldoTitulo(campo);
					}
					if (null == mpTransacao.getSaldoTitulo()) {
						campo = this.capturaCampo("Praca=", line);
						if (campo.length() >= 0)
							mpTransacao.setSaldoTitulo(campo);
					}
					if (null == mpTransacao.getTipoEndosso()) {
						campo = this.capturaCampo("Endosso=", line);
						if (campo.length() >= 0)
							mpTransacao.setTipoEndosso(campo);
					}
					if (null == mpTransacao.getInformacaoAceite()) {
						campo = this.capturaCampo("Aceite=", line);
						if (campo.length() >= 0)
							mpTransacao.setInformacaoAceite(campo);
					}
				}

				if (line.indexOf("<Participante") >= 0) {
					if (line.indexOf("Tipo=\"35\"") >= 0)
						mpTransacao.setNomeSacador(this.capturaCampo("Nome=", line));
					if (line.indexOf("Tipo=\"36\"") >= 0)
						mpTransacao.setNomeCedFav(" ");
					if (line.indexOf("Tipo=\"8\"") >= 0) {
						if (null == mpTransacao.getNumeroControleDevedor())
							mpTransacao.setNumeroControleDevedor("1");
						if (null == mpTransacao.getNomeDevedor())
							mpTransacao.setNomeDevedor(this.capturaCampo("Nome=", line));
						if (null == mpTransacao.getTipoIdentDevedor())
							if (line.indexOf("TipoPessoa=\"J\"") >= 0)
								mpTransacao.setTipoIdentDevedor("CGC");
							else
								mpTransacao.setTipoIdentDevedor("CNPJ");
						//
						if (null == mpTransacao.getEnderecoDevedor())
							mpTransacao.setEnderecoDevedor(this.capturaCampo("Logradouro=", line));
						if (null == mpTransacao.getCepDevedor())
							mpTransacao.setCepDevedor(this.capturaCampo("CEP=", line));
						if (null == mpTransacao.getCidadeDevedor())
							mpTransacao.setCidadeDevedor(this.capturaCampo("Cidade=", line));
						if (null == mpTransacao.getUfDevedor())
							mpTransacao.setUfDevedor(this.capturaCampo("UF=", line));
						if (null == mpTransacao.getNumeroCartorio())
							mpTransacao.setNumeroCartorio("????"); // ?????
						//
						if (null == mpTransacao.getBairroDevedor())
							mpTransacao.setBairroDevedor(this.capturaCampo("Bairro=", line));
					}
					if (null == mpTransacao.getInformacaoAceite()) {
						campo = this.capturaCampo("CPFCNPJ=", line);
						if (campo.length() >= 0)
							mpTransacao.setInformacaoAceite(campo);
					}
					//
					if (null == mpTransacao.getDocumentoDevedor())
						mpTransacao.setDocumentoDevedor("");
					//
					if (null == mpTransacao.getNumeroProtocoloCartorio()) {
						campo = this.capturaCampo("NumeroProtocolo=", line);
						if (campo.length() >= 0)
							mpTransacao.setNumeroProtocoloCartorio(StringUtils.leftPad(
																					campo, 6, "0"));
					}
					//
					if (null == mpTransacao.getDataProtocoloCartorio())
						mpTransacao.setDataProtocoloCartorio(this.mpImportarControle.
															getDataImportarSDF().replaceAll("/", ""));
					//
					if (line.indexOf("Tipo=\"34\"") >= 0)
						mpTransacao.setNomePortador(this.capturaCampo("Nome=", line));

					if (null == mpTransacao.getValorCustasCartorioDist()) {
						campo = this.capturaCampo("ValorTotal=", line);
						if (campo.length() >= 0)
							mpTransacao.setValorCustasCartorioDist(StringUtils.leftPad(
																					campo, 14, "0"));
					}
					if (null == mpTransacao.getNumeroDistribuicao()) {
						campo = this.capturaCampo("NumeroRegistro=", line);
						if (campo.length() >= 0)
							mpTransacao.setNumeroDistribuicao(campo);
					}
					//
					if (null == mpTransacao.getSeloDistribuidor())
						mpTransacao.setSeloDistribuidor("");
					if (null == mpTransacao.getComplementoRegistro())
						mpTransacao.setComplementoRegistro("");
					if (null == mpTransacao.getFinsFAlimentares())
						mpTransacao.setFinsFAlimentares("");
					//
					if (null == mpTransacao.getConvenio()) {
						campo = this.capturaCampo("Convenio=", line);
						if (campo.length() >= 0)
							mpTransacao.setConvenio(campo);
					}
					//
					if (null == mpTransacao.getEmpresa())
						mpTransacao.setEmpresa("");
					if (null == mpTransacao.getNumeroSeqRegistro())
						mpTransacao.setNumeroSeqRegistro(this.numSequencial + "");
				}
				//
				System.out.println("MpRecebimentoBean.receberFileXML().002 line = ( " + line);
				//
				totalProgresso = totalProgresso + contadorProgresso;
				if (totalProgresso > 20.0) {
					this.progresso = this.progresso + 20;
					totalProgresso = 0.0;
				} else if (totalProgresso > 10.0) {
					this.progresso = this.progresso + 10;
					totalProgresso = 0.0;
				}
				//
				if (this.progresso > 100)
					this.progresso = 100;
				//
			}
			//
			this.mpTransacaoList.add(mpTransacao);
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
	}
	
    public String capturaCampo(String campo, String line) {
    	//
    	String conteudo = "";
    	//
		if (line.indexOf(campo) >= 0) {
			//
			Integer iniX = line.indexOf(campo) + campo.length() + 1;
			Integer fimX = line.indexOf("\"", iniX + 1);

			conteudo = line.substring(iniX,	fimX);
			//
			System.out.println("MpRecebimentoBean.capturaCampo() - (Campo = " + campo + 
												" /iniX = " + iniX + " /fimX = " + fimX + 
												" /conteudo = " + conteudo + " /line = " + line);
		}
    	//
    	return conteudo;
    }
    
	
	public void gravaRecebimento() {
		//
		if (this.indGravaRecebimento) {
	    	MpFacesUtil.addInfoMessage("Gravação do Recebimento ...já foi Efetuada !");
	    	return;
		}		
		// Trata Controle ...
		this.mpImportarControle = this.mpImportarControleService.salvar(this.mpImportarControle);
		// Trata Remessa...
		this.mpRemessa.setMpImportarControle(this.mpImportarControle);
		this.mpRemessa = this.mpRemessaService.salvar(this.mpRemessa);
		// Trata Header ...
		for (MpHeader mpHeader : this.mpHeaderList) {
			mpHeader.setMpRemessa(this.mpRemessa);

			if (null == mpHeader.getAgenciaCentralizadora()) mpHeader.setAgenciaCentralizadora(
																					"??????");
			if (null == mpHeader.getVersaoLayout()) mpHeader.setVersaoLayout("???");
			//
			mpHeader = this.mpHeaderService.salvar(mpHeader);
		}
		// Trata Transacao ...
		for (MpTransacao mpTransacao : this.mpTransacaoList) {
			mpTransacao.setMpRemessa(this.mpRemessa);
			// Trata nulos ??? ...
			mpTransacao = this.TrataNullMpTransacao(mpTransacao);
			//
			mpTransacao = this.mpTransacaoService.salvar(mpTransacao);
		}
		// Trata Trailler ...
		for (MpTrailler mpTrailler : this.mpTraillerList) {
			mpTrailler.setMpRemessa(this.mpRemessa);
			//
			mpTrailler = this.mpTraillerService.salvar(mpTrailler);
		}		
		//
		MpFacesUtil.addInfoMessage("Gravação Recebimento Efetuado !");
		//
		this.indGravaRecebimento = true;
    }
    	
    public MpTransacao TrataNullMpTransacao(MpTransacao mpTransacao) {
    	//
		if (null == mpTransacao.getBairroDevedor()) mpTransacao.setBairroDevedor("?");
		if (null == mpTransacao.getCodigoIrregularidade()) mpTransacao.
															setCodigoIrregularidade("?");
		if (null == mpTransacao.getDataOcorrenciaCartorio()) mpTransacao.
															setDataOcorrenciaCartorio("?");
		if (null == mpTransacao.getDeclaracaoPortador()) mpTransacao.setDeclaracaoPortador("?");
		if (null == mpTransacao.getDocumentoSacador()) mpTransacao.setDocumentoSacador("?");
		if (null == mpTransacao.getEnderecoSacador()) mpTransacao.setEnderecoSacador("?");
		if (null == mpTransacao.getCepSacador()) mpTransacao.setCepSacador("?");
		if (null == mpTransacao.getCidadeSacador()) mpTransacao.setCidadeSacador("?");
		if (null == mpTransacao.getUfSacador()) mpTransacao.setUfSacador("?");
		if (null == mpTransacao.getTipoOcorrencia()) mpTransacao.setTipoOcorrencia("?");
		if (null == mpTransacao.getValorCustasCartorio()) mpTransacao.setValorCustasCartorio("?");
		if (null == mpTransacao.getDataOcorrenciaCartorio()) mpTransacao.
																setDataOcorrenciaCartorio("?");
		if (null == mpTransacao.getValorCustasCartorioDist()) mpTransacao.
																setValorCustasCartorioDist("?");
		if (null == mpTransacao.getNumeroDistribuicao()) mpTransacao.setNumeroDistribuicao("?");
		if (null == mpTransacao.getComplementoRegistro()) mpTransacao.setComplementoRegistro("?");
		if (null == mpTransacao.getSeloDistribuidor()) mpTransacao.setSeloDistribuidor("?");
		if (null == mpTransacao.getFinsFAlimentares()) mpTransacao.setFinsFAlimentares("?");
		if (null == mpTransacao.getConvenio()) mpTransacao.	setConvenio("?");
		if (null == mpTransacao.getEmpresa()) mpTransacao.setEmpresa("?");		
    	//
    	return mpTransacao;
    }
	
	// ---
	
    public void onComplete() {
		MpFacesUtil.addInfoMessage("Progresso Completado !");
    }
     
    public void cancel() {
        progresso = null;
    }	

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	

    // ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public MpRemessa getMpRemessa() { return mpRemessa; }
	public void setMpRemessa(MpRemessa mpRemessa) { this.mpRemessa = mpRemessa; }
	
	public MpImportarControle getMpImportarControle() { return mpImportarControle; }
	public void setMpImportarControle(MpImportarControle mpImportarControle) { 
													this.mpImportarControle = mpImportarControle; }
	
	public Integer getProgresso() { return progresso; }
	public void setProgresso(Integer progresso) { this.progresso = progresso; }
	
	public List<MpHeader> getMpHeaderList() { return mpHeaderList; }
	public void setMpHeaderList(List<MpHeader> mpHeaderList) { this.mpHeaderList = mpHeaderList; }
	
	public List<MpTrailler> getMpTraillerList() { return mpTraillerList; }
	public void setMpTraillerList(List<MpTrailler> mpTraillerList) { 
															this.mpTraillerList = mpTraillerList; }
	
	public List<MpTransacao> getMpTransacaoList() { return mpTransacaoList; }
	public void setMpTransacaoList(List<MpTransacao> mpTransacaoList) { 
															this.mpTransacaoList = mpTransacaoList; }
	
	public Boolean getIndReceber() { return indReceber; }
	public void setIndReceber(Boolean indReceber) { this.indReceber = indReceber; }
	
	public Boolean getIndComplemento() { return indComplemento; }
	public void setIndComplemento(Boolean indComplemento) { this.indComplemento = indComplemento; }
	
	public Boolean getIndXML() { return indXML; }
	public void setIndXML(Boolean indXML) { this.indXML = indXML; }
	
	public Boolean getIndCancela() { return indCancela; }
	public void setIndCancela(Boolean indCancela) { this.indCancela = indCancela; }
	
	public Boolean getIndGrava() { return indGrava; }
	public void setIndGrava(Boolean indGrava) { this.indGrava = indGrava; }
	
}