package com.mpxds.mpbasic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;
import org.datacontract.schemas._2004._07.ecartorio_service.Pedido;
import org.tempuri.MpHeaderHandlerResolver;

import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.MpAppUtil;

import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
//@ManagedBean
public class MpECartorioRJBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	@Inject
	private MpSeguranca mpSeguranca;
    
	private String scOficServentiaECartorioRJ = "";
	
	private String arquivoX;
	private String pathX;
		
	private Date dataInicio;
	private Date dataFim;
	
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String respostaWS;
    
	private StreamedContent fileSC;
	
	private Boolean indFile = false;

	private File fileX;

	//----------------
	
	public void preRender() {
		//
		
		// Trata Configuração Sistema ...
		// ======================================
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficServentiaECartorioRJ");
		if (null == mpSistemaConfig)
			this.scOficServentiaECartorioRJ = "????";
		else
			this.scOficServentiaECartorioRJ = mpSistemaConfig.getValor();
		//
		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(new Date());
		dataIni.set(Calendar.MONTH, 0);
		dataIni.set(Calendar.DAY_OF_MONTH, 1);
		
		this.dataInicio = dataIni.getTime();		
		this.dataFim = new Date();
		//		
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

		this.pathX = extContext.getRealPath(File.separator + "resources" + File.separator + 
																			"importacao" + File.separator);
		this.arquivoX = mpSeguranca.getLoginUsuario() + "_arrayOfPedido.txt";

		System.out.println("MpECartorioRJBean.preRender() - Path + Arquivo = " + this.pathX + " / " + this.arquivoX);
	}

	public void executaWS() throws Exception {
		//
		System.out.println("MpECartorioRJBean.executaWS() - 000");

        org.tempuri.Service serviceX = new org.tempuri.Service();
        
        MpHeaderHandlerResolver mpHandlerResolver = new MpHeaderHandlerResolver();
        
        serviceX.setHandlerResolver(mpHandlerResolver);
        
        org.tempuri.IService serviceInterfaceX = serviceX.getPort(org.tempuri.IService.class,
        																new javax.xml.ws.soap.AddressingFeature());
         
		ArrayOfPedido arrayOfPedido = serviceInterfaceX.listarPedidos(this.scOficServentiaECartorioRJ, "", 
										"PAGAMENTO REALIZADO", sdf.format(this.dataInicio), sdf.format(this.dataFim),
										"", "", "", "", "", "", "", "");
		//
		JAXBContext jaxbContext = JAXBContext.newInstance(Pedido.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		this.fileX = new File(this.pathX + File.separator + this.arquivoX);

		// Marshal the employees list in console
		OutputStream outputStreamX = new FileOutputStream(this.fileX);
		
		jaxbMarshaller.marshal(arrayOfPedido, outputStreamX);

		InputStream inputStreamX = new FileInputStream(this.fileX);
		
		Charset charsetX = StandardCharsets.UTF_8;
		
//		jaxbMarshaller.marshal(arrayOfPedido, System.out);

		// //Marshal the employees list in file
		// jaxbMarshaller.marshal(arrayOfPedido, new
		// File("c:/temp/employees.xml"));
		//
		String msgWS = MpAppUtil.convertIS(inputStreamX, charsetX);
		
		this.respostaWS = "Número Pedidos retornado pelo WebService = ( " + arrayOfPedido.getPedido().size() +
																" )\n\n" + msgWS.substring(0, msgWS.length() / 2);
		this.indFile = true;
		
		System.out.println("MpECartorioRJBean.executaWS() - RespostaWS = " + this.respostaWS);
		//
	}

	public void FileDownloadView() throws FileNotFoundException {
		//
//		InputStream streamX = new FileInputStream(this.fileX);
//        
//        this.fileSC = new DefaultStreamedContent(streamX, "text/plain", "down_" + this.arquivoX);

//        System.out.println("MpECartorioRJBean.FileDownloadView() " + this.fileSC.getName());
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        response.setHeader("Content-Disposition", "attachment;filename=pedidos.xml");
        response.setContentLength((int) this.fileX.length());
        
        FileInputStream input= null;
        //
        try {
            @SuppressWarnings("unused")
			int i= 0;
            input = new FileInputStream(this.fileX);  
            byte[] buffer = new byte[1024];
            while ((i = input.read(buffer)) != -1) {  
                response.getOutputStream().write(buffer);  
                response.getOutputStream().flush();  
            }               
            facesContext.responseComplete();
            facesContext.renderResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) {
                    input.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }        
        //
    }

	public void sairWS() {
		//
		this.respostaWS = "";
		
		this.indFile = false;

		System.out.println("MpECartorioRJBean.sairWS()");
		//
		FacesContext context = FacesContext.getCurrentInstance();
		//
		try {
			context.getExternalContext().redirect("/mpProtesto/dashboardRS");
			//
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}	
	
    // ---
    
	@NotNull
	public String getScOficServentiaECartorioRJ() { return scOficServentiaECartorioRJ; }
	public void setScOficServentiaECartorioRJ(String scOficServentiaECartorioRJ) { 
												this.scOficServentiaECartorioRJ = scOficServentiaECartorioRJ; }
    
	@NotNull
	public Date getDataInicio() { return dataInicio; }
	public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

	@NotNull
	public Date getDataFim() { return dataFim; }
	public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
	
	public String getRespostaWS() { return respostaWS; }
	public void setRespostaWS(String respostaWS) { this.respostaWS = respostaWS; }

    public String getArquivoX() { return arquivoX; }
	public void setArquivoX(String arquivoX) { this.arquivoX = arquivoX; }
	 
    public StreamedContent getFileSC() { return fileSC; }    

    public Boolean getIndFile() { return indFile; }
	public void setIndFile(Boolean indFile) { this.indFile = indFile; }
    
}