<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String find = request.getParameter("find");
	String name = request.getParameter("name");
	String birth = request.getParameter("birth");


	
	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	String WhereDefault = "SELECT uId FROM User WHERE uName = '" + name + "' AND uBirthDate = '" + birth + "'";

	PreparedStatement ps = null;

	if (find.equals("pw")) {
	   String email = request.getParameter("email");
	   WhereDefault = WhereDefault + " AND uEmail = '" + email + "'";
	}; 

    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();


        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"find_idpw"  : [ 
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
			"findidpw" : "<%=rs.getString(1) %>"
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
