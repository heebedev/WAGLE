<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String mSeqno = request.getParameter("mSeqno");

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select mName, mImage from Moim where mSeqno = '" + mSeqno+ "'";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
        if (rs.next()) {
%>
			<%=rs.getString(2) %>,<%=rs.getString(1) %>
<%
        }
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

%>

