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

        stmt = conn.prepareStatement("SELECT aname, alocation, aimageurl FROM users NATURAL INNER JOIN wishlist NATURAL INNER JOIN attractions WHERE uid = ?");
        stmt.setInt(1, userID);
        
        rs = stmt.executeQuery();
        
        return rs;
    }
}