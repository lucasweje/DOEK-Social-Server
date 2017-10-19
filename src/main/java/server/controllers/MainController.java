package server.controllers;

import server.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    private static Connection connection = null;

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
