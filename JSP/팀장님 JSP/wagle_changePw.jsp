<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");
	String pw = request.getParameter("pw");


	int uSeqno = 0;
	String uId = "";
	String uPassword = "";
	String uEmail = "";
	String uLoginType = "";
	String uName = "";
	String uImageName = "";
	String uBirthDate = "";


	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	
	PreparedStatement ps = null;


	String A = "SELECT * FROM User WHERE uEmail = '" + email + "'";

	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        	Statement stmt_mysql = conn_mysql.createStatement();


        	ResultSet rs = stmt_mysql.executeQuery(A); 


        	while (rs.next()) {

			uSeqno = rs.getInt(1);
			uId = rs.getString(2);
			uPassword = rs.getString(3);
			uEmail = rs.getString(4);
			uLoginType = rs.getString(5);
			uName = rs.getString(6);
			uImageName = rs.getString(7);
			uBirthDate = rs.getString(8);
		}
		
	int count = 0;
    

    	try {

		A = "UPDATE User set uPassword = ? WHERE uEmail = ?";

		ps = conn_mysql.prepareStatement(A);
		ps.setString(1, pw);
		ps.setString(2, email);

		ps.executeUpdate();


		A = "INSERT INTO UserBackup (uSeqno, uId, uPassword, uEmail, uLoginType, uName, uImageName, uBirthDate, uDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())";

		ps = conn_mysql.prepareStatement(A);
	
		ps.setInt(1, uSeqno);
		ps.setString(2, uId);
		ps.setString(3, uPassword);
		ps.setString(4, uEmail);
		ps.setString(5, uLoginType);
		ps.setString(6, uName);
		ps.setString(7, uImageName);
		ps.setString(8, uBirthDate);

		ps.executeUpdate();
		 
	   	conn_mysql.close();
	} catch (Exception e){
	    	e.printStackTrace();
	}
	
	
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }


%>




        