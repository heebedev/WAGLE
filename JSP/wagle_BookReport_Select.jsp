<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String wcSeqno = request.getParameter("wcSeqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        WhereDefault = "Select count(brSeqno)" +
	    		"from BookReport " +
	    		"left join Suggestion on sSeqno = Suggestion_sSeqno " +
	    		"where WagleCreate_wcSeqno = ? and (brValidation = 1 and sValidation = 1);";
        
        PreparedStatement ps = conn_mysql.prepareStatement(WhereDefault);

        ps.setString(1, wcSeqno);
        
        ResultSet rs = ps.executeQuery(); //         
        
        int check = 0;        
        if (rs.next()) {
        	check = rs.getInt(1);
        }
        
        if (check == 0) {
	        WhereDefault = "Select sSeqno, WagleCreate_wcSeqno, sType, sContent " +
		    		"from Suggestion " +
		    		"where WagleCreate_wcSeqno = ? and (sValidation = 1);";
	        
	        ps = conn_mysql.prepareStatement(WhereDefault);
	
	        ps.setString(1, wcSeqno);
	        
	        rs = ps.executeQuery(); // 
	%>
			{ 
				"check" : "0",
	  			"bookreport"  : [ 
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
				"brSeqno" : "", 
				"User_uSeqno" : "", 
				"brContent" : "", 
				"sSeqno" : "<%=rs.getInt(1) %>", 
				"WagleCreate_wcSeqno" : "<%=rs.getString(2) %>",  
				"sType" : "<%=rs.getString(3) %>",
				"sContent" : "<%=rs.getString(4) %>"
				}
	
	<%		
	             count++;
	        }
        } else {
        
	        WhereDefault = "Select brSeqno, User_uSeqno, brContent, sSeqno, WagleCreate_wcSeqno, sType, sContent " +
		    		"from BookReport " +
		    		"left join Suggestion on sSeqno = Suggestion_sSeqno " +
		    		"where WagleCreate_wcSeqno = ? and (brValidation = 1 and sValidation = 1);";

			ps = conn_mysql.prepareStatement(WhereDefault);
	
	        ps.setString(1, wcSeqno);
	        
	        rs = ps.executeQuery(); // 
	%>
			{ 
				"check" : "1",
	  			"bookreport"  : [ 
	<%	
			count = 0;
	        while (rs.next()) {
	            if (count == 0) {
	
	            }else{
	%>
	            , 
	<%
	            }
	%>            
				{
				"brSeqno" : "<%=rs.getInt(1) %>", 
				"User_uSeqno" : "<%=rs.getInt(2) %>", 
				"brContent" : "<%=rs.getString(3) %>", 
				"sSeqno" : "<%=rs.getInt(4) %>", 
				"WagleCreate_wcSeqno" : "<%=rs.getString(5) %>",  
				"sType" : "<%=rs.getString(6) %>",
				"sContent" : "<%=rs.getString(7) %>"
				}
	
	<%		
	             count++;
	        }
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
