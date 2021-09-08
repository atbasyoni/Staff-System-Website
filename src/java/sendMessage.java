import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/sendMessage"})
public class sendMessage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String receiver = request.getParameter("sta");
            String subject = request.getParameter("sub");
            
            Random rand = new Random();
            int rand_MsgID = rand.nextInt(10000);
            
            String message = request.getParameter("message"); 
            int sender = Integer.parseInt(request.getSession(false).getAttribute("session_ID").toString());
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffsystem";
            String user = "root";
            String database_password = "osaryelmasry";
            Connection Con = null;
            Statement Stmt = null;
            
            Con = DriverManager.getConnection(url, user, database_password);
            
            if(receiver.equals("all")){
                out.print(subject);
                out.print(receiver);
                Stmt = Con.createStatement();      
                String q = "SELECT StaffID FROM subject WHERE SubjectName IN (SELECT SubjectName From subject WHERE SubjectID = " + subject + ")";
                ResultSet rs = Stmt.executeQuery(q);
                List IDs = new ArrayList();
                while (rs.next()){
                    IDs.add(rs.getInt(1));
                }

                rs.close();
                Stmt.close();
                
                PreparedStatement preparedStmt = null;
                
                for(int i = 0 ; i < IDs.size() ; i++){
                    // the mysql insert statement
                    String query = "INSERT INTO message (MessageID, SenderID,"
                            + " ReceiverID, Subject, Content, "
                            + "Date)"
                      + " VALUES (?, ?, ?, ?, ?, ?)";

                    out.print(IDs.get(i).toString());
                    
                    // create the mysql insert preparedstatement
                    preparedStmt = Con.prepareStatement(query);
                    
                    preparedStmt.setInt(1, rand_MsgID);
                    preparedStmt.setInt(2, sender);
                    preparedStmt.setInt(3, Integer.parseInt(IDs.get(i).toString()));
                    preparedStmt.setString(4, subject);
                    preparedStmt.setString(5, message);
                    LocalDateTime time = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    preparedStmt.setString(6, time.format(format));
                    out.print("jhgjh");
                    // execute the preparedstatement
                    preparedStmt.execute();
                    out.print("hhhhh");
                    preparedStmt.close();
                }
            }
            else{
                int rece = Integer.parseInt(receiver);
                
                // the mysql insert statement
                String query = "INSERT INTO message (MessageID, SenderID,"
                        + " ReceiverID, Subject, Content, "
                        + "Date)"
                  + " VALUES (?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = Con.prepareStatement(query);
                preparedStmt.setInt(1, rand_MsgID);
                preparedStmt.setInt(2, sender);
                preparedStmt.setInt(3, rece);
                preparedStmt.setString(4, subject);
                preparedStmt.setString(5, message);
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                preparedStmt.setString(6, time.format(format));

                // execute the preparedstatement
                preparedStmt.execute();
                preparedStmt.close();
            }
            Con.close();
            response.sendRedirect("message.jsp");
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
            Logger.getLogger(sendMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(sendMessage.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(sendMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(sendMessage.class.getName()).log(Level.SEVERE, null, ex);
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
