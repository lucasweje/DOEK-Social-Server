package server.controllers;

import com.google.gson.Gson;
import server.models.Student;

import java.util.ArrayList;

public class StudentController {
    Gson gson;



    public ArrayList<AttendingStudents> getAttendingStudents(String idStudent, String idEvent) {
        ArrayList<AttendingStudents> attendingStudents = db.getAttendingStudents();
        db.close();
        return attendingStudents;
    }

/*    DBmanager db = new DBmanager();

    public StudentController() {
        this.gson = new Gson();

    }

    public ArrayList<Student> getStudents() {
        DBmanager db = new DBmanager();
        ArrayList<Student> students = db.getStudents();
        db.close();
        return students;
    }

/*
    public Student getStudents(int id) {
        DBmanager db = new DBmanager();
        Student student = db.getStudents(id);
        db.close();
        return students;
    }*/




    public boolean addStudent(Student student) throws Exception {
        return true;
        /*String hashedPassword = Digester.hashWithSalt(student.getPassword());
        student.setPassword(hashedPassword);
        return DBmanager.addStudent(student);*/

    }


}
