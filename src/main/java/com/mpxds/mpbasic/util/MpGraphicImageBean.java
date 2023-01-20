package com.mpxds.mpbasic.util;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import com.mpxds.mpbasic.model.MpChamado;
import com.mpxds.mpbasic.repository.MpChamados;

@Named
@ApplicationScoped
public class MpGraphicImageBean {

	@Inject
	private MpChamados mpChamados;
	
	// ---
	
    public InputStream getLogo() {
        // Note: this is a dummy example. In reality, you should be able to take e.g. a Long argument as ID and then
        // return the desired byte[] content from some service class by given ID.
        return Faces.getResourceAsStream("/resources/images/blank.gif");
    }

    public byte[] getImageByte(byte[] imageByteParam) {
    	//
    	byte[] imageByte = null;
		try {
			imageByte = Utils.toByteArray(Faces.getResourceAsStream("/resources/images/blank.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//    	System.out.println("MpGraphicImageBean.getImageByte()- Entrou 000" + imageByteParam);
    	//
    	if (null == imageByteParam)
    		return imageByte;
    	else
    		imageByte = imageByteParam; 
    	//
    	return imageByte;
    }

    public byte[] getContent(String entidade, Long id) {
        // Note: this is a dummy example. In reality, you should be able to return the desired byte[] content from some
        // service class by given ID.
    	byte[] imageByte = null;
		try {
			imageByte = Utils.toByteArray(Faces.getResourceAsStream("/resources/images/blank.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//
    	if (null == id)
        	return imageByte;
    	else {
    		if (entidade.equals("MpChamado")) {
    			MpChamado mpChamado = mpChamados.porId(id);
    			if (null == mpChamado)
    		    	return imageByte;
    			else
    				imageByte = mpChamado.getArquivoBD();
    		}
    	}
    	//
    	return imageByte;
//   	return Utils.toByteArray(Faces.getResourceAsStream("/resources/layout/img/OmniFaces-logo-90x90-" + IMAGES.get(id) + ".png"));
    }
    
    public InputStream getSvgLogo() {
        // Note: this is a dummy example. In reality, you should be able to take e.g. a Long argument as ID and then
        // return the desired byte[] content from some service class by given ID.
        return Faces.getResourceAsStream("/resources/images/blank.gif");
    }

}
