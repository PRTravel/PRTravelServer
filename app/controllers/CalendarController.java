package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Calendar;

public class CalendarController extends Controller {
    
    

    public Result getCalendar(Integer userID) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet events = Calendar.getCalendar(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(events).toString();

            if(!s.equals("[]")) {
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }
    
    public Result getProfileCalendar(Integer userID) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet events = Calendar.getProfileCalendar(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(events).toString();

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
