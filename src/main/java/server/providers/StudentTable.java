package server.providers;

import server.models.Event;
import server.models.Student;
import server.utility.Authenticator;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;

public class StudentTable extends DBmanager {

    Connection connection = null;
/*
    //Nulstiller connection. Maskineriet brokker sig hvis connection ikke er sat fra starten af.

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

    public boolean addStudent(Student student) throws Exception {


        PreparedStatement addStudentStatement = connection.prepareStatement("INSERT INTO Students (idStudent, firstName, lastName, email, password) VALUES (?, ?, ?, ?, ?)");

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
*/

    public ArrayList getAttendingEvents(String idStudent) throws IllegalAccessException {
        Event event = null;
        ResultSet resultSet = null;
        ArrayList attendingEvents = new ArrayList();

        //henter alle studenter der deltager på det valgte event.
        try {
            PreparedStatement getAttendingEvents = getConnection().prepareStatement
                    ("SELECT she.*, s.*, e.* " +
                            "FROM students_has_dsevent she " +
                            "INNER JOIN students s " +
                            "ON she.students_idStudent = s.idStudent " +
                            "INNER JOIN dsevent e " +
                            "ON she.dsevent_idEvent = e.idEvent " +
                            "WHERE e.idEvent = ?");

            getAttendingEvents.setString(1, idStudent);
            resultSet = getAttendingEvents.executeQuery();

            while (resultSet.next()) {
                try {
                    //Opretter ny instans af de studenter der er i ArrayListen. (Måden man henter oplysninger).
                    event = new Event(
                            resultSet.getString("idEvent"),
                            resultSet.getInt("price"),
                            resultSet.getString("idStudent"),
                            resultSet.getString("eventName"),
                            resultSet.getString("location"),
                            resultSet.getString("description"),
                            resultSet.getTimestamp("date"));

                    attendingEvents.add(event);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        //Returnerer attendingStudents med oplysninger.
        return attendingEvents;
    }

    // Skal ikke bruges, gemmes just in case.
/*// Henter en specifik bruger via idStudent attributten.
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
    }*/


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

    public void addToken(String token, String idStudent) throws SQLException {
        PreparedStatement addTokenStatement;
        try {
            addTokenStatement = getConnection().prepareStatement("INSERT INTO tokens (token, idStudent) VALUES (?,?)");
            addTokenStatement.setString(1, token);
            addTokenStatement.setString(2, idStudent);
            addTokenStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteToken(String idStudent) throws SQLException {
        PreparedStatement deleteTokenStatement = getConnection().prepareStatement("DELETE FROM tokens WHERE idStudent = ?");
        try {
            deleteTokenStatement.setString(1, idStudent);
            deleteTokenStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Student getStudentFromToken(String token) throws SQLException {
        ResultSet resultSet = null;
        Student studentFromToken = null;

        try {

            PreparedStatement getStudentFromToken = getConnection().prepareStatement(SELECT )
        }
    }
}