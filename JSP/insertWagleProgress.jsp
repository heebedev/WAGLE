<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String uSeqno = request.getParameter("uSeqno");
	String wcSeqno = request.getParameter("wcSeqno");

 	int wuSeqno = 0;
 	
	//out.println(id + pw);
		
//------
%>
<%= 1 %>
<%

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	%>
	<%= 2 %>
	<%
	
	PreparedStatement ps = null;
	
	%>
	<%= 3 %>
	<%
	
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();

		%>
		<%= 4 %>
		<%
	    
	 	String WhereDefault = "SELECT wuSeqno FROM WagleUser WHERE User_uSeqno=" + uSeqno + " AND wcSeqno=" + wcSeqno + " AND wuValidation=1";
	    ps = conn_mysql.prepareStatement(WhereDefault);
	 	ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        	wuSeqno = rs.getInt(1);
        }
        
		%>
		<%= 5 %>
		<%
        
	 	WhereDefault = "INSERT INTO WagleProgress(WagleUser_wuSeqno, WagleCreate_wcSeqno) VALUES(?, ?)";
	    ps = conn_mysql.prepareStatement(WhereDefault);
	    ps.setInt(1, wuSeqno);
	    ps.setString(2, wcSeqno);
	    ps.executeUpdate();
	 	
		%>
		<%= 6 %>
		<%

	    conn_mysql.close();
	}
	
	catch (Exception e){
	    e.printStackTrace();
	}

%>