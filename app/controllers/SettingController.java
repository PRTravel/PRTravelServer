package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Setting;

public class SettingController extends Controller {

    public Result changePassword(Integer userID, String password) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            Setting.changePassword(userID, password, activeConnection);
            
            db.close();
            return ok("Success");

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }
    
        public Result changeEmail(Integer userID, String email) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            Setting.changeEmail(userID, email, activeConnection);
            
            db.close();
            return ok("Success");

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }
    
         public Result changeCreditCard(Integer userID, Integer creditcard, Integer cvc) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            Setting.changeCreditCard(userID, creditcard, cvc, activeConnection);
            
            db.close();
            return ok("Success");

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }


}