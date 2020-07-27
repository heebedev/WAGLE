<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String uSeqno = request.getParameter("uSeqno");
	String moimSeqno = request.getParameter("moimSeqno");
	String wseqno = request.getParameter("wseqno");
	String count = request.getParameter("count");
	String head = request.getParameter("head");

	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;



		try {
       			 Class.forName("com.mysql.cj.jdbc.Driver");
        		 Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        		 Statement stmt_mysql = conn_mysql.createStatement();

			
			 String A = "INSERT INTO Suggestion (WagleCreate_wcSeqno, sType, sContent) VALUES (?, 'H', ?)";

			 ps = conn_mysql.prepareStatement(A);
	
			 ps.setInt(1, Integer.parseInt(wseqno));
			 ps.setString(2, head);

			 ps.executeUpdate();
			 
			 A = "update MoimUser set muWagleSuggestion = muWagleSuggestion + 1 where User_uSeqno = ? and Moim_mSeqno = ? ;";
			 
			 ps = conn_mysql.prepareStatement(A);
				
			 ps.setInt(1, Integer.parseInt(uSeqno));
			 ps.setInt(2, Integer.parseInt(moimSeqno));

			 ps.executeUpdate();
			 
	    		 conn_mysql.close();
		    } 
	
		 catch (Exception e){
			 e.printStackTrace();
		    }

	for (int i = 1; i <= Integer.parseInt(count); i++) {

		try {
       			 Class.forName("com.mysql.cj.jdbc.Driver");
        		 Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        		 Statement stmt_mysql = conn_mysql.createStatement();
				
			 String permid = "question"+i;

			 String context = request.getParameter(permid);

			 String A = "INSERT INTO Suggestion (WagleCreate_wcSeqno, sContent) VALUES (?, ?)";

			 ps = conn_mysql.prepareStatement(A);
	
			 ps.setInt(1, Integer.parseInt(wseqno));
			 ps.setString(2, context);

			 ps.executeUpdate();
	
	    		 conn_mysql.close();
		    } 
	
		 catch (Exception e){
			 e.printStackTrace();
		    }


	}


	
		
	
    
    
    

%>




        