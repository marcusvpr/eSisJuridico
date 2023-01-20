package com.mpxds.mpbasic.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@ManagedBean
@Named
@ViewScoped
public class MpGeraArtefatosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private boolean makeAsReadOnly;
	//
    private String nomeClasse = "Xyyyyyy";
    private String sqlFonte;
    //
    private Boolean indGeraModel = false;
    private Boolean indGeraFilter = false;
    private Boolean indGeraRepository = false;       
    private Boolean indGeraService = false;
    private Boolean indGeraConverter = false;
    private Boolean indGeraTelaCadastro = false;
    private Boolean indGeraBotaoCadastro = false;
    private Boolean indGeraControllerCadastro = false;
    private Boolean indGeraTelaPesquisa = false;
    private Boolean indGeraControllerPesquisa = false;
    //
    private String resultFonteModel;
    private String resultFonteFilter;
    private String resultFonteRepository;
    private String resultFonteService;
    private String resultFonteConverter;
    private String resultFonteTelaCadastro;
    private String resultFonteBotaoCadastro;
    private String resultFonteControllerCadastro;
    private String resultFonteTelaPesquisa;
    private String resultFonteControllerPesquisa;
    
    // ---
    	
    private String artefatoSels = "";
    private Boolean indSelecao = false;
        
    private StringBuilder outCampos = new StringBuilder();  
    //
    private StreamedContent fileModelX;
    private StreamedContent fileFilterX;
    private StreamedContent fileRepositoryX;
    private StreamedContent fileServiceX;
    private StreamedContent fileConverterX;
    private StreamedContent fileTelaCadastroX;
    private StreamedContent fileBotaoCadastroX;
    private StreamedContent fileControllerCadastroX;
    private StreamedContent fileTelaPesquisaX;
    private StreamedContent fileControllerPesquisaX;
	
    // -------------------------------- Inicio ------------------------------------

    public void geraArtefatos() {
    	//
    	String msgX = "";
    	if (null == this.nomeClasse || this.nomeClasse.isEmpty())
    		msgX += "(Nome Classe)";
    	//
    	if (this.indGeraModel == false 
    	&&  this.indGeraFilter == false
    	&&  this.indGeraRepository == false
    	&&  this.indGeraService == false
    	&&  this.indGeraConverter == false
    	&&  this.indGeraTelaCadastro == false
    	&&  this.indGeraBotaoCadastro == false 
    	&&  this.indGeraControllerCadastro == false
    	&&  this.indGeraTelaPesquisa == false 
    	&&  this.indGeraControllerPesquisa == false)
    		msgX += "(Selecione uma opção)";
    	
    	if (msgX.isEmpty())
    		assert(true); // nop
    	else{
    		//
    		MpFacesUtil.addInfoMessage("Verificar ! " + msgX);
    		return;
    	}
    	//
    	if (this.indGeraModel) trataGeraModel();    	
    	if (this.indGeraFilter) trataGeraFilter();    	
    	if (this.indGeraRepository) trataGeraRepository();
    	if (this.indGeraService) trataGeraService();
    	if (this.indGeraConverter) trataGeraConverter();
    	if (this.indGeraTelaCadastro) trataGeraTelaCadastro();
    	if (this.indGeraBotaoCadastro) trataGeraBotaoCadastro();
    	if (this.indGeraControllerCadastro) trataGeraControllerCadastro();
    	if (this.indGeraTelaPesquisa) trataGeraTelaPesquisa();
    	if (this.indGeraControllerPesquisa) trataGeraControllerPesquisa();
    	//
    	MpFacesUtil.addInfoMessage("Geração Artefato(s)... Efetuada ! ( Classe = " + this.nomeClasse +
    		" ) / Ind.s (M= " + this.indGeraModel +
	    	" /F= " + this.indGeraFilter + " /R= " + this.indGeraRepository +
	    	" /S= " + this.indGeraService + " /C= " + this.indGeraConverter + 
	    	" /TC= " + this.indGeraTelaCadastro + " /BC= " + this.indGeraBotaoCadastro +
	    	" /CC= " + this.indGeraControllerCadastro + " /TP= " + this.indGeraTelaPesquisa +
	    	" /CP= " + this.indGeraControllerPesquisa);
    }

    private void trataGeraModel() {
    	//
//	    MpAppUtil.PrintarLn("MpGeraartafatosBean.trataGeraModel() - Classe = " + this.nomeClasse.trim());
    	
        this.outCampos = new StringBuilder();  
        Boolean indCampos = true;

        this.trataCampoSQL();
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpDolar.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				// Ignora campos exemplos ...
	    		if (line.indexOf("@Column") >= 0 
		    	||	line.indexOf("@Getter") >= 0
			    ||	line.indexOf("private Date") >= 0) {
	    			// Trata inclusão de campos ...
	    			if (indCampos) {
	    				//
	    			    out.append(outCampos.toString());

	    			    indCampos = false;
	    			}
	    			//
	    			continue; 
	    		}
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			    
//			    MpAppUtil.PrintarLn("MpGeraartafatosBean.trataGeraModel() - Line = " + line);
			}
			//
			this.resultFonteModel = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraModel() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraModel() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "Mp" + this.nomeClasse.trim() + ".java";
			
			File fileModel = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileModel);
			
			fileWriter.write(this.resultFonteModel);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraModel() - File = " + fileModel.getAbsolutePath());
		    //
		    this.trataFileDownload(fileModel, "Model");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraModel() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraModel() - Error Gravação : e = " + e);
		}
    }
    
    private void trataCampoSQL() {
    	//
    	if (null == this.sqlFonte || this.sqlFonte.isEmpty()) return;    	
    	//    	
    	String campo = "";
    	String campoName = "";
    	String formato = "";
    	String tamanho = "";
    	String indNull = "";
    	
    	String[] linhas = this.sqlFonte.split("\n");
    	
    	for(String linha: linhas) {
    		//
//    		MpAppUtil.PrintarLn("MpGeraartafatosBean.trataCampoSQL() (Linha = " + linha);
    		//
    		if (linha.indexOf("NULL") >= 0) {
    			//
    			if (linha.indexOf("[quick~rs]") >= 0) continue;
    			// 
    			String wordCs[] = linha.split("]");
    			campo = wordCs[0].trim();
    			campo = campo.replace("[", "");
    			campo = campo.replace("]", "");
    			campoName = campo.toUpperCase();
    			campo = campo.replace("_", "");
    			//
        		formato = "String";
        		if (linha.indexOf("[decimal]") >= 0
            	||  linha.indexOf("[numeric]") >= 0) {
        			formato = "BigDecimal";
        			// Trata captura tamanho campo DECIMAL !
        			int posX1 = linha.indexOf("(");
        			int posX2 = linha.indexOf(",");
        			int posX3 = linha.indexOf(")");
        			//
        			tamanho = "precision = " + linha.substring(posX1+1, posX2) + ", scale = " + 
        									   linha.substring(posX2+1, posX3);
        		} else
                if (linha.indexOf("[text]") >= 0)
                	tamanho = "";
                else
                if (linha.indexOf("[image]") >= 0) {
                	formato = " byte[]";
                   	tamanho = "";
    			} else
            	if (linha.indexOf("[bit]") >= 0) {
            		formato = "Boolean";
            		tamanho = "";
            	} else
        		if (linha.indexOf("time]") >= 0) {
        			formato = "Date";
        			tamanho = "";
        		} else
            	if (linha.indexOf("int]") >= 0
            	||  linha.indexOf("[float]") >= 0) {
        			formato = "Integer";
        			tamanho = "";
        		} else {
        			//
        			String wordTs[] = linha.split(" ");
        			tamanho = wordTs[2];
        			tamanho = tamanho.replace("(", "");
        			tamanho = tamanho.replace(")", "");
        		}
        		//
        		if (linha.indexOf("NOT NULL") >= 0) {
        			indNull = "false";
    			    this.outCampos.append("        @NotNull(message = \"Valor é obrigatório\")\n");
        		} else
        			indNull = "true";    			
    			//
        		if (formato.equals("byte[]")) {
    			    this.outCampos.append("        @Lob\n");
    			    this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
    			    								"columnDefinition = \"blob\", nullable = true, length = 10000)\n");
        		} else
        		if (formato.equals("Date")) {
    			    this.outCampos.append("        @Temporal(TemporalType.TIMESTAMP)\n");
    			    this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
    			    								"nullable = " + indNull + ")\n");
        		} else
            	if (formato.equals("Integer") 
            	||  formato.equals("Boolean")) 
    			    this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
    			    								"nullable = " + indNull + ")\n");
        		else
            	if (formato.equals("BigDecimal")) 
            		this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
            										"nullable = " + indNull + ", " + tamanho + ")\n");
            	else
            		if (tamanho.isEmpty())
            			this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
            											"nullable = " + indNull + ")\n");
            		else
            			this.outCampos.append("        @Column(name = \"" + campoName + "\", " + 
            											"nullable = " + indNull + ", length = " + tamanho + ")\n");
        		//
        		this.outCampos.append("        @Getter @Setter\n");
        		this.outCampos.append("        private " + formato + " " + campo.substring(0,1).toLowerCase() +
        																   campo.substring(1) + ";\n");
        		this.outCampos.append("\n");
    		}
    	}
    	//
