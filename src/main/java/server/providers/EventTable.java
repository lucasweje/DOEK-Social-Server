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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allEvents;
    }

    public boolean joinEvent (String eventId, String studentId) throws IllegalArgumentException {

        try {
            PreparedStatement joinEvent = getConnection().prepareStatement
                    ("INSERT INTO student_has_event (events_idEvent, students_idStudent) VALUE (?, ?)");
            // OBS skal være en string men der er ikke ændret i model.Event endnu
            joinEvent.setString(1, eventId);
            joinEvent.setString(2, studentId);

            int rowsAffected = joinEvent.executeUpdate();

            //Måske skal det være ' == 1 ' i stedet for ' != 0 '
            if (rowsAffected != 1){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
