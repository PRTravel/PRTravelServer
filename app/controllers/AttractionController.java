package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import org.json.JSONObject;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Attraction;

public class AttractionController extends Controller {

    public Result getAttractions() {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet attractions = Attraction.getAttraction(activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(attractions).toString();

            if(!s.equals("[]")) {
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }

    public Result getAttractionsDetail(Integer attractionID) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONObject attraction = Attraction.getAttractionsDetail(attractionID, activeConnection);

            db.close();

            String s = attraction.toString();
            if(!s.equals("{}")) {
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }
    
    public Result addAttractionComment(Integer userID, String ctext, Integer aid, String cdate){
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONObject attraction = Attraction.addAttractionComment(userID, ctext, aid, cdate, activeConnection);

            db.close();

            String s = attraction.toString();
            if(!s.equals("{}")) {
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }
    
    public Result addToWishList(Integer userID, Integer aid){
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            Attraction.addToWishList(userID, aid, activeConnection);

            db.close();

            return ok();

        } catch (Exception e) {
            return notFound("Already has the attraction.");
        }
    }
    
    public Result removeFromWishlist(Integer userID, Integer aid){
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet result = Attraction.removeFromWishlist(userID, aid, activeConnection);

            db.close();
            
            String s = ToJSON.convertToJSONArray(result).toString();
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
