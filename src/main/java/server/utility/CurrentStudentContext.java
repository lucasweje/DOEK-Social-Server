package server.utility;

import org.apache.ibatis.jdbc.Null;
import server.models.Student;

public class CurrentStudentContext {

    private Student currentStudent;

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent){
        this.currentStudent = currentStudent;
    }
}
