package server.providers;

import com.sun.java.accessibility.util.EventID;
import server.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventTable extends DBmanager {

    Connection connection = null;

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> allEvents = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            PreparedStatement getAllEventsStatement = getConnection().prepareStatement
                    ("SELECT * FROM event");

            resultSet = getAllEventsStatement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getString("idEvent"),
                        resultSet.getInt("Price"),
                        resultSet.getString("idStudent"),
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

    public boolean createEvent (Event event) {

        try {
            PreparedStatement createEventStatement = connection.prepareStatement("INSERT INTO Events (idEvent, EventName, idStudent, Location, Price, Date, Description, Pictures) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            createEventStatement.setString(1, event.getIdEvent());
            createEventStatement.setString(2, event.getEventName());
            createEventStatement.setString(3, event.getStudentId());
            createEventStatement.setString(4, event.getLocation());
            createEventStatement.setInt(5, event.getPrice());
            createEventStatement.setTimestamp(6, event.getDate());
            createEventStatement.setString(7, event.getDescription());
            createEventStatement.setString(8, event.getPictures());

            createEventStatement.execute();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    return true;
    }

public boolean deleteEvent (Event event) {
    try {

            PreparedStatement deleteEventStatement = connection.prepareStatement("UPDATE Events SET Deleted = 1 WHERE EventId = ?");

                deleteEventStatement.setString(1, event);
                deleteEventStatement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();

            }
                return true;


            }


}

}
