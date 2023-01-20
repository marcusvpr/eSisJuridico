package com.mpxds.mpbasic.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.Serializable;

//import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.context.FacesContext;
//import javax.imageio.ImageIO;
import javax.inject.Named;

//import org.primefaces.event.CaptureEvent;
//import org.primefaces.event.FileUploadEvent;
//import org.primefaces.model.ByteArrayContent;
//import org.primefaces.model.DefaultStreamedContent;
//import org.primefaces.model.StreamedContent;
//import org.primefaces.model.UploadedFile;

//import com.mpxds.mpbasic.model.enums.MpArquivoAcao;

@Named
@ManagedBean(name = "mpFotoCameraBean")
@SessionScoped
public class MpFotoCameraBean implements Serializable {
	//
    private static final long serialVersionUID = 1L;
//	private StreamedContent fotoContent;
//	private byte[] fotoBytes;
//	private byte[] fotoContentUp;

	private UploadedFile uploadedFile;
    
	// -----------
	
//	public void limpar() {
//		this.fotoContent = null;
//		
//		this.fotoBytes = null;
//		//
////		System.out.println("MpFotoCameraBean.limpar()");
//	}
//	
//	public void aoCapturarFoto(CaptureEvent event) {
//		this.fotoBytes = event.getData();
//		
//		this.fotoContent = new ByteArrayContent(this.fotoBytes, "image/jpeg");
//	}
//
//	public StreamedContent getFotoContent() { return fotoContent; }
//
//	public byte[] getFotoBytes() { return fotoBytes; }
//	
//	public boolean isFotoCapturada() { return getFotoContent() != null; }
//	
//	public byte[] getFotoContentUp() { return fotoContentUp; }
//    public void setFotoContentUp(byte[] fotoContentUp) {
//    	if (null == fotoContentUp) {
//			BufferedImage originalImage;
//			try {
//				originalImage = ImageIO.read(new File("c:/temp/blank.jpg"));
//				// convert BufferedImage to byte array
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ImageIO.write(originalImage, "jpg", baos);
//				baos.flush();
//				
//				this.fotoContentUp = baos.toByteArray();
//				
//				baos.close();
//				//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
////			// convert byte array back to BufferedImage
////			InputStream in = new ByteArrayInputStream(imageInByte);
////			BufferedImage bImageFromConvert = ImageIO.read(in);
////
////			ImageIO.write(bImageFromConvert, "jpg", new File(
////													"c:/new-darksouls.jpg"));
//
////			this.fotoContentUp = "null".getBytes();
//    	} else
//    		this.fotoContentUp = fotoContentUp;
//    }
//    
//	public void handleFileUpload(FileUploadEvent event) {
//		//
//		try {
//			this.fotoContent = new DefaultStreamedContent(event.getFile().getInputstream(),
//													   "image/jpeg", event.getFile().getFileName());
//			this.fotoContentUp = getFileContents(event.getFile().getInputstream());
//			//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//		
//		System.out.println("MpFotoCameraBean.handleFileUpload() ( " + this.fotoContentUp.length);
//	}
//
//	private byte[] getFileContents(InputStream in) {
//		byte[] bytes = null;
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			int read = 0;
//			bytes = new byte[1024];
//
//			while ((read = in.read(bytes)) != -1) {
//				bos.write(bytes, 0, read);
//			}
//			bytes = bos.toByteArray();
//			in.close();
//			in = null;
//			bos.flush();
//			bos.close();
//			bos = null;
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
//		return bytes;
//	}
//
	// -----------------------
	
    public UploadedFile getUploadedFile() { return uploadedFile; }
    public void setFile(UploadedFile uploadedFile) { this.uploadedFile = uploadedFile; }
     
    public void uploadXXX() {    	
    	//
        if (this.uploadedFile != null) {
            FacesMessage message = new FacesMessage("Succeso ( ", this.uploadedFile.getFileName() +
            															" ) ... carregado.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
		//		
		System.out.println("MpFotoCameraBean.upload() (uploadedFile = " + this.uploadedFile);
    }

    public void handleFileUploadXXX(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() +
        																		" is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
		//		
		System.out.println("MpFotoCameraBean.handleFileUpload() (file = " + 
																	event.getFile().getFileName());
    }    
}
