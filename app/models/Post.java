package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Post{

    public static ResultSet getPost(int user, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT * FROM posts as p, users as u WHERE u.uid = ? AND p.uid = u.uid ORDER BY to_date(pdate,'YYYY MM DD') desc");
        stmt.setInt(1, user); 

        rs = stmt.executeQuery();

        return rs;
    }
}