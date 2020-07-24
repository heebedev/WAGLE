<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String moimSeqno = request.getParameter("moimSeqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

    
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        // 와글 데이터 구하기
    	String 	WhereDefault = "select s.sSeqno, s.sContent, wc.wcSeqno " +
				   "from Suggestion as s " +
				   "join WagleCreate as wc on s.WagleCreate_wcSeqno = wc.wcSeqno " +
				   "where wc.Moim_mSeqno = ? and s.sType = 'H' and " +
				   "s.sSeqno not in (select sSeqno from BookReport as br join Suggestion as s on s.sSeqno = br.Suggestion_sSeqno);";
        
        PreparedStatement ps = conn_mysql.prepareStatement(WhereDefault);

        ps.setString(1, moimSeqno);
        
        ResultSet rs = ps.executeQuery(); // 
%>
		{ 
  			"noBookReport"  : [ 
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
				"sSeqno" : "<%=rs.getString(1) %>", 
				"sContent" : "<%=rs.getString(2) %>",
				"wcSeqno" : "<%=rs.getString(3) %>"
			}

<%		
             count++;
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
