package server.providers;

import server.models.Event;

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
}
