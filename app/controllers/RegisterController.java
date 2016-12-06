package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.MakeConnection;
import models.Register;

public class RegisterController extends Controller {

    public Result register(String firstname, String lastname, String username, String password){

          try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            Register.signUp(firstname, lastname, username, password, activeConnection);
            db.close();

            return ok("Success");
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }

}