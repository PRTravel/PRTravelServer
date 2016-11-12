package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Calendar{

    public static ResultSet getCalendar(Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT title, start, end1, allday FROM events");
        

        rs = stmt.executeQuery();

        return rs;
    }
    
    public static ResultSet getProfileCalendar(Integer userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT uid, title, start, end1, allday FROM events WHERE  uid = ?");
        stmt.setInt(1, userID);

        rs = stmt.executeQuery();

        return rs;
    }

}