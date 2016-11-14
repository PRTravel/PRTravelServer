package utilities;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MakeConnection{
    private String url;
    private Connection conn;

    public MakeConnection() throws SQLException{
        url = "jdbc:postgresql://localhost:5432/postgres";

        conn = DriverManager.getConnection(url,"abdielvega","abdiel123");
    }

    public Connection connect() throws SQLException{
        return conn;
    }

    public void close() throws SQLException{
        conn.close();
    }


}