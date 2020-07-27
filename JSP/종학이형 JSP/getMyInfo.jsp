<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    


<%
	int uSeqno = Integer.parseInt(request.getParameter("uSeqno"));

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT * FROM user WHERE uSeqno=" + uSeqno;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"User"  : [ 
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
