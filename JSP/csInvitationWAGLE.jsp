<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String CHAT_TYPE = request.getParameter("CHAT_TYPE");
	String TEMPLATE_ID = request.getParameter("TEMPLATE_ID");
	String content_id = request.getParameter("content_id");
	String user_id = request.getParameter("user_id");
	String product_id = request.getParameter("product_id");

//------

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
%>
	    <%= CHAT_TYPE %>, <%= TEMPLATE_ID %>, <%= content_id %>, <%= user_id %>, <%= product_id %>
<%
	} catch (Exception e){
	    e.printStackTrace();
	}

%>