//		MpAppUtil.PrintarLn("MpGeraartafatosBean.trataCampoSQL() (campo = " + campo);
    }

    private void trataGeraFilter() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpDolarFilter.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
											 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteFilter = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraFilter() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraFilter() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "Mp" + this.nomeClasse.trim() + "Filter.java";
			
			File fileFilter = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileFilter);
			
			fileWriter.write(this.resultFonteFilter);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraFilter() - File = " + fileFilter.getAbsolutePath());
		    //
		    this.trataFileDownload(fileFilter, "Filter");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraFilter() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraFilter() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraRepository() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpDolars.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteRepository = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraRepository() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraRepository() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "Mp" + this.nomeClasse.trim() + "s.java";
			
			File fileRepository = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileRepository);
			
			fileWriter.write(this.resultFonteRepository);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraRepository() - File = " + 
		    																	fileRepository.getAbsolutePath());
		    //
		    this.trataFileDownload(fileRepository, "Repository");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraRepository() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraRepository() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraService() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpDolarService.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteService = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraService() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraService() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "Mp" + this.nomeClasse.trim() + "Service.java";
			
			File fileService = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileService);
			
			fileWriter.write(this.resultFonteService);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraService() - File = " + fileService.getAbsolutePath());
		    //
		    this.trataFileDownload(fileService, "Service");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraService() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraService() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraConverter() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpDolarConverter.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteConverter = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraConverter() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraConverter() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "Mp" + this.nomeClasse.trim() + "Converter.java";
			
			File fileConverter = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileConverter);
			
			fileWriter.write(this.resultFonteConverter);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraConverter() - File = " + 
		    																		fileConverter.getAbsolutePath());
		    //
		    this.trataFileDownload(fileConverter, "Converter");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraConverter() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraConverter() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraTelaCadastro() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/mpCadastroDolar.xhtml");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteTelaCadastro = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraTelaCadastro() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaCadastro() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "mpCadastro" + this.nomeClasse.trim() + ".xhtml";
			
			File fileTelaCadastro = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileTelaCadastro);
			
			fileWriter.write(this.resultFonteTelaCadastro);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaCadastro() - File = " + 
		    																	fileTelaCadastro.getAbsolutePath());
		    //
		    this.trataFileDownload(fileTelaCadastro, "TelaCadastro");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraTelaCadastro() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaCadastro() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraBotaoCadastro() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/mpBotoesDolar.xhtml");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteBotaoCadastro = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraBotaoCadastro() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraBotaoCadastro() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "mpBotoes" + this.nomeClasse.trim() + ".xhtml";
			
			File fileBotaoCadastro = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileBotaoCadastro);
			
			fileWriter.write(this.resultFonteBotaoCadastro);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraBotaoCadastro() - File = " + 
		    																	fileBotaoCadastro.getAbsolutePath());
		    //
		    this.trataFileDownload(fileBotaoCadastro, "BotaoCadastro");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraBotaoCadastro() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraBotaoCadastro() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraControllerCadastro() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpCadastroDolarBean.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteControllerCadastro = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraControllerCadastro() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerCadastro() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "MpCadastro" + this.nomeClasse.trim() + "Bean.java";
			
			File fileControllerCadastro = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileControllerCadastro);
			
			fileWriter.write(this.resultFonteControllerCadastro);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerCadastro() - File = " + 
		    																fileControllerCadastro.getAbsolutePath());
		    //
		    this.trataFileDownload(fileControllerCadastro, "ControllerCadastro");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraControllerCadastro() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerCadastro() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraTelaPesquisa() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/mpPesquisaDolars.xhtml");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteTelaPesquisa = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraTelaPesquisa() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaPesquisa() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "mpPesquisa" + this.nomeClasse.trim() + "s.xhtml";
			
			File fileTelaPesquisa = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileTelaPesquisa);
			
			fileWriter.write(this.resultFonteTelaPesquisa);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaPesquisa() - File = " + 
		    																	fileTelaPesquisa.getAbsolutePath());
		    //
		    this.trataFileDownload(fileTelaPesquisa, "TelaPesquisa");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraTelaPesquisa() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraTelaPesquisa() - Error Gravação : e = " + e);
		}
    }

    private void trataGeraControllerPesquisa() {
    	//
        try {
        	InputStream is = MpGeraArtefatosBean.class.getResourceAsStream("/artefatos/base/MpPesquisaDolarsBean.java");
        	
        	InputStreamReader r = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(r);
            //
            StringBuilder out = new StringBuilder();
            String line;
            //
			while ((line = br.readLine()) != null) {
				//
				line = line.replace("Dolar", this.nomeClasse.trim().substring(0,1).toUpperCase() +
	 					 					 this.nomeClasse.trim().substring(1));
				line = line.replace("dolar", this.nomeClasse.trim().substring(0,1).toLowerCase() +
						 					 this.nomeClasse.trim().substring(1));
				line = line.replace("DOLAR", this.nomeClasse.trim().toUpperCase());
				//		
			    out.append(line + "\n");
			}
			//
			this.resultFonteControllerPesquisa = out.toString();
			//
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraControllerPesquisa() Error : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerPesquisa() - Error Geração : e = " + e);
		}    	

		// Trata gravação arquivo...
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();						

		try {
			String nomeArquivo = "MpPesquisa" + this.nomeClasse.trim() + "sBean.java";
			
			File fileControllerPesquisa = new File(extContext.getRealPath("//resources//artefatos//" + nomeArquivo));
			FileWriter fileWriter = new FileWriter(fileControllerPesquisa);
			
			fileWriter.write(this.resultFonteControllerPesquisa);
			
			fileWriter.flush();
			fileWriter.close();
			//
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerPesquisa() - File = " + 
		    																fileControllerPesquisa.getAbsolutePath());
		    //
		    this.trataFileDownload(fileControllerPesquisa, "ControllerPesquisa");
		    //
		} catch (IOException e) {
    		MpFacesUtil.addInfoMessage("trataGeraControllerPesquisa() Error Gravação : e = " + e);
		    MpAppUtil.PrintarLn("MpGerartafatosBean.trataGeraControllerPesquisa() - Error Gravação : e = " + e);
		}
    }
        
    // ====
    
    public void trataFileDownload(File fileDown, String tipo) {
    	//
    	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    	
        InputStream streamX;
        
		try {
			streamX = new FileInputStream(fileDown);

			StreamedContent fileXX = new DefaultStreamedContent(streamX, externalContext.getMimeType(
												fileDown.getName()), "downloaded_" + fileDown.getName());
			//			
			switch (tipo) {
				case "Model":
					this.fileModelX = fileXX;
					break;
				case "Filter":
					this.fileFilterX = fileXX;
					break;
				case "Repository":
					this.fileRepositoryX = fileXX;
					break;
				case "Service":
					this.fileServiceX = fileXX;
					break;
				case "Converter":
					this.fileConverterX = fileXX;
					break;
				case "TelaCadastro":
					this.fileTelaCadastroX = fileXX;
					break;
				case "BotaoCadastro":
					this.fileBotaoCadastroX = fileXX;
					break;
				case "ControllerCadastro":
					this.fileControllerCadastroX = fileXX;
					break;
				case "TelaPesquisa":
					this.fileTelaPesquisaX = fileXX;
					break;
				case "ControllerPesquisa":
					this.fileControllerPesquisaX = fileXX;
					break;
			}			
			//
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//
    }
    
//    public StreamedContent prepDownloadModel() throws Exception {
//    	//
//        StreamedContent download=new DefaultStreamedContent();
//        File file = new File("C:\\file.csv");
//        InputStream input = new FileInputStream(file);
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        download = new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
//        System.out.println("PREP = " + download.getName());
//        return download;
//    }    
        
    // ========================================================
    
    public String getNomeClasse() { return this.nomeClasse; }
    public void setNomeClasse(String newNomeClasse) { this.nomeClasse = newNomeClasse; }
    
    public String getSqlFonte() { return this.sqlFonte; }
    public void setSqlFonte(String newSqlFonte) { this.sqlFonte = newSqlFonte; }
    //
    public String getResultFonteModel() { return this.resultFonteModel; }
    public void setResultFonteModel(String newResultFonteModel) { this.resultFonteModel = newResultFonteModel; }

    public String getResultFonteFilter() { return this.resultFonteFilter; }
    public void setResultFonteFilter(String newResultFonteFilter) { this.resultFonteFilter = newResultFonteFilter; }

    public String getResultFonteRepository() { return this.resultFonteRepository; }
    public void setResultFonteRepository(String newResultFonteRepository) { 
    														this.resultFonteRepository = newResultFonteRepository; }

    public String getResultFonteService() { return this.resultFonteService; }
    public void setResultFonteService(String newResultFonteService) { 
    															this.resultFonteService = newResultFonteService; }

    public String getResultFonteConverter() { return this.resultFonteConverter; }
    public void setResultFonteConverter(String newResultFonteConverter) { 
    														this.resultFonteConverter = newResultFonteConverter; }

    public String getResultFonteTelaCadastro() { return this.resultFonteTelaCadastro; }
    public void setResultFonteTelaCadastro(String newResultFonteTelaCadastro) { 
    													this.resultFonteTelaCadastro = newResultFonteTelaCadastro; }

    public String getResultFonteBotaoCadastro() { return this.resultFonteBotaoCadastro; }
    public void setResultFonteBotaoCadastro(String newResultFonteBotaoCadastro) { 
    												this.resultFonteBotaoCadastro = newResultFonteBotaoCadastro; }

    public String getResultFonteControllerCadastro() { return this.resultFonteControllerCadastro; }
    public void setResultFonteControllerCadastro(String newResultFonteControllerCadastro) { 
    										this.resultFonteControllerCadastro = newResultFonteControllerCadastro; }

    public String getResultFonteTelaPesquisa() { return this.resultFonteTelaPesquisa; }
    public void setResultFonteTelaPesquisa(String newResultFonteTelaPesquisa) {
    													this.resultFonteTelaPesquisa = newResultFonteTelaPesquisa; }

    public String getResultFonteControllerPesquisa() { return this.resultFonteControllerPesquisa; }
    public void setResultFonteControllerPesquisa(String newResultFonteControllerPesquisa) { 
    										this.resultFonteControllerPesquisa = newResultFonteControllerPesquisa; }
    //
    public boolean getIndGeraModel() { return this.indGeraModel; }
    public void setIndGeraModel(boolean newIndGeraModel) { this.indGeraModel = newIndGeraModel; }

    public boolean getIndGeraFilter() { return this.indGeraFilter; }
    public void setIndGeraFilter(boolean newIndGeraFilter) { this.indGeraFilter = newIndGeraFilter; }

    public boolean getIndGeraRepository() { return this.indGeraRepository; }
    public void setIndGeraRepository(boolean newIndGeraRepository) { this.indGeraRepository = newIndGeraRepository; }

    public boolean getIndGeraService() { return this.indGeraService; }
    public void setIndGeraService(boolean newIndGeraService) { this.indGeraService = newIndGeraService; }

    public boolean getIndGeraConverter() { return this.indGeraConverter; }
    public void setIndGeraConverter(boolean newIndGeraConverter) { this.indGeraConverter = newIndGeraConverter; }

    public boolean getIndGeraTelaCadastro() { return this.indGeraTelaCadastro; }
    public void setIndGeraTelaCadastro(boolean newIndGeraTelaCadastro) { 
    															this.indGeraTelaCadastro = newIndGeraTelaCadastro; }

    public boolean getIndGeraBotaoCadastro() { return this.indGeraBotaoCadastro; }
    public void setIndGeraBotaoCadastro(boolean newIndGeraBotaoCadastro) { 
    														this.indGeraBotaoCadastro = newIndGeraBotaoCadastro; }

    public boolean getIndGeraControllerCadastro() { return this.indGeraControllerCadastro; }
    public void setIndGeraControllerCadastro(boolean newIndGeraControllerCadastro) { 
    												this.indGeraControllerCadastro = newIndGeraControllerCadastro; }

    public boolean getIndGeraTelaPesquisa() { return this.indGeraTelaPesquisa; }
    public void setIndGeraTelaPesquisa(boolean newIndGeraTelaPesquisa) { 
    															this.indGeraTelaPesquisa = newIndGeraTelaPesquisa; }

    public boolean getIndGeraControllerPesquisa() { return this.indGeraControllerPesquisa; }
    public void setIndGeraControllerPesquisa(boolean newIndGeraControllerPesquisa) { 
    												this.indGeraControllerPesquisa = newIndGeraControllerPesquisa; }
    
    // ---
    
    public boolean getMakeAsReadOnly() { return this.makeAsReadOnly; }
    public void setMakeAsReadOnly(boolean newMakeAsReadOnly) { this.makeAsReadOnly = newMakeAsReadOnly; }

    public void setArtefatoSels(String newArtefatoSels) { this.artefatoSels = newArtefatoSels; }
    public String getArtefatoSels() { return this.artefatoSels; }
	
    public void setIndSelecao(Boolean newIndSelecao) { this.indSelecao = newIndSelecao; }
    public Boolean getIndSelecao() { return this.indSelecao; }
    
    // --- FileDownload ! 
    
    public void setFileModelX(StreamedContent fileModelX) { this.fileModelX = fileModelX; }
    public StreamedContent getFileModelX() { return fileModelX; }
    
    public void setFileFilterX(StreamedContent fileFilterX) { this.fileFilterX = fileFilterX; }
    public StreamedContent getFileFilterX() { return fileFilterX; }
    
    public void setFileRepositoryX(StreamedContent fileRepositoryX) { this.fileRepositoryX = fileRepositoryX; }
    public StreamedContent getFileRepositoryX() { return fileRepositoryX; }
    
    public void setFileServiceX(StreamedContent fileServiceX) { this.fileServiceX = fileServiceX; }
    public StreamedContent getFileServiceX() { return fileServiceX; }
    
    public void setFileConverterX(StreamedContent fileConverterX) { this.fileConverterX = fileConverterX; }
    public StreamedContent getFileConverterX() { return fileConverterX; }
    
    public void setFileTelaCadastroX(StreamedContent fileTelaCadastroX) { 
    																this.fileTelaCadastroX = fileTelaCadastroX; }
    public StreamedContent getFileTelaCadastroX() { return fileTelaCadastroX; }
    
    public void setFileBotaoCadastroX(StreamedContent fileBotaoCadastroX) { 
    																this.fileBotaoCadastroX = fileBotaoCadastroX; }
    public StreamedContent getFileBotaoCadastroX() { return fileBotaoCadastroX; }
    
    public void setFileControllerCadastroX(StreamedContent fileControllerCadastroX) { 
    														this.fileControllerCadastroX = fileControllerCadastroX; }
    public StreamedContent getFileControllerCadastroX() { return fileControllerCadastroX; }
    
    public void setFileTelaPesquisaX(StreamedContent fileTelaPesquisaX) { 
    																this.fileTelaPesquisaX = fileTelaPesquisaX; }
    public StreamedContent getFileTelaPesquisaX() { return fileTelaPesquisaX; }
    
    public void setFileControllerPesquisaX(StreamedContent fileControllerPesquisaX) { 
    														this.fileControllerPesquisaX = fileControllerPesquisaX; }
    public StreamedContent getFileControllerPesquisaX() { return fileControllerPesquisaX; }
    
}
