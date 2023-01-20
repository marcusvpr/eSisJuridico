package com.mpxds.mpbasic.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.repository.MpArquivoBDs;

// @ManagedBean
@Named
@ApplicationScoped
public class MpArquivoStreamer {
	//
    @Inject
    private MpArquivoBDs mpArquivoBDs;
    
    private StreamedContent arquivo  = new DefaultStreamedContent();

    public StreamedContent getArquivo() throws IOException {
    	//
        FacesContext context = FacesContext.getCurrentInstance();

//        DefaultStreamedContent defaultStreamedContent = new DefaultStreamedContent();
        
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will
        	// generate right URL.
        	System.out.println("MpImageStreamer 000");
        	
            return this.arquivo;
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            //
        	System.out.println("MpImageStreamer 001 - mpArquivoBD ( ID = " + id);
        	//
        	if (id.isEmpty())
                return this.arquivo;
        	//
            MpArquivoBD mpArquivoBD = mpArquivoBDs.porId(Long.valueOf(id));

            if (null == mpArquivoBD) {
            	//
            	System.out.println("MpImageStreamer 001 - mpArquivoBD ( NULL");
            	
                return this.arquivo;
            } else
            	if (null == mpArquivoBD.getArquivo()) {
                	System.out.println("MpImageStreamer 001-0 - mpArquivoBD.getArquivo() ( NULL");
                	
                    return this.arquivo;
            	} else
            		System.out.println("MpImageStreamer 001 ( " + mpArquivoBD.getArquivo().length);
            //
            this.arquivo = new DefaultStreamedContent(new ByteArrayInputStream(mpArquivoBD.
            																			getArquivo()));
        }
        //
        return this.arquivo;
    }
    
	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;		
	}
    
//    public byte[] getArquivo(Long id) {
//        return mpArquivoBDs.porId(id).getArquivo();
//    }
}