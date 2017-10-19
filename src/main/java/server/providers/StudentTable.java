package server.providers;

import server.models.Student;
import server.utility.Authenticator;

import javax.ws.rs.core.Response;
import java.sql.*;
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
                            resultSet.getLong("createdTime")
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
                            resultSet.getLong("createdTime")
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
        long unixTime = System.currentTimeMillis() / 1000L;
//generer salt password
        student.setSalt(student.getEmail() + unixTime);
//generer hashed password med salt.
        student.setPassword(Authenticator.hashWithSalt(student.getPassword(), student.getSalt()));
        student.setCreatedTime(unixTime);

        PreparedStatement addStudentStatement = getConnection().prepareStatement("INSERT INTO students (firstName, lastName, email, password, createdTime) VALUES (?, ?, ?, ?, ?)");

        try {
            addStudentStatement.setString(1, student.getFirstName());
            addStudentStatement.setString(2, student.getLastName());
            addStudentStatement.setString(3, student.getEmail());
            addStudentStatement.setString(4, student.getPassword());
            addStudentStatement.setLong(5, student.getCreatedTime());
            try {
                int rowsUpdated = addStudentStatement.executeUpdate();
                if (rowsUpdated != 1) {
                    throw new SQLException("More or less than 1 row was affected");
                }
            } catch (SQLIntegrityConstraintViolationException integrity) {
                integrity.printStackTrace();
                throw new SQLException("the user already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("SQL Error");
        }

        return true;
    }

    public Student getStudentByEmail(String email) {
        Student student = null;

        ResultSet resultSet = null;

        try {
            PreparedStatement getStudentEmailStatement = connection.prepareStatement("SELECT * FROM students WHERE email = ?");

            getStudentEmailStatement.setString(1, email);
            resultSet = getStudentEmailStatement.executeQuery();

            while (resultSet.next()) {
                student = new Student(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("createdTime")
                );
            }

            if (student == null) {
                throw new IllegalArgumentException();
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

}