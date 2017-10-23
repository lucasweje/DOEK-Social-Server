package server.models;

public class StudentHasEvent {

    private int eventId;
    private int studentId;

    public StudentHasEvent(int eventId, int studentId){
        this.eventId = eventId;
        this.studentId = studentId;
    }

    public int getStudent_idStudent() {
        return studentId;
    }

    public int getIdEvent() {
        return eventId;
    }
}
