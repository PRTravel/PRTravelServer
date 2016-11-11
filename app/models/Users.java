package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Users{

    public static ResultSet getUsersAndPass(String user, String password, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT * FROM users WHERE uusername = ? AND upassword = ?");
        stmt.setString(1, user);
        stmt.setString(2, password);

        rs = stmt.executeQuery();

        return rs;
    }
}