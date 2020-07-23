<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String moimSeqno = request.getParameter("moimSeqno");
	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select br.brSeqno, br.User_uSeqno, br.brContent, wc.wcSeqno " +
    					  "from BookReport as br " +
    					  "join Suggestion as s on s.sSeqno = br.Suggestion_sSeqno " +
    					  "join WagleCreate as wc on wc.wcSeqno = s.WagleCreate_wcSeqno " +
    					  "where (brValidation = 1 and sValidation = 1 and wc.wcValidation = 1) and wc.Moim_mSeqno = ? limit 4;";
    
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        
        PreparedStatement ps = conn_mysql.prepareStatement(WhereDefault);

        ps.setString(1, moimSeqno);
        
        ResultSet rs = ps.executeQuery(); // 
%>
		{ 
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
			"brSeqno" : "<%=rs.getInt(1) %>", 
			"uSeqno" : "<%=rs.getString(2) %>",  
			"brContent" : "<%=rs.getString(3) %>",
			"wcSeqno" : "<%=rs.getString(4) %>"
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
