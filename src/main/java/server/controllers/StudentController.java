package server.controllers;

import com.google.gson.Gson;
import server.models.Student;
import server.providers.DBmanager;
import server.providers.StudentTable;

import java.util.ArrayList;

public class StudentController {

/*
//skal ændres til feature 10.
    StudentTable studentTable = new StudentTable();

    public ArrayList getAttendingStudents(String idEvent) throws IllegalAccessException {
        ArrayList attendingStudents = studentTable.getAttendingStudents(idEvent);
        return attendingStudents;
    }
*/

/*
    public StudentController() {
        this.gson = new Gson();

    }

    public ArrayList<Student> getStudents() {
        DBmanager db = new DBmanager();
        ArrayList<Student> students = db.getStudents();
        return students;
    }

/*
    public Student getStudents(int id) {
        DBmanager db = new DBmanager();
        Student student = db.getStudents(id);
        return students;
    }*/




    public boolean addStudent(Student student) throws Exception {
        return true;
        /*String hashedPassword = Digester.hashWithSalt(student.getPassword());
        student.setPassword(hashedPassword);
        return DBmanager.addStudent(student);*/

    }


}
