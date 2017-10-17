package server.models;

public class Post {

    private int idPost;
    private String postText;

    public Post(int idPost, String postText) {
        this.idPost = idPost;
        this.postText = postText;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
