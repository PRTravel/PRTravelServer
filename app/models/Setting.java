package models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Setting{

    public static void changePassword(int userID, String password, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("UPDATE USERS set upassword = ? where uid= ?");
        stmt.setString(1, password); 
        stmt.setInt(2, userID);

       stmt.executeUpdate();

        
    }
    
     public static void changeEmail(int userID, String email, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("UPDATE USERS set uemail = ? where uid= ?");
        stmt.setString(1, email); 
        stmt.setInt(2, userID);

       stmt.executeUpdate();

        
    }
}