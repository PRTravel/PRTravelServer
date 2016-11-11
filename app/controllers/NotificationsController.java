package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.Connection;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Notifications;

public class NotificationsController extends Controller {

    public Result getNotifications(Integer userID) {

        try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            ResultSet notificationText = Notifications.getNotificationsInfo(userID, activeConnection);

            db.close();

            String s = ToJSON.convertToJSONArray(notificationText).toString();

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
