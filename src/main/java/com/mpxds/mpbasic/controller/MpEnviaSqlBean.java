package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mpxds.mpbasic.model.log.MpSistemaLog;
import com.mpxds.mpbasic.service.MpSistemaLogService;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@ManagedBean
@ViewScoped
public class MpEnviaSqlBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
		
	private MpSistemaLogService mpSistemaLogService;
	
    private String comandoSQL;
    private String resultSQL;
    private Boolean indResultSQL = true ;

	private Connection con = null;
	private Statement statement;
	//
    // Want to permit the SHUTDOWN SQL command in tearDown() to fail iff
    // the test method run has tested SHUTDOWN.
	//
    private boolean indShutdown = false;
    //
    private String msgErro = "OK";
    private String result = "";
    
    /** @todo fredt - need to define additional identifiers to use for all cases of expect */
    private static final int SQL_ABORT   = -1234;
    private static final int SQL_INITIAL = -1233;    
       
	private static final Logger logger = LogManager.getLogger(MpEnviaSqlBean.class);

	// -------------------------------- Inicio ------------------------------------

	public MpEnviaSqlBean() {
		//
		System.out.println("MpEnviaSqlBean() - Entrou.000"); 
		//
		this.mpSistemaLogService = MpCDIServiceLocator.getBean(
														MpSistemaLogService.class);
	}
	
    public void executaSql() {
		//
		System.out.println("MpEnviaSqlBean.executaSql() - Entrou.000"); 

		// --- logs debug ---
		if (logger.isDebugEnabled()) logger.debug("MpEnviaSqlBean.onSubmit()");
        //
    	if (null==comandoSQL || comandoSQL.isEmpty()) {
    		//
    		MpFacesUtil.addInfoMessage("Informar comando SQL !");
    		//
    		return;
    	}
    	//
    	if (comandoSQL.toLowerCase().indexOf("alter table") >= 0 ||
    		comandoSQL.toLowerCase().indexOf("create table") >= 0 ||
    		comandoSQL.toLowerCase().indexOf("drop table") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("update") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("delete from") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("insert into") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("grant") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("revoke") >= 0 ||
    	    comandoSQL.toLowerCase().indexOf("select") >= 0 ||
    		comandoSQL.toLowerCase().indexOf("set table") >= 0) {
    		this.result = "";
    	} else {
    		//
    		MpFacesUtil.addInfoMessage("Informar um comando SQL válido ( " +
    							"ALTER CREATE DROP UPDATE INSERT DELETE SET SELECT) !");
    		//
    		return;
    	}
    	//
        try {
    	    Class.forName("org.hsqldb.jdbcDriver").newInstance();
            //
			con = DriverManager.getConnection(
										"jdbc:hsqldb:file:~/db/mpProtesto/mpProtestoDB", "sa", "");
			//
	        statement = con.createStatement();
	        //
	        this.execSQL("SET AUTOCOMMIT false", 0);
	        //
	        this.execSQL(comandoSQL, 0);
        	//
        	String mensagem = "MpEnviaSqlBean.Executado - msgErro = ( " + msgErro + " )\n" + 
				  	 								" ( comandoSQL = " + comandoSQL + " )\n" + 
				  	 								" ( result = " + result + " )";
        	//
   	      	this.resultSQL = mensagem;

   	      	this.indResultSQL = false;
	        //
			// logs exception
			logger.error(mensagem);
        	//
        	// ------ Grava LOGS (HSQL modo file x Erro LOCK Startup TOMCAT !?
			MpSistemaLog mpSistemaLog = new MpSistemaLog();
        	//
			mpSistemaLog.setDataCriacao(new Date());
			mpSistemaLog.setCodigo("INFO.Level_1");
			if (mensagem.length() > 200)
				mpSistemaLog.setMensagem(mensagem.substring(0,200));
			else
				mpSistemaLog.setMensagem(mensagem);
        	//			
			mpSistemaLogService.salvar(mpSistemaLog);
        	//----------------------------------------
        	//
	        execSQL("COMMIT", 0);
	        //
		}
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

    public void onShowExemploSql() {
		//
		System.out.println("MpEnviaSqlBean.onShowExemploSql() - Entrou.000"); 

    	//
        // Alter Table commands
    	//
    	MpFacesUtil.addInfoMessage("Exemplos SQL: \n\n" + 
        		"ALTER TABLE tabela RENAME TO tabelaRenamed \n" +  
        		"ALTER TABLE tabela ADD CONSTRAINT con1 CHECK (i6 > 4) \n" +
        		"ALTER TABLE tabela ADD COLUMN vco1 VARCHAR(NN) \n" +
        		"ALTER TABLE tabela DROP COLUMN vco1 \n" +
        		"ALTER TABLE tabela ADD COLUMN vco1 VARCHAR(NN) \n" +
        		"ALTER TABLE tabela ALTER COLUMN vco1 RENAME TO j1 \n" +
        		"ALTER TABLE tabela DROP CONSTRAINT con1 \n" + 
        		"ALTER TABLE tabela DROP CONSTRAINT tstfk \n" +
        		"ALTER TABLE tabela ADD CONSTRAINT tstfk FOREIGN KEY(i7) REFERENCES primarytbl (i8) \n" +
        		"ALTER TABLE tabela ADD CONSTRAINT ucons9 UNIQUE (i9) \n\n" +
        		// Drop table command
        		"DROP TABLE tabela \n\n" +
        		// Set table readonly command
        		"SET TABLE tabela READONLY true \n" +
        		"SET TABLE tabela READONLY false \n\n" +
        		// Create table commands
        		"CREATE TABLE tabela (i INT, vc VARCHAR) \n" +
        		"CREATE CACHED TABLE tabela (i INT, vc VARCHAR) \n" +
        		"CREATE TABLE tabela (i6 INT, vc6 VARCHAR, CONSTRAINT uconsz UNIQUE(i6)) \n" +
        		"CREATE TABLE tabela (i7 INT, vc7 VARCHAR, "
        				+ "CONSTRAINT tstfkz FOREIGN KEY (i7) REFERENCES primarytbl (i8)) \n\n" +
        		// Update command
        		"UPDATE tabela SET vc = 'eleven' WHERE i = 1 \n\n" +
        		// delete
        		"DELETE FROM tabela WHERE i = 1 \n\n" +
        		// grant, revoke
        		"GRANT ALL ON tabela TO tstuser \n" +
        		"REVOKE ALL ON tabela FROM tstuser");
        //
    }
    
    protected void tearDown() throws Exception {
    	//
        // Shut down to destroy all of the DB objects (only works because
        // it's an in-memory instance.
        execSQL("SHUTDOWN", indShutdown);
        //
        if (con != null) { con.close();  }
        //
    }

    private void execSQL(String s, boolean ignoreError) throws SQLException {
    	//
        try {
        	if (comandoSQL.toLowerCase().indexOf("select") >= 0) {
        		//
        		ResultSet rs = statement.executeQuery(comandoSQL);
        		//
        		Integer cont = 0;
        		//
        		result = "";
        		//
       	      	while (rs.next()) {
        	      	cont++;
        	        result = result + "\n (" + cont + ")";
        	        //
        	        for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
        	        	result = result + "\n" + rs.getObject(j);
        	        }
       	      	}
       	      	//
       	      	this.resultSQL = result;

       	      	this.indResultSQL = false;
        	    // close the objects
        	    rs.close();
        	} else {
        		//
        		statement.execute(s);
        		//
        		statement.getUpdateCount();
        	}
        }
        catch (SQLException se) {
        	//
        	msgErro = se.toString();
        	//
            if (!ignoreError) {
                throw se;
            }
            //else System.err.println("FAILURE of (" + s + ')');
        }
    }

    private void execSQL(String m, String s, int expect) {
    	//
        int retval = SQL_INITIAL;
        //
        try {
        	if (comandoSQL.toLowerCase().indexOf("select") >= 0) {
        		//
        		ResultSet rs = statement.executeQuery(comandoSQL);
        		//
        		Integer cont = 0;
        		//
        		result = "";
        		//
       	      	while (rs.next()) {
        	      	cont++;
        	        result = result + "\n (" + cont + ")";
        	        //
        	        for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
        	        	result = result + "\n" + rs.getObject(j);
        	        }
       	      	}
       	      	//
       	      	this.resultSQL = result;

       	      	this.indResultSQL = false;
       	      	
       	      	// close the objects
        	    rs.close();
        	} else {
        		//
        		statement.execute(s);
        		//
        		statement.getUpdateCount();
        	}
        }
        catch (SQLException se) {
            retval = SQL_ABORT;
            //
        	msgErro = SQL_ABORT + " / " + se.toString();
        }
    }

