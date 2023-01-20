package com.mpxds.mpbasic.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
//import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.util.MpGenerateBarCode;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
//@ManagedBean
@SessionScoped
public class MpGeraQRCodeBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{param.cerp}")
	private String numCerp;
	@ManagedProperty(value = "#{param.tipo}")
	private String tipo ;
	@ManagedProperty(value = "#{param.posX}")
	private String posX ;
	@ManagedProperty(value = "#{param.posY}")
	private String posY ;
	
	private StreamedContent arquivoContent = new DefaultStreamedContent();
	
	private UploadedFile fileX;	

	private String pathY;
	private String msgX;
		
	// ======================================
	
	private static final Logger logger = LogManager.getLogger(MpGeraQRCodeBean.class);
	
	//----------------
		
	public void preRender() {
		// --- logs debug
		if (logger.isDebugEnabled()) logger.debug("MpAlertaLogBean.preRender()");
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

		File fileB = new File(extContext.getRealPath(File.separator + "resources" +
															File.separator + "pdfs" + File.separator + "BASE.PDF"));
				
		this.pathY = fileB.getPath() + " | " + fileB.getAbsolutePath();
		//
		if (null == this.tipo) this.tipo = "null";
		
		if (null == this.posX) this.posX = "405";
		if (null == this.posY) this.posY = "710";

		if (Integer.parseInt(this.posX) == 0 || Integer.parseInt(this.posY) == 0) {
			this.msgX = "Pos.X/Y ... inválido! ( " + this.posX + " / " + this.posY;
			return;
		}
		//
		if (this.tipo.equals("pdf") || this.tipo.equals("jpeg") || this.tipo.equals("null"))
			assert(true); // nop
		else {
			this.msgX = "Tipo... inválido! ( " + this.tipo;
			return;
		}		
		this.trataGeracaoQRCode();
		//
	}

	public void trataGeracaoQRCode() {
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		
		String pathXX = extContext.getRealPath(extContext.getRealPath(File.separator + "resources" +
																		File.separator + "pdfs" + File.separator));
		
		String arquivoX = "";
		//
		if (this.tipo.equals("pdf")) {
			//
			if (null == this.numCerp) {
				MpFacesUtil.addInfoMessage("Num.CERP ... inválido!");
				return;
			}
			//
			arquivoX = "MpQrCode.pdf";
			
			MpGenerateBarCode.createPDF(pathXX + arquivoX, this.numCerp);
			//
			try {
//			    extContext.redirect("http://localhost:8080/mpProtesto/resources/pdfs/" + arquivoX);
			    extContext.redirect("http://www.mpxds.com/mpProtesto/resources/pdfs/" + arquivoX);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//
		if (this.tipo.equals("jpeg")) {
			//
			if (null == this.numCerp) {
				MpFacesUtil.addInfoMessage("Num.CERP ... inválido!");
				return;
			}
			//
		    arquivoX = "MpQrCode.jpeg";
		    
			MpGenerateBarCode.createJPEG(pathXX + arquivoX, this.numCerp);
			//
		    try {
//			    extContext.redirect("http://localhost:8080/mpProtesto/resources/pdfs/" + arquivoX);
			    extContext.redirect("http://www.mpxds.com/mpProtesto/resources/pdfs/" + arquivoX);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// -------- Trata Upload PDF ...
	
	public void handleFileUpload(FileUploadEvent event) {
		//
		if (null == this.numCerp || this.numCerp.isEmpty()) {
			this.msgX = "Num.CERP ... inválido!";
			System.out.println("MpGenerateBarCode.handleFileUpload() - 000 (Num.CERP ... inválido!");
//			return;
		}
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		
		File fileB = new File(extContext.getRealPath(File.separator + "resources" +
															File.separator + "pdfs" + File.separator + "BASE.PDF"));
		
		String pathX = fileB.getPath().substring(0, fileB.getPath().length() - 8);		
		//
		try {
			//
			this.fileX = event.getFile();
			
			this.arquivoContent = new DefaultStreamedContent(event.getFile().getInputstream(),
												"application/pdf", event.getFile().getFileName());
			//
//			String pathX = extContext.getRealPath("//resources//") + "pdfs" + File.separator;
			String arquivoX = event.getFile().getFileName();
			//
			System.out.println("MpGenerateBarCode.handleFileUpload() - 000 (PathX =  " + pathX + " ( ArqX.= " + arquivoX);
					
//			byte[] bytes = IOUtils.toByteArray(this.arquivoContent.getStream());
			
//			this.criaArquivo(bytes, pathX + arquivoX);
//			this.criaArquivo(this.fileX.getContents(), pathX + arquivoX);
			this.criaArquivo(this.fileX, pathX + arquivoX);
			
			MpGenerateBarCode.atualizaPDF(pathX, arquivoX, this.numCerp, Integer.parseInt(this.posX),
																		 Integer.parseInt(this.posY));
			
//		    extContext.redirect("http://localhost:8080/mpProtesto/resources/pdfs/out_" + arquivoX);
		    extContext.redirect("http://www.mpxds.com/mpProtesto/resources/pdfs/out_" + arquivoX);
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
	}
	
	public void criaArquivo(byte[] bytes, String pathArquivoX) {
		//
		FileOutputStream fos;
		
		try {
			fos = new FileOutputStream(pathArquivoX);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
		
	public void criaArquivo(UploadedFile fileX, String pathArquivoX) {
		//
	    InputStream input = null;
	    OutputStream output = null;

	    try {
		    input = fileX.getInputstream();
		    output = new FileOutputStream(new File(pathArquivoX));

		    IOUtils.copy(input, output);
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        IOUtils.closeQuietly(input);
	        IOUtils.closeQuietly(output);
	    }		
	}
	
	// ---------------------------

	public String getNumCerp() { return numCerp; }
	public void setNumCerp(String newNumCerp) { this.numCerp = newNumCerp; }

	public String getTipo() { return tipo; }
	public void setTipo(String newTipo) { this.tipo = newTipo; }

	public String getPosX() { return posX; }
	public void setPosX(String newPosX) { this.posX = newPosX; }

	public String getPosY() { return posY; }
	public void setPosY(String newPosY) { this.posY = newPosY; }
    
	public StreamedContent getArquivoContent() { return arquivoContent; }
    public void setArquivoContent(StreamedContent arquivoContent) { this.arquivoContent = arquivoContent; }
    
	public UploadedFile getFileX() { return fileX; }
	public void setFileX(UploadedFile fileX) { this.fileX = fileX; }    

	public String getPathY() { return pathY; }
	public void setPathY(String newPathY) { this.pathY = newPathY; }
	
	public String getMsgX() { return msgX; }
	public void setMsgX(String newMsgX) { this.msgX = newMsgX; }
	
}