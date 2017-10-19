package server.models;

public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String idStudent;
    private String salt;
    private long createdTime;

    public Student(){}

    //has all student info.
    public Student(String idStudent, String firstName, String lastName, String email, String password, long createdTime) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    //used in verifyStudentCreation
    public Student(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    //used in studentEndpoint
    public Student(String email, String salt, String password) {
        this.email = email;
        this.salt = salt;
        this.password = password;
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
