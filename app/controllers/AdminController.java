package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Users;
import org.json.JSONArray;

public class AdminController extends Controller {

    public Result getAdmin() {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            JSONArray admin = Users.getAdminInfo(activeConnection);
            
            db.close();

            String s = admin.toString();
            if(!s.equals("[]")) {
                return ok(s);
            } 

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }
    
    public Result removeFromPictures(Integer picid) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            JSONArray admin = Users.removeFromPictures(picid, activeConnection);
            
            db.close();

            String s = admin.toString();
            if(!s.equals("[]")) {
                return ok(s);
            } 

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }
    
    public Result removeFromAlbums(Integer albumid) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            JSONArray admin = Users.removeFromAlbums(albumid, activeConnection);
            
            db.close();

            String s = admin.toString();
            if(!s.equals("[]")) {
                return ok(s);
            } 

        } catch (Exception e) {
            System.err.println("ERROR! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }
    
    public Result removeFromUsers(Integer userID) {
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            JSONArray admin = Users.removeFromUsers(userID, activeConnection);
            
            db.close();

            String s = admin.toString();
            if(!s.equals("[]")) {
                return ok(s);
            } 

        } catch (Exception e) {
            System.err.println("ERROR! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }

}