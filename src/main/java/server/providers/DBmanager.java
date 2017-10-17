package server.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {

    public Connection getConnection() {

        Connection connection = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/døk_social?useSSL=false&serverTimezone=GMT","root" , "Tvisling1012");
        } catch (SQLException sqlException) {
            System.out.print(sqlException.getMessage());
            sqlException.printStackTrace();
        }
        return connection;
    }
}


//localhost: 3306 skal ændres tilbage når config filen er lavet.