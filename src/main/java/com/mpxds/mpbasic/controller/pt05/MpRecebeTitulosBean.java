package com.mpxds.mpbasic.controller.pt05;

//import java.io.BufferedInputStream;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
//import javax.persistence.EntityManager;
//import javax.servlet.ServletContext;
import javax.faces.context.ExternalContext;

//import org.apache.commons.io.FilenameUtils;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt05.MpHeader;
import com.mpxds.mpbasic.model.pt05.MpImportarControle;
import com.mpxds.mpbasic.model.pt05.MpRemessa;
import com.mpxds.mpbasic.model.pt05.MpTrailler;
import com.mpxds.mpbasic.model.pt05.MpTransacao;
import com.mpxds.mpbasic.repository.pt05.MpHeaders;
import com.mpxds.mpbasic.repository.pt05.MpImportarControles;
import com.mpxds.mpbasic.repository.pt05.MpRemessas;
import com.mpxds.mpbasic.repository.pt05.MpTraillers;
import com.mpxds.mpbasic.repository.pt05.MpTransacaos;
import com.mpxds.mpbasic.model.pt08.MpFeriado;
import com.mpxds.mpbasic.repository.pt08.MpFeriados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpRecebeTitulosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

//	@Inject
//	private EntityManager manager;
	
	@Inject
	private MpImportarControles mpImportarControles;

	@Inject
	private MpRemessas mpRemessas;

	@Inject
	private MpTraillers mpTraillers;

	@Inject
	private MpTransacaos mpTransacaos;

	@Inject
	private MpHeaders mpHeaders;

	@Inject
	private MpFeriados mpFeriados;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private MpImportarControle mpImportarControle = new MpImportarControle();
	private List<MpImportarControle> mpImportarControleList = new ArrayList<MpImportarControle>();

	private List<MpImportarControle> selectedMpImportarControles = new ArrayList<MpImportarControle>();

	private UploadedFile arquivoUpload;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private File file;
	
	private Boolean indVisivel = false;
	
	private MpObjeto mpObjetoHelp;
	
	// ---
		    
    public void calculaData() {
    	//
//    	System.out.println("MpRecebeTitulosBean.calculaData() - 000 ( " + 
//    													this.mpImportarControle.getDataImportar());
    	
    	List<MpImportarControle> mpImportarControleXs = mpImportarControles.porDataImportar(
    												this.mpImportarControle.getDataImportar());
    	if (mpImportarControleXs.isEmpty())
    		assert(true);
    	else {
    		MpFacesUtil.addInfoMessage("Data Importar... já existe ! ( " +
    															mpImportarControleXs.size());
//    		return; //  ??? Removido para testes ...
    	}
    	//
    	this.mpImportarControleList = mpImportarControleXs; // mpImportarControles.mpImportarControleList();
    	//
		String diaSemana = "";
    	Integer contadorDiaUtil = 0;
    	//
		Calendar tIni = Calendar.getInstance();
		Calendar tEvt = Calendar.getInstance();
		Calendar tFim = Calendar.getInstance();
		//
		tIni.setTime(this.mpImportarControle.getDataImportar());
		tFim.add(Calendar.DAY_OF_MONTH, 50);
		
		for (Date dateX = tIni.getTime(); tIni.before(tFim); tIni.add(Calendar.DATE, 1),
																		dateX = tIni.getTime()) {
			//
			MpFeriado mpFeriado = this.mpFeriados.porDataFeriado(dateX);
			if (null == mpFeriado)
				diaSemana = MpAppUtil.diaSemana(dateX).toUpperCase();
			else
				diaSemana = "FERIADO";
			
			tEvt.setTime(dateX);
			// Trata feriado sabado e domingo !
			if (diaSemana.equals("FERIADO")
			||  diaSemana.equals("SÁBADO") 
			|| 	diaSemana.equals("DOMINGO"))
				assert(true);
			else {
				contadorDiaUtil++;
				
				if (contadorDiaUtil == 2)
					this.mpImportarControle.setDataControle(dateX);
				else
					if (contadorDiaUtil == 4) {
						//
						this.mpImportarControle.setDataAte(dateX);
						//
						break;
					}
			}
		}
		//
//    	System.out.println("MpRecebeTitulosBean.calculaData() - 001 ( " + 
//				this.mpImportarControle.getDataImportar() + " / " +
//				this.mpImportarControle.getDataControle() + " / " +
//				this.mpImportarControle.getDataAte());
    }	

    public void salvarReceber() {
    	//
    	if (null == this.mpImportarControle.getDataImportar()) {
    		MpFacesUtil.addInfoMessage("Informar... Data Importar !");
    		return;
    	}
    	//
		//sleep 5 seconds = 5000 
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			MpFacesUtil.addInfoMessage("Tread Interrompida !");
//		}
    	//
        FacesContext context = FacesContext.getCurrentInstance();
        //
        try {
			context.getExternalContext().redirect(
						"/mpProtesto/sentinel/cartorios/importacaos/mpRecebimento.xhtml" +
								"?dtiIC=" +	this.mpImportarControle.getDataImportarSDF() + 
								"&dtcIC=" +	this.mpImportarControle.getDataControleSDF() +
								"&dtaIC=" +	this.mpImportarControle.getDataAteSDF());
		} catch (IOException e) {
	    	MpFacesUtil.addInfoMessage("Error redirecionamento... contactar o Suporte !");
		}
    }
    
	public void handleFileUpload(FileUploadEvent event) {
    	//
    	if (null == this.mpImportarControle.getDataImportar()) {
    		MpFacesUtil.addInfoMessage("Informar... Data Importar !");
    		return;
    	}
		//
		try {
			//
			this.arquivoUpload = event.getFile();
			// 2016071802115103_AtoDigital_X.ATO
			// 01234567890
			String pathX = "";
			
			String pathAnoX = "";
			String pathMesX = "";
			String pathDiaX = "";
			//
			if ( this.arquivoUpload.getFileName().toUpperCase().indexOf(".ATO") >= 0) { 
				pathAnoX = this.arquivoUpload.getFileName().substring(0, 4);
				pathMesX = File.separator + this.arquivoUpload.getFileName().substring(4, 6);
				pathDiaX = File.separator + this.arquivoUpload.getFileName().substring(6, 8);
			} else
				pathX = sdf.format(this.mpImportarControle.getDataImportar()) + File.separator;
			//
//			System.out.println("MpRecebeTitulosBean.000 (pathX = " + pathX + "/PathAMDX = " + 
//																pathAnoX + pathMesX + pathDiaX);
			//
			ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
			//
//			ClassLoader classLoader = getClass().getClassLoader();
			//
			Path folder = null;
			Path filePath = null;

			Boolean indFile = false;
			//
			try {
//				this.file = new File(classLoader.getResource("importacao" + File.separator + 
//																			pathX).getFile());			
				//
				if ( this.arquivoUpload.getFileName().toUpperCase().indexOf(".ATO") >= 0) {
					// Trata pasta ANO...
					try {
						this.file = new File(extContext.getRealPath("//resources//importacao//" + pathAnoX));
						folder = Paths.get(this.file.getAbsolutePath());
						filePath = Files.createDirectory(folder);
					}
					catch(Exception e) {
				    	MpFacesUtil.addInfoMessage("Pasta/Diretório... (pathAnoX = " +
				    			extContext.getRealPath("//resources//importacao//" + pathAnoX + " /e = " + e));
					}
					// Trata pasta MES...
					try {
						this.file = new File(extContext.getRealPath("//resources//importacao//" + pathAnoX + 
																									pathMesX));
						folder = Paths.get(this.file.getAbsolutePath());
						filePath = Files.createDirectory(folder);
					}
					catch(Exception e) {
				    	MpFacesUtil.addInfoMessage("Pasta/Diretório... (pathAnoMesX = " +
				    						extContext.getRealPath("//resources//importacao//" +
				    										pathAnoX + pathMesX + " /e = " + e));
					}
					// Trata pasta DIA...
					try {
						this.file = new File(extContext.getRealPath("//resources//importacao//" + pathAnoX + 
																						pathMesX + pathDiaX));
						folder = Paths.get(this.file.getAbsolutePath());
						filePath = Files.createDirectory(folder);
					}
					catch(Exception e) {
				    	MpFacesUtil.addInfoMessage("Pasta/Diretório... (pathAnoMesDiaX = " +
				    							extContext.getRealPath("//resources//importacao//" +
				    							pathAnoX + pathMesX + pathDiaX + " /e = " + e));
					}
					//
				} else {
					//
					try {
						this.file = new File(extContext.getRealPath("//resources//importacao//" + pathX));
						folder = Paths.get(this.file.getAbsolutePath());
						filePath = Files.createDirectory(folder);
					}
					catch(Exception e) {
				    	MpFacesUtil.addInfoMessage("Pasta/Diretório... (pathX = " +
				    			extContext.getRealPath("//resources//importacao//" + pathX + " /e = " + e));
					}
				}
//				indFile = this.file.mkdir();
				//
//				System.out.println("MpRecebeTitulosBean.001 ( indFile = " + indFile + " / " +  
//									extContext.getRealPath("//resources//importacao//" + pathX));
			}
			catch(Exception e) {
		    	MpFacesUtil.addInfoMessage("XML Pasta/Diretório... não existe ! ( indFile = " +
		    		indFile + " / " + extContext.getRealPath("//resources//importacao//" + pathX));
		    	
//		    	System.out.println("XML Pasta/Diretório... não existe ! ( indFile = " +
//			    	indFile + " / " + extContext.getRealPath("//resources//importacao//" + pathX));
//		    	return;
			}		
			//	
			try {
				folder = Paths.get(this.file.getAbsolutePath());
			
				if (null == folder)
					filePath = Files.createDirectory(folder);
				//
				folder = Paths.get(this.file.getAbsolutePath() + File.separator + this.arquivoUpload.getFileName());
				filePath = Files.createFile(folder);
				//
				try (InputStream input = this.arquivoUpload.getInputstream()) {
					Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
				}
				//
//				System.out.println("MpRecebeTitulosBean.002 ( filePath = " + filePath.toString());  
				//
			}
			catch(Exception e) {
		    	MpFacesUtil.addInfoMessage("Erro Arquivo... (file = " +	folder.toString() + " /e = " + e);
			}
			//		
//			String filename = FilenameUtils.getBaseName(this.arquivoUpload.getFileName()); 
//			String extension = FilenameUtils.getExtension(this.arquivoUpload.getFileName());
//			
//			Path filePath = Files.createTempFile(folder, filename, "." + extension);
			//
//			InputStream in = new BufferedInputStream(this.arquivoUpload.getInputstream());
//
//
//			File file = new File(classLoader.getResource("importacao" + 
//									pathX + "X_" + this.arquivoUpload.getFileName()).getFile());					
//
//			FileOutputStream fout = new FileOutputStream(file);
//			//
//			while (in.available() != 0) {
//				fout.write(in.read());
//			}
//			//
//			fout.close();
			//
			MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoUpload.getFileName() + 
															" )... carregado com sucesso.");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			MpFacesUtil.addInfoMessage("Erro Upload Arquivo ( e= " + e); 
		}		
		//
	}

    public void estornarReceber() {
		//
		System.out.println("MpRecebeTitulosBean.estornarReceber() - 000 (Dt.ImportarId = " + 
																this.mpImportarControle.getId());
	}

    public void rowSelect() {
		//
		System.out.println("MpRecebeTitulosBean.rowSelect() - 000 (Selec.Size = " + 
														selectedMpImportarControles.size());
		this.trataIndVisivel();
	}
    public void rowUnSelect() {
		//
		System.out.println("MpRecebeTitulosBean.rowUnSelect() - 000 (Selec.Size = " + 
														selectedMpImportarControles.size());
		this.trataIndVisivel();
	}
    public void headerToggleSelect() {
		//
		System.out.println("MpRecebeTitulosBean.headerToggleSelect() - 000 (Selec.Size = " + 
														selectedMpImportarControles.size());
		this.trataIndVisivel();
	}
    
    public void estornarReceberConfirma() {
    	//
		System.out.println("MpRecebeTitulosBean.estornarReceberConfirma() - 000 (Dt.ImportarId = " 
							+ this.mpImportarControle.getDataImportar() + " / selecSize = " +
							this.selectedMpImportarControles.size());
		//
		if (null == this.mpImportarControle.getDataImportar()) {
    		MpFacesUtil.addInfoMessage("Informar... Data Importar !");
    		return;
    	}
		
		if (this.selectedMpImportarControles.size() == 0) {
    		MpFacesUtil.addInfoMessage("Nenhuma Data para estorno... selecioanda !");
    		return;
    	}

		// Iniciar Transação...
//		this.manager.getTransaction().begin();
		//
		Integer contadorTrailler = 0;
		Integer contadorTransacao = 0;
		Integer contadorHeader = 0;
		Integer contadorRemessa = 0;
		Integer contadorImportarControle = 0;
		//
//		List<MpImportarControle>  mpImportarControleDels = new ArrayList<MpImportarControle>();
		
		for (MpImportarControle mpImportarControleS : selectedMpImportarControles) {
			//
			System.out.println("MpRecebeTitulosBean.estornarReceber() ( id =" + 
																mpImportarControleS.getId());

//			MpImportarControle mpImportarControleN = mpImportarControles.porId(
//																mpImportarControleS.getId());
//			if (null == mpImportarControleN) continue;
			//
			// Trata Estorno Trailler ...
			//
			List<MpTrailler> mpTraillerList = mpTraillers.porMpImportarControle(
																			mpImportarControleS);
			for (MpTrailler mpTraillerX : mpTraillerList) {
				mpTraillers.remover(mpTraillerX);
				contadorTrailler++;
			}
			//
			// Trata Estorno Transacao ...
			//
			List<MpTransacao> mpTransacaoList = mpTransacaos.porMpImportarControle(
																			mpImportarControleS);
			for (MpTransacao mpTransacaoX : mpTransacaoList) {
				mpTransacaos.remover(mpTransacaoX);
				contadorTransacao++;
			}
			//
			// Trata Estorno Header ...
			//
			List<MpHeader> mpHeaderList = mpHeaders.porMpImportarControle(mpImportarControleS);
			for (MpHeader mpHeaderX : mpHeaderList) {
				mpHeaders.remover(mpHeaderX);
				contadorHeader++;
			}
			//
			// Trata Estorno Remessa ...
			//
			List<MpRemessa> mpRemessaList = mpRemessas.porMpImportarControle(
																		mpImportarControleS);
			for (MpRemessa mpRemessaX : mpRemessaList) {
				mpRemessas.remover(mpRemessaX);
				contadorRemessa++;
			}
			//
			// Trata Estorno Importar Controle ...
			//
			mpImportarControles.remover(mpImportarControleS);
			contadorImportarControle++;

			this.mpImportarControleList.remove(mpImportarControleS);
			//
			this.trataIndVisivel();

			// Efetuar Commit...
			// this.manager.getTransaction().commit();
			//
			MpFacesUtil.addInfoMessage("Estorno concluido ! (Data importar : "
					+ mpImportarControleS.getDataImportarSDF() 
					+ " /Cont.ImportarControle = " + contadorImportarControle
					+ " /Cont.Remessa = " + contadorRemessa 
					+ " /Cont.Trailler = " + contadorTrailler
					+ " /Cont.Transacao = " + contadorTransacao 
					+ " /Cont.Trailler = " + contadorTrailler);
		}
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	
	
    // ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public MpImportarControle getMpImportarControle() { return mpImportarControle; }
	public void setMpImportarControle(MpImportarControle mpImportarControle) {
												this.mpImportarControle = mpImportarControle; }

	public List<MpImportarControle> getMpImportarControleList() { return mpImportarControleList; }
	public void setMpImportarControleList(List<MpImportarControle> mpImportarControleList) {
										this.mpImportarControleList = mpImportarControleList; }

	public void trataIndVisivel() {
		//
		if (this.mpImportarControleList.size() > 0)
			this.indVisivel = true;
		else
			this.indVisivel = false;
	}
	
	public boolean getIndVisivel() { return indVisivel; }
	public void setIndVisivel(Boolean indVisivel) { this.indVisivel = indVisivel; }
	
	public List<MpImportarControle> getSelectedMpImportarControles() { 
														return selectedMpImportarControles; }
	public void setSelectedMpImportarControles(List<MpImportarControle> 
																selectedMpImportarControles) {
								this.selectedMpImportarControles = selectedMpImportarControles; }

    public UploadedFile getArquivoUpload() { return arquivoUpload; }
    public void setArquivoUpload(UploadedFile arquivoUpload) {
    														this.arquivoUpload = arquivoUpload; }
    	
}