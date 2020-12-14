package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection connection;

    public Connection getConnection()
    {
        String url="jdbc:postgresql://localhost:5432/charity_Db";
        String username="postgres";
        String password="123456";

        try {
            connection= DriverManager.getConnection(url, username, password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
