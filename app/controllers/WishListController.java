package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Attraction;

public class WishListController extends Controller {

    public Result getWishList(Integer userID) {
        System.out.println(userID);
        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();
            
            ResultSet wishlist = Attraction.getWishList(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(wishlist).toString();
            
            if(!s.equals("[]")) {
                return ok(s);
            } 

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");

    }

}