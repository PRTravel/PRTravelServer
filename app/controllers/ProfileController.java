package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Users;

public class ProfileController extends Controller {

    public Result isFollowed(Integer userID, Integer friendID){

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet follows = Users.isFollowed(userID, friendID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONObj(follows).toString();
            System.out.println(s);
            if(!s.equals("{}")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
      public Result followOrUnfollow(Integer userID, Integer friendID, String follow, String ntext){

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            String result = Users.followOrUnfollow(userID, friendID, follow, ntext, activeConnection);

            db.close();

            System.out.println(result);
            if(result.equals("Unfollow") || result.equals("Follow")){
                return ok(result);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }

}