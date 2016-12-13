package models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.sendgrid.*;
import java.io.IOException;
import java.util.Random;
import java.sql.ResultSet;

public class Register{

    public static void signUp(String firstname, String lastname, String email, String username, String password, int creditcard, int cvc, String billing, Connection conn) throws SQLException, IOException{

        PreparedStatement stmt;
        int pin = (int)(Math.random()*9000)+1000;
        stmt = conn.prepareStatement("INSERT INTO USERS (ufirst, ulast, uusername, upassword, uemail, ucreditcard, ucvc , ubilling, active, adminstatus) "
               + "VALUES (?, ?, ?, ?, ?,?,?,?, ?, FALSE);");
        stmt.setString(1, firstname); 
        stmt.setString(2, lastname);
        stmt.setString(3, username);
        stmt.setString(4, password);
        stmt.setString(5, email);
        stmt.setInt(6, creditcard);
        stmt.setInt(7, cvc);
        stmt.setString(8, billing);
        stmt.setInt(9, pin);

       stmt.executeUpdate();
       sendemail(pin, email);
    }


public static void sendemail(int pin, String email) throws IOException {
    String text = "Thank you for joining the PRTravel community.\n To verify your account please enter this PIN at login: " + pin;
    Email from = new Email("prtravelapp@gmail.com");
    String subject = "Hello World from the SendGrid Java Library!";
    Email to = new Email(email);
    Content content = new Content("text/plain", text);
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid("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");
    Request request = new Request();
    try {
      request.method = Method.POST;
      request.endpoint = "mail/send";
      request.body = mail.build();
      System.out.println("ejrknqwnwnerwe");
      Response response = sg.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
  }
  
  
  public static ResultSet checkPin(String username, Connection conn) throws SQLException{
        PreparedStatement stmt;
        ResultSet rs;

        stmt = conn.prepareStatement("SELECT active FROM users WHERE uusername = ?");
        stmt.setString(1, username);

        rs = stmt.executeQuery();

        return rs;

    }
    
        public static void pinOK(String username, Connection conn) throws SQLException, IOException{

        PreparedStatement stmt;
        stmt = conn.prepareStatement("UPDATE USERS set active = -1 where uusername= ?");
        stmt.setString(1, username); 

       stmt.executeUpdate();
    }
    
  
  
  
}