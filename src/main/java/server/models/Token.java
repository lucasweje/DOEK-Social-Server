package server.models;

public class Token {

    private String token;
    private Student student;

    public void setStudent (Student student) {
        this.student = student;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
