package server.controllers;

import server.providers.StudentTable;
import server.models.Student;
import server.models.Token;
import server.providers.DBmanager;
import server.utility.Crypter;
;import java.sql.SQLException;

public class TokenController {

    DBmanager dBmanager = new DBmanager();
    StudentTable st = new StudentTable();

    public Token authorize(String email, String password) throws SQLException {

        Token token = new Token();

        Student studentFound = st.authorize(email, password);
        if (studentFound != null) {

            // Hvis der eksisterer en bruger med ovenstående, vil der blive oprettet et token med disse forekomster.
            // Denne oprettelse af token vil ske i klassen Crypter.java

            token.setToken(Crypter.buildToken(" adasdvxxvklxvadsaetwf2131242£©©31", 25));
            token.setStudent(studentFound);

            //Dette gemmer token i StudentTable og sættes til en bestemt bruger ID
            st.addToken(token.getToken(), studentFound.getIdStudent());

        } else {
            token = null;

        }
        // Fra token til klienten vha. return
        return token;

        }
        // Metode til at modtage en token og sende et student objekt retur
    public Student getStudentFromTokens(String token) throws SQLException {
        StudentTable st = new StudentTable();
        Student student = st.getStudentFromToken(token);
        st.close();
        return student;

    }

    // Metode til ta slette en token (eventuelt ved log ud)

    public boolean deleteToken(String token) throws SQLException{
        StudentTable st = new StudentTable();
        boolean deleteToken = new st.deleteToken(token);
        st.close();
        return true;



    }







}
