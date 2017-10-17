package server.controllers;

import server.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    private static Connection connection = null;

    public Student authorizeStudent(String firstName, String password) throws IllegalArgumentException {
        ResultSet resultSet = null;
        Student student = null;

        try {
            PreparedStatement authorizeStudent = connection.prepareStatement("SELECT * FROM students where FirstName = ? AND Password = ?");

            authorizeStudent.setString(1, firstName);
            authorizeStudent.setString(2, password);

            resultSet = authorizeStudent.executeQuery();

            while (resultSet.next()) {
                student = new Student();
                student.setIdStudent(resultSet.getString("idStudent"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setEmail(resultSet.getString("Email"));
                student.setPassword(resultSet.getString("Password"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return student;
    }
}
