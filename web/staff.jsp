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
        <!-- search bar-->
        <div class="search-container" style="margin: 0 180px; text-align: center;">
            <form action="" method="get">
                <div class="wrap-input100 validate-input m-b-50">
                    <input class="input100" type="text" placeholder="Search" name="search">
                </div>
            </form>
        </div>
            <div class="staffTable">
                <h3>Staff Members</h3>
                <br>
                <table class="tablecss">
                    <thead>
                        <tr>
                            <th>Staff</th>
                            <th>Contact</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        String search = request.getParameter("search");
                        if(search !=null)
                            search = search.replaceAll("\\s+","");
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/staffsystem";
                        String user = "root";
                        String database_password = "0000";
                        Connection Con = null;
                        Con = DriverManager.getConnection(url, user, database_password);
                        int stud_id = Integer.valueOf(session.getAttribute("session_ID").toString());                 
                        Statement Stmt = Con.createStatement();
                        ResultSet rs = Stmt.executeQuery("SELECT StaffFirstname, StaffLastname, StaffContact, StaffEmail, Type FROM staff "
                                    + " WHERE concat(StaffFirstname,StaffLastname) like '%" + search + "%' AND StaffID IN (SELECT StaffID FROM subject WHERE subject.StudentID = " + stud_id + ")");
                        while (rs.next()){%>
                        <tr>
                            <td>
                                <%
                                    if(rs.getString(5).equals("Doctor")){
                                        out.print("Dr/ ");
                                    }
                                    else{
                                        out.print("TA/ ");
                                    }  
                                %>
                                <%=rs.getString(1) + " " + rs.getString(2)%>
                            </td>
                            <td><%=rs.getString(3)%></td>
                            <td><%=rs.getString(4)%></td>
                        </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <%  
                rs.close();
                Stmt.close();
            %>
    </body>
</html>
