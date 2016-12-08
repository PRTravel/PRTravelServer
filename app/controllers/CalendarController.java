package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Calendar;

public class CalendarController extends Controller {
    
    

    public Result getCalendar() {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet events = Calendar.getCalendar(activeConnection);

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
    public Result addProfileCalendar(Integer userID, String title, String start, String end1,Integer aid) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

           Calendar.addProfileCalendar(userID, title, start, end1,aid, activeConnection);

            db.close();

        return ok("Success");
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }
    public Result deleteProfileCalendar(String title) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

           Calendar.deleteProfileCalendar(title, activeConnection);

            db.close();

        return ok("Success");
           

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notFound("Failed!");
    }


}
