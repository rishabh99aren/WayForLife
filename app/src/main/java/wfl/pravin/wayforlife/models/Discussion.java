package wfl.pravin.wayforlife.models;

import java.util.List;

public class Discussion {
    private String title;
    private String userId;
    private String userName;
    private List<Comment> comments;

    public Discussion() {
    }

    public Discussion(String title, String userId, String userName, List<Comment> comments) {
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", comments=" + comments +
                '}';
    }
}
