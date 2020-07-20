<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>  
      
<%
	request.setCharacterEncoding("utf-8");
	String seqno = request.getParameter("seqno");
	String userSeqno = request.getParameter("userSeqno");
	String type = request.getParameter("type");
	String title = request.getParameter("title");
	String content = request.getParameter("content");	
	
//------
	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
		String query = "update PostContent set pcTitle = ?, pcContent = ? where pcSeqno = ?;";
		ps = conn_mysql.prepareStatement(query);
		
		ps.setString(1, title);
		ps.setString(2, content);
	    ps.setInt(3, Integer.parseInt( seqno ));
		
		ps.executeUpdate();	// 실행.	    
	    
	    conn_mysql.close();
	} 	
	catch (Exception e){
	    e.printStackTrace();
	}
%>