<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	int uSeqno = Integer.parseInt(request.getParameter("uSeqno"));
	int wcSeqno = Integer.parseInt(request.getParameter("wcSeqno"));
	int Moim_mSeqno = Integer.parseInt(request.getParameter("Moim_mSeqno"));
		
//------

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
	 	String WhereDefault = "INSERT INTO WagleUser (User_uSeqno, wcSeqno, wuDate) VALUES (?, ?, now())";
	
	    ps = conn_mysql.prepareStatement(WhereDefault);
	    ps.setInt(1, uSeqno);
	    ps.setInt(2, wcSeqno);
	    
	    ps.executeUpdate();
	    
	    String B = "update MoimUser set muWagleNum = muWagleNum + 1 where Moim_mSeqno = ? and User_uSeqno = ?;";

	    ps = conn_mysql.prepareStatement(B);
	       ps.setInt(1, Moim_mSeqno);
	       ps.setInt(2, uSeqno);

	       ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}

%>
