<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String moimseqno = request.getParameter("moimseqno");

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        String WhereDefault = "select m.mSeqno, m.mName, m.mImage from Moim as m " + 
       						  "where m.mValidation = 1 and m.mSeqno = " + moimseqno + ";";
        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
		
%>
		{ 
<%
		if (rs.next()) {
%>
			"moimSeqno" : "<%=rs.getString(1) %>", 
			"moimName" : "<%=rs.getString(2) %>",   
			"moimImage" : "<%=rs.getString(3) %>"
<%
		}
		
		WhereDefault = "select count(maSeqno) from MoimAdminister where Moim_mSeqno = '" + moimseqno + "';";
        rs = stmt_mysql.executeQuery(WhereDefault); // 
		
        int check = 0;
        if(rs.next()) {
        	check = rs.getInt(1);
        }
        
        if (check >= 2) {
    		WhereDefault = "select ma.maSeqno, ma.maGrade, ma.MoimUser_muSeqno, u.uName, u.uEmail, u.uImageName " + 
					   "from MoimAdminister as ma, MoimUser as mu, User as u " + 
					   "where (ma.maValidation = '1' and mu.muValidation = '1') and " + 
					          "ma.Moim_mSeqno = '" + moimseqno + "' and " +  
						      "(ma.MoimUser_muSeqno = mu.muSeqno and mu.User_uSeqno = u.uSeqno) " + 
					   "order by ma.maGrade ASC;";
					   
     rs = stmt_mysql.executeQuery(WhereDefault); // 
%>

			,"moimadminister" : [
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
			"maSeqno" : "<%=rs.getString(1) %>", 
			"maGrade" : "<%=rs.getString(2) %>",
			"muSeqno" : "<%=rs.getString(3) %>",
			"uName" : "<%=rs.getString(4) %>",
			"uEmail" : "<%=rs.getString(5) %>",
			"uImageName" : "<%=rs.getString(6) %>"
			}

<%		
          count++;
     }


%>
		  ] 
		} 
<%		        	
        } else {
%>
	}
<%
        }

       
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
