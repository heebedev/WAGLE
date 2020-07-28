<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String Post_pSeqno = request.getParameter("Post_pSeqno");
	String pcTitle = request.getParameter("pcTitle");
	String pcContent = request.getParameter("pcContent");

//------

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "insert into postContent (Post_pSeqno, pcTitle, pcContent) values (?, ?, ?);";
	
	    ps = conn_mysql.prepareStatement(A);
	    ps.setString(1, Post_pSeqno);
	    ps.setString(2, pcTitle);
	    ps.setString(3, pcContent);

	    ps.executeUpdate();
	
	    conn_mysql.close();
	} catch (Exception e){
	    e.printStackTrace();
	}

%>