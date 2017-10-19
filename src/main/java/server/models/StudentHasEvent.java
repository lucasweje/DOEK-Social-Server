package server.models;

public class StudentHasEvent {

    private int Event_idEvent;
    private int Student_idStudent;

    public StudentHasEvent(int Student_idStudent, int Event_idEvent){
        this.Event_idEvent = Event_idEvent;
        this.Student_idStudent = Student_idStudent;
    }

    public int getStudent_idStudent() {
        return Student_idStudent;
    }

    public int getEvent_idEvent() {
        return Event_idEvent;
    }
}
