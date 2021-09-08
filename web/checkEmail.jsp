<%-- 
    Document   : checkUsername
    Created on : Jan 11, 2021, 7:05:34 AM
    Author     : Taha
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String email = request.getParameter("email");
    try{
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/staffsystem";
    String user = "root";
    String database_password = "0000";

    Connection Con = null;
    Statement Stmt = null;
    
    Con = DriverManager.getConnection(url, user, database_password);

    Stmt = Con.createStatement();
    String sql = "SELECT * FROM student WHERE StudentEmail = '" + email + "'";
    ResultSet rs = Stmt.executeQuery(sql);

    if (rs.next()){
        %>
            <span style="color:red" >Sorry, <%=rs.getString("StudentEmail")%> Already used !</span>
        <%
    }
    else{
        %>
            <span style="color:green">Email isn't used!</span>
        <%
    }
    
    rs.close();
    Stmt.close();

    Con.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
%>