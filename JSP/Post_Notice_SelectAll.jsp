<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String moimSeqno = request.getParameter("moimSeqno");

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select p.pSeqno, pc.pcTitle, pc.pcContent, p.User_uSeqno from PostContent as pc, Post as p " + 
    		"where p.pType = 'N' and p.pValidation = '1' and p.pSeqno = pc.Post_pSeqno and p.Moim_mSeqno = " + moimSeqno + " " +
    		"order by pc.pcSeqno DESC;";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 

        %>
		{ 
  			"notice"  : [ 
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
			"pcSeqno" : "<%=rs.getString(1) %>", 
			"pcTitle" : "<%=rs.getString(2) %>",   
			"pcContent" : "<%=rs.getString(3) %>",
			"User_uSeqno" : "<%=rs.getString(4) %>" 
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
