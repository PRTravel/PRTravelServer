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

        stmt = conn.prepareStatement("SELECT imageurl, ufirst, ulast FROM users WHERE ufirst LIKE ? OR ulast LIKE ? OR uusername LIKE ? OR uemail LIKE ?");
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
                    stmt2.setInt(1, (int) columnValue);
                    
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
                    stmt3 = conn.prepareStatement("SELECT albumid, picname, pimageurl FROM picture WHERE albumid = ?");
                    stmt3.setInt(1, (int) columnValue);
                    
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