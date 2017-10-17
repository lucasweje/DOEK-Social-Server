package server.models;

public class Comment {

    private int idComment;
    private String commentText;

    public Comment(int idComment, String commentText) {
        this.idComment = idComment;
        this.commentText = commentText;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
