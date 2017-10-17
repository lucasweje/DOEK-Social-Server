package server.providers;

import server.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentTable extends DBmanager {

    //sætter connection til null, for at
    Connection connection = null;

    //Metode til at hente alle students.
    public ArrayList getStudents() throws IllegalAccessException {

        //Opretter metodens attributter.
        ArrayList studentList = new ArrayList();
        ResultSet resultSet = null;

        //henter alle students, der ikke er slettet.
        try {
            PreparedStatement getStudents = connection.prepareStatement("SELECT * FROM Students WHERE Deleted != 1");
            resultSet = getStudents.executeQuery();

            while (resultSet.next()) {
                try {
                    Student students = new Student(
                            resultSet.getString("idStudent"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email")
                    );
                    studentList.add(students);

                } catch (Exception e) {

                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return studentList;
    }

    public Student getStudent(String idStudent) throws IllegalAccessException {
        Student student = null;
        ResultSet resultSet = null;

        try {
            PreparedStatement getStudent = connection.prepareStatement("SELECT * FROM Students WHERE idStudent=?");
            getStudent.setString(1, idStudent);
            resultSet = getStudent.executeQuery();

            while (resultSet.next()) {
                try {
                    student = new Student(
                            resultSet.getString("idStudent"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email")
                    );

                } catch (Exception e) {

                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return student;
    }

    public boolean addStudent (Student student) throws Exception {

        PreparedStatement addStudentStatement = connection.prepareStatement("INSERT INTO Students (idStudent, firstName, lastName, email, password) VALUES (?, ?, ?, ? ?)");

        try {
            addStudentStatement.setString(1, student.getIdStudent());
            addStudentStatement.setString(2, student.getFirstName());
            addStudentStatement.setString(3, student.getLastName());
            addStudentStatement.setString(4, student.getEmail());
            addStudentStatement.setString(5, student.getPassword());

            addStudentStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}