package com.mpxds.mpbasic.controller.sisJuri;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaJuridica;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaSJ;
import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.model.sisJuri.MpProcessoAndamento;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpProcessoFilter;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpTabelaInternaSJFilter;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaJuridicas;
//import com.mpxds.mpbasic.repository.sisJuri.MpPessoaSJs;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessoAndamentos;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessos;
import com.mpxds.mpbasic.service.sisJuri.MpTabelaInternaSJServiceX;
import com.mpxds.mpbasic.service.sisJuri.MpClienteSJService;
import com.mpxds.mpbasic.service.sisJuri.MpPessoaFisicaService;
import com.mpxds.mpbasic.service.sisJuri.MpPessoaJuridicaService;
import com.mpxds.mpbasic.service.sisJuri.MpProcessoAndamentoService;
import com.mpxds.mpbasic.service.sisJuri.MpProcessoService;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpCargaDadosSisJuriBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	private String tenantIdCarga = "11"; // Login.Reginaldo

	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpClienteSJs mpClienteSJs;

	@Inject
	private MpPessoaFisicas mpPessoaFisicas;

	@Inject
	private MpPessoaJuridicas mpPessoaJuridicas;

	@Inject
	private MpProcessos mpProcessos;

	@Inject
	private MpProcessoAndamentos mpProcessoAndamentos;

