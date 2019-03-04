package wfl.pravin.wayforlife.models;

public class Comment {
    public String time;
    private String comment;
    private String userId;
    private String userName;

    public Comment() {
    }

    public Comment(String comment, String userId, String userName, String time) {
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
