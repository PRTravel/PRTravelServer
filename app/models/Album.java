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
}