//	@Inject
//	private MpPessoaSJs mpPessoaSJs;

	@Inject
	private MpTabelaInternaSJServiceX mpTabelaInternaSJServiceX;

	@Inject
	private MpClienteSJService mpClienteSJService;

	@Inject
	private MpPessoaFisicaService mpPessoaFisicaService;

	@Inject
	private MpPessoaJuridicaService mpPessoaJuridicaService;

	@Inject
	private MpProcessoService mpProcessoService;

	@Inject
	private MpProcessoAndamentoService mpProcessoAndamentoService;

	// ---	
	
	private Boolean indCargaTabelaInternaSJ = false;
	private Integer contCargaTabelaInternaSJ = 0;

	private Boolean indCargaClienteSJ = false;
	private Integer contCargaClienteSJ = 0;

	private Boolean indCargaPessoaFisica = false;
	private Integer contCargaPessoaFisica = 0;

	private Boolean indCargaPessoaJuridica = false;
	private Integer contCargaPessoaJuridica = 0;

	private Boolean indCargaProcesso = false;
	private Integer contCargaProcesso = 0;

	private Boolean indCargaProcessoAndamento = false;
	private Integer contCargaProcessoAndamento = 0;

	// ---
	
	public void carregar() {
		//
		if (indCargaTabelaInternaSJ)
			this.trataTabelaInternaSJ();
		if (indCargaClienteSJ)
			this.trataClienteSJ();
		if (indCargaPessoaFisica)
			this.trataPessoaFisica();
		if (indCargaPessoaJuridica)
			this.trataPessoaJuridica();
		if (indCargaProcesso)
			this.trataProcesso();
		if (indCargaProcessoAndamento)
			this.trataProcessoAndamento();
		//
		MpFacesUtil.addErrorMessage("Carga Efetuada ! ( " + 
										" \nTabela InternaSJ = " + this.indCargaTabelaInternaSJ +
										" Qtd. = " + this.contCargaTabelaInternaSJ +
										" / \nClienteSJ = " + this.indCargaClienteSJ +
										" Qtd. = " + this.contCargaClienteSJ +
										" / \nPessoaFisica = " + this.indCargaPessoaFisica +
										" Qtd. = " + this.contCargaPessoaFisica +
										" / \nPessoaJuridica = " + this.indCargaPessoaJuridica +
										" Qtd. = " + this.contCargaPessoaJuridica +
										" / \nProcesso = " + this.indCargaProcesso +
										" Qtd. = " + this.contCargaProcesso +
										" / \nProcessoAndamento = " + this.indCargaProcessoAndamento +
										" Qtd. = " + this.contCargaProcessoAndamento);
	}
	
	public void trataTabelaInternaSJ() {
		//
    	this.contCargaTabelaInternaSJ = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
						
			resultSet = statement.executeQuery("SELECT t.\"ID\" AS id, t.\"NUMERO\" AS numero," + 
											   " t.\"CODIGO\" AS codigo, t.\"DESCRICAO\" AS descricao," +
											   " t.\"INDPAI\" AS indpai, t.\"INDFILHA\" AS indfilha," +
											   " t.\"PAIID\" AS paiid" +
			        						   " FROM \"TABELA_INTERNA\" t");
			//
			Long id = 0L;
			MpTipoTabelaInternaSJ mpNumero;
			String codigo = "";
			String descricao = "";
			Long paiId = 0L;
			//
			while (resultSet.next()) {
				// 
////				if (resultSet.getString("numero").trim().equals("0015")  // Banco
//				if (resultSet.getString("numero").trim().equals("1022")  // AndamentoTipo
//				||  resultSet.getString("numero").trim().equals("1040")  // AtoPraticado
//				||  resultSet.getString("numero").trim().equals("1060")  // Orgão Número
//				||  resultSet.getString("numero").trim().equals("1070")  // Andamento Detalhamento
//				||  resultSet.getString("numero").trim().equals("1008")  // Comarca
//				||  resultSet.getString("numero").trim().equals("1041")  // Resultado Audiência
//			 	||  resultSet.getString("numero").trim().equals("1042")) // Comarca Cartorio
//					assert(true); // nop!
//				else
//					continue;
				//
				if (resultSet.getString("numero").trim().equals("1014")    // Area 
				||  resultSet.getString("codigo").indexOf("*******") >= 0) // Header
					continue; // Ignora 
				//
				id = Long.parseLong(resultSet.getString("id"));
				mpNumero = MpTipoTabelaInternaSJ.valueOf("TAB_" + resultSet.getString("numero"));
				codigo = resultSet.getString("codigo");
				descricao = resultSet.getString("descricao");
				//
				if (null == resultSet.getString("paiid"))
					paiId = 0L;
				else
					paiId = Long.parseLong(resultSet.getString("paiid"));
				
				this.contCargaTabelaInternaSJ++;

//				MpTabelaInternaSJ mpTabelaInternaSJ = mpTabelaInternaSJs.porIdCarga(id);
//				if (null == mpTabelaInternaSJ) { 
//					mpTabelaInternaSJ = new MpTabelaInternaSJ();
//				}
				MpTabelaInternaSJ mpTabelaInternaSJ = new MpTabelaInternaSJ();
				
				mpTabelaInternaSJ.setTenantId(tenantIdCarga);
				mpTabelaInternaSJ.setIdCarga(id);
				mpTabelaInternaSJ.setMpTipoTabelaInternaSJ(mpNumero);
				mpTabelaInternaSJ.setCodigo(codigo);
				mpTabelaInternaSJ.setDescricao(descricao);
				
				if (paiId != 0L) {
					MpTabelaInternaSJ mpPai = mpTabelaInternaSJs.porIdCarga(paiId);;

					mpTabelaInternaSJ.setMpPai(mpPai);
				}
				
				//
//				try {
					mpTabelaInternaSJ = mpTabelaInternaSJServiceX.salvar(mpTabelaInternaSJ);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpTabelaInternaSJ - Salvar.Erro ( e = " + e.toString() +
//																										" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		// Trata carga -> Pai x Filhas !
		MpTabelaInternaSJFilter mpFilter = new MpTabelaInternaSJFilter();
		
		List<MpTabelaInternaSJ> mpPaiList = mpTabelaInternaSJs.filtrados(mpFilter);
		
		for (MpTabelaInternaSJ mpPaiX : mpPaiList) {
			//
			List<MpTabelaInternaSJ> mpFilhaList = mpTabelaInternaSJs.mpFilhaList(mpPaiX);
			
			if (mpFilhaList.size() > 0) {
				//
				mpPaiX.setMpFilhas(mpFilhaList);
				
				mpTabelaInternaSJs.guardar(mpPaiX);
				//
				MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.trataTabelaInternaSJ - Pai X Filha = ( " +
							mpPaiX.getMpTipoTabelaInternaSJ().getDescricao() + " / " + mpPaiX.getCodigo() + " / " + 
							mpFilhaList.size());
			}
		}
	}
	
	public void trataClienteSJ() {
		//
    	this.contCargaClienteSJ = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT c.\"ID\" AS id, p.\"NOME\" AS nome, p.\"CPFCNPJ\" AS cpfcnpj," + 
					" p.\"INDPARTECONTRARIA\" AS indpartecontraria, p.\"EMAIL\" AS email," +
			        " p.\"TELRES\" AS telres, c.\"OBS\" AS obs" +
			        " FROM \"CLIENTE\" c, \"PESSOA\" p WHERE p.id = c.pessoaid");
			//
			Long id = 0L;
			String nome = "";
			String cpfCnpj = "";
			Boolean indParteContraria = false;
			String email = "";
			String telRes = "";
			String obs = "";
			
			MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();
			//
			while (resultSet.next()) {
				//
				id = Long.parseLong(resultSet.getString("id"));
				nome = resultSet.getString("nome");
				if (null == resultSet.getString("cpfcnpj")) cpfCnpj = "";
				else cpfCnpj = resultSet.getString("cpfcnpj"); 
				if (null == resultSet.getString("indpartecontraria")) indParteContraria = false;
				else indParteContraria = Boolean.valueOf(resultSet.getString("indpartecontraria"));
				email = resultSet.getString("email");
				telRes = resultSet.getString("telres");
				obs = resultSet.getString("obs");
				
				this.contCargaClienteSJ++;

//				MpClienteSJ mpClienteSJ = mpClienteSJs.porIdCarga(id);
//				if (null == mpClienteSJ) { 
				MpClienteSJ mpClienteSJ = new MpClienteSJ();
					
					mpEnderecoLocal = new MpEnderecoLocal();
					mpEnderecoLocal.setLogradouro("");
					mpEnderecoLocal.setNumero("");
					mpEnderecoLocal.setComplemento("");
					mpEnderecoLocal.setCidade("");
					mpEnderecoLocal.setBairro("");
					mpEnderecoLocal.setCep("");
					mpEnderecoLocal.setMpEstadoUF(MpEstadoUF.XX);
					
					mpClienteSJ.setMpEnderecoLocal(mpEnderecoLocal);						
//				}
				
				mpClienteSJ.setTenantId(tenantIdCarga);
				mpClienteSJ.setIdCarga(id);
				mpClienteSJ.setNome(nome);
				mpClienteSJ.setCpfCnpj(cpfCnpj);
				mpClienteSJ.setNome(nome);
				mpClienteSJ.setIndParteContraria (indParteContraria );
				mpClienteSJ.setEmail(email);
				mpClienteSJ.setTelefone(telRes);
				mpClienteSJ.setObservacao(obs);
				
//				try {
					mpClienteSJ = mpClienteSJService.salvar(mpClienteSJ);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpClienteSJ - Salvar.Erro ( e = " + e.toString() +
//																										" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public void trataPessoaFisica() {
		//
    	this.contCargaPessoaFisica = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT p.\"ID\" AS id, p.\"NOME\" AS nome, pf.\"CPF\" AS cpf," + 
					" p.\"INDPARTECONTRARIA\" AS indpartecontraria, p.\"OAB\" AS oab, p.\"OABUFID\" AS oabufid," +
			        " p.\"EMAIL\" AS email, p.\"BANCOID\" AS bancoid, p.\"AGENCIA\" AS agencia," +
					" p.\"CONTA\" AS conta, p.\"TELRES\" AS telres, p.\"TELTRAB\" AS teltrab, p.\"TELCEL\" AS telcel," +
					" p.\"WEBPAGE\" AS webpage, p.\"OBS\" AS obs" +
			        " FROM \"PESSOA_FISICA\" pf, \"PESSOA\" p" +
        			" WHERE p.id = pf.pessoa_fisica_id");
			//
			Long id = 0L;
			String nome = "";
			String cpf = "";
			Boolean indParteContraria = false;
			String oab = "";
			Long oabUfId = 0L;
			String email = "";
			Long bancoId = 0L;
			String agencia = "";
			String conta = "";
			String telRes = "";
			String telTrab = "";
			String telCel = "";
			String webPage = "";
			String obs = "";
			
			MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();
			//
			while (resultSet.next()) {
				//
				id = Long.parseLong(resultSet.getString("id"));
				nome = resultSet.getString("nome");
				if (null == resultSet.getString("cpf")) cpf = "";
				else cpf = resultSet.getString("cpf"); 
				if (null == resultSet.getString("indpartecontraria")) indParteContraria = false;
				else indParteContraria = Boolean.valueOf(resultSet.getString("indpartecontraria"));
				if (null == resultSet.getString("oab")) oab = "null";
				else {
					oab = resultSet.getString("oab").trim();
					if (oab.isEmpty()) oab = "null.";
				}
				if (null == resultSet.getString("oabufid")) oabUfId = null;
				else oabUfId = Long.parseLong(resultSet.getString("oabufid")); 
				if (null == resultSet.getString("email")) email = "null";
				else {
					email = resultSet.getString("email");
					if (email.isEmpty()) email = "null.";
				}
				if (null == resultSet.getString("bancoid")) bancoId = null;
				else bancoId = Long.parseLong(resultSet.getString("bancoid")); 
				agencia = resultSet.getString("agencia");
				conta = resultSet.getString("conta");
				if (null == resultSet.getString("telres")) telRes = "";
				else telRes = resultSet.getString("telres");
				if (null == resultSet.getString("teltrab")) telTrab = "";
				else telTrab = resultSet.getString("teltrab");
				if (null == resultSet.getString("telcel")) telCel = "";
				else telCel = resultSet.getString("telcel");
				webPage = resultSet.getString("webpage");
				obs = resultSet.getString("obs");
				
				this.contCargaPessoaFisica++;

//				MpPessoaFisica mpPessoaFisica = mpPessoaFisicas.porIdCarga(id);
//				if (null == mpPessoaFisica) { 
					MpPessoaFisica mpPessoaFisica = new MpPessoaFisica();
					
					mpEnderecoLocal = new MpEnderecoLocal();
					mpEnderecoLocal.setLogradouro("");
					mpEnderecoLocal.setNumero("");
					mpEnderecoLocal.setComplemento("");
					mpEnderecoLocal.setCidade("");
					mpEnderecoLocal.setBairro("");
					mpEnderecoLocal.setCep("");
					mpEnderecoLocal.setMpEstadoUF(MpEstadoUF.XX);
					
					mpPessoaFisica.setMpEnderecoLocal(mpEnderecoLocal);					
//				}

				mpPessoaFisica.setTenantId(tenantIdCarga);
				mpPessoaFisica.setIdCarga(id);
				mpPessoaFisica.setNome(nome);
				mpPessoaFisica.setCpf(cpf);
				mpPessoaFisica.setIndParteContraria(indParteContraria);
				mpPessoaFisica.setOab(oab);
				if (null == oabUfId) assert(true);
				else {
					MpTabelaInternaSJ mpOabUf = mpTabelaInternaSJs.porId(oabUfId);
					if (null == mpOabUf) assert(true);
					else {
						MpEstadoUF mpEstadoUf;
						try {
							mpEstadoUf = MpEstadoUF.valueOf(mpOabUf.getCodigo().trim());
						} catch (Exception e) {
							mpEstadoUf = MpEstadoUF.XX;
						}
						mpPessoaFisica.setMpOabUF(mpEstadoUf);
					}
				}
				mpPessoaFisica.setEmail(email);
				if (null == bancoId) assert(true);
				else {
					MpTabelaInternaSJ mpBanco = mpTabelaInternaSJs.porId(bancoId);
					if (null == mpBanco) assert(true);
					else mpPessoaFisica.setMpBanco(mpBanco);
				}
				mpPessoaFisica.setBancoAgencia(agencia);
				mpPessoaFisica.setBancoConta(conta);
				mpPessoaFisica.setTelefone(telRes + " " + telTrab + " " + telCel);
				mpPessoaFisica.setWebPage(webPage);
				mpPessoaFisica.setObservacao(obs);
				
//				try {
					mpPessoaFisica = mpPessoaFisicaService.salvar(mpPessoaFisica);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpPessoaFisica - Salvar.Erro ( e = " + e.toString() +
//																										" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public void trataPessoaJuridica() {
		//
    	this.contCargaPessoaJuridica = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT p.\"ID\" AS id, p.\"NOME\" AS nome, pj.\"CNPJ\" AS cnpj," + 
					" p.\"INDPARTECONTRARIA\" AS indpartecontraria, p.\"OAB\" AS oab, p.\"OABUFID\" AS oabufid," +
			        " p.\"EMAIL\" AS email, p.\"BANCOID\" AS bancoid, p.\"AGENCIA\" AS agencia," +
					" p.\"CONTA\" AS conta, p.\"TELRES\" AS telres, p.\"TELTRAB\" AS teltrab, p.\"TELCEL\" AS telcel," +
					" p.\"WEBPAGE\" AS webpage, p.\"OBS\" AS obs, pj.\"RAMOATIVIDADE\" AS ramoAtividade," +
					" pj.\"RESPONSAVEL\" AS responsavel, pj.\"INDRESPONSAVEL\" AS indresponsavel," +
					" pj.\"RAZAOSOCIAL\" AS razaosocial" +
			        " FROM \"PESSOA_JURIDICA\" pj, \"PESSOA\" p" +
        			" WHERE p.id = pj.pessoa_juridica_id");
			//
			Long id = 0L;
			String nome = "";
			String cnpj = "";
			Boolean indParteContraria = false;
			String oab = "";
			Long oabUfId = 0L;
			String email = "";
			Long bancoId = 0L;
			String agencia = "";
			String conta = "";
			String telRes = "";
			String telTrab = "";
			String telCel = "";
			String webPage = "";
			String obs = "";
			
			String ramoAtividade = "";
			String responsavel = "";
			Boolean indResponsavel = false;
			String razaoSocial = "";
			
			MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();
			//
			while (resultSet.next()) {
				//
				id = Long.parseLong(resultSet.getString("id"));
				nome = resultSet.getString("nome");
				if (null == resultSet.getString("cnpj")) cnpj = "";
				else cnpj = resultSet.getString("cnpj"); 
				if (null == resultSet.getString("indpartecontraria")) indParteContraria = false;
				else indParteContraria = Boolean.valueOf(resultSet.getString("indpartecontraria"));
				if (null == resultSet.getString("oab")) oab = "null";
				else {
					oab = resultSet.getString("oab").trim();
					if (oab.isEmpty()) oab = "null.";
				}
				if (null == resultSet.getString("oabufid")) oabUfId = null;
				else oabUfId = Long.parseLong(resultSet.getString("oabufid")); 
				if (null == resultSet.getString("email")) email = "null";
				else {
					email = resultSet.getString("email");
					if (email.isEmpty()) email = "null.";
				}
				if (null == resultSet.getString("bancoid")) bancoId = null;
				else bancoId = Long.parseLong(resultSet.getString("bancoid")); 
				agencia = resultSet.getString("agencia");
				conta = resultSet.getString("conta");
				if (null == resultSet.getString("telres")) telRes = "";
				else telRes = resultSet.getString("telres");
				if (null == resultSet.getString("teltrab")) telTrab = "";
				else telTrab = resultSet.getString("teltrab");
				if (null == resultSet.getString("telcel")) telCel = "";
				else telCel = resultSet.getString("telcel");
				webPage = resultSet.getString("webpage");
				obs = resultSet.getString("obs");

				ramoAtividade = resultSet.getString("ramoatividade");
				responsavel = resultSet.getString("responsavel");
				if (null == resultSet.getString("indresponsavel")) indResponsavel = false;
				else indResponsavel = Boolean.valueOf(resultSet.getString("indresponsavel"));
				razaoSocial = resultSet.getString("razaosocial");
				
				this.contCargaPessoaJuridica++;

//				MpPessoaJuridica mpPessoaJuridica = mpPessoaJuridicas.porIdCarga(id);
//				if (null == mpPessoaJuridica) { 
					MpPessoaJuridica mpPessoaJuridica = new MpPessoaJuridica();
					
					mpEnderecoLocal = new MpEnderecoLocal();
					mpEnderecoLocal.setLogradouro("");
					mpEnderecoLocal.setNumero("");
					mpEnderecoLocal.setComplemento("");
					mpEnderecoLocal.setCidade("");
					mpEnderecoLocal.setBairro("");
					mpEnderecoLocal.setCep("");
					mpEnderecoLocal.setMpEstadoUF(MpEstadoUF.XX);
					
					mpPessoaJuridica.setMpEnderecoLocal(mpEnderecoLocal);					
//				}
				
				mpPessoaJuridica.setTenantId(tenantIdCarga);
				mpPessoaJuridica.setIdCarga(id);
				mpPessoaJuridica.setNome(nome);
				mpPessoaJuridica.setCnpj(cnpj);
				mpPessoaJuridica.setIndParteContraria(indParteContraria);
				mpPessoaJuridica.setOab(oab);
				if (null == oabUfId) assert(true);
				else {
					MpTabelaInternaSJ mpOabUf = mpTabelaInternaSJs.porId(oabUfId);
					if (null == mpOabUf) assert(true);
					else {
						MpEstadoUF mpEstadoUf;
						try {
							mpEstadoUf = MpEstadoUF.valueOf(mpOabUf.getCodigo().trim());
						} catch (Exception e) {
							mpEstadoUf = MpEstadoUF.XX;
						}
						mpPessoaJuridica.setMpOabUF(mpEstadoUf);
					}
				}
				mpPessoaJuridica.setEmail(email);
				if (null == bancoId) assert(true);
				else {
					MpTabelaInternaSJ mpBanco = mpTabelaInternaSJs.porId(bancoId);
					if (null == mpBanco) assert(true);
					else mpPessoaJuridica.setMpBanco(mpBanco);
				}
				mpPessoaJuridica.setBancoAgencia(agencia);
				mpPessoaJuridica.setBancoConta(conta);
				mpPessoaJuridica.setTelefone(telRes + " " + telTrab + " " + telCel);
				mpPessoaJuridica.setWebPage(webPage);
				mpPessoaJuridica.setObservacao(obs);
				
				mpPessoaJuridica.setRamoAtividade(ramoAtividade);
				mpPessoaJuridica.setResponsavel(responsavel);
				mpPessoaJuridica.setIndResponsavel(indResponsavel);
				mpPessoaJuridica.setRazaoSocial(razaoSocial);
				//	
//				try {
					mpPessoaJuridica = mpPessoaJuridicaService.salvar(mpPessoaJuridica);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpPessoaJuridica - Salvar.Erro ( e = " + e.toString() + 
//																											" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public void trataProcesso() {
		//
    	this.contCargaProcesso = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT p.\"ID\" AS id, p.\"DATA_CADASTRO\" AS datacadastro," + 
					" p.\"PROCESSOCODIGO\" AS processocodigo, p.\"AUTORTXT\" AS autortxt," + 
					" p.\"PARTECONTRARIAID\" AS partecontrariaid, p.\"CLIENTEID\" AS clienteid," + 
					" p.\"ADVOGADORESPID\" AS advogadorespid, p.\"COMARCAID\" AS comarcaid," + 
					" p.\"LOCALTRAMITEID\" AS localtramiteid, p.\"PASTACLIENTE\" AS pastacliente" +
			        " FROM \"PROCESSO\" p");
			//
			Long id = 0L;
			Date dataCadastro = new Date();;
			String processoCodigo = "";
			String autorTxt = "";
			Long parteContrariaId = 0L;
			Long clienteId = 0L;
			Long responsavelId = 0L;
			Long comarcaId = 0L;
			Long cartorioComarcaId = 0L;
			String pastaCliente = "";
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while (resultSet.next()) {
				//
				id = Long.parseLong(resultSet.getString("id"));
				dataCadastro = sdf.parse(resultSet.getString("datacadastro"));
				processoCodigo = resultSet.getString("processocodigo");
				autorTxt = resultSet.getString("autorTxt");
				parteContrariaId = Long.parseLong(resultSet.getString("parteContrariaid"));
				clienteId = Long.parseLong(resultSet.getString("clienteid"));
				responsavelId = Long.parseLong(resultSet.getString("advogadorespid"));
				//
				comarcaId = Long.parseLong(resultSet.getString("comarcaid"));
				if (null == resultSet.getString("localtramiteid")) cartorioComarcaId = 0L;
				else cartorioComarcaId = Long.parseLong(resultSet.getString("localtramiteid"));
				//
				if (null == resultSet.getString("pastacliente")) pastaCliente = "";
				else pastaCliente = resultSet.getString("pastacliente");

				this.contCargaProcesso++;

//				MpProcesso mpProcesso = mpProcessos.porIdCarga(id);
//				if (null == mpProcesso) { 
					MpProcesso mpProcesso = new MpProcesso();
//				}
				
				mpProcesso.setTenantId(tenantIdCarga);
				mpProcesso.setIdCarga(id);				
				mpProcesso.setDataCadastro(dataCadastro);
				mpProcesso.setProcessoCodigo(processoCodigo);
				mpProcesso.setAutor(autorTxt);
				//
				MpPessoaSJ mpParteContraria = mpPessoaJuridicas.porIdCarga(parteContrariaId);
				mpProcesso.setMpParteContraria(mpParteContraria);
				MpClienteSJ mpClienteSJ = mpClienteSJs.porIdCarga(clienteId);
				mpProcesso.setMpClienteSJ(mpClienteSJ);
				MpPessoaSJ mpAdvogadoResponsavel = mpPessoaFisicas.porIdCarga(responsavelId);
				mpProcesso.setMpAdvogadoResponsavel(mpAdvogadoResponsavel);
				//
				MpTabelaInternaSJ mpComarca = mpTabelaInternaSJs.porIdCarga(comarcaId);
				mpProcesso.setMpComarca(mpComarca);
				MpTabelaInternaSJ mpComarcaCartorio = mpTabelaInternaSJs.porIdCarga(cartorioComarcaId);
				mpProcesso.setMpComarcaCartorio(mpComarcaCartorio);
				//
				mpProcesso.setPastaCliente(pastaCliente);
				
//				try {
					mpProcesso = mpProcessoService.salvar(mpProcesso);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpProcesso - Salvar.Erro ( e = " + e.toString() +
//						                                                 							" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public void trataProcessoAndamento() {
		//
    	this.contCargaProcessoAndamento = 0;
    	//
    	Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection("jdbc:hsqldb:file:~/db/sisjuri/sisjuriDB", "SA", "");
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(
					"SELECT op.\"ID\" AS id, op.\"PROCESSOID\" AS processoid, op.\"DATA_CADASTRO\" AS datacadastro," + 
					" op.\"DETALHAMENTO\" AS detalhamento, op.\"ANDAMENTOTIPOID\" AS andamentotipoid," + 
					" op.\"ATOPRATICADOID\" AS atopraticadoid, op.\"FOTOBD\" AS fotobd," + 
					" op.\"TIPOFOTOBD\" AS tipofotobd" + 
			        " FROM \"OBJETO_PROCESSO\" op");
			//
			Long id = 0L;
			Long processoId = 0L;
			Date dataCadastro = new Date();;
			String detalhamento = "";
			Long andamentoTipoId = 0L;
			Long atoPraticadoId = 0L;
			String tipoFotoBD = "";
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while (resultSet.next()) {
				//
				id = Long.parseLong(resultSet.getString("id"));
				
				processoId = Long.parseLong(resultSet.getString("processoid"));
				dataCadastro = sdf.parse(resultSet.getString("datacadastro"));
				detalhamento = resultSet.getString("detalhamento");
				
				if (null == resultSet.getString("andamentotipoid"))
					andamentoTipoId = 0L;
				else
					andamentoTipoId = Long.parseLong(resultSet.getString("andamentotipoid"));
				
				if (null == resultSet.getString("atopraticadoid"))
					atoPraticadoId = 0L;
				else
					atoPraticadoId = Long.parseLong(resultSet.getString("atopraticadoid"));

				tipoFotoBD = resultSet.getString("tipofotobd");
				//
				this.contCargaProcessoAndamento++;

//				MpProcessoAndamento mpProcessoAndamento = mpProcessoAndamentos.porIdCarga(id);
//				if (null == mpProcessoAndamento) { 
					MpProcessoAndamento mpProcessoAndamento = new MpProcessoAndamento();
//				}
				
				MpProcesso mpProcesso = mpProcessos.porIdCarga(processoId);
				if (null == mpProcesso) { 
					MpAppUtil.PrintarLn("Processo Não Existe... Registro Ignorado = " + processoId);
					continue;
				}
				
				mpProcessoAndamento.setTenantId(tenantIdCarga);
				mpProcessoAndamento.setIdCarga(id);
				mpProcessoAndamento.setMpProcesso(mpProcesso);
				mpProcessoAndamento.setDataCadastro(dataCadastro);
				mpProcessoAndamento.setDetalhamento(detalhamento);
				//
				MpTabelaInternaSJ mpAndamentoTipo = mpTabelaInternaSJs.porIdCarga(andamentoTipoId);
				if (null == mpAndamentoTipo) { 
					MpAppUtil.PrintarLn("Andamento Tipo Não Existe... Registro Ignorado = " + andamentoTipoId);
				}
				mpProcessoAndamento.setMpAndamentoTipo(mpAndamentoTipo);
				
				MpTabelaInternaSJ mpAtoPraticado = mpTabelaInternaSJs.porIdCarga(atoPraticadoId);
				if (null == mpAtoPraticado) { 
					MpAppUtil.PrintarLn("Ato Praticado Não Existe... Registro Ignorado = " + atoPraticadoId);
				}
				mpProcessoAndamento.setMpAtoPraticado(mpAtoPraticado);
				mpProcessoAndamento.setTipoFotoBD(tipoFotoBD);

				Blob blob = resultSet.getBlob("fotobd");					
				if (null == blob)
					assert(true); // nop
				else {
					int blobLength = (int) blob.length();  
					byte[] blobAsBytes = blob.getBytes(1, blobLength);
					
					mpProcessoAndamento.setFotoBD(blobAsBytes);
					//Release blob and free up memory.
					blob.free();
				}
				//
//				try {
					mpProcessoAndamento = mpProcessoAndamentoService.salvar(mpProcessoAndamento);
//				} catch (Exception e) {
//					MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.MpProcesso - Salvar.Erro ( e = " + e.toString() +
// 																									" / " + id);
//				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Trata carga -> Pai x Filhas !
		MpProcessoFilter mpFilter = new MpProcessoFilter();
		
		List<MpProcesso> mpPaiList = mpProcessos.filtrados(mpFilter);
		
		for (MpProcesso mpPaiX : mpPaiList) {
			//
			List<MpProcessoAndamento> mpFilhaList = mpProcessoAndamentos.findAllFilhas(mpPaiX.getId());
			
			if (mpFilhaList.size() > 0) {
				//
				mpPaiX.setMpAndamentos(mpFilhaList);
				
				mpProcessos.guardar(mpPaiX);
				//
				MpAppUtil.PrintarLn("MpCargaDadosSisJuriBean.trataProcessoAndamento - Pai X Filha = ( " +
														mpPaiX.getProcessoCodigo() + " / " + mpFilhaList.size());
			}
		}
	}
	
	// ---
	
	public Boolean getIndCargaTabelaInternaSJ() { return indCargaTabelaInternaSJ; }
	public void setIndCargaTabelaInternaSJ(Boolean indCargaTabelaInternaSJ) { 
														this.indCargaTabelaInternaSJ = indCargaTabelaInternaSJ; }
	
	public Boolean getIndCargaClienteSJ() { return indCargaClienteSJ; }
	public void setIndCargaClienteSJ(Boolean indCargaClienteSJ) { this.indCargaClienteSJ = indCargaClienteSJ; }
	
	public Boolean getIndCargaPessoaFisica() { return indCargaPessoaFisica; }
	public void setIndCargaPessoaFisica(Boolean indCargaPessoaFisica) { 
																this.indCargaPessoaFisica = indCargaPessoaFisica; }
	
	public Boolean getIndCargaPessoaJuridica() { return indCargaPessoaJuridica; }
	public void setIndCargaPessoaJuridica(Boolean indCargaPessoaJuridica) { 
															this.indCargaPessoaJuridica = indCargaPessoaJuridica; }
	
	public Boolean getIndCargaProcesso() { return indCargaProcesso; }
	public void setIndCargaProcesso(Boolean indCargaProcesso) { this.indCargaProcesso = indCargaProcesso; }
	
	public Boolean getIndCargaProcessoAndamento() { return indCargaProcessoAndamento; }
	public void setIndCargaProcessoAndamento(Boolean indCargaProcessoAndamento) { 
													this.indCargaProcessoAndamento = indCargaProcessoAndamento; }
	
	public String getTenantIdCarga() { return tenantIdCarga; }
	public void setTenantIdCarga(String tenantIdCarga) { this.tenantIdCarga = tenantIdCarga; }

}