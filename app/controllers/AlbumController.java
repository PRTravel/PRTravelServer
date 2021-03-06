package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Album;

public class AlbumController extends Controller {

    public Result getAlbums(int userID) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet album = Album.getAlbumOfUser(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(album).toString();
            if(!s.equals("[]")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");


    }
    
    public Result getDiffAlbums(int userID) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet albums = Album.getDiffAlbums(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(albums).toString();
            if(!s.equals("[]")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");
    }
    
    public Result addAlbum(int userID, int attractionID, String attractionName) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet albums = Album.addAlbum(userID, attractionID, attractionName, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(albums).toString();
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