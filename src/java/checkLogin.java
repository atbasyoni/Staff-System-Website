/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @author Taha
 */
@WebServlet(urlPatterns = {"/checkLogin"})
public class checkLogin extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String usr = request.getParameter("usrname");
            String psw = request.getParameter("psw");
            String type = request.getParameter("types");
            int id = 0;
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffsystem";
            String user = "root";
            String database_password = "0000";
            
            Connection Con = null;
            Statement Stmt = null;
            int rowCount = 0;
            
            Con = DriverManager.getConnection(url, user, database_password);

            if(type.equals("doctor") || type.equals("teacher")){
                Stmt = Con.createStatement();
                String sql = "SELECT * FROM staff WHERE StaffUsername = '" + usr + "' AND StaffPassword = '" + psw + "'";
                ResultSet rs = Stmt.executeQuery(sql);
                
                while (rs.next()){
                    rowCount++;
                    id = rs.getInt("StaffID");
                }

                rs.close();
                Stmt.close();
            }
            else{
                Stmt = Con.createStatement();
                String sql = "SELECT * FROM student WHERE StudentUsername = '" + usr + "' AND StudentPassword = '" + psw + "'";
                ResultSet rs = Stmt.executeQuery(sql);
                
                while (rs.next()){
                    rowCount++;
                    id = rs.getInt("StudentID");
                }

                rs.close();
                Stmt.close();
            }
            
            Con.close();
            
            if(rowCount < 1){
                out.println("<script type=\"text/javascript\">");
                out.println("alert('incorrect username/password/type of access');");
                out.println("location='index.html';");
                out.println("</script>");
            }
            
            else{  
                HttpSession session = request.getSession(true);
                session.setAttribute("session_usr", usr);
                session.setAttribute("session_ID", id);
                session.setAttribute("session_psw", psw);
                session.setAttribute("session_type", type);
                response.sendRedirect("homepage.jsp");
            }
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
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
