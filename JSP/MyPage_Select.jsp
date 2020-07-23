<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String userSeqno = request.getParameter("userSeqno");
	String moimSeqno = request.getParameter("moimSeqno");

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        PreparedStatement ps = null;

        String WhereDefault = "select mu.muWagleNum, mu.muWagleBookReport, mu.muWagleScore " +
			        		"from MoimUser as mu " +
			        		"where mu.Moim_mSeqno = ? and mu.User_uSeqno = ?;";
		ps = conn_mysql.prepareStatement(WhereDefault);
		
		ps.setString(1, moimSeqno);
		ps.setString(2, userSeqno);
		
		
        ResultSet rs = ps.executeQuery();
		
%>
		{ 
<%
		if (rs.next()) {
%>
			"wagleNum" : "<%=rs.getString(1) %>", 
			"wagleBookReportNum" : "<%=rs.getString(2) %>",   
			"wagleScore" : "<%=rs.getString(3) %>"
<%
		}
		
		WhereDefault = "select count(wuSeqno) " +
				"from WagleUser as wu, WagleCreate as wc  " +
				"where wu.wcSeqno = wc.wcSeqno and wc.Moim_mSeqno = ? and (wu.wuValidation = 1 and wc.wcValidation = 1) and " +
				"wu.User_uSeqno = ? ;";
		
		ps = conn_mysql.prepareStatement(WhereDefault);
		
		ps.setString(1, moimSeqno);
		ps.setString(2, userSeqno);
		
		rs = ps.executeQuery();
				
		if(rs.next()) {
%>
			"userWagle" : "<%=rs.getString(1) %>",
<% 
		}

		WhereDefault = 	"select count(wcSeqno) " +
						"from WagleCreate " +
						"where Moim_mSeqno = ? and wcValidation = 1;";
		
		ps = conn_mysql.prepareStatement(WhereDefault);
		
		ps.setString(1, moimSeqno);
		
		rs = ps.executeQuery();
				
		if(rs.next()) {
%>
			"totalWagle" : "<%=rs.getString(1) %>"
<% 
		}
		
		// 독후감 쓴 숫자, 총 숫자 구해야함.
		
		WhereDefault = "select count(wu.wuSeqno) " +
				"from WagleUser as wu, WagleCreate as wc " +
				"where wu.wcSeqno = wc.wcSeqno and wc.Moim_mSeqno = ? and (wu.wuValidation = 1 and wc.wcValidation = 1) and " +
				"wu.User_uSeqno != ? limit 4;";		

		ps = conn_mysql.prepareStatement(WhereDefault);
								
		ps.setString(1, moimSeqno);
		ps.setString(2, userSeqno);
				
		rs = ps.executeQuery(); // 
		
        int check = 0;
        if(rs.next()) {
        	check = rs.getInt(1);
        } else {
%>
   	}
<%        	
        }
        
        if (check >= 1) {
        
		WhereDefault = 	"select wc.wcName, wc.wcStartDate, wc.wcEndDate, wc.wcLocate, wc.wcEntryFee " +
						"from WagleUser as wu, WagleCreate as wc " +
						"where wu.wcSeqno = wc.wcSeqno and wc.Moim_mSeqno = ? and (wu.wuValidation = 1 and wc.wcValidation = 1) and " +
						"wu.User_uSeqno != ? limit 4;";

		ps = conn_mysql.prepareStatement(WhereDefault);
						
		ps.setString(1, moimSeqno);
		ps.setString(2, userSeqno);

		rs = ps.executeQuery();
%>
		,"noWagle" : [
<%
		
		while(rs.next()) {
	         if (count == 0) {

	         }else{
	%>
	         , 
	<%
	         }
	%>            
				{
				"wcName" : "<%=rs.getString(1) %>", 
				"wcStartDate" : "<%=rs.getString(2) %>",
				"wcEndDate" : "<%=rs.getString(3) %>",
				"wcLocate" : "<%=rs.getString(4) %>",
				"wcEntryFee" : "<%=rs.getString(5) %>"
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
