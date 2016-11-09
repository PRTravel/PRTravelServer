package controllers;

import play.mvc.*;
import java.sql.*;

public class LoginController extends Controller {

    public Result login(String user, String password) {
        
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Connection conn = DriverManager.getConnection(url,"abdielvega","abdiel123");
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT uusername, upassword FROM users");
            while ( rs.next() ) {
                String username = rs.getString("uusername");
                String upassword = rs.getString("upassword");
                if(username.equals(user) && upassword.equals(password)){
                    conn.close();
                    return ok("Success");
                }
            }
          conn.close();  
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }

}
