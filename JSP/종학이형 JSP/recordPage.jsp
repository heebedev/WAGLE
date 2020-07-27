<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	int wpReadPage = Integer.parseInt(request.getParameter("wpReadPage"));
	int wpSeqno = Integer.parseInt(request.getParameter("wpSeqno"));

		
//------

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
	 	String WhereDefault = "UPDATE WagleProgress SET wpReadPage=? WHERE wpSeqno=?";
	
	    ps = conn_mysql.prepareStatement(WhereDefault);
	    ps.setInt(1, wpReadPage);
	    ps.setInt(2, wpSeqno);

	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}

%>
