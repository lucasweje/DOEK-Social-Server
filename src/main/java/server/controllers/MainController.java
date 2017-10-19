package server.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import server.models.Student;
import server.providers.StudentTable;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmConstraints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MainController {

    private static Connection connection = null;
    StudentTable studentTable = new StudentTable();

    public String setToken(Student student) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512("indsaetstring");
            long timeValue = (System.currentTimeMillis() * 1000) + 2000125124L;
            Date expDate = new Date(timeValue);

            token = JWT.create().withClaim("User", student.getEmail()).withExpiresAt(expDate).withIssuer("STFU").sign(algorithm);
            studentTable.addToken(token, student.getIdStudent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (token != null) {
            return token;
        } else {
            return null;
        }
    }

    public Student getStudentFromTokens(String token) throws SQLException {
        Student student = studentTable.getStudentFromToken(token);
        return student;
    }


    /* MANGLER AT TESTES
    public boolean logout(Student currentUser) throws IllegalArgumentException {
        if(currentUser != null) {
            currentUser = null;
            return true;
        }
        //så er currentUser allerede null, send da en fejl tilbage - da ingen så er logget ind
        return false;
    }*/
}
