package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Calendar{

    public static ResultSet getCalendar(Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT title, start, allday FROM events");
        

        rs = stmt.executeQuery();

        return rs;
    }

}