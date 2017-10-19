package server.models;
import server.providers.StudentTable;
import server.utility.Authenticator;

import server.utility.Authenticator;

public class Student {

    private String firstName, lastName, email, password, idStudent,salt;

    public Student(String idStudent, String firstName, String lastName, String email,String password, String salt) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public Student(String firstName, String lastName, String password, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    //Used for login - maybe
    public Student(String firstName, String password) {
        this.firstName = firstName;
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Student() {

    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
