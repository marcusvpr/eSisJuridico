<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%> 

<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page import="com.mpxds.mpbasic.model.MpAlarme" %>
<%@ page import="com.mpxds.mpbasic.repository.MpAlarmes" %>
<%@ page import="com.mpxds.mpbasic.repository.filter.MpAlarmeFilter" %>
<%@ page import="com.mpxds.mpbasic.util.MpAppUtil" %>

<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<jsp:useBean id="mpAlarme" scope="session" class="com.mpxds.mpbasic.model.MpAlarme" />
<jsp:useBean id="mpAlarmes" scope="session" class="com.mpxds.mpbasic.repository.MpAlarmes" />

<html>
 <head>
  	<title>Acesso MpAlarme ...</title>
 </head>
  <body>
  	<h3>Acesso MpAlarme ...</h3>
  
  	<% 
		ApplicationContext context = new ClassPathXmlApplicationContext(
													"./context/mpxds-applicationContext.xml");

		MpAlarmeFilter mpAlarmeFilter = MpAppUtil.capturaAlarme(new Date());

		mpAlarmeFilter.setIndAtivo(true);
  	
//  		MpAlarmes mpAlarmes = (MpAlarmes) context.getBean("mpAlarmes");
  		
  		List<MpAlarme> mpAlarmeList = mpAlarmes.filtrados(mpAlarmeFilter);
  		
  	%>
    
    <h5><%=context.getApplicationName() %></h5>
    <h5><%=mpAlarmeList.size() %></h5>
    
 </body>
</html>