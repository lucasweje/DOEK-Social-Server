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

        try {
            PreparedStatement getAllEventsStatement = getConnection().prepareStatement
                    ("SELECT * FROM dsevent");

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getString("idEvent"),

                        resultSet.getInt("price"),
                        resultSet.getString("idStudent"),
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


    public ArrayList getAttendingStudents(String idEvent) throws IllegalAccessException {
        Student student = null;
        ResultSet resultSet = null;
        ArrayList attendingStudents = new ArrayList();

        //henter alle studenter der deltager på det valgte event.
        try {
            PreparedStatement getAttendingStudents = getConnection().prepareStatement
                    ("SELECT she.*, e.*, s.* " +
                            "FROM students_has_dsevent she " +
                            "INNER JOIN dsevent e " +
                            "ON she.dsevent_idEvent = e.idEvent " +
                            "INNER JOIN students s " +
                            "ON she.students_idStudent = s.idStudent " +
                            "WHERE e.idEvent = ?");

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

    public boolean joinEvent (String eventId, String studentId) throws IllegalArgumentException {


        try {
            //kalder metoden der tjekker om studenten allerede har tilmeldt sig det pågældende event


            //Statement der sætter studentens id og eventets id sammen i en tabel
            PreparedStatement joinEvent = getConnection().prepareStatement
                    ("INSERT INTO student_has_dsevent (dsevent_idEvent, students_idStudent) VALUE (?, ?)");

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
/*
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
    }*/
/*
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
            }*/


}

