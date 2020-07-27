<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	out.println(id + pw);
		
//------

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
	    String query = "INSERT INTO user (uId, uPassword, uEmail, uLoginType, uDate) VALUES (?, ?, ?, 'wagle', now())";
	
	    ps = conn_mysql.prepareStatement(query);
	    ps.setString(1, id);
	    ps.setString(2, pw);
	    ps.setString(3, id);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	catch (Exception e){
	    e.printStackTrace();
	}
%>
