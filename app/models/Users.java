package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.ToJSON;

public class Users{

    public static ResultSet getUsersAndPass(String user, String password, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT * FROM users WHERE uusername = ? AND upassword = ?");
        stmt.setString(1, user);
        stmt.setString(2, password);

        rs = stmt.executeQuery();

        return rs;
    }
    
    public static ResultSet getAllUsers(String find, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT uid, imageurl, ufirst, ulast, udescription FROM users WHERE ufirst LIKE ? OR ulast LIKE ? OR uusername LIKE ? OR uemail LIKE ?");
        stmt.setString(1, "%" + find + "%");
        stmt.setString(2, "%" + find + "%");
        stmt.setString(3, "%" + find + "%");
        stmt.setString(4, "%" + find + "%");
        rs = stmt.executeQuery();
        return rs;
    }
    
    public static JSONArray getAdminInfo(Connection conn) throws Exception{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT * FROM users");

        rs = stmt.executeQuery();
        
        JSONArray users = convertToJSONArray(rs, conn);
        
        return users;
    }
    
    public static ResultSet isFollowed(Integer userID, Integer friendID, Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT uid, fid FROM friends WHERE uid = ? AND fid = ?");
        stmt.setInt(1, userID);
        stmt.setInt(2, friendID);

        rs = stmt.executeQuery();

        return rs;
    }
    
    public static String followOrUnfollow(Integer userID, Integer friendID, String follow, String ntext, Connection conn) throws SQLException{

        PreparedStatement stmt;
        String rs;
        
        if(follow.equals("Unfollow")){
            stmt = conn.prepareStatement("DELETE FROM friends WHERE uid = ? AND fid = ?");
            rs = "Follow";
        } else{
            stmt = conn.prepareStatement("INSERT INTO friends (uid, fid) VALUES (?, ?)");
            rs = "Unfollow";
        }
        stmt.setInt(1, userID);
        stmt.setInt(2, friendID);

        stmt.executeUpdate();
        
        if(follow.equals("Follow")){
            stmt = conn.prepareStatement("INSERT INTO notifications (uid, authorid, cid, ntext) VALUES (?, ?, null, ?)");
            stmt.setInt(1, friendID);
            stmt.setInt(2, userID);
            stmt.setString(3, ntext);
         
            stmt.executeUpdate();
        }

        return rs;
    }
    
    public static JSONArray removeFromPictures(Integer picid, Connection conn) throws Exception{

        PreparedStatement stmt;
        
        stmt = conn.prepareStatement("DELETE FROM comments WHERE picid = ?");
        stmt.setInt(1, picid);

        stmt.executeUpdate();

        stmt = conn.prepareStatement("DELETE FROM picture WHERE picid = ?");
        stmt.setInt(1, picid);

        stmt.executeUpdate();
        
        return getAdminInfo(conn);
    }
    
    public static JSONArray removeFromAlbums(Integer albumid, Connection conn) throws Exception{

        PreparedStatement stmt;
        
        stmt = conn.prepareStatement("DELETE FROM comments WHERE picid in (SELECT C.picid  FROM comments as C, picture as P WHERE C.picid = P.picid AND albumid = ?)");
        stmt.setInt(1, albumid);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM picture WHERE albumid = ?");
        stmt.setInt(1, albumid);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM albums WHERE albumid = ?");
        stmt.setInt(1, albumid);

        stmt.executeUpdate();
        
        return getAdminInfo(conn);
    }
    
    public static JSONArray removeFromUsers(Integer userID, Connection conn) throws Exception{

        PreparedStatement stmt;
        
        stmt = conn.prepareStatement("DELETE FROM comments WHERE uid = ? OR pid in (SELECT C.pid FROM comments as C, posts as P WHERE C.pid = P.pid AND C.uid != ?)");
        stmt.setInt(1, userID);
        stmt.setInt(2, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM picture WHERE albumid in (SELECT P.albumid FROM picture as P, albums as A WHERE P.albumid = A.albumid AND uid = ?)");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM albums WHERE uid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM events WHERE uid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM posts WHERE uid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM friends WHERE uid = ? OR fid = ?");
        stmt.setInt(1, userID);
        stmt.setInt(2, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM notifications WHERE authorid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM wishlist WHERE uid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        stmt = conn.prepareStatement("DELETE FROM users WHERE uid = ?");
        stmt.setInt(1, userID);

        stmt.executeUpdate();
        
        return getAdminInfo(conn);
    }
    
    public static JSONArray convertToJSONArray(ResultSet resultSet, Connection conn) throws Exception {
        
        PreparedStatement stmt2;
        ResultSet rs2;
        JSONArray jsonArray = new JSONArray();
       
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);

                if (columnValue == null){
                    columnValue = "null";
                }

                if (obj.has(columnName)){
                    columnName += "1";
                }
                
                if(columnName.equals("uid")){
                    stmt2 = conn.prepareStatement("SELECT alocation, albumname, albumid, aimageurl FROM albums natural inner join attractions WHERE uid = ?");
                    stmt2.setLong(1, (Long) columnValue);
                    
                    rs2 = stmt2.executeQuery();
                    JSONArray albums = getPicturesJSONArray(rs2, conn);
                    
                    obj.put("albums", albums);
                }
                
                obj.put(columnName, columnValue);
                
            }
            
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    
    public static JSONArray getPicturesJSONArray(ResultSet resultSet, Connection conn) throws Exception {
        
        PreparedStatement stmt3;
        ResultSet rs3;
        JSONArray jsonArray = new JSONArray();
       
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);

                if (columnValue == null){
                    columnValue = "null";
                }

                if (obj.has(columnName)){
                    columnName += "1";
                }
                
                if(columnName.equals("albumid")){
                    stmt3 = conn.prepareStatement("SELECT picid, albumid, picname, pimageurl FROM picture WHERE albumid = ?");
                    stmt3.setLong(1, (Long) columnValue);
                    
                    rs3 = stmt3.executeQuery();
                    JSONArray pictures = ToJSON.convertToJSONArray(rs3);
                    
                    obj.put("pictures", pictures);
                }
                
                obj.put(columnName, columnValue);
                
            }
            
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    
}