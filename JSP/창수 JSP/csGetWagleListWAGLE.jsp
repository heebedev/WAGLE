<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String moimSeq = request.getParameter("Moim_wmSeqno");

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select * from WagleCreate where Moim_mSeqno = '" + moimSeq+ "' order by wcDueDate;";
    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"wagle_list"  : [
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
			"wcSeqno" : "<%=rs.getInt(1) %>",
			"Moim_wmSeqno" : "<%=rs.getString(2) %>",
			"MoimUser_muSeqno" : "<%=rs.getString(3) %>",
			"WagleBook_wbSeqno" : "<%=rs.getString(4) %>",
			"wcName" : "<%=rs.getString(5) %>",
			"wcType" : "<%=rs.getString(6) %>",
			"wcStartDate" : "<%=rs.getString(7) %>",
			"wcEndDate" : "<%=rs.getString(8) %>",
			"wcDueDate" : "<%=rs.getString(9) %>",
			"wcLocate" : "<%=rs.getString(10) %>",
			"wcEntryFee" : "<%=rs.getString(11) %>",
			"wcWagleDetail" : "<%=rs.getString(12) %>",
			"wcWagleAgreeRefund" : "<%=rs.getString(13) %>",
			"wcValidation" : "<%=rs.getString(14) %>"
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

