/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Taha
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataForMessage {
    
    public List<StaffData> getStaff(int subject_id) throws ClassNotFoundException, SQLException{
        List<StaffData> stafflist = new ArrayList();
        
        
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/staffsystem";
        String user = "root";
        String database_password = "osaryelmasry";

        Connection Con = null;
        Statement Stmt = null;

        Con = DriverManager.getConnection(url, user, database_password);

        Stmt = Con.createStatement();
        String sql = "SELECT StaffID, StaffFirstname, StaffLastname,Type FROM staff "
                + "WHERE StaffID IN (SELECT StaffID FROM subject WHERE subject.SubjectID = " + subject_id + ")";
        ResultSet rs = Stmt.executeQuery(sql);

        while (rs.next()){
            StaffData staff = new StaffData();
            staff.id = rs.getInt(1);
            staff.firstname = rs.getString(2);
            staff.lastname = rs.getString(3);
            staff.type = rs.getString(4);
            stafflist.add(staff);
        }

        rs.close();
        Stmt.close();
        Con.close();
        
        return stafflist;
    }
    
    public List<SubjectData> getSubject(int stud_id) throws ClassNotFoundException, SQLException{
        List<SubjectData> subjectlist = new ArrayList();
        
        
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/staffsystem";
        String user = "root";
        String database_password = "0000";

        Connection Con = null;
        Statement Stmt = null;

        Con = DriverManager.getConnection(url, user, database_password);

        Stmt = Con.createStatement();
        String sql = "SELECT DISTINCT SubjectID, SubjectName FROM subject WHERE subject.StudentID = " + stud_id;
        ResultSet rs = Stmt.executeQuery(sql);

        while (rs.next()){
            SubjectData subject = new SubjectData();
            subject.id = rs.getInt(1);
            subject.name = rs.getString(2);
            subjectlist.add(subject);
        }

        rs.close();
        Stmt.close();
        Con.close();
        
        return subjectlist;
    }
} 