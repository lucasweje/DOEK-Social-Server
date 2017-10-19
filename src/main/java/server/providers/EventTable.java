package server.providers;

import server.models.Event;
import server.models.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventTable extends DBmanager {
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> allEvents = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            PreparedStatement getAllEventsStatement = getConnection().prepareStatement
                    ("SELECT * FROM event");

            resultSet = getAllEventsStatement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("idEvent"),
                        resultSet.getInt("Price"),
                        resultSet.getInt("idStudent"),
                        resultSet.getString("EventName"),
                        resultSet.getString("Location"),
                        resultSet.getString("Description"),
                        resultSet.getString("Pictures"),
                        resultSet.getTimestamp("Date"));


                        allEvents.add(event);
            }

            resultSet.close();

            getAllEventsStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allEvents;
    }

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

}
