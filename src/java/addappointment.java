/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ahmed
 */
@WebServlet(urlPatterns = {"/addappointment"})
public class addappointment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            String ID = session.getAttribute("session_ID").toString();
            String StaffID = request.getParameter("StaffID");
            String startApp = request.getParameter("start");
            String endApp = request.getParameter("end");
            if(startApp.equals("") || endApp.equals("")){
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Empty field try again');");
                out.println("location='appointment.jsp';");
                out.println("</script>");
            }
            String newStart = startApp.substring(0,10)+' '+startApp.substring(11,startApp.length());
            String newEnd = endApp.substring(0,10)+' '+endApp.substring(11,endApp.length());
            //2021-01-09 17:56  newStart/end
            String newStartHour = newStart.substring(11,13);
            String newStartMinute= newStart.substring(14,newStart.length());
            String newEndHour = newEnd.substring(11,13);
            String newEndMinute= newEnd.substring(14,newEnd.length());
            //out.println("<html>"+"<body>"+"<p>"+StaffID+"</p>"+"</body>"+"</html");
            //out.println("<html>"+"<body>"+"<p>"+newEnd+"</p>"+"</body>"+"</html");
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffsystem";
            String user = "root";
            String database_password = "0000";
            Connection Con = null;
            Statement Stmt = null;
            Con = DriverManager.getConnection(url, user, database_password);
            Stmt = Con.createStatement();
            ResultSet rs = Stmt.executeQuery("SELECT Start,End FROM officehour WHERE StaffID = '"+StaffID+"'");
            while(rs.next()){
                
                String officeHoursStartHour = rs.getString("Start").substring(0,2);
                String officeHoursStartMinute = rs.getString("Start").substring(3,5);
                String officeHoursEndHour = rs.getString("End").substring(0,2);
                String officeHoursEndMinute = rs.getString("End").substring(3,5);
                int startofficehours = Integer.parseInt(officeHoursStartHour)*60 + Integer.parseInt(officeHoursStartMinute);
                int endofficehours = Integer.parseInt(officeHoursEndHour)*60 + Integer.parseInt(officeHoursEndMinute);
                int newAppStart = Integer.parseInt(newStartHour)*60 + Integer.parseInt(newStartMinute);
                int newAppEnd = Integer.parseInt(newEndHour)*60 + Integer.parseInt(newEndMinute);
                if(startofficehours <= newAppStart && endofficehours>=newAppEnd && newAppStart < newAppEnd)
                {
                    int i = Stmt.executeUpdate("INSERT INTO appointment (AppointmentDateStart,AppointmentDateEnd,StudentID,MemberID) VALUES ('"+newStart+"','"+newEnd+"','"+ID+"','"+StaffID+"');");
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Appointment added succesfully');");
                    out.println("location='appointment.jsp';");
                    out.println("</script>");
                }
                else{
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('OUT OF OFFICE HOURS or invalid input order');");
                    out.println("location='appointment.jsp';");
                    out.println("</script>");
                }
            }
            Stmt.close();
            Con.close();
            //int i = Stmt.executeUpdate("");
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addappointment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
