package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.sql.Connection;
import org.json.JSONArray;
import utilities.ToJSON;
import utilities.MakeConnection;
import models.Post;

public class NewsfeedController extends Controller {

    public Result getProfileInfo() {

        return ok("Success");

    }

    public Result getNewsfeedInfo(int user) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONArray post = Post.getPost(user, activeConnection);

            db.close();

            String s = post.toString();

            if(!s.equals("[]")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("EXCEPTION HERE! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
    public Result postIt(int userID, String ptext, String pdate) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONArray posts = Post.postIt(userID, ptext, pdate, activeConnection);

            db.close();

            String s = posts.toString();

            if(!s.equals("[]")){
                return ok(s);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
       
        return notFound("Failed!");

    }
    
    public Result addPostComment(int userID, String ctext, int pid, String cdate) {

         try {
            MakeConnection db = new MakeConnection();
            Connection activeConnection = db.connect();

            JSONArray post = Post.addPostComment(userID, ctext, pid, cdate, activeConnection);

            db.close();

            String s = post.toString();

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

