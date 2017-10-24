package server.controllers;

import server.providers.StudentTable;
import server.models.Student;
import server.models.Token;
import server.providers.DBmanager;
import server.utility.Crypter;
;import java.sql.SQLException;

public class TokenController {

    StudentTable st = new StudentTable();

    // Metode til at modtage en token og sende et student objekt retur
    public Student getStudentFromTokens(String token) throws SQLException {
        Student student = st.getStudentFromToken(token);
        st.close();
        return student;
    }

    // Metode til ta slette en token (eventuelt ved log ud)
    public boolean deleteToken(String token) throws SQLException {
        boolean deleteToken = st.deleteToken(token);
        st.close();
        if (deleteToken) {
            return true;
        }
        return false;
    }


}
