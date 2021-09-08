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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <style>
            .select-wrapper input.select-dropdown {
                position: relative;
                cursor: pointer;
                background-color: transparent;
                border: 2px solid #d9d9d9;
                outline: none;
                border-radius: 50px;
                height: 3rem;
                line-height: 3rem;
                width: 100%;
                font-family: Poppins-Regular;
                font-size: 18px;
                margin: 0 0 8px 0;
                padding-left: 20px;
                display: block;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
                z-index: 1;
            }
            textarea{
                
            }
            textarea:focus, input:focus {
                border-color: #d9d9d9 !important;
            }
        </style>
    </head>
    <body>
        <div id="nav-placeholder"></div>
        <script>
        $(function(){
          $("#nav-placeholder").load("nav.html");
        });
        </script>
        <br>
        <div style="width:50%">
        <button class="option-btn" onclick="showCreateMessage()">Create Message</button>
        </div>
        <div style="width:50%">
        <button class="option-btn" onclick="showMailbox()">Inbox</button>
        </div>
        <form action="sendMessage" method="post">
            <div id="createMessage" style="display: none; margin: 100px;">
                <div class="input-field">
                    <select id="subject" name="sub">
                    </select>
                </div>
                <div class="input-field">
                    <select id="staff" name="sta">
                    </select>
                </div>
                
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $.ajax({
                    url: "GSONData",
                    method: "GET",
                    data: {operation: 'subject'},
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        let obj = $.parseJSON(data);
                        $.each(obj, function (key, value) {
                            $('#subject').append('<option value="' + value.id +'">' + value.name + '</option>');
                        });
                        $('select').formSelect();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $('#subject').append('<option>Subject Unavailable</option>');
                    },
                    cache: false
                });


                $('#subject').change(function () {
                    $('#staff').find('option').remove();
                    $('#staff').append('<option value="all">All</option>'); 

                    let sid = $('#subject').val();
                    let data = {
                        operation: "staff",
                        id: sid
                    };

                    $.ajax({
                        url: "GSONData",
                        method: "GET",
                        data: data,
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            let obj = $.parseJSON(data);
                            $.each(obj, function (key, value) {
                                $('#staff').append('<option value="' + value.id + '">' + value.firstname + " " + value.lastname + '</option>');
                            });
                            $('select').formSelect();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $('#staff').append('<option>Staff Unavailable</option>');
                        },
                        cache: false
                    });
                });
            });
        </script>    
                <div class="wrap-input100 validate-input m-b-50">
                    <textarea class="textarea100" name="message" placeholder="Message" rows="8" cols="50"></textarea>
                </div>
                <div style="width: 50%;">
                    <button class="option-btn" type="submit">Send</button>
                </div>
            </div>
        </form>
        <div id="showMessages" style="display: none">
            <h3 style="margin: 4rem 0rem 2rem 3rem;">Messages</h3>
                <br>
                <table class="tablecss">
                    <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Message</th>
                            <th>Date</th>
                            <th>Sender</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/staffsystem";
                        String user = "root";
                        String database_password = "0000";
                        Connection Con = null;
                        Con = DriverManager.getConnection(url, user, database_password);

                        int stud_id = Integer.valueOf(session.getAttribute("session_ID").toString());

                        String selectQuery = "SELECT Subject, Content, Date, StaffFirstname, StaffLastname, Type FROM message "
                                    + "INNER JOIN staff ON message.SenderID = staff.StaffID WHERE message.ReceiverID = " + stud_id;
                        
                        
                        Statement Stmt = Con.createStatement();
                        ResultSet rs = Stmt.executeQuery(selectQuery);
                    
                        while (rs.next()){%>
                        <tr>
                            <td><%=rs.getString(1)%></td>
                            <td><%=rs.getString(2)%></td>
                            <td><%=rs.getString(3)%></td>
                            <td>
                                <%
                                    if(rs.getString(6).equals("Doctor")){
                                        out.print("Dr/ ");
                                    }
                                    else{
                                        out.print("TA/ ");
                                    }  
                                %>
                                <%=rs.getString(4) + " " + rs.getString(5)%>
                            </td>
                        </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <%  
                rs.close();
                Stmt.close();
            %>
        </div>
        <script>
            function showCreateMessage()
            {
                var x = document.getElementById("createMessage");
                var y = document.getElementById("showMessages");
                if (x.style.display === "none") {
                    y.style.display = "none";
                    x.style.display = "block";
                }
                else if(x.style.display === "block"){
                    x.style.display = "none";
                }
            }
            function showMailbox()
            {
                var x = document.getElementById("showMessages");
                var y = document.getElementById("createMessage");
                if (x.style.display === "none") {
                    y.style.display = "none";
                    x.style.display = "block";
                }
                else if(x.style.display === "block"){
                    x.style.display = "none";
                }
            }
        </script>
    </body>
</html>