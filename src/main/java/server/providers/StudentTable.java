package server.providers;

import server.models.Student;
import server.providers.DBmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentTable extends DBmanager {


/*
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
                            resultSet.getString("email")
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

/*
    public ArrayList getAttendingStudents(String idEvent) throws IllegalAccessException {
        Student student = null;
        ResultSet resultSet = null;
        ArrayList attendingStudents = new ArrayList();

        //henter alle studenter der deltager på det valgte event.
        try {
            PreparedStatement getAttendingStudents = getConnection().prepareStatement
                    ("SELECT she.*, e.*, s. " +
                            "FROM student_has_event she " +
                            "INNER JOIN events e " +
                            "ON she.Event_idEvent = e.idEvent " +
                            "INNER JOIN student s " +
                            "ON she.Student_idStudent = s.idStudent " +
                            "WHERE e.idEvent = ?;");

            getAttendingStudents.setString(1, idEvent);
            resultSet = getAttendingStudents.executeQuery();

            while (resultSet.next()) {
                try {
                    //Opretter ny instans af de studenter der er i ArrayListen. (Måden man henter oplysninger).
                    student = new Student(
                            resultSet.getString("idStudent"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email")
                    );
                    attendingStudents.add(student);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        //Returnerer attendingStudents med oplysninger.
        return attendingStudents;
    }
*/

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
                            resultSet.getString("email")
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
}