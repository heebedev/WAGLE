<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>  
      
<%
	request.setCharacterEncoding("utf-8");
	String userSeqno = request.getParameter("userSeqno");
	String moimSeqno = request.getParameter("MoimSeqno");
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
	
	    String A = "insert into Post (User_uSeqno, Moim_mSeqno, pType, pValidation) ";
	    String B = "values (?, ?, ?, 1);";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, userSeqno);
	    ps.setString(2, moimSeqno);
	    ps.setString(3, type);
	    
	    ps.executeUpdate();
	
	    A = "insert into WaGle.PostContent (Post_PSeqno, pcTitle, pcContent, pcDate) ";
	    B = "values ( (select pSeqno from WaGle.Post where User_uSeqno = ? order by pSeqno DESC limit 1) , ? , ? , now());";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, userSeqno);
	    ps.setString(2, title);
	    ps.setString(3, content);
	    
	    ps.executeUpdate();
	    
	    conn_mysql.close();
	} 	
	catch (Exception e){
	    e.printStackTrace();
	}
%>