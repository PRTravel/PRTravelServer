package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.lang.Exception;
import org.json.JSONObject;
import org.json.JSONArray;
import utilities.ToJSON;

public class Attraction{

    public static ResultSet getAttraction(Connection conn) throws SQLException{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT * FROM attractions");

        rs = stmt.executeQuery();

        return rs;
    }

    public static JSONObject getAttractionsDetail(Integer attractionID, Connection conn) throws Exception{

        PreparedStatement stmt;
        ResultSet rs, rs1, rs2 ;

        stmt = conn.prepareStatement("SELECT aname, alocation, adescription, aimageurl FROM attractions WHERE aid = ?");
        stmt.setInt(1, attractionID);

        rs = stmt.executeQuery();

        JSONObject attraction = ToJSON.convertToJSONObj(rs);

        stmt = conn.prepareStatement("SELECT sname, price FROM services NATURAL INNER JOIN attractions WHERE aid = ?");
        stmt.setInt(1, attractionID);

        rs1 = stmt.executeQuery();

        JSONArray services = ToJSON.convertToJSONArray(rs1);
        
        stmt = conn.prepareStatement("SELECT ufirst, ulast, imageurl, ctext, cdate FROM users NATURAL INNER JOIN comments WHERE aid = ? ORDER BY to_date(cdate,'YYYY MM DD') DESC");
        stmt.setInt(1, attractionID);
        
        rs2 = stmt.executeQuery();
        
        JSONArray attractionComments = ToJSON.convertToJSONArray(rs2);
        
        attraction.put("services", services);
        attraction.put("comments", attractionComments);

        return attraction;
    }
    
    public static ResultSet getWishList(Integer userID, Connection conn) throws Exception{
        
        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT aid, aname, alocation, aimageurl FROM users NATURAL INNER JOIN wishlist NATURAL INNER JOIN attractions WHERE uid = ?");
        stmt.setInt(1, userID);
        
        rs = stmt.executeQuery();
        
        return rs;
    }
    
    public static JSONObject addAttractionComment(Integer userID, String ctext, Integer aid, String cdate, Connection conn) throws Exception{
        
        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO comments (uid, ctext, cdate, pid, aid, picid) VALUES (?, ?, ?, null, ?, null)");
        stmt.setInt(1, userID);
        stmt.setString(2, ctext);
        stmt.setString(3, cdate);
        stmt.setInt(4, aid);
        
        stmt.executeUpdate();
        
        return getAttractionsDetail(aid, conn);
    }
    
    public static void addToWishList(Integer userID, Integer aid, Connection conn) throws Exception{
        
        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO wishlist (uid, aid) VALUES (?, ?)");
        stmt.setInt(1, userID);
        stmt.setInt(2, aid);
        
        stmt.executeUpdate();
    }
    
    public static ResultSet removeFromWishlist(Integer userID, Integer aid, Connection conn) throws Exception{
        
        PreparedStatement stmt;

        stmt = conn.prepareStatement("DELETE FROM wishlist WHERE uid = ? AND aid = ?");
        stmt.setInt(1, userID);
        stmt.setInt(2, aid);
        
        stmt.executeUpdate();
        
        return getWishList(userID, conn);
    }
    
    
}