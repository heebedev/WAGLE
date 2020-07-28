<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String uName = request.getParameter("uName");
	String uBirthDate = request.getParameter("uBirthDate");
	String uEmail = request.getParameter("uEmail");	
	String uImageName = request.getParameter("uImageName");	
	String uId = request.getParameter("uId");	
	
//------

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
	 	String WhereDefault = "UPDATE user SET uEmail=?, uName=?, uImageName=?, uBirthDate=? WHERE uId=?";
	
	    ps = conn_mysql.prepareStatement(WhereDefault);
	    ps.setString(1, uEmail);
	    ps.setString(2, uName);
	    ps.setString(3, uImageName);
	    ps.setString(4, uBirthDate);
	    ps.setString(5, uId);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}

%>
