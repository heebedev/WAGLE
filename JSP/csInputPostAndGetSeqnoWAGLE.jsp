<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String User_uSeqno = request.getParameter("User_uSeqno");
	String Moim_mSeqno = request.getParameter("Moim_mSeqno");
	String Board_bSeqno = request.getParameter("Board_bSeqno");

//------

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "insert into post (User_uSeqno, Moim_mSeqno, Board_bSeqno) values (?, ?, ?);";

	    ps = conn_mysql.prepareStatement(A);
	    ps.setString(1, User_uSeqno);
	    ps.setString(2, Moim_mSeqno);
	    ps.setString(3, Board_bSeqno);

	    ps.executeUpdate();


	    String D = "select pSeqno from post order by pSeqno desc limit 1;";
	    ResultSet rs = stmt_mysql.executeQuery(D);

	    if(rs.next()) {
%>
        <%=rs.getInt(1) %>
<%
        }

	    conn_mysql.close();
	} catch (Exception e){
	    e.printStackTrace();
	}

%>