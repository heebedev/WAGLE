<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String useqno = request.getParameter("useqno");
	String mseqno = request.getParameter("mseqno");
	int count = 0;


	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	String WhereDefault = "SELECT a.maGrade as maGrade, a.Moim_mSeqno as mSeqno, u.User_uSeqno as uSeqno FROM MoimAdminister a, MoimUser u WHERE a.maValidation = 1 AND u.muValidation = 1 AND a.MoimUser_muSeqno = u.muSeqno AND a.Moim_mSeqno = " + mseqno + " AND u.User_uSeqno = " + useqno;
    

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"magrade_check"  : [ 
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
			"maGrade" : "<%=rs.getString(1) %>" 
			
			
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
