package server.exceptions;

public class ResponseException extends Exception {

    public ResponseException(String message){
        super(message);
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public ResponseException setStatus(int status) {
        this.status = status;
        return this;
    }
}
