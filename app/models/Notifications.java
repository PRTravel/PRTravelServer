package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Notifications{

    public static ResultSet getNotificationsInfo(Integer userID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT ntext FROM notifications WHERE  uid = ?");
        stmt.setInt(1, userID);

        rs = stmt.executeQuery();

        return rs;
    }

}