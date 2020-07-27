<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String wcSeqno = request.getParameter("wcSeqno");
	

	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	String WhereDefault = "SELECT * FROM WaGleBook WHERE wbSeqno = (SELECT WagleBook_wbSeqno from WagleCreate where wcSeqno = " + wcSeqno + ")";
    int count = 0;
    

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"book_info"  : [ 
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
			"wbSeqno" : "<%=rs.getInt(1) %>", 
			"wbTitle" : "<%=rs.getString(2) %>",   
			"wbWriter" : "<%=rs.getString(3) %>",
			"wbMaxPage" : "<%=rs.getInt(4) %>",  
			"wbIntro" : "<%=rs.getString(5) %>",
			"wbData" : "<%=rs.getString(6) %>",
			"wbImage" : "<%=rs.getString(7) %>"
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
