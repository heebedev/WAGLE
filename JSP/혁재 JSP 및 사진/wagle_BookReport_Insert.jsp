<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>  
      
<%
	request.setCharacterEncoding("utf-8");
	String User_uSeqno = request.getParameter("uSeqno");
	String moimSeqno = request.getParameter("moimSeqno");
	int num1 = Integer.parseInt(request.getParameter("num"));

	String[] sSeqno = new String[num1];
	String[] brContent = new String[num1];
	
	for (int i = 0 ; i < num1 ; i++) {
		sSeqno[i] = request.getParameter("sSeqno" + i);
		brContent[i] = request.getParameter("bContent" + i);
	}

	
//------
	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into BookReport (Suggestion_sSeqno, User_uSeqno, brContent, brValidation) ";
	    String B = "values (?, ?, ?, 1);";

	    ps = conn_mysql.prepareStatement(A+B);
	    
	    for (int i = 0 ; i < num1 ; i++) {
		    ps.setString(1, sSeqno[i]);
		    ps.setString(2, User_uSeqno);
		    ps.setString(3, brContent[i]);
		    
		    ps.executeUpdate();	
	    }	    
	    
	    A = "update MoimUser set muWagleReport = muWagleReport + 1 where User_uSeqno = ? and Moim_mSeqno = ?;";

	    ps = conn_mysql.prepareStatement(A);	    

		ps.setInt(1, Integer.parseInt(User_uSeqno));
		 ps.setInt(2, Integer.parseInt(moimSeqno));
		

		ps.executeUpdate();
	    
	    
	    conn_mysql.close();
	} 	
	catch (Exception e){
	    e.printStackTrace();
	}
%>