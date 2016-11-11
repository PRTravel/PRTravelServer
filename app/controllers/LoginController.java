package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Users;

public class LoginController extends Controller {

    public Result login(String user, String password) {
        
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet usersAndPass = Users.getUsersAndPass(user, password, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONObj(usersAndPass).toString();

            if(!s.equals("{}")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
}