/** @todo fredt - this method body seems to be incorrect */
    private void execSQL(String s, int expect) {
        execSQL(s, s, expect);
    }

    private int queryRowCount(String query) throws SQLException {
    	//
        int count = 0;
        //
        if (!statement.execute(query)) { return count; }
        //
        ResultSet rs = statement.getResultSet();
        //
        try {
            while (rs.next()) {
                count++;
            }
        }
        finally {
            rs.close();
        }
        //
        return count;
    }

    private int tableRowCount(String tableName) throws SQLException {
    	//
        String query = "SELECT count(*) FROM " + tableName;
        //
        if (!statement.execute(query)) { return 0; }
        //
        ResultSet rs = statement.getResultSet();
        //
        try {
            if (!rs.next()) {
                throw new SQLException("0 rows returned by (" + query + ')');
            }
            //
            int count = rs.getInt(1);
            //
            if (rs.next()) {
                throw new SQLException("> 1 row returned by (" + query + ')');
            }
            //
            return count;
        }
        finally {
            rs.close();
        }
        //throw new Exception("Failed to get rowcount for " + tableName);
    }

    // -------------------------------------
	   
    public String getComandoSQL() { return this.comandoSQL; }
    public void setComandoSQL(String newComandoSQL) { this.comandoSQL = newComandoSQL; }

    public String getResultSQL() { return this.resultSQL; }
    public void setResultSQL(String newResultSQL) { this.resultSQL = newResultSQL; }

    public Boolean getIndResultSQL() { return this.indResultSQL; }
    public void setIndResultSQL(Boolean newIndResultSQL) { this.indResultSQL = newIndResultSQL; }

}