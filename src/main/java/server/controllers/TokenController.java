package server.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import server.providers.StudentTable;
import server.models.Student;
import server.models.Token;
import server.providers.DBmanager;
import server.utility.Crypter;
import server.utility.CurrentStudentContext;
;import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

public class TokenController {

    StudentTable studentTable = new StudentTable();


/*
    // Metode til at modtage en token og sende et student objekt retur
    public Student getStudentFromTokens(String token) throws SQLException {
        Student student = st.getStudentFromToken(token);
        st.close();
        return student;
    }
*/

    // Metode til ta slette en token (eventuelt ved log ud)
    public boolean deleteToken(String token) throws SQLException {
        boolean deleteToken = studentTable.deleteToken(token);
        studentTable.close();
        if (deleteToken) {
            return true;
        }
        return false;
    }

    public String setToken(Student student) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512("harpoonsurvival");
            long timeValue = (System.currentTimeMillis() * 1000) + 2000125124L;
            Date expDate = new Date(timeValue);

            token = JWT.create().withClaim("User", student.getEmail()).withExpiresAt(expDate).withIssuer("STFU").sign(algorithm);
            studentTable.addToken(token, student.getIdStudent());
            studentTable.close();
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

    public CurrentStudentContext getStudentFromTokens(String token) throws SQLException {
        Student student = studentTable.getStudentFromToken(token);
        CurrentStudentContext context = new CurrentStudentContext();
        context.setCurrentStudent(student);
        studentTable.close();
        return context;
    }

}
