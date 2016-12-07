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

        stmt = conn.prepareStatement("SELECT P.pid, U.ufirst, U.ulast, U.imageurl, P.ptext, P.pdate, P.plikes, P.pcomments_count FROM posts as P, friends as F, users as U WHERE P.pauthor = F.fid AND P.pauthor = U.uid AND F.uid = ? ORDER BY pid desc");
        stmt.setInt(1, user);
        
        rs = stmt.executeQuery();
        
        JSONArray posts = convertToJSONArray(rs, conn);
        return posts;
    }
    
    public static JSONArray postIt(int userID, String ptext, String pdate, Connection conn) throws Exception{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO posts (uid, ptext, pdate, plikes, pcomments_count, pauthor) VALUES (?, ?, ?, 0, 0, ?)");
        stmt.setInt(1, userID);
        stmt.setString(2, ptext); 
        stmt.setString(3, pdate); 
        stmt.setInt(4, userID);

        stmt.executeUpdate();

        return getPost(userID, conn);
    }
    
    public static JSONArray addPostComment(int userID, String ctext, int pid, String cdate, Connection conn) throws Exception{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO comments (uid, ctext, cdate, ctime, pid, aid, picid) VALUES (?, ?, ?, null, ?, null, null)");
        stmt.setInt(1, userID);
        stmt.setString(2, ctext);
        stmt.setString(3, cdate);
        stmt.setInt(4, pid);
        
        stmt.executeUpdate();

        return getPost(userID, conn);
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
                    stmt2.setLong(1, (Long) columnValue);
                    
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