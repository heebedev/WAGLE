<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String wcSeqno = request.getParameter("wcSeqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select sSeqno, WagleCreate_wcSeqno, sType, sContent " +
    					"from Suggestion where WagleCreate_wcSeqno = ? and sValidation = 1;";
    
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        
        PreparedStatement ps = conn_mysql.prepareStatement(WhereDefault);

        ps.setString(1, wcSeqno);
        
        ResultSet rs = ps.executeQuery(); // 
%>
		{ 
  			"suggestion"  : [ 
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
			"sSeqno" : "<%=rs.getInt(1) %>", 
			"WagleCreate_wcSeqno" : "<%=rs.getString(2) %>",  
			"sType" : "<%=rs.getString(3) %>",
			"sContent" : "<%=rs.getString(4) %>"
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
