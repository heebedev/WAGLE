<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String useqno = request.getParameter("useqno");
	String size = request.getParameter("size");

	for (int i = 0; i < size;
	String rcontent = request.getParameter("rcontent");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;





		try {
       			 Class.forName("com.mysql.cj.jdbc.Driver");
        		 Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        		 Statement stmt_mysql = conn_mysql.createStatement();

			 String A = "UPDATE BookReport set brValidation = 0 WHERE Suggestion_sSeqno = ? AND User_uSeqno = ? ";

			 ps = conn_mysql.prepareStatement(A);
	
			 ps.setInt(1, Integer.parseInt(sseqno));
			 ps.setInt(2, Integer.parseInt(useqno));

			 ps.executeUpdate();
			
			 A = "INSERT INTO BookReport (Suggestion_sSeqno, User_uSeqno, brContent) VALUES (?, ?, ?)";

			 ps = conn_mysql.prepareStatement(A);
	
			 ps.setInt(1, Integer.parseInt(sseqno));
			 ps.setInt(2, Integer.parseInt(useqno));
			 ps.setString(3, rcontent);

			 ps.executeUpdate();
	
	    		 conn_mysql.close();
		    } 
	
		 catch (Exception e){
			 e.printStackTrace();
		    }


    

%>




        