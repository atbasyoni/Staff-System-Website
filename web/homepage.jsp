<%-- 
    Document   : homepage
    Created on : Jan 7, 2021, 2:08:47 AM
    Author     : Taha
--%>

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
        <%if(session.getAttribute("session_type").toString().equals("student")){%>
            <div id="nav-placeholder"></div>
            <script>
            $(function(){
              $("#nav-placeholder").load("nav.html");
            });
            </script>
        <%}
        else{%>
            <div id="nav-placeholder"></div>
            <script>
            $(function(){
              $("#nav-placeholder").load("nav2.html");
            });
            </script>
        <%}%>

        
    </body>
</html>
