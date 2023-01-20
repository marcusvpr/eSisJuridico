package com.mpxds.mpbasic.util.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.jdbc.Work;

import com.mpxds.mpbasic.model.dto.sisJuri.MpObjetoProcessoDTO;

// ---

public class MpExecutorRelatorioSJ implements Work {
	//
	private List<MpObjetoProcessoDTO> mpObjetoProcessoDTOList;
	
	private String usuario;
	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	
	private boolean relatorioGerado;
	
	// ---
	
	public MpExecutorRelatorioSJ(String usuario, String caminhoRelatorio, HttpServletResponse response,
								List<MpObjetoProcessoDTO> mpObjetoProcessoDTOList,
								Map<String, Object> parametros, String nomeArquivoSaida) {
		//
		this.usuario = usuario;
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.mpObjetoProcessoDTOList = mpObjetoProcessoDTOList;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		
		if (this.nomeArquivoSaida.toUpperCase().indexOf("PDF") >= 0)
			assert(true); // nop
		else
			this.parametros.put(JRParameter.IS_IGNORE_PAGINATION, true);
		//
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		//
		try {
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
			//
			ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
						
			String pathX = extContext.getRealPath("//resources//relatorios//");
			String arquivoX = pathX + this.usuario.trim().toUpperCase() + "_" + this.nomeArquivoSaida;
			
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, 
								new JRBeanCollectionDataSource(this.mpObjetoProcessoDTOList));
			//
			this.relatorioGerado = print.getPages().size() > 0;			
			//
			if (this.relatorioGerado) {
				//
				if (this.nomeArquivoSaida.toUpperCase().indexOf("PDF") >= 0) {
					//
					response.setHeader("Content-Disposition", "attachment; filename=\"" + arquivoX + "\"");
					response.setContentType("application/pdf");
					JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
				} else {
					//
					response.setHeader("Content-Disposition", "attachment; filename=\"" + 
											this.usuario.trim().toUpperCase() + "_" + this.nomeArquivoSaida + "\"");
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // XLSx
				       
				    JRXlsxExporter docxExporter = new JRXlsxExporter();

				    docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				    docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
				    docxExporter.exportReport();
				       
//					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // XLSx
//
//					OutputStream output = new ByteArrayOutputStream();
//
//				    JRXlsxExporter docxExporter = new JRXlsxExporter();
//				    
//				    docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//				    docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
//				    docxExporter.exportReport();
//				    
//				    InputStream stream = new ByteArrayInputStream(((ByteArrayOutputStream) output).toByteArray());
//				    StreamedContent file = new DefaultStreamedContent(stream, "application/xml", arquivoX);
//				    // return file;					
//
//					File outputFile = new File(this.usuario.trim().toUpperCase() + "_" + this.nomeArquivoSaida);
//			        
//					SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
//					
//					configuration.setDetectCellType(true);//Set configuration as you like it!!
//					configuration.setCollapseRowSpan(false);
//
//					JRXlsxExporter exporter = new JRXlsxExporter();
//					
//					exporter.setExporterInput(new SimpleExporterInput(print));
//					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));					
//					
//					exporter.setConfiguration(configuration);
//					exporter.exportReport();
				}
				//	            
				System.out.println("MpExecutorRelatorioSJ.execute() - ( response.status = " + 
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
