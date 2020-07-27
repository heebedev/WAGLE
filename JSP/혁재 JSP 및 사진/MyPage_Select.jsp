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

        // 유저의 와글 참가수, 와글 제출한 레포트 수, 와글 점수
        String WhereDefault = "select mu.muWagleNum, mu.muWagleReport, mu.muWagleScore " +
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
			"wagleScore" : "<%=rs.getString(3) %>",
<%
		}		

		// 발제문 총 수
		WhereDefault = "select count(s.sSeqno) " +
					   "from Suggestion as s " +
				 	   "join WagleCreate as wc on wc.wcSeqno = s.WagleCreate_wcSeqno " +
				 	   "where s.sValidation = '1' and s.sType = 'H' and wc.Moim_mSeqno = ?;";
		
		ps = conn_mysql.prepareStatement(WhereDefault);
		
		ps.setString(1, moimSeqno);
		
		rs = ps.executeQuery();
				
		if(rs.next()) {
%>
			"totalBookReport" : "<%=rs.getString(1) %>",
<% 
		}
		
		// 와글 총합 구하기
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
		
		// 안쓴 레포트 구하기
		WhereDefault = "select s.sSeqno, s.sContent, wc.wcSeqno " +
					   "from Suggestion as s " +
					   "join WagleCreate as wc on s.WagleCreate_wcSeqno = wc.wcSeqno " +
					   "where wc.Moim_mSeqno = ? and s.sType = 'H' and " +
					   "s.sSeqno not in (select sSeqno from BookReport as br join Suggestion as s on s.sSeqno = br.Suggestion_sSeqno) " +
					   "order by s.sSeqno desc limit 4;";
		
		ps = conn_mysql.prepareStatement(WhereDefault);
		
		ps.setString(1, moimSeqno);
		
		rs = ps.executeQuery();
				
	%>
		,"noBookReport" : [
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
				"sSeqno" : "<%=rs.getString(1) %>", 
				"sContent" : "<%=rs.getString(2) %>",
				"wcSeqno" : "<%=rs.getString(3) %>"
				}

	<%		
	          count++;
		}
	%>
	
		]
	<% 		
		// 와글이 있는지 확인
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
        
        // 와글 데이터 구하기
		WhereDefault = "select * from WagleCreate " + 
					   "where wcValidation = 1 and Moim_mSeqno = ? and " +
					   "wcSeqno not in (select wcSeqno from WagleUser where User_uSeqno = ?) limit 4;";

		ps = conn_mysql.prepareStatement(WhereDefault);
						
		ps.setString(1, moimSeqno);
		ps.setString(2, userSeqno);

		rs = ps.executeQuery();
%>
		,"noWagle" : [
<%
		count = 0;
		while(rs.next()) {
	         if (count == 0) {

	         }else{
	%>
	         , 
	<%
	         }
	%>            
				{
					"wcSeqno" : "<%=rs.getInt(1)%>", 
					"Moim_wmSeqno" : "<%=rs.getString(2)%>",
					"MoimUser_muSeqno" : "<%=rs.getString(3)%>", 
					"WagleBook_wbSeqno" : "<%=rs.getString(4)%>",
					"wcName" : "<%=rs.getString(5)%>", 
					"wcType" : "<%=rs.getString(6)%>",
					"wcStartDate" : "<%=rs.getString(7)%>", 
					"wcEndDate" : "<%=rs.getString(8)%>",
					"wcDueDate" : "<%=rs.getString(9)%>", 
					"wcLocate" : "<%=rs.getString(10)%>",
					"wcEntryFee" : "<%=rs.getString(11)%>", 
					"wcWagleDetail" : "<%=rs.getString(12)%>",
					"wcWagleAgreeRefund" : "<%=rs.getString(13)%>", 
					"wcValidation" : "<%=rs.getString(14)%>"
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
