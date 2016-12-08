package models;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.sendgrid.*;
import java.io.IOException;

public class Register{

    public static void signUp(String fname, String lname, String username, String password, Connection conn) throws SQLException, IOException{

        PreparedStatement stmt;

        stmt = conn.prepareStatement("INSERT INTO USERS (uid, ufirst, ulast, uusername, upassword, adminstatus, ucreditcard, ucvc) "
               + "VALUES (10, ?, ?, ?, ?, false, 12, 2);");
        stmt.setString(1, fname); 
        stmt.setString(2, lname);
        stmt.setString(3, username);
        stmt.setString(4, password);

        stmt.executeUpdate();
        sendemail();
    }


public static void sendemail() throws IOException {
    Email from = new Email("abdiel.vega2@upr.edu");
    String subject = "Hello World from the SendGrid Java Library!";
    Email to = new Email("abdiel017@gmail.com");
    Content content = new Content("text/plain", "Hello, Email!");
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid("9PgPSqAJSwOmhM94iuUX4A");
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
  
}