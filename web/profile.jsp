<%-- 
    Document   : profile
    Created on : Jan 11, 2021, 5:24:40 AM
    Author     : Taha
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff System</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <div id="nav-placeholder"></div>
        <script>
        $(function(){
          $("#nav-placeholder").load("nav.html");
        });
        </script>
        <form action="editProfile" method="post">
            <div id="createMessage" style="margin: 100px 300px 0 300px;">
                
                <%
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/staffsystem";
                    String user = "root";
                    String database_password = "0000";
                    Connection Con = null;
                    Con = DriverManager.getConnection(url, user, database_password);

                    int stud_id = Integer.valueOf(session.getAttribute("session_ID").toString());

                    String q = "SELECT * FROM student WHERE student.StudentID = " + stud_id; 

                    Statement Stmt = Con.createStatement();
                    ResultSet rs = Stmt.executeQuery(q);
                    
                    String usrname = "", password = "", firstname = "", lastname = "", email = "", contact = "";
                    while (rs.next()){
                        usrname = rs.getString("StudentUsername");
                        password = rs.getString("StudentPassword");
                        firstname = rs.getString("StudentFirstname");
                        lastname = rs.getString("StudentLastname");
                        email = rs.getString("StudentEmail");
                        contact = rs.getString("StudentContact");
                    }
                    rs.close();
                    Stmt.close();
                    Con.close();
                %>   
                
                <span style="margin-left: 15px; display: block;">Username</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="username" readonly="readonly" value="<%=usrname%>">
                </div>
                
                <span style="margin-left: 15px; display: block;">Firstname</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="firstname" value="<%=firstname%>">
                </div>
                
                <span style="margin-left: 15px; display: block;">Lastname</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="lastname" value="<%=lastname%>">
                </div>
                        
                <span style="margin-left: 15px; display: block;">Password</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="password" value="<%=password%>">
                </div>
                        
                <span style="margin-left: 15px; display: block;">Email</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="email" value="<%=email%>">
                </div>
                      
                <span style="margin-left: 15px; display: block;">Contact</span>
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" name="contact" value="<%=contact%>">
                </div>
                <br>
                <div style="width: 50%;">
                <button class="option-btn" type="submit">Done</button>
                </div>
            </div>
        </form>
    </body>
</html>
