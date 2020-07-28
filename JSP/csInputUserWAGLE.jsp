<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String uId = request.getParameter("uId");
	String uEmail = request.getParameter("uEmail");
	String uName = request.getParameter("uName");
	String uImageName = request.getParameter("uImageName");
	String uBirthDate = request.getParameter("uBirthDate");
	String uLoginType = request.getParameter("uLoginType");

//------

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "insert into user (uId, uEmail, uName, uImageName, uBirthDate, uLoginType, uDate";
	    String B = ") values (?,?,?,?,?,?,now())";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, uId);
	    ps.setString(2, uEmail);
	    ps.setString(3, uName);
	    ps.setString(4, uImageName);
	    ps.setString(5, uBirthDate);
	    ps.setString(6, uLoginType);

	    ps.executeUpdate();
	
	    conn_mysql.close();
	} catch (Exception e){
	    e.printStackTrace();
	}

%>