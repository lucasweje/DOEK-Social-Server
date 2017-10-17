package server.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {

    private static Connection connection;

    static {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://"+System.getenv("DATABASE_HOST") + ":" + System.getenv("DATABASE_PORT") + "/" + System.getenv("DATABASE_NAME"), System.getenv("DATABASE_USER"), System.getenv("DATABASE_PASSWORD"));
        } catch (SQLException sqlException){
            System.out.print(sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}