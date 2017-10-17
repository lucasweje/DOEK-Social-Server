package server.providers;

import server.models.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventTable {
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> allEvents = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            PreparedStatement getAllEventsStatement = DBmanager.getConnection().prepareStatement
                    ("SELECT * FROM events");

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
}
