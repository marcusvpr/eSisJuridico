package com.mpxds.mpbasic.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpCargaDadosBDBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpArquivoBDs mpArquivoBDs;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private String lineSQL;

	private Boolean indCargaCustasComposicao = false;
	private Integer contCargaCustasComposicao = 0;

	private Boolean indCargaTipoProtocolo = false;
	private Integer contCargaTipoProtocolo = 0;

	private Boolean indCargaSistemaConfig = false;
	private Integer contCargaSistemaConfig = 0;
	
	private MpObjeto mpObjetoHelp;

	// ---
	
	public void carregar() {
		//
		if (indCargaCustasComposicao)
			this.trataCustasComposicao();

		if (indCargaTipoProtocolo)
			this.trataTipoProtocolo();

		if (indCargaSistemaConfig)
			this.trataSistemaConfig();
		//
		MpFacesUtil.addErrorMessage("Carga Efetuada ! ( " + 
										"CustasComposicao = " + this.indCargaCustasComposicao +
										" / Qtd. = " + this.contCargaCustasComposicao +
										" / TipoProtocolo = " + this.indCargaTipoProtocolo +
										" / Qtd. = " + this.contCargaTipoProtocolo +
										" / SistemaConfig = " + this.indCargaSistemaConfig +
										" / Qtd. = " + this.contCargaSistemaConfig);
	}
	
	public void trataCustasComposicao() {
		//
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("sql/mp_pt05_custas_composicao.sql").getFile());
		
    	this.lineSQL = "";
    	this.contCargaCustasComposicao = 0;
    	//
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String lineSQL = scanner.nextLine();

				String result = this.mpArquivoBDs.executaSQL(lineSQL);

				this.contCargaCustasComposicao++;

				System.out.println("MpCargaDadosBDBean.trataCustasComposicao().line =  ( " + 
																result + " / " + this.lineSQL);
			}
			//
			scanner.close();
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage("Error : ( e = " + e.toString());
		}
		
	}
	
	public void trataTipoProtocolo() {
		//
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("sql/mp_pt08_tipo_protocolo.sql").getFile());
    	this.lineSQL = "";
    	this.contCargaTipoProtocolo = 0;
    	//
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String lineSQL = scanner.nextLine();

				String result = this.mpArquivoBDs.executaSQL(lineSQL);

				this.contCargaTipoProtocolo++;

				System.out.println("MpCargaDadosBDBean.trataTipoProtocolo().line =  ( " +
																result + " / " + this.lineSQL);
			}
			//
			scanner.close();
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage("Error : ( e = " + e.toString());
		}
		//
	}
	
	public void trataSistemaConfig() {
		//
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("sql/mp_sistema_config.sql").getFile());
		
    	this.lineSQL = "";
    	this.contCargaSistemaConfig = 0;
    	//
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String lineSQL = scanner.nextLine();

				String result = this.mpArquivoBDs.executaSQL(lineSQL);

				this.contCargaSistemaConfig++;

				System.out.println("MpCargaDadosBDBean.trataSistemaConfig().line =  ( " +
																result + " / " + this.lineSQL);
			}
			//
			scanner.close();
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage("Error : ( e = " + e.toString());
		}
		//
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	
	
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public Boolean getIndCargaCustasComposicao() { return indCargaCustasComposicao; }
	public void setIndCargaCustasComposicao(Boolean indCargaCustasComposicao) { 
									this.indCargaCustasComposicao = indCargaCustasComposicao; }

	public Boolean getIndCargaTipoProtocolo() { return indCargaTipoProtocolo; }
	public void setIndCargaTipoProtocolo(Boolean indCargaTipoProtocolo) { 
									this.indCargaTipoProtocolo = indCargaTipoProtocolo; }

	public Boolean getIndCargaSistemaConfig() { return indCargaSistemaConfig; }
	public void setIndCargaSistemaConfig(Boolean indCargaSistemaConfig) { 
									this.indCargaSistemaConfig = indCargaSistemaConfig; }

}