<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	int maxpage = Integer.parseInt(request.getParameter("maxpage"));
	String intro = request.getParameter("intro");
	String data = request.getParameter("data");
	String imgname = request.getParameter("imgName");

	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;



		try {
       			 Class.forName("com.mysql.cj.jdbc.Driver");
        		 Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        		 Statement stmt_mysql = conn_mysql.createStatement();

			
			 String A = "INSERT INTO WagleBook (wbTitle, wbWriter, wbMaxPage, wbIntro, wbData, wbImage) VALUES (?, ?, ?, ?, ?, ?)";

			 ps = conn_mysql.prepareStatement(A);
	
			 ps.setString(1, title);
			 ps.setString(2, writer);
			 ps.setInt(3, maxpage);
			 ps.setString(4, intro);
			 ps.setString(5, data);
			 ps.setString(6, imgname);


			 ps.executeUpdate();
	
	    		 conn_mysql.close();
		    } 
	
		 catch (Exception e){
			 e.printStackTrace();
		    }
    
    

%>




        