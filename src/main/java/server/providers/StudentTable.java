package server.providers;

import server.models.Student;
import server.utility.Authenticator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentTable extends DBmanager {


    //Nulstiller connection. Maskineriet brokker sig hvis connection ikke er sat fra starten af.
    Connection connection = null;

    //Metode til at hente alle students.
    public ArrayList getStudents() throws IllegalAccessException {

        //Opretter metodens attributter.
        ArrayList studentList = new ArrayList();
        ResultSet resultSet = null;

        //henter alle students, der ikke er slettet.
        try {
            PreparedStatement getStudents = getConnection().prepareStatement("SELECT * FROM Students WHERE Deleted != 1");
            resultSet = getStudents.executeQuery();

            while (resultSet.next()) {
                try {
                    //Opretter ny instans af alle studenter. (Måden man henter oplysninger om alle studenter).
                    Student students = new Student(
                            resultSet.getString("idStudent"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("salt")
                    );
                    studentList.add(students);

                } catch (Exception e) {

                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        //Returnerere listen med studenter.
        return studentList;
    }

    // Henter en specifik bruger via idStudent attributten.
    public Student getStudentById(String idStudent) throws IllegalAccessException {
        Student student = null;
        ResultSet resultSet = null;

        //henter studenten med det valgte id.
        try {
            PreparedStatement getStudentById = getConnection().prepareStatement("SELECT * FROM Students WHERE idStudent=?");
            getStudentById.setString(1, idStudent);
            resultSet = getStudentById.executeQuery();

            while (resultSet.next()) {
                try {
                    //Opretter ny instans af den valgte student. (Måden man henter oplysninger om den valgte student).
                    student = new Student(
                            resultSet.getString("idStudent"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("salt")
                    );

                } catch (Exception e) {

                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        //Returnerer den enkelte student med oplysninger.
        return student;
    }
    public boolean addStudent(Student student) throws SQLException {
//generer salt password
        student.setSalt (Authenticator.randomSalt(student.getPassword()));
//generer hashed password med salt.
        student.setPassword(Authenticator.hashWithSalt(student.getPassword(),student.getSalt()));

        PreparedStatement addStudentStatement = connection.prepareStatement("INSERT INTO Students (idStudent, firstName, lastName, email, password, salt) VALUES (?, ?, ?, ?,?, ?)");

        try {
            addStudentStatement.setString(1, student.getIdStudent());
            addStudentStatement.setString(2, student.getFirstName());
            addStudentStatement.setString(3, student.getLastName());
            addStudentStatement.setString(4, student.getEmail());
            addStudentStatement.setString(5, student.getPassword());
            addStudentStatement.setString(6, student.getSalt());

            int rowsUpdated = addStudentStatement.executeUpdate();

            if(rowsUpdated != 1) {
                throw new SQLException("ERROR - NOT 1 ROW CREATED");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}