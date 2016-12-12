package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.MakeConnection;
import models.Register;
import utilities.ToJSON;

public class RegisterController extends Controller {

    public Result register(String firstname, String lastname, String email, String username, String password,  Integer creditcard, Integer cvc, String billing)
    {

          try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            Register.signUp(firstname, lastname, email, username, password, creditcard, cvc, billing, activeConnection);
            db.close();

            return ok("Success");
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
     public Result checkPin(String username)
    {

          try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            ResultSet PIN = Register.checkPin(username,activeConnection);

            db.close();

            String s = ToJSON.convertToJSONObj(PIN).toString();
           if(!s.equals("{}")){
                return ok(s);
            }
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
       public Result pinOK(String username)
    {

          try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            Register.pinOK(username, activeConnection);
            db.close();

            return ok("Success");
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    

}