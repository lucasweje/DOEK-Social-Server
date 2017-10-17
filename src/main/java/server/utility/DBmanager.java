package server.utility;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBmanager {

    private static Connection connection;

    private static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://"
                            + System.getenv("DATABASE_HOST") + ":"
                            + System.getenv("DATABASE_PORT") + "/"
                            + System.getenv("DATABASE_NAME") + "?useSSL=false&serverTimezone=GMT",
                    System.getenv("DATABASE_USER"),
                    System.getenv("DATABASE_PASSWORD"));
        } catch (SQLException sqlException) {
            System.out.print(sqlException.getMessage());
            sqlException.printStackTrace();

            //?useSSL=false&serverTimezone=GMT er taget fra Db-utility.
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static void deleteDatabase() {
        try {
            PreparedStatement deleteStatement = getConnection()
                    .prepareStatement("DROP DATABASE " + System.getenv("DATABASE_NAME") + ";");
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void createDatabase() {
        try {
            PreparedStatement createStatement = getConnection()
                    .prepareStatement("CREATE DATABASE " + System.getenv("DATABASE_NAME") + ";");
            createStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeSql(String sqlFilePath) {
        try (InputStreamReader file = new InputStreamReader(new FileInputStream(sqlFilePath))) {
            ScriptRunner runner = new ScriptRunner(getConnection());
            runner.runScript(file);
            runner.closeConnection();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void resetDatabase() {
        deleteDatabase();
        System.out.println("Database has been deleted ... ");
        createDatabase();
        System.out.println("Database has been created ... ");
        String sqlFilePath = getWorkingDir() + "/src/main/server/Utility/sql.sql";
        executeSql(sqlFilePath);
        System.out.println("SQL Script done!");

    }

    public static void main(String[] args) {
        resetDatabase();
    }

    public static Connection getConnection() {

        return connection;
    }
}