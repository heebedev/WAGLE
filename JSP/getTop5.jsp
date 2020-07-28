<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    


<%
	int Moim_mSeqno = Integer.parseInt(request.getParameter("Moim_mSeqno"));

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT uName, muWagleNum, muWagleReport, (muWagleNum*10 + muWagleReport*5) AS score FROM MoimUser AS mu, User AS u WHERE Moim_mSeqno="+Moim_mSeqno+" AND mu.User_uSeqno = u.uSeqno ORDER BY score DESC LIMIT 5";
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
			"uName" : "<%=rs.getString(1) %>", 
			"muWagleNum" : "<%=rs.getInt(2) %>", 
			"muWagleReport" : "<%=rs.getInt(3) %>", 
			"muWagleScore" : "<%=rs.getInt(4) %>"
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
