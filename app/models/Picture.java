package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.lang.Exception;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.ToJSON;

public class Picture{

    public static JSONArray getPictureOfAlbum(int albumID, Connection conn) throws Exception{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT picid, picname, piclikes, piccomments, pimageurl, p.albumid FROM picture as p, albums as a WHERE a.albumid = ? and p.albumid = a.albumid");
        stmt.setInt(1, albumID); 

        rs = stmt.executeQuery();
        
        JSONArray pictures = convertToJSONArray(rs, conn);

        return pictures;
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
                
                if(columnName.equals("picid")){
                    stmt2 = conn.prepareStatement("SELECT imageurl, ufirst, ulast, ctext, cdate FROM users NATURAL INNER JOIN comments WHERE picid = ? ORDER BY to_date(cdate,'YYYY MM DD') desc");
                    stmt2.setInt(1, (int) columnValue);
                    
                    rs2 = stmt2.executeQuery();
                    
                    JSONArray pictureComments = ToJSON.convertToJSONArray(rs2);
                    
                    obj.put("comments", pictureComments);
                }
                
                obj.put(columnName, columnValue);
            }
            
            jsonArray.put(obj);
        }
        return jsonArray;
    }
}