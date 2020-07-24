<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String moimSeq = request.getParameter("Moim_wmSeqno");

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select * from board where Moim_mSeqno = '" + moimSeq+ "' order by bOrder;";
    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"board_title_list"  : [
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
			"bSeqno" : "<%=rs.getInt(1) %>",
			"bName" : "<%=rs.getString(2) %>",
			"Moim_mSeqno" : "<%=rs.getString(3) %>",
			"bDate" : "<%=rs.getString(4) %>",
			"bOrder" : "<%=rs.getString(5) %>"
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

