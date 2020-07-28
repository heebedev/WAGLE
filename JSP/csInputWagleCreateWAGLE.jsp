<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String Moim_mSeqno = request.getParameter("Moim_wmSeqno");
	String User_uSeqno = request.getParameter("User_uSeqno");
	String WagleBook_wbSeqno = request.getParameter("WagleBook_wbSeqno");
	String wcName = request.getParameter("wcName");
	String wcType = request.getParameter("wcType");
	String wcStartDate = request.getParameter("wcStartDate");
	String wcEndDate = request.getParameter("wcEndDate");
	String wcDueDate = request.getParameter("wcDueDate");
	String wcLocate = request.getParameter("wcLocate");
	String wcEntryFee = request.getParameter("wcEntryFee");
	String wcWagleDetail = request.getParameter("wcWagleDetail");
	String wcWagleAgreeRefund = request.getParameter("wcWagleAgreeRefund");

//------

	String url_mysql = "jdbc:mysql://192.168.0.82/wagle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "insert into WagleCreate (Moim_mSeqno, User_uSeqno, WagleBook_wbSeqno, wcName, wcType, wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund";
	    String B = ") values (?,?,?,?,?,?,?,?,?,?,?,?)";

	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, Moim_mSeqno);
	    ps.setString(2, User_uSeqno);
	    ps.setString(3, WagleBook_wbSeqno);
	    ps.setString(4, wcName);
	    ps.setString(5, wcType);
	    ps.setString(6, wcStartDate);
	    ps.setString(7, wcEndDate);
	    ps.setString(8, wcDueDate);
	    ps.setString(9, wcLocate);
	    ps.setString(10, wcEntryFee);
	    ps.setString(11, wcWagleDetail);
	    ps.setString(12, wcWagleAgreeRefund);

	    ps.executeUpdate();


	    String C = "update MoimUser set muWagleNum = muWagleNum + 1 where Moim_mSeqno = ? and User_uSeqno = ?;";

	    ps = conn_mysql.prepareStatement(C);
        ps.setString(1, Moim_mSeqno);
        ps.setString(2, User_uSeqno);

        ps.executeUpdate();


	    String D = "select wcSeqno from waglecreate order by wcSeqno desc limit 1;";
	    ResultSet rs = stmt_mysql.executeQuery(D);

	    if(rs.next()) {
%>
        <%=rs.getInt(1) %>
<%
        }

	    conn_mysql.close();
	} catch (Exception e){
	    e.printStackTrace();
	}

%>