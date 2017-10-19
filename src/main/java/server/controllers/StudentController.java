package server.controllers;

import com.google.gson.Gson;
import server.models.Event;
import server.models.Student;
import server.providers.DBmanager;
import server.providers.StudentTable;

import java.util.ArrayList;

/**
 * Created by STFU on 19/10/2017
 */

public class StudentController {

    /**
     * StudentController klassen forbinder vores endpoints med vores providers,
     * som sender SQL statements videre til databasen
     */


    StudentTable studentTable = new StudentTable();

    public ArrayList getAttendingEvents(String idStudent) throws IllegalAccessException {
        ArrayList attendingEvents = studentTable.getAttendingEvents(idStudent);
        return attendingEvents;
    }

      /*  public ArrayList<Student> getStudents() {
            DBmanager db = new DBmanager();
            ArrayList<Student> students = db.getStudents();
            return students;
        }
*/
    /*
        public Student getStudents(int id) {
            DBmanager db = new DBmanager();
            Student student = db.getStudents(id);
            return students;
        }*/

//Denne metode bruges til at verificere en student, når brugeren skal oprette sig i døk social.

    public Student verifyStudentCreation(String firstName, String lastName, String password, String email) throws IllegalArgumentException {
        Student verifiedStudent = new Student(firstName, lastName, password, email);

            // Nedenunder viser at der skal være et "@", når man indtaster sin mail.
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email did not contain @");

            // Dette gør at serveren kræver at der ikke indgår tal i navnet. og at det indtastet navn indeholder mindst 2 char.
            // ".*\\d.*" --> Tjekker om der er tal i navnet.
        } else if (firstName.length() < 2 || firstName.matches(".*\\d.*")) {
            throw new IllegalArgumentException("firstName must contain at least 2 characters, and can't contain numbers");


            // Dette tjekker det samme som overstående kode, bare med efternavn.
        } else if (lastName.length() < 2 || lastName.matches(".*\\d.*")) {
            throw new IllegalArgumentException("lastName must contain at least 2 characters, and can't contain numbers");


            // her tjekkes om det indtastede indeholder mindst et bogstav samt mindst et tal.
            // ".*[a-zA->].*" --> Her tjekker koden om det indtastet kode indeholder enten store og små bogstaver.
            // tæller dog ikke æ,ø og å.
        } else if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("password must contain atleast one char and one number");

        }
        //hvis alle tjeks godkendes så bliver studenten returneret
        return verifiedStudent;


    }
}