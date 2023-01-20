package com.mpxds.mpbasic.util.report;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

//import net.sf.jasperreports.engine.JRExporter;
//import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.hibernate.jdbc.Work;

//import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

// ---

public class MpExecutorRelatorio implements Work {
	//
	private String usuario;
	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	
	private boolean relatorioGerado;
	
	// ---
	
	public MpExecutorRelatorio(String usuario, String caminhoRelatorio, HttpServletResponse response, 
								Map<String, Object> parametros, String nomeArquivoSaida) {
		//
		this.usuario = usuario;
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		//
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			//
			ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
						
			String pathX = extContext.getRealPath("//resources//relatorios//");
//			Path folder = null;
//			Path filePath = null;
//			folder = Paths.get(this.file.getAbsolutePath()); // File.separator
//			filePath = Files.createDirectory(folder);
//			try {
//				this.file = new File(extContext.getRealPath(
//												"//resources//importacao//" + pathX));
//				folder = Paths.get(this.file.getAbsolutePath());
//				filePath = Files.createDirectory(folder);
//			}
//			catch(Exception e) {
//		    	MpFacesUtil.addInfoMessage("Pasta/Diretório... (pathX = " +
//		    			extContext.getRealPath("//resources//importacao//" + pathX +
//		    															" /e = " + e));
//			}

			String arquivoX = pathX + this.usuario.trim().toUpperCase() + "_" + this.nomeArquivoSaida ;
			
//			OutputStream saida = new FileOutputStream(arquivoX);
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream,
															 this.parametros, connection);
			//
			this.relatorioGerado = print.getPages().size() > 0;			
			//
			if (this.relatorioGerado) {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + arquivoX + "\"");
				//
				JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
				//
//	            JRExporter exporter = new JRPdfExporter();
//	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, saida);
//	            exporter.exportReport();
				
//				// --------------
//				JasperExportManager.exportReportToPdfStream(print, saida);
//	            
//	            FacesContext facesContextX = (FacesContext) FacesContext.getCurrentInstance();
//	            HttpServletResponse responseX = (HttpServletResponse) facesContextX.
//	            												getExternalContext().getResponse();
//	            responseX.setContentType("application/pdf");
//				responseX.setHeader("Content-Disposition", "attachment; filename=\"" 
//																				+ arquivoX + "\"");
//				// ServletOutputStream servletOutputStream = responseX.getOutputStream();
//				JasperExportManager.exportReportToPdfStream(print, responseX.getOutputStream());
//	            responseX.flushBuffer();
//	            facesContextX.responseComplete();
//	            // ----------------
	            
				System.out.println("MpExecutorRelatorio.execute() - ( response.status = " + 
									response.getStatus() + " / Pages = " + print.getPages().size());
			}
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		}
	}

	public boolean isRelatorioGerado() {
		//
		return relatorioGerado;
	}

}
