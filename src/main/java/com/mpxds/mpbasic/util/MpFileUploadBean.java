package com.mpxds.mpbasic.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
@ManagedBean(name = "mpFileUploadBean")
public class MpFileUploadBean {
	//
    private UploadedFile fileX;
    
	private StreamedContent fotoContent = new DefaultStreamedContent();
	private byte[] fotoBytes;

    // ---
     
	public void handleFileUpload(FileUploadEvent event) {
		//
		try {
			this.fotoContent = new DefaultStreamedContent(event.getFile().getInputstream(),
														"image/jpeg", event.getFile().getFileName());
			
			this.fotoBytes = this.getFileContents(event.getFile().getInputstream());
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		System.out.println("MpFileUploadBean.handleFileUpload() ( " + this.fotoBytes.length);
	}

	private byte[] getFileContents(InputStream in) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int read = 0;
			bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
			bytes = bos.toByteArray();
			in.close();
			in = null;
			bos.flush();
			bos.close();
			bos = null;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}    
 
    // ---

    public UploadedFile getFileX() { return fileX; }
    public void setFileX(UploadedFile fileX) { this.fileX = fileX; }
    
	public StreamedContent getFotoContent() { return fotoContent; }

	public byte[] getFotoBytes() { return fotoBytes; }
	
	public boolean isFotoCapturada() { return getFotoContent() != null; }
 
}