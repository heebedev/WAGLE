<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String userseqno = request.getParameter("userseqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT distinct u.Moim_mSeqno as mSeqno, m.mName , m.mSubject, u.muSeqno FROM Moim m, MoimUser u WHERE m.mValidation = '1' AND u.muValidation = '1' AND u.Moim_mSeqno = m.mSeqno AND u.User_uSeqno =" + userseqno;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"moim_list"  : [ 
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
			"mSeqno" : "<%=rs.getInt(1) %>", 
			"mName" : "<%=rs.getString(2) %>",
			"mSubject" : "<%=rs.getString(3) %>",
			"muSeqno" : "<%=rs.getInt(4) %>"   
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
