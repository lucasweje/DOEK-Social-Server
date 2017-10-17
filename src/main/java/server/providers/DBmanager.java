package server.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {

    public Connection getConnection() {

        Connection connection = null;

        try {
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/døk_social","root" , "Tvisling1012");
        } catch (SQLException sqlException) {
            System.out.print(sqlException.getMessage());
            sqlException.printStackTrace();
        }
        return connection;
    }
}


//localhost: 3306 skal ændres tilbage når config filen er lavet.