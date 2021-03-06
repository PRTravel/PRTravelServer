package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Calendar{

    public static ResultSet getCalendar(Integer userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT distinct FE.uid, FE.fid, E.uid, E.title, E.start, E.end1 FROM (events natural inner join friends) as FE, events as E WHERE  FE.uid = ? AND FE.fid = E.uid");
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

public static void addProfileCalendar(Integer userID, String title, String start, String end1,Integer aid, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO events (uid, title, start, end1, allday, aid)" + "VALUES (?,?,?,?, false, ?)");
        stmt.setInt(1, userID);
        stmt.setString(2, title);
        stmt.setString(3, start);
        stmt.setString(4, end1);
        stmt.setInt(5, aid);
        



        stmt.executeUpdate();

    }
    public static void deleteProfileCalendar(String title, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("delete from events where title = ?");
        stmt.setString(1, title);
       
        



        stmt.executeUpdate();

    }

}