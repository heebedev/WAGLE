<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String useqno = request.getParameter("useqno");
	String wcseqno = request.getParameter("wcseqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    	String WhereDefault = "SELECT s.sSeqno, s.sContent, r.brContent FROM Suggestion s LEFT OUTER JOIN BookReport r ON s.sSeqno = r.Suggestion_sSeqno WHERE s.sValidation = 1 AND r.brValidation = 1 AND s.WagleCreate_wcSeqno = " + wcseqno + " AND r.User_uSeqno  = " + useqno + " order by s.sSeqno";
    	int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"suggestion_list"  : [ 
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
			"seqno" : "<%=rs.getInt(1) %>", 
			"scontent" : "<%=rs.getString(2) %>",  
			"rcontent" : "<%=rs.getString(3) %>" 
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
