package com.mpxds.mpbasic.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.repository.MpClientes;

@WebServlet(urlPatterns = "/fotoCliente")
public class MpFotoClienteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	@Inject
	private MpClientes mpClientes;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Long idCliente = Long.valueOf(request.getParameter("mpCliente"));
		
		MpCliente mpCliente = mpClientes.porId(idCliente);
		
		response.setContentType("image/jpeg");
		
		IOUtils.write(mpCliente.getMpArquivoBD().getArquivo(), response.getOutputStream());
	}

}
