package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Picture{

    public static ResultSet getPictureOfAlbum(int albumID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT picid, picname, piclikes, pimageurl, p.albumid FROM picture as p, albums as a WHERE a.albumid = ? and p.albumid = a.albumid");
        stmt.setInt(1, albumID); 

        rs = stmt.executeQuery();

        return rs;
    }
}