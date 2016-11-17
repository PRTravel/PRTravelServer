package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Users;

public class SearchBarController extends Controller {

    public Result search(String find){

        
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet allUsers = Users.getAllUsers(find, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(allUsers).toString();
            System.out.println(s);
            if(!s.equals("[]")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }

}