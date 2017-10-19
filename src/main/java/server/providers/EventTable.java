package server.providers;

import com.google.gson.Gson;
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
                    ("SELECT * FROM dsevents");

            resultSet = getAllEventsStatement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("idEvent"),
                        resultSet.getInt("price"),
                        resultSet.getInt("idStudent"),
                        resultSet.getString("eventName"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"));


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
                    ("INSERT INTO student_has_event (events_idEvent, students_idStudent1) VALUE (?, ?)");
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

    // Anvendes til at ændre et event. Modtager et idEvent og data om eventet. Dette opdates i DBmanager.
    // Skal der også anvendes et StudentID til, at genkende hvorvidt eventet tilhører den enkelte???

    public boolean updateEvent(Event event) throws Exception {

        PreparedStatement updateEventStatement = null;

        try {
            updateEventStatement = getConnection().prepareStatement
                    ("UPDATE dsevent " +
                            "SET eventName = ?, location = ?, price = ?, date = ?, description = ? " +
                            "WHERE idEvent = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            updateEventStatement.setString(1, event.getEventName());
            updateEventStatement.setString(2, event.getLocation());
            updateEventStatement.setInt(3, event.getPrice());
            updateEventStatement.setDate(4, event.getEventDate());
            updateEventStatement.setString(5, event.getDescription());
            updateEventStatement.setInt(6, event.getIdEvent());

            updateEventStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

}
