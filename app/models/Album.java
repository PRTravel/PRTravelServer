package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Album{

    public static ResultSet getAlbumOfUser(int userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT albumid, aid, albumname, alocation, aimageurl FROM albums natural inner join attractions natural inner join users WHERE uid = ?");
        stmt.setInt(1, userID); 

        rs = stmt.executeQuery();

        return rs;
    }
    
    public static ResultSet getDiffAlbums(int userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT aid, aimageurl, aname, alocation FROM attractions WHERE aid NOT IN (SELECT aid FROM albums WHERE uid = ?)");
        stmt.setInt(1, userID); 

        rs = stmt.executeQuery();

        return rs;
    }
    
    public static ResultSet addAlbum(int userID, int attractionID, String attractionName, Connection conn) throws SQLException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO albums (uid, aid, albumname) VALUES (?, ?, ?)");
        stmt.setInt(1, userID);
        stmt.setInt(2, attractionID); 
        stmt.setString(3, attractionName); 

        stmt.executeUpdate();

        return getAlbumOfUser(userID, conn);
    }
}