package models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.lang.Exception;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.ToJSON;

public class Post{

    public static JSONArray getPost(int user, Connection conn) throws Exception{

        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT PAUTHOR.ufirst, PAUTHOR.ulast, PAUTHOR.imageurl, p.ptext, p.ptitle, p.pdate, p.plikes, p.pcomments_count FROM posts as p, users as u, users as PAUTHOR WHERE u.uid = ? AND p.uid = u.uid AND p.pauthor = PAUTHOR.uid ORDER BY to_date(pdate,'YYYY MM DD') desc");
        stmt.setInt(1, user); 

        rs = stmt.executeQuery();
        
        JSONArray posts = convertToJSONArray(rs, conn);

        return posts;
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
                
                if(columnName.equals("pid")){
                    stmt2 = conn.prepareStatement("SELECT imageurl, ufirst, ulast, ctext, cdate FROM users NATURAL INNER JOIN comments WHERE pid = ? ORDER BY to_date(cdate,'YYYY MM DD') desc");
                    stmt2.setInt(1, (int) columnValue);
                    
                    rs2 = stmt2.executeQuery();
                    
                    JSONArray postComments = ToJSON.convertToJSONArray(rs2);
                    
                    obj.put("comments", postComments);
                }
                
                obj.put(columnName, columnValue);
            }
            
            jsonArray.put(obj);
        }
        return jsonArray;
    }
}