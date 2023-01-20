package com.mpxds.mpbasic.util.jsf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
//import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.log.MpErrorLog;
//import com.mpxds.mpbasic.repository.MpErrorLogs;

public class MpJsfExceptionHandler extends ExceptionHandlerWrapper {

	private static Log log = LogFactory.getLog(MpJsfExceptionHandler.class);

	private ExceptionHandler wrapped;

//	@Inject
//	private MpErrorLogs mpErrorLogs = new MpErrorLogs();

	// ---

	public MpJsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable exception = context.getException();
			MpNegocioException negocioException = getNegocioException(exception);

			boolean handled = false;
			//
			try {
				if (exception instanceof ViewExpiredException) {
					//
					handled = true;

					redirect("/");
				} else if (negocioException != null) {
					//
					handled = true;

					MpFacesUtil.addErrorMessage(negocioException.getMessage());
				} else {
					handled = true;

					log.error("Erro de sistema: " + exception.getMessage(), exception);
					redirect("/sentinel/mpError.xhtml");
				}
			} finally {
				if (handled) {
					// ========
					this.trataMpErrorLog (exception);
					// ========
					events.remove();
				}
			}
		}
		//
		getWrapped().handle();
	}

	private void trataMpErrorLog (Throwable exception) {
		// ============================================
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MpErrorLog mpErrorLog = new MpErrorLog();
		
		mpErrorLog.setCreatedDate(new Date());
		mpErrorLog.setClassName(exception.getStackTrace()[0].getClassName());
		mpErrorLog.setMethodName(exception.getStackTrace()[0].getMethodName());
		mpErrorLog.setLineNumber(exception.getStackTrace()[0].getLineNumber());
		
		String message = exception.getMessage();
		
		if (message.length() > 120)
			message = message.substring(0,120);
		
		message.replace("[", "(");
		message.replace("]", ")");
		message.replace("{", "(");
		message.replace("}", ")");
		
		mpErrorLog.setMessage(message);

		/**
		 * Error type. Can be evaluated for showing different icons
		 * for the error log entry<br>
		 * -----------------------------------<br>
		 * 1 = DB exception. <br>
		 * 2 = Other exception.<br>
		 * 3 = ARITHMETIC exception <br>
		 * 4 = ZKoss UI/JSF exception <br>
		 * 5 = JSF View Expired exception <br>
		 */
		if (exception instanceof SQLException) {
			mpErrorLog.setSqlState(((SQLException) exception).getSQLState());
			mpErrorLog.setErrorCode(Integer.toString(((SQLException) exception).getErrorCode()));
			mpErrorLog.setType(1);
		} else if (exception instanceof ArithmeticException)
			mpErrorLog.setType(3);
		else if (exception instanceof ViewExpiredException)
			mpErrorLog.setType(5);
		else if (exception instanceof FacesException)
			mpErrorLog.setType(4);
		else
			mpErrorLog.setType(2);
		//
		mpErrorLog.setTenantId("0");
		//
		// this.mpErrorLogs.guardar(mpErrorLog);
		// ===========================================
		//
		try {
	      // load the driver into memory
	      Class.forName("org.hsqldb.jdbcDriver");
	      // create a connection. The first command line parameter is assumed to
	      // be the directory in which the .dbf files are held
	      Connection conn = DriverManager.
	    		  					getConnection("jdbc:hsqldb:file:~/db/mpProtesto/mpProtestoDB");
	      // create a Statement object to execute the query with
	      Statement stmt = conn.createStatement();
	      // execute a query
	      String sql = "INSERT INTO mp_error_log (erro_created_date, erro_classname, " +
	    		  	" erro_methodname, erro_line, erro_message, erro_type, ind_ativo, ind_exclusao, tenant_id) VALUES (" + 
	    			"'" + df.format(mpErrorLog.getCreatedDate()) + "', " +
	  				"'" + mpErrorLog.getClassName() + "', " +
	  				"'" + mpErrorLog.getMethodName() + "', " +
					"'" + mpErrorLog.getLineNumber() + "', " +
	  				"'" + mpErrorLog.getMessage() + "', " +
	  					  mpErrorLog.getType() + ", " + 
	  					  mpErrorLog.getIndAtivo() + ", " + 
	  					  mpErrorLog.getIndExclusao() + ", " + 
	  					  mpErrorLog.getType() + ", " + 
	  				"'" + mpErrorLog.getTenantId() + "')";
	      //
//	      System.out.println("MpJsfExceptionHandler.trataMpErrorLog() (Sql = " + sql);
	      //
	      stmt.executeUpdate(sql);
	      // read the data and put it to the console
	      // close the objects
	      stmt.close();
	      conn.close();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	private MpNegocioException getNegocioException(Throwable exception) {
		if (exception instanceof MpNegocioException) {
			return (MpNegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}
		//
		return null;
	}

	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();

			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}