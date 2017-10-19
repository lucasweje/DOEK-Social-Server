package server.models;

public class StudentHasEvent {

    private String Event_idEvent;
    private String Student_idStudent;

    public StudentHasEvent(String Student_idStudent, String Event_idEvent){
        this.Event_idEvent = Event_idEvent;
        this.Student_idStudent = Student_idStudent;
    }


    public String getStudent_idStudent() {
        return Student_idStudent;
    }

    public String getEvent_idEvent() {
        return Event_idEvent;
    }
}
