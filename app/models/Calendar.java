package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Calendar{

    public static ResultSet getCalendar(Integer userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT FE.uid, FE.fid, E.uid, E.title, E.start, E.end1 FROM (events natural inner join friends) as FE, events as E WHERE  FE.uid = ? AND FE.fid = E.uid");
        stmt.setInt(1, userID);


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