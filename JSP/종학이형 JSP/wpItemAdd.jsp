<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	int wcSeqno = Integer.parseInt(request.getParameter("wcSeqno"));
	String wpItem = request.getParameter("wpItem");
	int wpPrice = Integer.parseInt(request.getParameter("wpPrice"));
	
		
//------

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
	    String query = "INSERT INTO WaglePayment (wcSeqno, wpItem, wpPrice, wpDate) VALUES (?, ?, ?, now())";
	
	    ps = conn_mysql.prepareStatement(query);
	    ps.setInt(1, wcSeqno);
	    ps.setString(2, wpItem);
	    ps.setInt(3, wpPrice);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}

%>