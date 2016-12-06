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

    public static void addProfileCalendar(Integer userID, String title, String start, String end1, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO events (uid, title, start, end1, allday, aid)" + "VALUES (?,?,?,?, false, 1)");
        stmt.setInt(1, userID);
        stmt.setString(2, title);
        stmt.setString(3, start);
        stmt.setString(4, end1);
        



        stmt.executeUpdate();

    }

}