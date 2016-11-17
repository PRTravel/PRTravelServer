package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import org.json.JSONArray;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Picture;

public class PictureController extends Controller {

    public Result getPictures(int albumID) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONArray picture = Picture.getPictureOfAlbum(albumID, activeConnection);

            db.close();

            String s = picture.toString();
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