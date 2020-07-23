<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>  
      
<%
	request.setCharacterEncoding("utf-8");
	String userSeqno = request.getParameter("userSeqno");
	String title = request.getParameter("title");
	String subject = request.getParameter("subject");
	String imgName = request.getParameter("imgName");	
	String intro = request.getParameter("intro");			
//------
	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into Moim (mName, mSubject, mImage, mIntro, mValidation, User_uSeqno";
	    String B = ") values (?,?,?,?,'1',?);";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, title);
	    ps.setString(2, subject);
	    ps.setString(3, imgName);
	    ps.setString(4, intro);
	    ps.setInt(5, Integer.parseInt( userSeqno ));
	    
	    ps.executeUpdate();
	    
	    A = "insert into MoimUser (Moim_mSeqno, User_uSeqno, date, muValidation) " +
	    	"values ( (select mSeqno from Moim where User_uSeqno = ? order by mSeqno desc Limit 0, 1), ?, now(), 1);";
	    	
		ps = conn_mysql.prepareStatement(A);
	    ps.setInt(1, Integer.parseInt( userSeqno ));
	    ps.setInt(2, Integer.parseInt( userSeqno ));

	    ps.executeUpdate();
	    
	    A = "insert into MoimAdminister (maGrade, maDate, maValidation, Moim_mSeqno, MoimUser_muSeqno) " +
	    	"values ('O', now(), 1, (select mSeqno from Moim where User_uSeqno = ? and mValidation = 1 order by mSeqno desc Limit 0, 1), " +
	    	"(select muSeqno from MoimUser where muValidation = 1 and User_uSeqno = ? order by muSeqno desc Limit 0, 1) );";

		ps = conn_mysql.prepareStatement(A);
	    ps.setInt(1, Integer.parseInt( userSeqno ));
	    ps.setInt(2, Integer.parseInt( userSeqno ));

	    ps.executeUpdate();
	    
	    conn_mysql.close();
	} 	
	catch (Exception e){
	    e.printStackTrace();
	}
%>
