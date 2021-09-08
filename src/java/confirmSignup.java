import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


@WebServlet(urlPatterns = {"/confirmSignup"})
public class confirmSignup extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String usr = request.getParameter("usrname");
            String first_name = request.getParameter("firstname");
            String last_name = request.getParameter("lastname");
            String contact = request.getParameter("contact");
            String email = request.getParameter("email");
            String type = request.getParameter("types");
            String year = request.getParameter("year");
            /*
            String remoteAddr = request.getRemoteAddr();
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
            
            reCaptcha.setPrivateKey("6LdtjSQaAAAAAAapa5RGXU_VavyjJM5phXNRyDdF");

            String challenge = request.getParameter("recaptcha_challenge_field");
            String uresponse = request.getParameter("recaptcha_response_field");
            
            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

            if (reCaptchaResponse.isValid()) {
              out.print("Answer was entered correctly!");
            } else {
              out.print("Answer is wrong");
            }
            */
            Random rand = new Random();
            int rand_ID = rand.nextInt(10000);
            
            Random rand2 = new Random();
            int rand_pass = rand2.nextInt(100000);
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/staffsystem";
            String user = "root";
            String database_password = "0000";
            Connection Con = null;
            Statement Stmt = null;
            
            Con = DriverManager.getConnection(url, user, database_password);
            
            if(type.equals("Student"))
            {
                // the mysql insert statement
                String query = "INSERT INTO student (StudentID, StudentUsername,"
                        + " StudentPassword, StudentFirstname, StudentLastname, "
                        + "StudentContact, StudentEmail, StudentYear)"
                  + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                String pass = String.valueOf(rand_pass);
                
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = Con.prepareStatement(query);
                preparedStmt.setInt(1, rand_ID);
                preparedStmt.setString(2, usr);
                preparedStmt.setString(3, pass);
                preparedStmt.setString(4, first_name);
                preparedStmt.setString(5, last_name);
                preparedStmt.setString(6, contact);
                preparedStmt.setString(7, email);
                preparedStmt.setString(8, year);

                // execute the preparedstatement
                preparedStmt.execute();
                preparedStmt.close();
                
                sendMail(email, first_name, pass);
                //out.println("Sent message successfully....");
            }
            else{
                // the mysql insert statement
                String query = "INSERT INTO staff (StaffID, StaffUsername,"
                        + " StaffPassword, StaffFirstname, StaffLastname, "
                        + "StaffContact, StaffEmail, StaffType)"
                  + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = Con.prepareStatement(query);
                preparedStmt.setInt(1, rand_ID);
                preparedStmt.setString(2, usr);
                preparedStmt.setString(3, String.valueOf(rand_pass));
                preparedStmt.setString(4, first_name);
                preparedStmt.setString(5, last_name);
                preparedStmt.setString(6, contact);
                preparedStmt.setString(7, email);
                preparedStmt.setString(8, type);

                // execute the preparedstatement
                preparedStmt.execute();
                preparedStmt.close();
                
                
            }
            Con.close();
            response.sendRedirect("index.html");
        }
    }
    
    private void sendMail(String to, String name, String pass){
        final String username = "staff56465@gmail.com";
        final String password = "staff123789";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("staff56465@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            message.setSubject("Staff System");
            message.setText("Hi " + name + "\n\n Congratulations! Your registration process successed and "
                 + "your temporary password is " + pass);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
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
            Logger.getLogger(confirmSignup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(confirmSignup.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(confirmSignup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(confirmSignup.class.getName()).log(Level.SEVERE, null, ex);
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
