package controllers;

import play.mvc.*;
import java.sql.*;
import org.json.*;

public class LoginController extends Controller {

    public Result login(String user, String password) {
        
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Connection conn = DriverManager.getConnection(url,"abdielvega","abdiel123");
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM users WHERE uusername = " + "'" + user + "'" + " AND upassword = " + "'" + password + "'");
            String s = convertResultSetIntoJSON(rs).toString();
            System.out.println(s);
            conn.close();
            
            return ok(s);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
 
public static JSONObject convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONObject obj = new JSONObject();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc - 
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            
        }
        return obj;
    }   

}
