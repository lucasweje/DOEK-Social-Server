package server.providers;

import server.exceptions.ResponseException;
import server.models.Event;
import server.models.Student;
import server.models.StudentHasEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventTable extends DBmanager {

    public ArrayList<Event> getAllEvents() throws SQLException {
        ArrayList<Event> allEvents = new ArrayList<>();

        ResultSet resultSet = null;


        PreparedStatement getAllEventsStatement = getConnection().prepareStatement
                ("SELECT * FROM events");

        resultSet = getAllEventsStatement.executeQuery();

        while (resultSet.next()) {
            Event event = new Event(
                    resultSet.getInt("idEvent"),
                    resultSet.getInt("price"),
                    resultSet.getInt("idStudent"),
                    resultSet.getString("eventName"),
                    resultSet.getString("location"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("date"));


            allEvents.add(event);
        }

        resultSet.close();

        getAllEventsStatement.close();


        return allEvents;
    }

    public boolean isStudentAlreadyAttending(String eventId, String studentId) {

        ResultSet resultSet = null;
        ArrayList<StudentHasEvent> attendingStudents = new ArrayList<>();
        //Henter alt fra student_has_event for at kunne tjekke om en student har joinet det samme event flere gange
        PreparedStatement alreadyAttending = null;

        try {
            alreadyAttending = getConnection().prepareStatement
                    ("SELECT * FROM student_has_event WHERE events_idEvent = ? AND students_idStudent1 = ?");
            alreadyAttending.setString(1, eventId);
            alreadyAttending.setString(2, studentId);


            resultSet = alreadyAttending.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean joinEvent(String eventId, String studentId) throws ResponseException {

        if (isStudentAlreadyAttending(eventId, studentId)) {
            throw new ResponseException("Student is already attending event").setStatus(400);
        }

        try {
            //kalder metoden der tjekker om studenten allerede har tilmeldt sig det pågældende event


            //Statement der sætter studentens id og eventets id sammen i en tabel
            PreparedStatement joinEvent = getConnection().prepareStatement
                    ("INSERT INTO student_has_event (events_idEvent, students_idStudent1) VALUE (?, ?)");
            // OBS skal være en string men der er ikke ændret i model.Event endnu
            joinEvent.setString(1, eventId);
            joinEvent.setString(2, studentId);

            int rowsAffected = joinEvent.executeUpdate();


            if (rowsAffected != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
