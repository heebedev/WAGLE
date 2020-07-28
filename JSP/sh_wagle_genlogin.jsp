<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String uemail = request.getParameter("uEmail");
	String upw = request.getParameter("uPw");


	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	String WhereDefault = "SELECT * FROM User WHERE uEmail = '" + uemail + "' AND uPassword= '" + upw + "'";
    int count = 0;
    

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"user_info"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"uSeqno" : "<%=rs.getInt(1) %>", 
			"uId" : "<%=rs.getString(2) %>",
			"uPassword" : "<%=rs.getString(3) %>",   
			"uEmail" : "<%=rs.getString(4) %>",
			"uLoginType" : "<%=rs.getString(5) %>",  
			"uName" : "<%=rs.getString(6) %>",
			"uImageName" : "<%=rs.getString(7) %>",
			"uBirthDate" : "<%=rs.getString(8) %>",
			"uDate" : "<%=rs.getString(9) %>"
			}

<%		
             count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
